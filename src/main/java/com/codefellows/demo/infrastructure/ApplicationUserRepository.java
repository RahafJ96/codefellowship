package com.codefellows.demo.infrastructure;

import com.codefellows.demo.Model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.context.annotation.Bean;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser,Long> {

        ApplicationUser findByUsername(String userName);

        ApplicationUser findById(long id);

        //ApplicationUser save(ApplicationUser newUser);
}
