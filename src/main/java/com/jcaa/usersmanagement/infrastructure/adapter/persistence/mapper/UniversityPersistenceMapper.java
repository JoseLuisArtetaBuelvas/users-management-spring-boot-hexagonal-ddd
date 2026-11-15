package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

import com.jcaa.usersmanagement.domain.enums.UniversityAccess;
import com.jcaa.usersmanagement.domain.enums.UniversityCategory;
import com.jcaa.usersmanagement.domain.model.UniversityModel;
import com.jcaa.usersmanagement.domain.valueobject.UniversityCampusesCount;
import com.jcaa.usersmanagement.domain.valueobject.UniversityCareersCount;
import com.jcaa.usersmanagement.domain.valueobject.UniversityCity;
import com.jcaa.usersmanagement.domain.valueobject.UniversityEmail;
import com.jcaa.usersmanagement.domain.valueobject.UniversityId;
import com.jcaa.usersmanagement.domain.valueobject.UniversityName;
import com.jcaa.usersmanagement.domain.valueobject.UniversityPhone;
import com.jcaa.usersmanagement.domain.valueobject.UniversityRector;
import com.jcaa.usersmanagement.domain.valueobject.UniversityWebsite;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.UniversityPersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.UniversityEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UniversityPersistenceMapper {

  public UniversityPersistenceDto fromModelToDto(final UniversityModel university) {
    return new UniversityPersistenceDto(
        university.getId().value(),
        university.getName().value(),
        university.getCategory().name(),
        university.getWebsite().value(),
        university.getRector().value(),
        university.getEmail().value(),
        university.getAccess().name(),
        university.getPhone().value(),
        university.getCity().value(),
        university.getCareersCount().value(),
        university.getCampusesCount().value(),
        null,
        null);
  }

  public UniversityEntity fromResultSetToEntity(final ResultSet resultSet) throws SQLException {
    return new UniversityEntity(
        resultSet.getString("id"),
        resultSet.getString("name"),
        resultSet.getString("category"),
        resultSet.getString("website"),
        resultSet.getString("rector"),
        resultSet.getString("email"),
        resultSet.getString("access"),
        resultSet.getString("phone"),
        resultSet.getString("city"),
        resultSet.getInt("careers_count"),
        resultSet.getInt("campuses_count"),
        resultSet.getString("created_at"),
        resultSet.getString("updated_at"));
  }

  public UniversityModel fromEntityToModel(final UniversityEntity entity) {
    return new UniversityModel(
        new UniversityId(entity.id()),
        new UniversityName(entity.name()),
        UniversityCategory.fromString(entity.category()),
        new UniversityWebsite(entity.website()),
        new UniversityRector(entity.rector()),
        new UniversityEmail(entity.email()),
        UniversityAccess.fromString(entity.access()),
        new UniversityPhone(entity.phone()),
        new UniversityCity(entity.city()),
        new UniversityCareersCount(entity.careersCount()),
        new UniversityCampusesCount(entity.campusesCount()));
  }

  public UniversityModel fromResultSetToModel(final ResultSet resultSet) throws SQLException {
    return fromEntityToModel(fromResultSetToEntity(resultSet));
  }

  public List<UniversityModel> fromResultSetToModelList(final ResultSet resultSet)
      throws SQLException {
    final List<UniversityModel> universities = new ArrayList<>();
    while (resultSet.next()) {
      universities.add(fromResultSetToModel(resultSet));
    }
    return universities;
  }
}
