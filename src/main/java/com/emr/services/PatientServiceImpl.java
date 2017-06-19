package com.emr.services;

import com.emr.domain.Patient;
import com.emr.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {
    private PatientRepository patientRepository;

    @Autowired
    public void setPatientRepository(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Iterable<Patient> listAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Patient getPatientById(Integer id) {
        return patientRepository.findOne(id);
    }

    @Override
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public void deletePatient(Integer id) {
        patientRepository.delete(id);
    }
}
