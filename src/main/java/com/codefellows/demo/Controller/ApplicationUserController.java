package com.codefellows.demo.Controller;

import com.codefellows.demo.Model.ApplicationUser;
import com.codefellows.demo.infrastructure.ApplicationUserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


@Controller
public class ApplicationUserController {
    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    ApplicationUserRepository applicationUserRepository;


    @PostMapping("/users")
    public RedirectView createUser(String userName, String password, String firstName, String lastName,String dateOfBirth, String bio) throws ParseException {
        String hashedpwd = bCryptPasswordEncoder.encode(password);
        Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth);
        ApplicationUser newUser = new ApplicationUser(userName, hashedpwd, firstName, lastName,dob, bio);
        applicationUserRepository.save(newUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new RedirectView("/");
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String getSignUpPage() {
        return "signup";
    }

    @GetMapping("/myprofile")
    public String getMyProfilePage(Principal p, Model m) {
        ApplicationUser applicatonUser = applicationUserRepository.findByUsername(p.getName());
        m.addAttribute("applicatonUser", applicatonUser);
        m.addAttribute("principal", p.getName());
        return "myprofile";
    }


    @GetMapping("/users/{id}")
    public String getSingleAppUserPage(Model m, @PathVariable String id) {
        long ID = Long.parseLong(id);
        ApplicationUser applicationUser = applicationUserRepository.findById(ID);
        m.addAttribute("applicationUser", applicationUser);
        return "singleappuser";
    }
}
