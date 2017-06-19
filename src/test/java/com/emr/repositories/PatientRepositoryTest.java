package com.emr.repositories;

import com.emr.configuration.RepositoryConfiguration;
import com.emr.domain.Patient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RepositoryConfiguration.class})
public class PatientRepositoryTest {

    private PatientRepository patientRepository;

    @Autowired
    public void setPatientRepository(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Test
    public void testSaveProduct(){
        //setup patient
        Patient patient = new Patient();
        patient.setPracticeName("Test Practice");
        patient.setPatientName("Test Patient");
        patient.setPatientId("11111");

        //save patient, verify has ID value after save
        assertNull(patient.getId()); //null before save
        patientRepository.save(patient);
        assertNotNull(patient.getId()); //not null after save

        //fetch from DB
        Patient fetchedPatient = patientRepository.findOne(patient.getId());

        //should not be null
        assertNotNull(fetchedPatient);

        //should equal
        assertEquals(patient.getId(), fetchedPatient.getId());
        assertEquals(patient.getPracticeName(), fetchedPatient.getPracticeName());

        //update practiceName and save
        fetchedPatient.setPracticeName("New Description");
        patientRepository.save(fetchedPatient);

        //get from DB, should be updated
        Patient fetchedUpdatedPatient = patientRepository.findOne(fetchedPatient.getId());
        assertEquals(fetchedPatient.getPracticeName(), fetchedUpdatedPatient.getPracticeName());

        //verify count of products in DB
        long patientCount = patientRepository.count();
        assertEquals(patientCount, 1);

        //get all products, list should only have one
        Iterable<Patient> patients = patientRepository.findAll();

        int count = 0;

        for(Patient p : patients){
            count++;
        }

        assertEquals(count, 1);
    }
}
