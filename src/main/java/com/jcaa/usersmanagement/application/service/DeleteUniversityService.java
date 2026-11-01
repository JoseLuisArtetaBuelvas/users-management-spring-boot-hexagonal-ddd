package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.DeleteUniversityUseCase;
import com.jcaa.usersmanagement.application.port.out.DeleteUniversityPort;
import com.jcaa.usersmanagement.application.port.out.GetUniversityByIdPort;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteUniversityCommand;
import com.jcaa.usersmanagement.application.service.mapper.UniversityApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.UniversityNotFoundException;
import com.jcaa.usersmanagement.domain.valueobject.UniversityId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUniversityService implements DeleteUniversityUseCase {

  private final DeleteUniversityPort deleteUniversityPort;
  private final GetUniversityByIdPort getUniversityByIdPort;
  private final Validator validator;

  @Override
  public void execute(final DeleteUniversityCommand command) {
    validateCommand(command);

    final UniversityId universityId =
        UniversityApplicationMapper.fromDeleteCommandToUniversityId(command);
    ensureUniversityExists(universityId);
    deleteUniversityPort.delete(universityId);
  }

  private void validateCommand(final DeleteUniversityCommand command) {
    final Set<ConstraintViolation<DeleteUniversityCommand>> violations = validator.validate(command);
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
}
