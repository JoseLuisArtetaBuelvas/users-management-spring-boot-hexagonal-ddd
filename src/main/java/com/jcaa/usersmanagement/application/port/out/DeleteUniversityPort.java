package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.valueobject.UniversityId;

public interface DeleteUniversityPort {
  void delete(UniversityId universityId);
}
