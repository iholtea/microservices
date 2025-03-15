package io.ionuth.patient.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.ionuth.patient.dto.PatientRequestDTO;
import io.ionuth.patient.dto.PatientResponseDTO;
import io.ionuth.patient.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patinet", description = "API for managing Patients") 
public class PatientResource {
	
	private PatientService patientService;

	// default Spring DI
	public PatientResource(PatientService patientService) {
		this.patientService = patientService;
	}
	
	@GetMapping
	@Operation(summary = "Get All Patients")
	public ResponseEntity<List<PatientResponseDTO>> getPatients() {
		List<PatientResponseDTO> patients = patientService.getPatients();
		return ResponseEntity.ok( patients );
	}
	
	// @Valid will validate the PatientRequestDTO according to the annotations
	// on the fields from the class. Probably throws an exception if not valid
	// @RequestBody will transform the received HTTP POST in JSON to the PatientRequestDTO
	@PostMapping 
	@Operation(summary = "Create a new Patient")
	public ResponseEntity<PatientResponseDTO> createPatient(
				@Valid @RequestBody PatientRequestDTO reqDTO ) {
		
		PatientResponseDTO respDTO = patientService.createPatient(reqDTO);
		return ResponseEntity.ok(respDTO);
	
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Update an existing Patient")
	public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID id, 
				@Valid @RequestBody PatientRequestDTO reqDTO) {
		
		PatientResponseDTO respDTO = patientService.updatePatient(id, reqDTO);
		return ResponseEntity.ok(respDTO);
		
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Delete an existing Patient")
	public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
		patientService.deletePatient(id);
		return ResponseEntity.noContent().build();
	}
	
	
}
