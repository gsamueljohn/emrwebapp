package com.emr.services;


import com.emr.domain.Patient;

public interface PatientService {
    Iterable<Patient> listAllPatients();

    Patient getPatientById(Integer id);

    Patient savePatient(Patient patient);

    void deletePatient(Integer id);
}
