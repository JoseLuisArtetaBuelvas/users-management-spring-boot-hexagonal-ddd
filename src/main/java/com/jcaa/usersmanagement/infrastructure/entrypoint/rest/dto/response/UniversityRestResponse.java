package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response;

public record UniversityRestResponse(
    String id,
    String name,
    String category,
    String website,
    String rector,
    String email,
    String access,
    String phone,
    String city,
    int careersCount,
    int campusesCount) {}
