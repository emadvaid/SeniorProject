package com.ALCverificationtool.config.preloads;

import com.ALCverificationtool.dao.users.UserRepository;
import com.ALCverificationtool.models.UserRec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserPreload implements ApplicationRunner {
    private final UserRepository userDao;

    @Value("${createAdmin:#{null}}")
    private String createAdmin;
    @Value("${adminUsername:#{null}}")
    private String adminUserName;
    @Value("${adminPass:#{null}}")
    private String adminPassword;

    @Autowired
    public AdminUserPreload(UserRepository userDao) {
        this.userDao = userDao;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        if(createAdmin!=null && "true".equals(createAdmin.trim().toLowerCase())) {

            // defaults for the "bootstrapping admin"
            String userName = "admin";
            String password = "password";

            // Allow overriding the defaults if set in config files
            if(adminUserName != null && adminUserName.length()>1) {
                userName = adminUserName;
            }
            // Allow overriding the defaults if set in config files
            if(adminPassword != null && adminPassword.length()>1) {
                password = adminPassword;
            }

            // first check if the admin user is in the database
            if(!userDao.findByUsername("admin").isPresent()) {
                // create the admin user
                UserRec adminUserRec = new UserRec(
                        userName, "admin", true,null,
                        null, null, null);
                adminUserRec.setPassword(passwordEncoder.encode(password));
                userDao.save(adminUserRec);
            }
        }
    }
}