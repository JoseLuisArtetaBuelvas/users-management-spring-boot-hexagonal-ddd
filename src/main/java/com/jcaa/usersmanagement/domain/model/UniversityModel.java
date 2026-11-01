package com.jcaa.usersmanagement.domain.model;

import com.jcaa.usersmanagement.domain.enums.UniversityAccess;
import com.jcaa.usersmanagement.domain.enums.UniversityCategory;
import com.jcaa.usersmanagement.domain.valueobject.UniversityCampusesCount;
import com.jcaa.usersmanagement.domain.valueobject.UniversityCareersCount;
import com.jcaa.usersmanagement.domain.valueobject.UniversityCity;
import com.jcaa.usersmanagement.domain.valueobject.UniversityEmail;
import com.jcaa.usersmanagement.domain.valueobject.UniversityId;
import com.jcaa.usersmanagement.domain.valueobject.UniversityName;
import com.jcaa.usersmanagement.domain.valueobject.UniversityPhone;
import com.jcaa.usersmanagement.domain.valueobject.UniversityRector;
import com.jcaa.usersmanagement.domain.valueobject.UniversityWebsite;
import lombok.Value;

@Value
public class UniversityModel {

  UniversityId id;
  UniversityName name;
  UniversityCategory category;
  UniversityWebsite website;
  UniversityRector rector;
  UniversityEmail email;
  UniversityAccess access;
  UniversityPhone phone;
  UniversityCity city;
  UniversityCareersCount careersCount;
  UniversityCampusesCount campusesCount;

  public static UniversityModel create(
      final UniversityId id,
      final UniversityName name,
      final UniversityCategory category,
      final UniversityWebsite website,
      final UniversityRector rector,
      final UniversityEmail email,
      final UniversityAccess access,
      final UniversityPhone phone,
      final UniversityCity city,
      final UniversityCareersCount careersCount,
      final UniversityCampusesCount campusesCount) {
    return new UniversityModel(
        id,
        name,
        category,
        website,
        rector,
        email,
        access,
        phone,
        city,
        careersCount,
        campusesCount);
  }
}
