package com.migrationservice.utils;

import com.migrationservice.repository.CompanyRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.PreparedStatement;
import javax.annotation.PostConstruct;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
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

  @Value("${spring.datasource.dataSourceClassName:org.postgresql.ds.PGSimpleDataSource}")
  private String dataSourceClassName;

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
                    getDbConfig(dataSourceClassName, url, user, password);
                final HikariDataSource masterDataSource = new HikariDataSource(masterConfig);
                final PreparedStatement schemaExists =
                    masterDataSource.getConnection()
                        .prepareStatement(
                            "SELECT schema_name FROM information_schema.schemata WHERE schema_name = ?");
                schemaExists.setString(1, company.getUuid().toString());
                if (schemaExists.executeQuery().next()) {
                  schemaExists.close();
                  masterDataSource.close();
                  try {
                    final String schemaName = url + "?currentSchema=" + company.getUuid();
                    log.info("Configuring datasource {} {} {}", dataSourceClassName, schemaName, user);
                    final HikariConfig config = getDbConfig(dataSourceClassName, url, user, password);
                    config.addDataSourceProperty("currentSchema", company.getUuid());
                    HikariDataSource dataSource = new HikariDataSource(config);
                    try {
                      Database database = DatabaseFactory.getInstance()
                          .findCorrectDatabaseImplementation(new JdbcConnection(dataSource.getConnection()));
                      Liquibase liquibase = new Liquibase("db/changelog/changelog.yaml", new ClassLoaderResourceAccessor(),
                          database);
                      liquibase.update("test, production");
                      database.close();
                    } catch (Exception ex) {
                      ex.printStackTrace();
                    }
                  } catch (Exception ex) {
                    ex.printStackTrace();
                  }
                }
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            });
  }

  private HikariConfig getDbConfig(
      String dataSourceClassName,
      String url,
      String user,
      String password) {
    url = url.replace("jdbc:postgresql://", "");
    HikariConfig config = new HikariConfig();
    config.setDataSourceClassName(dataSourceClassName);
    config.addDataSourceProperty("serverName", url);
    config.addDataSourceProperty("user", user);
    config.addDataSourceProperty("password", password);
    config.setMinimumIdle(2);
    config.setIdleTimeout(120000);
    config.setMaximumPoolSize(5);
    return config;
  }
}
