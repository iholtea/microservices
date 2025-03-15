package io.ionuth.patient.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.ionuth.patient.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
	
	boolean existsByEmail(String email);
	
}
