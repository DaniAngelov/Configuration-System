package com.codingtask.configmanagementapi.controller;

import com.codingtask.configmanagementapi.exception.CustomConfigurationException;
import com.codingtask.configmanagementapi.kafka.KafkaProducer;
import com.codingtask.configmanagementapi.model.dto.ConfigurationDTO;
import com.codingtask.configmanagementapi.model.dto.DeleteConfigurationDTO;
import com.codingtask.configmanagementapi.model.dto.GetConfigurationDTO;
import com.codingtask.configmanagementapi.model.dto.UpdateConfigurationDTO;
import com.codingtask.configmanagementapi.model.entity.Configuration;
import com.codingtask.configmanagementapi.service.ConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/configs")
@AllArgsConstructor
public class ConfigurationController {

    private final ConfigService configService;

    private final KafkaProducer producer;

    @Operation(summary = "Add a new configuration")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ConfigurationDTO.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "There is already such configuration!", content = @Content(
                    schema = @Schema(hidden = true)))})
    @PostMapping
    public ResponseEntity<?> addConfiguration(@RequestBody ConfigurationDTO configurationDTO) throws CustomConfigurationException {
        Configuration configuration = configService.addConfiguration(configurationDTO);
        producer.publishToTopic(configuration, configuration.getEnvironmentType());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get all configurations")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ConfigurationDTO.class))})})
    @GetMapping
    public ResponseEntity<List<GetConfigurationDTO>> getConfigurations() {
        List<GetConfigurationDTO> configurationList = configService.getConfigurations();
        return new ResponseEntity<>(configurationList, HttpStatus.OK);
    }

    @Operation(summary = "Modify an existing configuration")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = UpdateConfigurationDTO.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "There is no such configuration!", content = @Content(
                    schema = @Schema(hidden = true)))})
    @PutMapping
    public ResponseEntity<?> updateConfiguration(@RequestBody UpdateConfigurationDTO updateConfigurationDTO) throws CustomConfigurationException {
        Configuration configuration = configService.updateConfiguration(updateConfigurationDTO);
        producer.publishToTopic(configuration, configuration.getEnvironmentType());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Delete an existing configuration")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = DeleteConfigurationDTO.class))})})
    @DeleteMapping
    public ResponseEntity<?> deleteConfiguration(@RequestBody DeleteConfigurationDTO deleteConfigurationDTO) {
        configService.deleteConfiguration(deleteConfigurationDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
