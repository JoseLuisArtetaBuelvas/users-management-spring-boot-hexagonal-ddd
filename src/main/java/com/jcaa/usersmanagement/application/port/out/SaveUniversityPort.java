package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.UniversityModel;

public interface SaveUniversityPort {
  UniversityModel save(UniversityModel university);
}
