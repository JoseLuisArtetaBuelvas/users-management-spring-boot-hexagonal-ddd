package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.UniversityModel;
import com.jcaa.usersmanagement.domain.valueobject.UniversityName;
import java.util.Optional;

public interface GetUniversityByNamePort {
  Optional<UniversityModel> getByName(UniversityName name);
}
