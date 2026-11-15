package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateUniversityCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteUniversityCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateUniversityCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetUniversityByIdQuery;
import com.jcaa.usersmanagement.domain.model.UniversityModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.CreateUniversityRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.UpdateUniversityRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.UniversityRestResponse;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UniversityRestMapper {

  public CreateUniversityCommand toCreateCommand(final CreateUniversityRestRequest request) {
    return new CreateUniversityCommand(
        request.name(),
        request.category(),
        request.website(),
        request.rector(),
        request.email(),
        request.access(),
        request.phone(),
        request.city(),
        request.careersCount(),
        request.campusesCount());
  }

  public UpdateUniversityCommand toUpdateCommand(
      final String id, final UpdateUniversityRestRequest request) {
    return new UpdateUniversityCommand(
        id,
        request.name(),
        request.category(),
        request.website(),
        request.rector(),
        request.email(),
        request.access(),
        request.phone(),
        request.city(),
        request.careersCount(),
        request.campusesCount());
  }

  public GetUniversityByIdQuery toGetByIdQuery(final String id) {
    return new GetUniversityByIdQuery(id);
  }

  public DeleteUniversityCommand toDeleteCommand(final String id) {
    return new DeleteUniversityCommand(id);
  }

  public UniversityRestResponse toResponse(final UniversityModel university) {
    return new UniversityRestResponse(
        university.getId().value(),
        university.getName().value(),
        university.getCategory().name(),
        university.getWebsite().value(),
        university.getRector().value(),
        university.getEmail().value(),
        university.getAccess().name(),
        university.getPhone().value(),
        university.getCity().value(),
        university.getCareersCount().value(),
        university.getCampusesCount().value());
  }

  public List<UniversityRestResponse> toResponseList(final List<UniversityModel> universities) {
    return universities.stream().map(UniversityRestMapper::toResponse).toList();
  }
}
