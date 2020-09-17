package com.apiservice.multitenancy;

import com.apiservice.utils.Constants;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MultiTenantConnectionProvider implements
    org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider {

  private final DataSource dataSource;
  private final Constants constants;

  @Override
  public Connection getAnyConnection() throws SQLException {
    return dataSource.getConnection();
  }

  @Override
  public void releaseAnyConnection(Connection connection) throws SQLException {
    connection.close();
  }

  @Override
  public Connection getConnection(String tenantIdentifier) throws SQLException {
    final Connection connection = getAnyConnection();
    connection.setSchema(tenantIdentifier);
    return connection;
  }

  @Override
  public void releaseConnection(String tenantIdentifier, Connection connection)
      throws SQLException {
    connection.setSchema(constants.defaultCompanyIdentifier);
    releaseAnyConnection(connection);
  }

  @Override
  public boolean supportsAggressiveRelease() {
    return false;
  }

  @Override
  @SuppressWarnings("rawtypes")
  public boolean isUnwrappableAs(Class clazz) {
    return false;
  }

  @Override
  public <T> T unwrap(Class<T> clazz) {
    return null;
  }
}
