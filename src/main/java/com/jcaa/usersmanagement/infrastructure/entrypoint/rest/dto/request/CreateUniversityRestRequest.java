package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUniversityRestRequest(
    @NotBlank(message = "name must not be blank")
        @Size(min = 3, max = 100, message = "name must have between 3 and 100 characters")
        String name,
    @NotBlank(message = "category must not be blank") String category,
    @NotBlank(message = "website must not be blank")
        @Size(max = 150, message = "website must have at most 150 characters")
        String website,
    @NotBlank(message = "rector must not be blank")
        @Size(min = 3, max = 100, message = "rector must have between 3 and 100 characters")
        String rector,
    @NotBlank(message = "email must not be blank")
        @Email(message = "email must be a valid email address")
        @Size(max = 150, message = "email must have at most 150 characters")
        String email,
    @NotBlank(message = "access must not be blank") String access,
    @NotBlank(message = "phone must not be blank")
        @Pattern(regexp = "^[0-9]{10}$", message = "phone must contain exactly 10 digits")
        String phone,
    @NotBlank(message = "city must not be blank")
        @Size(min = 3, max = 100, message = "city must have between 3 and 100 characters")
        String city,
    @NotNull(message = "careersCount must not be null")
        @Min(value = 0, message = "careersCount must not be negative")
        Integer careersCount,
    @NotNull(message = "campusesCount must not be null")
        @Min(value = 0, message = "campusesCount must not be negative")
        Integer campusesCount) {}
