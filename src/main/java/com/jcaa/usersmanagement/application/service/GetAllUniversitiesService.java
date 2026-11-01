package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetAllUniversitiesUseCase;
import com.jcaa.usersmanagement.application.port.out.GetAllUniversitiesPort;
import com.jcaa.usersmanagement.domain.model.UniversityModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAllUniversitiesService implements GetAllUniversitiesUseCase {

  private final GetAllUniversitiesPort getAllUniversitiesPort;

  @Override
  public List<UniversityModel> execute() {
    return getAllUniversitiesPort.getAll();
  }
}
