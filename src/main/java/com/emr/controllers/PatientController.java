package com.emr.controllers;

import com.emr.domain.Patient;
import com.emr.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class PatientController {

    private PatientService patientService;

    @Autowired
    public void setPatientService(PatientService patientService) {
        this.patientService = patientService;
    }

    @RequestMapping(value = "/patients", method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("patients", patientService.listAllPatients());
        return "patients";
    }

    @RequestMapping("patient/show/{id}")
    public String showPatient(@PathVariable Integer id, Model model){
        model.addAttribute("patient", patientService.getPatientById(id));
        return "patientshow";
    }

    @RequestMapping("patient/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        model.addAttribute("patient", patientService.getPatientById(id));
        return "patientform";
    }

    @RequestMapping("patient/new")
    public String newPatient(Model model){
        model.addAttribute("patient", new Patient());
        return "patientform";
    }

    @RequestMapping(value = "patient", method = RequestMethod.POST)
    public String savePatient(Patient patient){
        patientService.savePatient(patient);
        return "redirect:/patient/show/" + patient.getId();
    }

    @RequestMapping("patient/delete/{id}")
    public String delete(@PathVariable Integer id){
        patientService.deletePatient(id);
        return "redirect:/patients";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
         return "login";
    }

    @RequestMapping(value="patient/getMedicalRecord/{id}", method=RequestMethod.GET)
    public ResponseEntity<byte[]> getMedicalRecord(@PathVariable Integer id) throws IOException {

        Patient patient = patientService.getPatientById(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.add("content-disposition", "inline;filename=" + patient.getPracticeName());
        //headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(loadFile(patient.getFileUrl()), headers, HttpStatus.OK);
        return response;
    }

    private byte[] readFully(InputStream stream) throws IOException
    {
        byte[] buffer = new byte[8192];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int bytesRead;
        while ((bytesRead = stream.read(buffer)) != -1)
        {
            baos.write(buffer, 0, bytesRead);
        }
        return baos.toByteArray();
    }

    private byte[] loadFile(String sourcePath) throws IOException
    {
        InputStream inputStream = null;
        try
        {
            inputStream = new FileInputStream(sourcePath);
            return readFully(inputStream);
        }
        finally
        {
            if (inputStream != null)
            {
                inputStream.close();
            }
        }
    }

}
