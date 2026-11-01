package com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto;

public record UniversityPersistenceDto(
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
    int campusesCount,
    String createdAt,
    String updatedAt) {}
