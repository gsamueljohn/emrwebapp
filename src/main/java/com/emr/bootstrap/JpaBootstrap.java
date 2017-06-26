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

        String sourcePath1 = "emrs/123456/XYZ Practice/123456-XYZ Practice.pdf";
        String encryptedFilePath1 = "emrs/123456/XYZ Practice/123456-XYZ Practice-enc.pdf";

        String sourcePath2 = "emrs/16863939/ABC Practice/16863939-ABC Practice.pdf";
        String encryptedFilePath2 = "emrs/16863939/ABC Practice/16863939-ABC Practice-enc.pdf";

        try {
            CryptoUtils.jasyptEncrypt(sourcePath1, encryptedFilePath1);
            CryptoUtils.jasyptEncrypt(sourcePath2, encryptedFilePath2);
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

        log.info("Saved Will Smith - id:" + patient2.getId());
    }

    private String getFileURL(Patient patient) {
        return "emrs/" + patient.getPatientId() + "/"
                + patient.getPracticeName() + "/"
                + patient.getPatientId() + "-" + patient.getPracticeName()+"-enc.pdf";
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


