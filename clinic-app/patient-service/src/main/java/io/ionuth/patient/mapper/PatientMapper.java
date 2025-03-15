package io.ionuth.patient.mapper;

import java.time.LocalDate;

import io.ionuth.patient.dto.PatientRequestDTO;
import io.ionuth.patient.dto.PatientResponseDTO;
import io.ionuth.patient.model.Patient;

public class PatientMapper {
	
	public static PatientResponseDTO toDTO(Patient patient) {
		PatientResponseDTO dto = new PatientResponseDTO();
		dto.setId( patient.getId().toString() );
		dto.setName(patient.getName());
		dto.setEmail(patient.getEmail());
		dto.setAddress(patient.getAddress());
		dto.setDateOfBirth( patient.getDateOfBirth().toString() );
		return dto;
	}
	
	public static Patient toModel(PatientRequestDTO dto) {
		Patient patient = new Patient();
		patient.setName(dto.getName());
		patient.setEmail(dto.getEmail());
		patient.setAddress(dto.getAddress());
		patient.setDateOfBirth( LocalDate.parse(dto.getDateOfBirth()) );
		patient.setRegisteredDate( LocalDate.now() );
		return patient;
	}
	
}
