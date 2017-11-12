package com.emr.bootstrap;

import com.emr.domain.Role;
import com.emr.domain.Patient;
import com.emr.domain.User;
import com.emr.repositories.PatientRepository;
import com.emr.services.RoleService;
import com.emr.services.UserService;
import com.emr.util.CryptoException;
import com.emr.util.CryptoUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class JpaBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private PatientRepository patientRepository;
    private UserService userService;
    private RoleService roleService;

    private Logger log = Logger.getLogger(JpaBootstrap.class);

    @Autowired
    public void setPatientRepository(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //encryptFiles();
        jasyptEncryptFiles();
        loadPatients();
        loadUsers();
        loadRoles();
        assignUsersToUserRole();
        assignUsersToAdminRole();
    }

    private void jasyptEncryptFiles() {

        String sourcePath1 = "emrs/ABC Practice/534/ABOUJAWDAH, IMAD-534-ABC Practice.pdf";
        String encryptedFilePath1 = "emrs/ABC Practice/534/ABOUJAWDAH, IMAD-534-ABC Practice-enc.pdf";

        String sourcePath2 = "emrs/ABC Practice/544/AABEDI, FARHAD-544-ABC Practice.pdf";
        String encryptedFilePath2 = "emrs/ABC Practice/544/AABEDI, FARHAD-544-ABC Practice-enc.pdf";

        String sourcePath3 = "emrs/ABC Practice/545/AARON, JONATHAN-545-ABC Practice.pdf";
        String encryptedFilePath3 = "emrs/ABC Practice/545/AARON, JONATHAN-545-ABC Practice-enc.pdf";

        String sourcePath4 = "emrs/ABC Practice/547/ABASSIAN, HELEN-547-ABC Practice.pdf";
        String encryptedFilePath4 = "emrs/ABC Practice/547/ABASSIAN, HELEN-547-ABC Practice-enc.pdf";

        String sourcePath5 = "emrs/ABC Practice/548/ABEDI, AKHTAR-548-ABC Practice.pdf";
        String encryptedFilePath5 = "emrs/ABC Practice/548/ABEDI, AKHTAR-548-ABC Practice-enc.pdf";

        String sourcePath6 = "emrs/ABC Practice/549/ABELL, CHRISTINE-549-ABC Practice.pdf";
        String encryptedFilePath6 = "emrs/ABC Practice/549/ABELL, CHRISTINE-549-ABC Practice-enc.pdf";

        String sourcePath7 = "emrs/ABC Practice/550/ABITBUL, YOSSI-550-ABC Practice.pdf";
        String encryptedFilePath7 = "emrs/ABC Practice/550/ABITBUL, YOSSI-550-ABC Practice-enc.pdf";

        String sourcePath8 = "emrs/ABC Practice/552/ABOTBOL, MAOR-552-ABC Practice.pdf";
        String encryptedFilePath8 = "emrs/ABC Practice/552/ABOTBOL, MAOR-552-ABC Practice-enc.pdf";

        String sourcePath9 = "emrs/ABC Practice/563/ABBOTT, WILLIAM-563-ABC Practice.pdf";
        String encryptedFilePath9 = "emrs/ABC Practice/563/ABBOTT, WILLIAM-563-ABC Practice-enc.pdf";

       // String sourcePath10 = "emrs/ABC Practice/564/ABACHI, SHAHRIAR-564-ABC Practice.pdf";
       // String encryptedFilePath10 = "emrs/ABC Practice/564/ABACHI, SHAHRIAR-564-ABC Practice-enc.pdf";






        try {
            CryptoUtils.jasyptEncrypt(sourcePath1, encryptedFilePath1);
            CryptoUtils.jasyptEncrypt(sourcePath2, encryptedFilePath2);
            CryptoUtils.jasyptEncrypt(sourcePath3, encryptedFilePath3);
            CryptoUtils.jasyptEncrypt(sourcePath4, encryptedFilePath4);
            CryptoUtils.jasyptEncrypt(sourcePath5, encryptedFilePath5);
            CryptoUtils.jasyptEncrypt(sourcePath6, encryptedFilePath6);
            CryptoUtils.jasyptEncrypt(sourcePath7, encryptedFilePath7);
            CryptoUtils.jasyptEncrypt(sourcePath8, encryptedFilePath8);
            CryptoUtils.jasyptEncrypt(sourcePath9, encryptedFilePath9);
           // CryptoUtils.jasyptEncrypt(sourcePath10, encryptedFilePath10);
        } catch (CryptoException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void encryptFiles() {

        String key = "Mary has one cat";
        File inputFile1 = new File("emrs/123456/XYZ Practice/123456-XYZ Practice.pdf");
        File encryptedFile1 = new File("emrs/123456/XYZ Practice/123456-XYZ Practice-enc.pdf");

        File inputFile2 = new File("emrs/16863939/ABC Practice/16863939-ABC Practice.pdf");
        File encryptedFile2 = new File("emrs/16863939/ABC Practice/16863939-ABC Practice-enc.pdf");

        try {
            CryptoUtils.encrypt(key, inputFile1, encryptedFile1);
            CryptoUtils.encrypt(key, inputFile2, encryptedFile2);
        } catch (CryptoException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void loadPatients() {
        Patient patient1 = new Patient();

        patient1.setPatientName("ABOUJAWDAH, IMAD");
        patient1.setPracticeName("ABC Practice");
        patient1.setPatientId("534");
        patient1.setFileUrl(getFileURL(patient1));
        patientRepository.save(patient1);
        log.info("Saved "+patient1.getPatientName()+" - id: " + patient1.getId());

        Patient patient2 = new Patient();

        patient2.setPatientName("AABEDI, FARHAD");
        patient2.setPracticeName("ABC Practice");
        patient2.setPatientId("544");
        patient2.setFileUrl(getFileURL(patient2));
        patientRepository.save(patient2);
        log.info("Saved "+patient2.getPatientName()+" - id: " + patient2.getId());

        Patient patient3 = new Patient();

        patient3.setPatientName("AARON, JONATHAN");
        patient3.setPracticeName("ABC Practice");
        patient3.setPatientId("545");
        patient3.setFileUrl(getFileURL(patient3));
        patientRepository.save(patient3);
        log.info("Saved "+patient3.getPatientName()+" - id: " + patient3.getId());

        Patient patient4 = new Patient();

        patient4.setPatientName("ABASSIAN, HELEN");
        patient4.setPracticeName("ABC Practice");
        patient4.setPatientId("547");
        patient4.setFileUrl(getFileURL(patient4));
        patientRepository.save(patient4);
        log.info("Saved "+patient4.getPatientName()+" - id: " + patient4.getId());

        Patient patient5 = new Patient();

        patient5.setPatientName("ABEDI, AKHTAR");
        patient5.setPracticeName("ABC Practice");
        patient5.setPatientId("548");
        patient5.setFileUrl(getFileURL(patient5));
        patientRepository.save(patient5);
        log.info("Saved "+patient5.getPatientName()+" - id: " + patient5.getId());

        Patient patient6 = new Patient();

        patient6.setPatientName("ABELL, CHRISTINE");
        patient6.setPracticeName("ABC Practice");
        patient6.setPatientId("549");
        patient6.setFileUrl(getFileURL(patient6));
        patientRepository.save(patient6);
        log.info("Saved "+patient6.getPatientName()+" - id: " + patient6.getId());

        Patient patient7 = new Patient();

        patient7.setPatientName("ABITBUL, YOSSI");
        patient7.setPracticeName("ABC Practice");
        patient7.setPatientId("550");
        patient7.setFileUrl(getFileURL(patient7));
        patientRepository.save(patient7);
        log.info("Saved "+patient7.getPatientName()+" - id: " + patient7.getId());

        Patient patient8 = new Patient();

        patient8.setPatientName("ABOTBOL, MAOR");
        patient8.setPracticeName("ABC Practice");
        patient8.setPatientId("552");
        patient8.setFileUrl(getFileURL(patient8));
        patientRepository.save(patient8);
        log.info("Saved "+patient8.getPatientName()+" - id: " + patient8.getId());

        Patient patient9 = new Patient();

        patient9.setPatientName("ABBOTT, WILLIAM");
        patient9.setPracticeName("ABC Practice");
        patient9.setPatientId("563");
        patient9.setFileUrl(getFileURL(patient9));
        patientRepository.save(patient9);
        log.info("Saved "+patient9.getPatientName()+" - id: " + patient9.getId());

    /*    Patient patient10 = new Patient();

        patient10.setPatientName("ABBOTT, WILLIAM");
        patient10.setPracticeName("ABC Practice");
        patient10.setPatientId("564");
        patient10.setFileUrl(getFileURL(patient9));
        patientRepository.save(patient10);
        log.info("Saved "+patient10.getPatientName()+" - id: " + patient10.getId());
*/

        /*Patient patient1 = new Patient();

        patient1.setPatientName("Jim Cary");
        patient1.setPracticeName("XYZ Practice");
        patient1.setPatientId("123456");
        patient1.setFileUrl(getFileURL(patient1));
        //patient1.getFileUrl().getBinaryStream(new File("emrs/test.pdf"));
       // patient1.setFileUrl(new File("emrs/test.pdf"));


        patientRepository.save(patient1);

        log.info("Saved Jim Cary - id: " + patient1.getId());

        Patient patient2 = new Patient();
        patient2.setPatientName("Will Smith");
        patient2.setPracticeName("ABC Practice");
        patient2.setPatientId("16863939");
        patient2.setFileUrl(getFileURL(patient2));
        patientRepository.save(patient2);

        log.info("Saved Will Smith - id:" + patient2.getId());*/
    }

    /*private String getFileURL(Patient patient) {
        return "emrs/" + patient.getPatientId() + "/"
                + patient.getPracticeName() + "/"
                + patient.getPatientId() + "-" + patient.getPracticeName()+"-enc.pdf";
    }
*/
    private String getFileURL(Patient patient) {
        return "emrs/" + patient.getPracticeName() + "/"
                + patient.getPatientId() + "/"
                + patient.getPatientName()+ "-" + patient.getPatientId() + "-" + patient.getPracticeName()+"-enc.pdf";
    }


    private void loadUsers() {
        User user1 = new User();
        user1.setUsername("user");
        user1.setPassword("user");
        userService.saveOrUpdate(user1);

        User user2 = new User();
        user2.setUsername("admin");
        user2.setPassword("admin");
        userService.saveOrUpdate(user2);

    }

    private void loadRoles() {
        Role role = new Role();
        role.setRole("USER");
        roleService.saveOrUpdate(role);
        log.info("Saved role" + role.getRole());
        Role adminRole = new Role();
        adminRole.setRole("ADMIN");
        roleService.saveOrUpdate(adminRole);
        log.info("Saved role" + adminRole.getRole());
    }
    private void assignUsersToUserRole() {
        List<Role> roles = (List<Role>) roleService.listAll();
        List<User> users = (List<User>) userService.listAll();

        roles.forEach(role -> {
            if (role.getRole().equalsIgnoreCase("USER")) {
                users.forEach(user -> {
                    if (user.getUsername().equals("user")) {
                        user.addRole(role);
                        userService.saveOrUpdate(user);
                    }
                });
            }
        });
    }
    private void assignUsersToAdminRole() {
        List<Role> roles = (List<Role>) roleService.listAll();
        List<User> users = (List<User>) userService.listAll();

        roles.forEach(role -> {
            if (role.getRole().equalsIgnoreCase("ADMIN")) {
                users.forEach(user -> {
                    if (user.getUsername().equals("admin")) {
                        user.addRole(role);
                        userService.saveOrUpdate(user);
                    }
                });
            }
        });
    }
}


