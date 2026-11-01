package com.jcaa.usersmanagement.application.service.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateUniversityCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteUniversityCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateUniversityCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetUniversityByIdQuery;
import com.jcaa.usersmanagement.domain.enums.UniversityAccess;
import com.jcaa.usersmanagement.domain.enums.UniversityCategory;
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
import java.util.UUID;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UniversityApplicationMapper {

  public UniversityModel fromCreateCommandToModel(final CreateUniversityCommand command) {
    return UniversityModel.create(
        new UniversityId(UUID.randomUUID().toString()),
        new UniversityName(command.name()),
        UniversityCategory.fromString(command.category()),
        new UniversityWebsite(command.website()),
        new UniversityRector(command.rector()),
        new UniversityEmail(command.email()),
        UniversityAccess.fromString(command.access()),
        new UniversityPhone(command.phone()),
        new UniversityCity(command.city()),
        new UniversityCareersCount(command.careersCount()),
        new UniversityCampusesCount(command.campusesCount()));
  }

  public UniversityModel fromUpdateCommandToModel(final UpdateUniversityCommand command) {
    return new UniversityModel(
        new UniversityId(command.id()),
        new UniversityName(command.name()),
        UniversityCategory.fromString(command.category()),
        new UniversityWebsite(command.website()),
        new UniversityRector(command.rector()),
        new UniversityEmail(command.email()),
        UniversityAccess.fromString(command.access()),
        new UniversityPhone(command.phone()),
        new UniversityCity(command.city()),
        new UniversityCareersCount(command.careersCount()),
        new UniversityCampusesCount(command.campusesCount()));
  }

  public UniversityId fromGetUniversityByIdQueryToUniversityId(final GetUniversityByIdQuery query) {
    return new UniversityId(query.id());
  }

  public UniversityId fromDeleteCommandToUniversityId(final DeleteUniversityCommand command) {
    return new UniversityId(command.id());
  }
}
