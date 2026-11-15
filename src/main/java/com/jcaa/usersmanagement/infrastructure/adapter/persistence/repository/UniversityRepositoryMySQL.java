package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import com.jcaa.usersmanagement.application.port.out.DeleteUniversityPort;
import com.jcaa.usersmanagement.application.port.out.GetAllUniversitiesPort;
import com.jcaa.usersmanagement.application.port.out.GetUniversityByIdPort;
import com.jcaa.usersmanagement.application.port.out.GetUniversityByNamePort;
import com.jcaa.usersmanagement.application.port.out.SaveUniversityPort;
import com.jcaa.usersmanagement.application.port.out.UpdateUniversityPort;
import com.jcaa.usersmanagement.domain.exception.UniversityNotFoundException;
import com.jcaa.usersmanagement.domain.model.UniversityModel;
import com.jcaa.usersmanagement.domain.valueobject.UniversityId;
import com.jcaa.usersmanagement.domain.valueobject.UniversityName;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.UniversityPersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception.PersistenceException;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper.UniversityPersistenceMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UniversityRepositoryMySQL
    implements SaveUniversityPort,
        UpdateUniversityPort,
        GetUniversityByIdPort,
        GetUniversityByNamePort,
        GetAllUniversitiesPort,
        DeleteUniversityPort {

  private static final String SQL_INSERT =
      "INSERT INTO universities "
          + "(id, name, category, website, rector, email, access, phone, city, "
          + "careers_count, campuses_count, created_at, updated_at) "
          + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";

  private static final String SQL_UPDATE =
      "UPDATE universities SET name = ?, category = ?, website = ?, rector = ?, email = ?, "
          + "access = ?, phone = ?, city = ?, careers_count = ?, campuses_count = ?, "
          + "updated_at = NOW() "
          + "WHERE id = ?";

  private static final String SQL_SELECT_BY_ID =
      "SELECT id, name, category, website, rector, email, access, phone, city, "
          + "careers_count, campuses_count, created_at, updated_at "
          + "FROM universities "
          + "WHERE id = ? LIMIT 1";

  private static final String SQL_SELECT_BY_NAME =
      "SELECT id, name, category, website, rector, email, access, phone, city, "
          + "careers_count, campuses_count, created_at, updated_at "
          + "FROM universities "
          + "WHERE name = ? LIMIT 1";

  private static final String SQL_SELECT_ALL =
      "SELECT id, name, category, website, rector, email, access, phone, city, "
          + "careers_count, campuses_count, created_at, updated_at "
          + "FROM universities "
          + "ORDER BY name ASC";

  private static final String SQL_DELETE = "DELETE FROM universities WHERE id = ?";

  private final DataSource dataSource;

  @Override
  public UniversityModel save(final UniversityModel university) {
    final UniversityPersistenceDto dto = UniversityPersistenceMapper.fromModelToDto(university);
    executeSave(dto);
    return findByIdOrFail(university.getId());
  }

  @Override
  public UniversityModel update(final UniversityModel university) {
    final UniversityPersistenceDto dto = UniversityPersistenceMapper.fromModelToDto(university);
    executeUpdate(dto);
    return findByIdOrFail(university.getId());
  }

  @Override
  public Optional<UniversityModel> getById(final UniversityId universityId) {
    try (final Connection connection = dataSource.getConnection();
        final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
      statement.setString(1, universityId.value());
      final ResultSet resultSet = statement.executeQuery();
      if (!resultSet.next()) {
        return Optional.empty();
      }
      return Optional.of(UniversityPersistenceMapper.fromResultSetToModel(resultSet));
    } catch (final SQLException exception) {
      throw PersistenceException.becauseUniversityFindByIdFailed(
          universityId.value(), exception);
    }
  }

  @Override
  public Optional<UniversityModel> getByName(final UniversityName name) {
    try (final Connection connection = dataSource.getConnection();
        final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_NAME)) {
      statement.setString(1, name.value());
      final ResultSet resultSet = statement.executeQuery();
      if (!resultSet.next()) {
        return Optional.empty();
      }
      return Optional.of(UniversityPersistenceMapper.fromResultSetToModel(resultSet));
    } catch (final SQLException exception) {
      throw PersistenceException.becauseUniversityFindByNameFailed(name.value(), exception);
    }
  }

  @Override
  public List<UniversityModel> getAll() {
    try (final Connection connection = dataSource.getConnection();
        final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL)) {
      final ResultSet resultSet = statement.executeQuery();
      return UniversityPersistenceMapper.fromResultSetToModelList(resultSet);
    } catch (final SQLException exception) {
      throw PersistenceException.becauseUniversityFindAllFailed(exception);
    }
  }

  @Override
  public void delete(final UniversityId universityId) {
    try (final Connection connection = dataSource.getConnection();
        final PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
      statement.setString(1, universityId.value());
      statement.executeUpdate();
    } catch (final SQLException exception) {
      throw PersistenceException.becauseUniversityDeleteFailed(universityId.value(), exception);
    }
  }

  private void executeSave(final UniversityPersistenceDto dto) {
    try (final Connection connection = dataSource.getConnection();
        final PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {
      statement.setString(1, dto.id());
      bindCommonFields(statement, dto, 2);
      statement.executeUpdate();
    } catch (final SQLException exception) {
      throw PersistenceException.becauseUniversitySaveFailed(dto.id(), exception);
    }
  }

  private void executeUpdate(final UniversityPersistenceDto dto) {
    try (final Connection connection = dataSource.getConnection();
        final PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
      bindCommonFields(statement, dto, 1);
      statement.setString(11, dto.id());
      statement.executeUpdate();
    } catch (final SQLException exception) {
      throw PersistenceException.becauseUniversityUpdateFailed(dto.id(), exception);
    }
  }

  private void bindCommonFields(
      final PreparedStatement statement, final UniversityPersistenceDto dto, final int startIndex)
      throws SQLException {
    statement.setString(startIndex, dto.name());
    statement.setString(startIndex + 1, dto.category());
    statement.setString(startIndex + 2, dto.website());
    statement.setString(startIndex + 3, dto.rector());
    statement.setString(startIndex + 4, dto.email());
    statement.setString(startIndex + 5, dto.access());
    statement.setString(startIndex + 6, dto.phone());
    statement.setString(startIndex + 7, dto.city());
    statement.setInt(startIndex + 8, dto.careersCount());
    statement.setInt(startIndex + 9, dto.campusesCount());
  }

  private UniversityModel findByIdOrFail(final UniversityId universityId) {
    return getById(universityId)
        .orElseThrow(
            () -> UniversityNotFoundException.becauseIdWasNotFound(universityId.value()));
  }
}
