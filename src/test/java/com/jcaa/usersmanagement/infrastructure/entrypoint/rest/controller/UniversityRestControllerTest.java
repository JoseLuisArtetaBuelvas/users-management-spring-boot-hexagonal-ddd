package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jcaa.usersmanagement.application.port.in.CreateUniversityUseCase;
import com.jcaa.usersmanagement.application.port.in.DeleteUniversityUseCase;
import com.jcaa.usersmanagement.application.port.in.GetAllUniversitiesUseCase;
import com.jcaa.usersmanagement.application.port.in.GetUniversityByIdUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdateUniversityUseCase;
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
import com.jcaa.usersmanagement.infrastructure.security.JwtAuthenticationFilter;
import com.jcaa.usersmanagement.infrastructure.security.JwtTokenService;
import com.jcaa.usersmanagement.infrastructure.security.RestAccessDeniedHandler;
import com.jcaa.usersmanagement.infrastructure.security.RestAuthenticationEntryPoint;
import com.jcaa.usersmanagement.infrastructure.security.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
    controllers = UniversityRestController.class,
    properties = "spring.main.web-application-type=servlet")
@Import({
  SecurityConfig.class,
  JwtAuthenticationFilter.class,
  RestAuthenticationEntryPoint.class,
  RestAccessDeniedHandler.class
})
@DisplayName("UniversityRestController")
class UniversityRestControllerTest {

  private static final String ID = "00000000-0000-0000-0000-000000000101";

  @Autowired private MockMvc mockMvc;

  @MockBean private CreateUniversityUseCase createUniversityUseCase;
  @MockBean private UpdateUniversityUseCase updateUniversityUseCase;
  @MockBean private DeleteUniversityUseCase deleteUniversityUseCase;
  @MockBean private GetUniversityByIdUseCase getUniversityByIdUseCase;
  @MockBean private GetAllUniversitiesUseCase getAllUniversitiesUseCase;
  @MockBean private JwtTokenService jwtTokenService;

  @Test
  @WithMockUser(roles = "REVIEWER")
  @DisplayName("GET /api/universities/{id} debe responder usando el caso de uso inyectado")
  void shouldGetUniversityByIdThroughSpringMvc() throws Exception {
    when(getUniversityByIdUseCase.execute(new GetUniversityByIdQuery(ID))).thenReturn(university());

    mockMvc
        .perform(get("/api/universities/{id}", ID))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(ID))
        .andExpect(jsonPath("$.name").value("Universidad de Cartagena"))
        .andExpect(jsonPath("$.category").value("PUBLICA"))
        .andExpect(jsonPath("$.access").value("ICFES"))
        .andExpect(jsonPath("$.city").value("Cartagena"))
        .andExpect(jsonPath("$.careersCount").value(40));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  @DisplayName("POST /api/universities debe rechazar una solicitud inválida antes del caso de uso")
  void shouldRejectInvalidCreateRequest() throws Exception {
    final String invalidRequest =
        """
        {
          "name": "A",
          "category": "",
          "website": "",
          "rector": "W",
          "email": "invalid",
          "access": "",
          "phone": "123",
          "city": "",
          "careersCount": -1,
          "campusesCount": -1
        }
        """;

    mockMvc
        .perform(
            post("/api/universities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequest))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status").value(400));
  }

  @Test
  @DisplayName("GET /api/universities/{id} debe requerir autenticacion")
  void shouldRejectUnauthenticatedRequest() throws Exception {
    mockMvc
        .perform(get("/api/universities/{id}", ID))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.status").value(401));
  }

  @Test
  @WithMockUser(roles = "MEMBER")
  @DisplayName("GET /api/universities/{id} debe rechazar un rol sin permisos")
  void shouldRejectUnauthorizedRole() throws Exception {
    mockMvc
        .perform(get("/api/universities/{id}", ID))
        .andExpect(status().isForbidden())
        .andExpect(jsonPath("$.status").value(403));
  }

  @Test
  @WithMockUser(roles = "REVIEWER")
  @DisplayName("POST /api/universities debe requerir rol ADMIN")
  void shouldRejectReviewerOnCreate() throws Exception {
    mockMvc
        .perform(
            post("/api/universities")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
        .andExpect(status().isForbidden())
        .andExpect(jsonPath("$.status").value(403));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  @DisplayName("POST /api/universities debe crear con rol ADMIN")
  void shouldCreateWithAdminRole() throws Exception {
    when(createUniversityUseCase.execute(any())).thenReturn(university());

    final String validRequest =
        """
        {
          "name": "Universidad de Cartagena",
          "category": "PUBLICA",
          "website": "https://unicartagena.edu.co",
          "rector": "William Malkun",
          "email": "rectoria@unicartagena.edu.co",
          "access": "ICFES",
          "phone": "6056699800",
          "city": "Cartagena",
          "careersCount": 40,
          "campusesCount": 4
        }
        """;

    mockMvc
        .perform(
            post("/api/universities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validRequest))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(ID));
  }

  private UniversityModel university() {
    return new UniversityModel(
        new UniversityId(ID),
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
