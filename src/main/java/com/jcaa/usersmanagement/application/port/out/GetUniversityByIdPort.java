package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.UniversityModel;
import com.jcaa.usersmanagement.domain.valueobject.UniversityId;
import java.util.Optional;

public interface GetUniversityByIdPort {
  Optional<UniversityModel> getById(UniversityId universityId);
}
