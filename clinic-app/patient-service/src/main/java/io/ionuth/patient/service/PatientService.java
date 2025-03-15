package io.ionuth.patient.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import io.ionuth.patient.dto.PatientRequestDTO;
import io.ionuth.patient.dto.PatientResponseDTO;
import io.ionuth.patient.exception.EmailExistsException;
import io.ionuth.patient.exception.PatientNotFoundException;
import io.ionuth.patient.mapper.PatientMapper;
import io.ionuth.patient.model.Patient;
import io.ionuth.patient.repository.PatientRepository;

@Service
public class PatientService {
	
	private PatientRepository patientRepo;
	
	// default Spring DI
	public PatientService(PatientRepository patientRepo) {
		this.patientRepo = patientRepo;
	}
	
	public List<PatientResponseDTO> getPatients() {
		return patientRepo.findAll().stream()
				.map(PatientMapper::toDTO)
				.toList();
	}
	
	public PatientResponseDTO createPatient(PatientRequestDTO reqDTO) {
		
		if(patientRepo.existsByEmail(reqDTO.getEmail())) {
			throw new EmailExistsException("A patient with this email already exists: " +
					reqDTO.getEmail());
		}
		
		Patient newPatient = patientRepo.save(PatientMapper.toModel(reqDTO));
		return PatientMapper.toDTO(newPatient);
	}
	
	
	public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO reqDTO) {
		
		Patient patient = patientRepo.findById(id).orElseThrow( 
				() -> new PatientNotFoundException("Patient not found with ID: " + id));
		
		// check again if the email was changed and not already exists
		if( !reqDTO.getEmail().equals(patient.getEmail()) &&
				patientRepo.existsByEmail(reqDTO.getEmail()) ) {
			throw new EmailExistsException("A patient with this email already exists: " +
					reqDTO.getEmail());
		}
		
		patient.setName(reqDTO.getName());
		patient.setEmail(reqDTO.getEmail());
		patient.setAddress(reqDTO.getAddress());
		patient.setDateOfBirth(LocalDate.parse(reqDTO.getDateOfBirth()));
		patientRepo.save(patient);
		
		return PatientMapper.toDTO(patient);
		
	}
	
	public void deletePatient(UUID id) {
		patientRepo.deleteById(id);
	}
	
}
