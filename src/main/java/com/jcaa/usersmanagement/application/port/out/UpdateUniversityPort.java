package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.UniversityModel;

public interface UpdateUniversityPort {
  UniversityModel update(UniversityModel university);
}
