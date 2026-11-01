package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.CreateUniversityUseCase;
import com.jcaa.usersmanagement.application.port.out.GetUniversityByNamePort;
import com.jcaa.usersmanagement.application.port.out.SaveUniversityPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateUniversityCommand;
import com.jcaa.usersmanagement.application.service.mapper.UniversityApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.UniversityAlreadyExistsException;
import com.jcaa.usersmanagement.domain.model.UniversityModel;
import com.jcaa.usersmanagement.domain.valueobject.UniversityName;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUniversityService implements CreateUniversityUseCase {

  private final SaveUniversityPort saveUniversityPort;
  private final GetUniversityByNamePort getUniversityByNamePort;
  private final Validator validator;

  @Override
  public UniversityModel execute(final CreateUniversityCommand command) {
    validateCommand(command);

    final UniversityName name = new UniversityName(command.name());
    ensureNameIsNotTaken(name);

    final UniversityModel universityToSave =
        UniversityApplicationMapper.fromCreateCommandToModel(command);

    return saveUniversityPort.save(universityToSave);
  }

  private void validateCommand(final CreateUniversityCommand command) {
    final Set<ConstraintViolation<CreateUniversityCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  private void ensureNameIsNotTaken(final UniversityName name) {
    getUniversityByNamePort
        .getByName(name)
        .ifPresent(
            ignored -> {
              throw UniversityAlreadyExistsException.becauseNameAlreadyExists(name.value());
            });
  }
}
