package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.query.GetUniversityByIdQuery;
import com.jcaa.usersmanagement.domain.model.UniversityModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface GetUniversityByIdUseCase {
  UniversityModel execute(@NotNull @Valid GetUniversityByIdQuery query);
}
