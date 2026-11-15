package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.CreateUniversityCommand;
import com.jcaa.usersmanagement.domain.model.UniversityModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface CreateUniversityUseCase {
  UniversityModel execute(@NotNull @Valid CreateUniversityCommand command);
}
