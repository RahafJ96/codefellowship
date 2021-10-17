package com.codefellows.demo.infrastructure;

import com.codefellows.demo.Model.ApplicationUser;
import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;

public interface ApplicationUserRepository extends JpaAttributeConverter<ApplicationUser,Long> {
        ApplicationUser findApplicationUserByUsername(String userName);
        ApplicationUser getById(Long id);

        ApplicationUser save(ApplicationUser newUser);
}
