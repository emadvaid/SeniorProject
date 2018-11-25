package com.ALCverificationtool.config.preloads;

import com.ALCverificationtool.dao.users.UserRepository;
import com.ALCverificationtool.models.UserRec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserPreload implements ApplicationRunner {
    private final UserRepository userDao;

    @Autowired
    public AdminUserPreload(UserRepository userDao) {
        this.userDao = userDao;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        // first check if the admin user is in the database
        if(!userDao.findByUsername("admin").isPresent()) {
            // create the admin user
            UserRec adminUserRec = new UserRec(
                    "admin", "admin", true,null,
                    null, null, null);
            adminUserRec.setPassword(passwordEncoder.encode("admin"));
            userDao.save(adminUserRec);
        }
    }
}
