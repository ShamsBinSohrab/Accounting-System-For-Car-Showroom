package com.migrationservice.utils;

import com.migrationservice.entity.Company;
import com.migrationservice.repository.CompanyRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.annotation.PostConstruct;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TenantMigrationsUtils {

  private final CompanyRepository companyRepository;

  @Value("${spring.datasource.url}")
  private String url;

  @Value("${spring.datasource.username}")
  private String user;

  @Value("${spring.datasource.password}")
  private String password;

  @PostConstruct
  private void initialize() {
    companyRepository
        .findAll()
        .forEach(
            company -> {
              try {
                final HikariConfig masterConfig =
                    getDbConfig(url, user, password);
                final HikariDataSource masterDataSource = new HikariDataSource(masterConfig);
                final PreparedStatement schemaExists =
                    masterDataSource.getConnection()
                        .prepareStatement(
                            "SELECT schema_name FROM information_schema.schemata "
                                + "WHERE schema_name = ?");
                schemaExists.setString(1, company.getUuid().toString());
                if (schemaExists.executeQuery().next()) {
                  schemaExists.close();
                  masterDataSource.close();
                  runTenantMigration(company.getUuid().toString());
                }
              } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
              }
            });
  }

  public String createNewCompany(Company company) throws SQLException, LiquibaseException {
    final String schema = company.getUuid().toString();
    createSingleTenant(schema);
    runTenantMigration(schema);
    return schema;
  }

  private void runTenantMigration(String schema) throws LiquibaseException, SQLException {
    log.info("Configuring datasource {} {}", schema, user);
    final HikariConfig config = getDbConfig(url, user, password);
    config.addDataSourceProperty("currentSchema", schema);
    HikariDataSource dataSource = new HikariDataSource(config);
    Database database = DatabaseFactory.getInstance()
        .findCorrectDatabaseImplementation(new JdbcConnection(dataSource.getConnection()));
    Liquibase liquibase = new Liquibase("db/changelog/db.changelog-tenant.yaml",
        new ClassLoaderResourceAccessor(),
        database);
    liquibase.update("production");
    database.close();
  }

  public void createSingleTenant(String schema) throws DatabaseException, SQLException {
    final HikariConfig config = getDbConfig(url, user, password);
    final HikariDataSource dataSource = new HikariDataSource(config);
    final Database database = DatabaseFactory.getInstance()
        .findCorrectDatabaseImplementation(new JdbcConnection(dataSource.getConnection()));
    final JdbcConnection connection = (JdbcConnection) database.getConnection();
    final String createSchemaQuery = "CREATE SCHEMA \"" + schema + "\"";
    final PreparedStatement createSchema = connection.prepareStatement(createSchemaQuery);
    createSchema.executeUpdate();
    connection.commit();
    createSchema.close();
    connection.close();
    dataSource.close();
  }

  private HikariConfig getDbConfig(
      String url,
      String user,
      String password) {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(url);
    config.setUsername(user);
    config.setPassword(password);
    config.setMinimumIdle(2);
    config.setIdleTimeout(120000);
    config.setMaximumPoolSize(5);
    return config;
  }
}
