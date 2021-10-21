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


    @PostMapping("/createuser")
    public RedirectView createUser(String username, String password, String dateOfBirth, String firstName, String lastName, String bio) throws ParseException {
        String hashedpwd = bCryptPasswordEncoder.encode(password);
        Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth);
        ApplicationUser newUser = new ApplicationUser(username, hashedpwd,dob, firstName, lastName, bio);
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
    public String getSingleAppUserPage(Model m, Principal p, @PathVariable String id) {
        long ID = Long.parseLong(id);
        ApplicationUser applicationUser = applicationUserRepository.findById(ID);
        m.addAttribute("applicationUser", applicationUser);
        m.addAttribute("principal",p.getName());
        return "singleappuser";
    }

    @GetMapping("/users")
    public String getUsersPage(Principal p, Model m) {
        ApplicationUser appUser = applicationUserRepository.findByUsername(p.getName());
        Iterable<ApplicationUser> users = applicationUserRepository.findAll();
        m.addAttribute("applicationUser",appUser);
        m.addAttribute("principal", p.getName());
        m.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/following")
    public String getFollowingPage(Principal p, Model m) {
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(p.getName());
        Iterable<ApplicationUser> users = applicationUser.getFollowing();
        m.addAttribute("applicationUser",applicationUser);
        m.addAttribute("principal", p.getName());
        m.addAttribute("users", users);
        return "following";
    }

    @PostMapping("/follow/{id}")
    public RedirectView followUser(Principal p, @PathVariable long id) throws ParseException {

        ApplicationUser loggedInUser = applicationUserRepository.findByUsername(p.getName());
        ApplicationUser userToFollow = applicationUserRepository.findById(id);

        loggedInUser.getFollowing().add(userToFollow);
        userToFollow.getFollowers().add(loggedInUser);
        applicationUserRepository.save(loggedInUser);
        applicationUserRepository.save(userToFollow);

        return new RedirectView("/users");
    }
}
