package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.domain.model.UniversityModel;
import java.util.List;

public interface GetAllUniversitiesUseCase {
  List<UniversityModel> execute();
}
