package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.GetAllUniversitiesPort;
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
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("GetAllUniversitiesService")
@ExtendWith(MockitoExtension.class)
class GetAllUniversitiesServiceTest {

  @Mock private GetAllUniversitiesPort getAllUniversitiesPort;
  @InjectMocks private GetAllUniversitiesService service;

  @Test
  @DisplayName("execute() retorna la lista del puerto")
  void shouldReturnAllUniversities() {
    final List<UniversityModel> expected = List.of(university());
    when(getAllUniversitiesPort.getAll()).thenReturn(expected);

    final List<UniversityModel> result = service.execute();

    assertSame(expected, result);
    verify(getAllUniversitiesPort).getAll();
  }

  private UniversityModel university() {
    return new UniversityModel(
        new UniversityId("00000000-0000-0000-0000-000000000101"),
        new UniversityName("Universidad de Cartagena"),
        UniversityCategory.PUBLICA,
        new UniversityWebsite("https://unicartagena.edu.co"),
        new UniversityRector("William Malkun"),
        new UniversityEmail("rectoria@unicartagena.edu.co"),
        UniversityAccess.ICFES,
        new UniversityPhone("6056699800"),
        new UniversityCity("Cartagena"),
        new UniversityCareersCount(40),
        new UniversityCampusesCount(4));
  }
}
