package com.jcaa.usersmanagement.infrastructure.adapter.persistence.config;

import java.util.Locale;
import java.util.Objects;

/**
 * Datos de conexión a la base de datos. El motor se elige con {@code driver}:
 *
 * <ul>
 *   <li>{@code mysql}: MySQL / MariaDB (desarrollo local).
 *   <li>{@code postgresql}: PostgreSQL (Supabase en producción). {@code sslMode} permite exigir
 *       conexión cifrada ({@code require}).
 * </ul>
 */
public record DatabaseConfig(
    String driver,
    String host,
    int port,
    String databaseName,
    String username,
    String password,
    String sslMode) {

  public static final String DRIVER_MYSQL = "mysql";
  public static final String DRIVER_POSTGRESQL = "postgresql";

  private static final String MYSQL_URL_TEMPLATE =
      "jdbc:mysql://%s:%d/%s?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
  private static final String POSTGRESQL_URL_TEMPLATE = "jdbc:postgresql://%s:%d/%s";
  private static final String POSTGRESQL_SSL_PARAMETER = "?sslmode=";
  private static final String UNSUPPORTED_DRIVER_MESSAGE =
      "Unsupported database driver '%s'. Use 'mysql' or 'postgresql'.";
  private static final String EMPTY = "";

  public DatabaseConfig {
    Objects.requireNonNull(driver, "Database driver cannot be null");
    driver = driver.trim().toLowerCase(Locale.ROOT);
    if (!DRIVER_MYSQL.equals(driver) && !DRIVER_POSTGRESQL.equals(driver)) {
      throw new IllegalArgumentException(String.format(UNSUPPORTED_DRIVER_MESSAGE, driver));
    }
    sslMode = Objects.isNull(sslMode) ? EMPTY : sslMode.trim();
  }

  /** Conserva el constructor original (MySQL sin SSL) para no romper usos existentes. */
  public DatabaseConfig(
      final String host,
      final int port,
      final String databaseName,
      final String username,
      final String password) {
    this(DRIVER_MYSQL, host, port, databaseName, username, password, EMPTY);
  }

  public String buildJdbcUrl() {
    if (DRIVER_POSTGRESQL.equals(driver)) {
      final String url = String.format(POSTGRESQL_URL_TEMPLATE, host, port, databaseName);
      return sslMode.isEmpty() ? url : url + POSTGRESQL_SSL_PARAMETER + sslMode;
    }
    return String.format(MYSQL_URL_TEMPLATE, host, port, databaseName);
  }
}
