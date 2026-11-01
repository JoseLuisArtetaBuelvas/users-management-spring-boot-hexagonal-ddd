package com.jcaa.usersmanagement.domain.event;

import com.jcaa.usersmanagement.domain.valueobject.UniversityId;
import java.util.Map;
import lombok.Getter;

@Getter
public final class UniversityDeletedDomainEvent extends DomainEvent {

  private static final String EVENT_NAME = "university.deleted";

  private final UniversityId universityId;

  public UniversityDeletedDomainEvent(final UniversityId universityId) {
    super(EVENT_NAME);
    this.universityId = universityId;
  }

  @Override
  public Map<String, String> payload() {
    return Map.of("id", universityId.value());
  }
}
