package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.DeleteUniversityCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface DeleteUniversityUseCase {
  void execute(@NotNull @Valid DeleteUniversityCommand command);
}
