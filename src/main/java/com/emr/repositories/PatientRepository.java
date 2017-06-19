package com.emr.repositories;

import com.emr.domain.Patient;
import org.springframework.data.repository.CrudRepository;

public interface PatientRepository extends CrudRepository<Patient, Integer>{
}
