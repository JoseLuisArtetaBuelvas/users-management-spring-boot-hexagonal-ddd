package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.UniversityModel;
import java.util.List;

public interface GetAllUniversitiesPort {
  List<UniversityModel> getAll();
}
