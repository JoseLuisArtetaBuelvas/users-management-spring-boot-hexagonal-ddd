package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.controller;

import com.jcaa.usersmanagement.application.port.in.CreateUniversityUseCase;
import com.jcaa.usersmanagement.application.port.in.DeleteUniversityUseCase;
import com.jcaa.usersmanagement.application.port.in.GetAllUniversitiesUseCase;
import com.jcaa.usersmanagement.application.port.in.GetUniversityByIdUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdateUniversityUseCase;
import com.jcaa.usersmanagement.application.service.dto.command.CreateUniversityCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteUniversityCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateUniversityCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetUniversityByIdQuery;
import com.jcaa.usersmanagement.domain.model.UniversityModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.CreateUniversityRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.UpdateUniversityRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.UniversityRestResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.mapper.UniversityRestMapper;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/universities")
@RequiredArgsConstructor
public class UniversityRestController implements UniversityRestControllerDocs {

  private final CreateUniversityUseCase createUniversityUseCase;
  private final UpdateUniversityUseCase updateUniversityUseCase;
  private final DeleteUniversityUseCase deleteUniversityUseCase;
  private final GetUniversityByIdUseCase getUniversityByIdUseCase;
  private final GetAllUniversitiesUseCase getAllUniversitiesUseCase;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UniversityRestResponse create(
      @Valid @RequestBody final CreateUniversityRestRequest request) {
    final CreateUniversityCommand command = UniversityRestMapper.toCreateCommand(request);
    final UniversityModel university = createUniversityUseCase.execute(command);
    return UniversityRestMapper.toResponse(university);
  }

  @GetMapping
  public List<UniversityRestResponse> getAll() {
    return UniversityRestMapper.toResponseList(getAllUniversitiesUseCase.execute());
  }

  @GetMapping("/{id}")
  public UniversityRestResponse getById(@PathVariable final String id) {
    final GetUniversityByIdQuery query = UniversityRestMapper.toGetByIdQuery(id);
    final UniversityModel university = getUniversityByIdUseCase.execute(query);
    return UniversityRestMapper.toResponse(university);
  }

  @PutMapping("/{id}")
  public UniversityRestResponse update(
      @PathVariable final String id,
      @Valid @RequestBody final UpdateUniversityRestRequest request) {
    final UpdateUniversityCommand command = UniversityRestMapper.toUpdateCommand(id, request);
    final UniversityModel university = updateUniversityUseCase.execute(command);
    return UniversityRestMapper.toResponse(university);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable final String id) {
    final DeleteUniversityCommand command = UniversityRestMapper.toDeleteCommand(id);
    deleteUniversityUseCase.execute(command);
  }
}
