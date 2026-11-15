package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.controller;

import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.CreateUniversityRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.UpdateUniversityRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.ApiErrorResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.UniversityRestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(
    name = "Universities",
    description = "Gestión de universidades: crear, consultar, actualizar y eliminar.")
public interface UniversityRestControllerDocs {

  @Operation(
      summary = "Crear universidad",
      description =
          "Registra una nueva universidad. El nombre debe ser único y los datos deben cumplir "
              + "las validaciones del dominio.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "201",
        description = "Universidad creada exitosamente.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UniversityRestResponse.class))),
    @ApiResponse(
        responseCode = "400",
        description = "Datos de entrada inválidos.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class))),
    @ApiResponse(
        responseCode = "409",
        description = "Ya existe una universidad con ese nombre.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class))),
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  UniversityRestResponse create(@Valid @RequestBody CreateUniversityRestRequest request);

  @Operation(
      summary = "Listar todas las universidades",
      description = "Retorna la lista completa de universidades ordenada alfabéticamente por nombre.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Lista de universidades obtenida exitosamente. Puede ser vacía.",
        content =
            @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = UniversityRestResponse.class)))),
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  List<UniversityRestResponse> getAll();

  @Operation(
      summary = "Obtener universidad por ID",
      description = "Retorna los datos de una universidad específica identificada por su ID único.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Universidad encontrada.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UniversityRestResponse.class))),
    @ApiResponse(
        responseCode = "404",
        description = "No existe ninguna universidad con el ID proporcionado.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class))),
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  UniversityRestResponse getById(
      @Parameter(
              description = "ID único de la universidad.",
              example = "00000000-0000-0000-0000-000000000101",
              required = true)
          @PathVariable
          String id);

  @Operation(
      summary = "Actualizar universidad",
      description =
          "Actualiza los datos de una universidad existente. Si se cambia el nombre, este no debe "
              + "estar en uso por otra universidad.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Universidad actualizada exitosamente.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UniversityRestResponse.class))),
    @ApiResponse(
        responseCode = "400",
        description = "Datos de entrada inválidos.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class))),
    @ApiResponse(
        responseCode = "404",
        description = "No existe ninguna universidad con el ID proporcionado.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class))),
    @ApiResponse(
        responseCode = "409",
        description = "El nuevo nombre ya está en uso por otra universidad.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class))),
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  UniversityRestResponse update(
      @Parameter(
              description = "ID único de la universidad a actualizar.",
              example = "00000000-0000-0000-0000-000000000101",
              required = true)
          @PathVariable
          String id,
      @Valid @RequestBody UpdateUniversityRestRequest request);

  @Operation(
      summary = "Eliminar universidad",
      description = "Elimina permanentemente una universidad del sistema por su ID.")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Universidad eliminada exitosamente."),
    @ApiResponse(
        responseCode = "404",
        description = "No existe ninguna universidad con el ID proporcionado.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class))),
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  void delete(
      @Parameter(
              description = "ID único de la universidad a eliminar.",
              example = "00000000-0000-0000-0000-000000000101",
              required = true)
          @PathVariable
          String id);
}
