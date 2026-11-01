package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.GetUniversityByNamePort;
import com.jcaa.usersmanagement.application.port.out.SaveUniversityPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateUniversityCommand;
import com.jcaa.usersmanagement.domain.enums.UniversityAccess;
import com.jcaa.usersmanagement.domain.enums.UniversityCategory;
import com.jcaa.usersmanagement.domain.exception.UniversityAlreadyExistsException;
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
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("CreateUniversityService")
@ExtendWith(MockitoExtension.class)
class CreateUniversityServiceTest {

  private static final String ID = "00000000-0000-0000-0000-000000000101";
  private static final String NAME = "Universidad de Cartagena";
  private static final String EMAIL = "rectoria@unicartagena.edu.co";

  @Mock private SaveUniversityPort saveUniversityPort;
  @Mock private GetUniversityByNamePort getUniversityByNamePort;

  private CreateUniversityService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service =
          new CreateUniversityService(
              saveUniversityPort, getUniversityByNamePort, validatorFactory.getValidator());
    }
  }

  @Test
  @DisplayName("execute() guarda la universidad cuando el nombre no existe")
  void shouldSaveUniversityWhenNameIsNew() {
    final CreateUniversityCommand command = validCommand();
    final UniversityModel saved = university();

    when(getUniversityByNamePort.getByName(any())).thenReturn(Optional.empty());
    when(saveUniversityPort.save(any())).thenReturn(saved);

    final UniversityModel result = service.execute(command);

    assertAll(
        "create university",
        () -> assertSame(saved, result, "must return the saved university"),
        () -> assertEquals(ID, result.getId().value(), "id"));
    verify(saveUniversityPort).save(any(UniversityModel.class));
  }

  @Test
  @DisplayName("execute() lanza UniversityAlreadyExistsException cuando el nombre existe")
  void shouldThrowWhenNameAlreadyExists() {
    final CreateUniversityCommand command = validCommand();

    when(getUniversityByNamePort.getByName(any())).thenReturn(Optional.of(university()));

    assertThrows(UniversityAlreadyExistsException.class, () -> service.execute(command));
    verify(saveUniversityPort, never()).save(any());
  }

  @Test
  @DisplayName("execute() lanza ConstraintViolationException cuando el command es inválido")
  void shouldThrowWhenCommandIsInvalid() {
    final CreateUniversityCommand command =
        new CreateUniversityCommand(
            "", "", "", "", "not-an-email", "", "123", "", -1, -1);

    assertThrows(ConstraintViolationException.class, () -> service.execute(command));
    verifyNoInteractions(saveUniversityPort, getUniversityByNamePort);
  }

  private CreateUniversityCommand validCommand() {
    return new CreateUniversityCommand(
        NAME,
        "PUBLICA",
        "https://unicartagena.edu.co",
        "William Malkun",
        EMAIL,
        "ICFES",
        "6056699800",
        "Cartagena",
        40,
        4);
  }

  private UniversityModel university() {
    return new UniversityModel(
        new UniversityId(ID),
        new UniversityName(NAME),
        UniversityCategory.PUBLICA,
        new UniversityWebsite("https://unicartagena.edu.co"),
        new UniversityRector("William Malkun"),
        new UniversityEmail(EMAIL),
        UniversityAccess.ICFES,
        new UniversityPhone("6056699800"),
        new UniversityCity("Cartagena"),
        new UniversityCareersCount(40),
        new UniversityCampusesCount(4));
  }
}
