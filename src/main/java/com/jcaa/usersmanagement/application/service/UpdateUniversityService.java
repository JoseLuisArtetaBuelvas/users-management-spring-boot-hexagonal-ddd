package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.UpdateUniversityUseCase;
import com.jcaa.usersmanagement.application.port.out.GetUniversityByIdPort;
import com.jcaa.usersmanagement.application.port.out.GetUniversityByNamePort;
import com.jcaa.usersmanagement.application.port.out.UpdateUniversityPort;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateUniversityCommand;
import com.jcaa.usersmanagement.application.service.mapper.UniversityApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.UniversityAlreadyExistsException;
import com.jcaa.usersmanagement.domain.exception.UniversityNotFoundException;
import com.jcaa.usersmanagement.domain.model.UniversityModel;
import com.jcaa.usersmanagement.domain.valueobject.UniversityId;
import com.jcaa.usersmanagement.domain.valueobject.UniversityName;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUniversityService implements UpdateUniversityUseCase {

  private final UpdateUniversityPort updateUniversityPort;
  private final GetUniversityByIdPort getUniversityByIdPort;
  private final GetUniversityByNamePort getUniversityByNamePort;
  private final Validator validator;

  @Override
  public UniversityModel execute(final UpdateUniversityCommand command) {
    validateCommand(command);

    final UniversityId universityId = new UniversityId(command.id());
    ensureUniversityExists(universityId);

    final UniversityName newName = new UniversityName(command.name());
    ensureNameIsNotTakenByAnotherUniversity(newName, universityId);

    final UniversityModel universityToUpdate =
        UniversityApplicationMapper.fromUpdateCommandToModel(command);

    return updateUniversityPort.update(universityToUpdate);
  }

  private void validateCommand(final UpdateUniversityCommand command) {
    final Set<ConstraintViolation<UpdateUniversityCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  private void ensureUniversityExists(final UniversityId universityId) {
    getUniversityByIdPort
        .getById(universityId)
        .orElseThrow(
            () -> UniversityNotFoundException.becauseIdWasNotFound(universityId.value()));
  }

  private void ensureNameIsNotTakenByAnotherUniversity(
      final UniversityName newName, final UniversityId ownerId) {
    getUniversityByNamePort
        .getByName(newName)
        .ifPresent(
            found -> {
              if (!found.getId().equals(ownerId)) {
                throw UniversityAlreadyExistsException.becauseNameAlreadyExists(newName.value());
              }
            });
  }
}
