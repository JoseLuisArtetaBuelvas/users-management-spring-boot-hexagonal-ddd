package com.jcaa.usersmanagement.application.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.application.service.dto.command.CreateUniversityCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteUniversityCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateUniversityCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetUniversityByIdQuery;
import com.jcaa.usersmanagement.domain.enums.UniversityAccess;
import com.jcaa.usersmanagement.domain.enums.UniversityCategory;
import com.jcaa.usersmanagement.domain.model.UniversityModel;
import com.jcaa.usersmanagement.domain.valueobject.UniversityId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UniversityApplicationMapper")
class UniversityApplicationMapperTest {

  private static final String ID = "00000000-0000-0000-0000-000000000101";
  private static final String NAME = "Universidad de Cartagena";
  private static final String CATEGORY = "PUBLICA";
  private static final String WEBSITE = "https://unicartagena.edu.co";
  private static final String RECTOR = "William Malkun";
  private static final String EMAIL = "rectoria@unicartagena.edu.co";
  private static final String ACCESS = "ICFES";
  private static final String PHONE = "6056699800";
  private static final String CITY = "Cartagena";

  @Test
  @DisplayName("fromCreateCommandToModel() mapea todos los campos y genera un id")
  void shouldMapCreateCommandToModel() {
    final CreateUniversityCommand command =
        new CreateUniversityCommand(
            NAME, CATEGORY, WEBSITE, RECTOR, EMAIL, ACCESS, PHONE, CITY, 40, 4);

    final UniversityModel result = UniversityApplicationMapper.fromCreateCommandToModel(command);

    assertAll(
        "fromCreateCommandToModel()",
        () -> assertNotNull(result.getId(), "id must not be null"),
        () -> assertFalse(result.getId().value().isBlank(), "id must not be blank"),
        () -> assertEquals(NAME, result.getName().value(), "name"),
        () -> assertEquals(UniversityCategory.PUBLICA, result.getCategory(), "category"),
        () -> assertEquals(WEBSITE, result.getWebsite().value(), "website"),
        () -> assertEquals(RECTOR, result.getRector().value(), "rector"),
        () -> assertEquals(EMAIL, result.getEmail().value(), "email"),
        () -> assertEquals(UniversityAccess.ICFES, result.getAccess(), "access"),
        () -> assertEquals(PHONE, result.getPhone().value(), "phone"),
        () -> assertEquals(CITY, result.getCity().value(), "city"),
        () -> assertEquals(40, result.getCareersCount().value(), "careersCount"),
        () -> assertEquals(4, result.getCampusesCount().value(), "campusesCount"));
  }

  @Test
  @DisplayName("fromUpdateCommandToModel() mapea todos los campos usando el id del command")
  void shouldMapUpdateCommandToModel() {
    final UpdateUniversityCommand command =
        new UpdateUniversityCommand(
            ID, NAME, CATEGORY, WEBSITE, RECTOR, EMAIL, ACCESS, PHONE, CITY, 40, 4);

    final UniversityModel result = UniversityApplicationMapper.fromUpdateCommandToModel(command);

    assertAll(
        "fromUpdateCommandToModel()",
        () -> assertEquals(ID, result.getId().value(), "id"),
        () -> assertEquals(NAME, result.getName().value(), "name"),
        () -> assertEquals(UniversityCategory.PUBLICA, result.getCategory(), "category"),
        () -> assertEquals(UniversityAccess.ICFES, result.getAccess(), "access"));
  }

  @Test
  @DisplayName("fromGetUniversityByIdQueryToUniversityId() extrae el id del query")
  void shouldExtractIdFromQuery() {
    final UniversityId result =
        UniversityApplicationMapper.fromGetUniversityByIdQueryToUniversityId(
            new GetUniversityByIdQuery(ID));

    assertEquals(ID, result.value());
  }

  @Test
  @DisplayName("fromDeleteCommandToUniversityId() extrae el id del command")
  void shouldExtractIdFromDeleteCommand() {
    final UniversityId result =
        UniversityApplicationMapper.fromDeleteCommandToUniversityId(
            new DeleteUniversityCommand(ID));

    assertEquals(ID, result.value());
  }
}
