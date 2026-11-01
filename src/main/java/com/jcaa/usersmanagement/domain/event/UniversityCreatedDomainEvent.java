package com.jcaa.usersmanagement.domain.event;

import com.jcaa.usersmanagement.domain.model.UniversityModel;
import java.util.Map;
import lombok.Getter;

@Getter
public final class UniversityCreatedDomainEvent extends DomainEvent {

  private static final String EVENT_NAME = "university.created";

  private final UniversityModel university;

  public UniversityCreatedDomainEvent(final UniversityModel university) {
    super(EVENT_NAME);
    this.university = university;
  }

  @Override
  public Map<String, String> payload() {
    return Map.of(
        "id", university.getId().value(),
        "name", university.getName().value(),
        "category", university.getCategory().name(),
        "email", university.getEmail().value(),
        "access", university.getAccess().name(),
        "city", university.getCity().value(),
        "phone", university.getPhone().value());
  }
}
