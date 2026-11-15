package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetUniversityByIdUseCase;
import com.jcaa.usersmanagement.application.port.out.GetUniversityByIdPort;
import com.jcaa.usersmanagement.application.service.dto.query.GetUniversityByIdQuery;
import com.jcaa.usersmanagement.application.service.mapper.UniversityApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.UniversityNotFoundException;
import com.jcaa.usersmanagement.domain.model.UniversityModel;
import com.jcaa.usersmanagement.domain.valueobject.UniversityId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUniversityByIdService implements GetUniversityByIdUseCase {

  private final GetUniversityByIdPort getUniversityByIdPort;
  private final Validator validator;

  @Override
  public UniversityModel execute(final GetUniversityByIdQuery query) {
    validateQuery(query);

    final UniversityId universityId =
        UniversityApplicationMapper.fromGetUniversityByIdQueryToUniversityId(query);

    return getUniversityByIdPort
        .getById(universityId)
        .orElseThrow(
            () -> UniversityNotFoundException.becauseIdWasNotFound(universityId.value()));
  }

  private void validateQuery(final GetUniversityByIdQuery query) {
    final Set<ConstraintViolation<GetUniversityByIdQuery>> violations = validator.validate(query);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
