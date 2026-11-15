package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.GetUniversityByIdPort;
import com.jcaa.usersmanagement.application.port.out.GetUniversityByNamePort;
import com.jcaa.usersmanagement.application.port.out.UpdateUniversityPort;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateUniversityCommand;
import com.jcaa.usersmanagement.domain.enums.UniversityAccess;
import com.jcaa.usersmanagement.domain.enums.UniversityCategory;
import com.jcaa.usersmanagement.domain.exception.UniversityAlreadyExistsException;
import com.jcaa.usersmanagement.domain.exception.UniversityNotFoundException;
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

@DisplayName("UpdateUniversityService")
@ExtendWith(MockitoExtension.class)
class UpdateUniversityServiceTest {

  private static final String ID = "00000000-0000-0000-0000-000000000101";
  private static final String OTHER_ID = "00000000-0000-0000-0000-000000000102";
  private static final String NAME = "Universidad de Cartagena";
  private static final String EMAIL = "rectoria@unicartagena.edu.co";

  @Mock private UpdateUniversityPort updateUniversityPort;
  @Mock private GetUniversityByIdPort getUniversityByIdPort;
  @Mock private GetUniversityByNamePort getUniversityByNamePort;

  private UpdateUniversityService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service =
          new UpdateUniversityService(
              updateUniversityPort,
              getUniversityByIdPort,
              getUniversityByNamePort,
              validatorFactory.getValidator());
    }
  }

  @Test
  @DisplayName("execute() actualiza cuando la universidad existe y el nombre está libre")
  void shouldUpdateWhenUniversityExistsAndNameIsFree() {
    final UpdateUniversityCommand command = validCommand(ID);
    final UniversityModel updated = university(ID);

    when(getUniversityByIdPort.getById(any())).thenReturn(Optional.of(university(ID)));
    when(getUniversityByNamePort.getByName(any())).thenReturn(Optional.empty());
    when(updateUniversityPort.update(any())).thenReturn(updated);

    final UniversityModel result = service.execute(command);

    assertSame(updated, result);
    verify(updateUniversityPort).update(any(UniversityModel.class));
  }

  @Test
  @DisplayName("execute() permite conservar el mismo nombre de la universidad")
  void shouldAllowSameName() {
    final UpdateUniversityCommand command = validCommand(ID);
    final UniversityModel updated = university(ID);

    when(getUniversityByIdPort.getById(any())).thenReturn(Optional.of(university(ID)));
    when(getUniversityByNamePort.getByName(any())).thenReturn(Optional.of(university(ID)));
    when(updateUniversityPort.update(any())).thenReturn(updated);

    final UniversityModel result = service.execute(command);

    assertSame(updated, result);
    verify(updateUniversityPort).update(any(UniversityModel.class));
  }

  @Test
  @DisplayName("execute() lanza UniversityNotFoundException cuando el id no existe")
  void shouldThrowWhenUniversityDoesNotExist() {
    when(getUniversityByIdPort.getById(any())).thenReturn(Optional.empty());

    assertThrows(
        UniversityNotFoundException.class, () -> service.execute(validCommand(ID)));
    verify(updateUniversityPort, never()).update(any());
  }

  @Test
  @DisplayName("execute() lanza UniversityAlreadyExistsException cuando el nombre pertenece a otra")
  void shouldThrowWhenNameBelongsToAnotherUniversity() {
    when(getUniversityByIdPort.getById(any())).thenReturn(Optional.of(university(ID)));
    when(getUniversityByNamePort.getByName(any())).thenReturn(Optional.of(university(OTHER_ID)));

    assertThrows(
        UniversityAlreadyExistsException.class, () -> service.execute(validCommand(ID)));
    verify(updateUniversityPort, never()).update(any());
  }

  @Test
  @DisplayName("execute() lanza ConstraintViolationException cuando el command es inválido")
  void shouldThrowWhenCommandIsInvalid() {
    assertThrows(
        ConstraintViolationException.class, () -> service.execute(validCommand("")));
    verifyNoInteractions(updateUniversityPort, getUniversityByIdPort, getUniversityByNamePort);
  }

  private UpdateUniversityCommand validCommand(final String id) {
    return new UpdateUniversityCommand(
        id,
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

  private UniversityModel university(final String id) {
    return new UniversityModel(
        new UniversityId(id),
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
