package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("UniversityPersistenceMapper")
@ExtendWith(MockitoExtension.class)
class UniversityPersistenceMapperTest {

  private static final String ID = "00000000-0000-0000-0000-000000000101";
  private static final String NAME = "Universidad de Cartagena";
  private static final String CATEGORY = "PUBLICA";
  private static final String WEBSITE = "https://unicartagena.edu.co";
  private static final String RECTOR = "William Malkun";
  private static final String EMAIL = "rectoria@unicartagena.edu.co";
  private static final String ACCESS = "ICFES";
  private static final String PHONE = "6056699800";
  private static final String CITY = "Cartagena";

  @Mock private ResultSet resultSet;

  @Test
  @DisplayName("fromModelToDto() mapea todos los campos")
  void shouldMapModelToDto() {
    final UniversityPersistenceDto dto =
        UniversityPersistenceMapper.fromModelToDto(university());

    assertAll(
        "model to dto",
        () -> assertEquals(ID, dto.id(), "id"),
        () -> assertEquals(NAME, dto.name(), "name"),
        () -> assertEquals(CATEGORY, dto.category(), "category"),
        () -> assertEquals(WEBSITE, dto.website(), "website"),
        () -> assertEquals(RECTOR, dto.rector(), "rector"),
        () -> assertEquals(EMAIL, dto.email(), "email"),
        () -> assertEquals(ACCESS, dto.access(), "access"),
        () -> assertEquals(PHONE, dto.phone(), "phone"),
        () -> assertEquals(CITY, dto.city(), "city"),
        () -> assertEquals(40, dto.careersCount(), "careersCount"),
        () -> assertEquals(4, dto.campusesCount(), "campusesCount"),
        () -> assertNull(dto.createdAt(), "createdAt"),
        () -> assertNull(dto.updatedAt(), "updatedAt"));
  }

  @Test
  @DisplayName("fromEntityToModel() mapea todos los campos")
  void shouldMapEntityToModel() {
    final UniversityModel model =
        UniversityPersistenceMapper.fromEntityToModel(entity());

    assertAll(
        "entity to model",
        () -> assertEquals(ID, model.getId().value(), "id"),
        () -> assertEquals(NAME, model.getName().value(), "name"),
        () -> assertEquals(UniversityCategory.PUBLICA, model.getCategory(), "category"),
        () -> assertEquals(WEBSITE, model.getWebsite().value(), "website"),
        () -> assertEquals(RECTOR, model.getRector().value(), "rector"),
        () -> assertEquals(EMAIL, model.getEmail().value(), "email"),
        () -> assertEquals(UniversityAccess.ICFES, model.getAccess(), "access"),
        () -> assertEquals(PHONE, model.getPhone().value(), "phone"),
        () -> assertEquals(CITY, model.getCity().value(), "city"),
        () -> assertEquals(40, model.getCareersCount().value(), "careersCount"),
        () -> assertEquals(4, model.getCampusesCount().value(), "campusesCount"));
  }

  @Test
  @DisplayName("fromResultSetToEntity() lee todas las columnas")
  void shouldMapResultSetToEntity() throws SQLException {
    when(resultSet.getString("id")).thenReturn(ID);
    when(resultSet.getString("name")).thenReturn(NAME);
    when(resultSet.getString("category")).thenReturn(CATEGORY);
    when(resultSet.getString("website")).thenReturn(WEBSITE);
    when(resultSet.getString("rector")).thenReturn(RECTOR);
    when(resultSet.getString("email")).thenReturn(EMAIL);
    when(resultSet.getString("access")).thenReturn(ACCESS);
    when(resultSet.getString("phone")).thenReturn(PHONE);
    when(resultSet.getString("city")).thenReturn(CITY);
    when(resultSet.getInt("careers_count")).thenReturn(40);
    when(resultSet.getInt("campuses_count")).thenReturn(4);
    when(resultSet.getString("created_at")).thenReturn("2026-01-01 00:00:00");
    when(resultSet.getString("updated_at")).thenReturn("2026-01-02 00:00:00");

    final UniversityEntity entity = UniversityPersistenceMapper.fromResultSetToEntity(resultSet);

    assertAll(
        "result set to entity",
        () -> assertEquals(ID, entity.id(), "id"),
        () -> assertEquals(NAME, entity.name(), "name"),
        () -> assertEquals(CATEGORY, entity.category(), "category"),
        () -> assertEquals(ACCESS, entity.access(), "access"),
        () -> assertEquals(40, entity.careersCount(), "careersCount"),
        () -> assertEquals(4, entity.campusesCount(), "campusesCount"),
        () -> assertEquals("2026-01-01 00:00:00", entity.createdAt(), "createdAt"));
  }

  private UniversityModel university() {
    return new UniversityModel(
        new UniversityId(ID),
        new UniversityName(NAME),
        UniversityCategory.PUBLICA,
        new UniversityWebsite(WEBSITE),
        new UniversityRector(RECTOR),
        new UniversityEmail(EMAIL),
        UniversityAccess.ICFES,
        new UniversityPhone(PHONE),
        new UniversityCity(CITY),
        new UniversityCareersCount(40),
        new UniversityCampusesCount(4));
  }

  private UniversityEntity entity() {
    return new UniversityEntity(
        ID,
        NAME,
        CATEGORY,
        WEBSITE,
        RECTOR,
        EMAIL,
        ACCESS,
        PHONE,
        CITY,
        40,
        4,
        "2026-01-01 00:00:00",
        "2026-01-02 00:00:00");
  }
}
