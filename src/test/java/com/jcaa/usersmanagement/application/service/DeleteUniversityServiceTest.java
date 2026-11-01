package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.DeleteUniversityPort;
import com.jcaa.usersmanagement.application.port.out.GetUniversityByIdPort;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteUniversityCommand;
import com.jcaa.usersmanagement.domain.enums.UniversityAccess;
import com.jcaa.usersmanagement.domain.enums.UniversityCategory;
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

@DisplayName("DeleteUniversityService")
@ExtendWith(MockitoExtension.class)
class DeleteUniversityServiceTest {

  private static final String ID = "00000000-0000-0000-0000-000000000101";

  @Mock private DeleteUniversityPort deleteUniversityPort;
  @Mock private GetUniversityByIdPort getUniversityByIdPort;

  private DeleteUniversityService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service =
          new DeleteUniversityService(
              deleteUniversityPort, getUniversityByIdPort, validatorFactory.getValidator());
    }
  }

  @Test
  @DisplayName("execute() elimina cuando la universidad existe")
  void shouldDeleteWhenUniversityExists() {
    when(getUniversityByIdPort.getById(any())).thenReturn(Optional.of(university()));

    service.execute(new DeleteUniversityCommand(ID));

    verify(deleteUniversityPort).delete(new UniversityId(ID));
  }

  @Test
  @DisplayName("execute() lanza UniversityNotFoundException cuando el id no existe")
  void shouldThrowWhenUniversityDoesNotExist() {
    when(getUniversityByIdPort.getById(any())).thenReturn(Optional.empty());

    assertThrows(
        UniversityNotFoundException.class,
        () -> service.execute(new DeleteUniversityCommand(ID)));
    verify(deleteUniversityPort, never()).delete(any());
  }

  @Test
  @DisplayName("execute() lanza ConstraintViolationException cuando el id está en blanco")
  void shouldThrowWhenCommandIsInvalid() {
    assertThrows(
        ConstraintViolationException.class,
        () -> service.execute(new DeleteUniversityCommand("")));
    verifyNoInteractions(deleteUniversityPort, getUniversityByIdPort);
  }

  private UniversityModel university() {
    return new UniversityModel(
        new UniversityId(ID),
        new UniversityName("Universidad de Cartagena"),
        UniversityCategory.PUBLICA,
        new UniversityWebsite("https://unicartagena.edu.co"),
        new UniversityRector("William Malkun"),
        new UniversityEmail("rectoria@unicartagena.edu.co"),
        UniversityAccess.ICFES,
        new UniversityPhone("6056699800"),
        new UniversityCity("Cartagena"),
        new UniversityCareersCount(40),
        new UniversityCampusesCount(4));
  }
}
