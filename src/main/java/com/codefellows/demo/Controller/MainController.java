package com.codefellows.demo.Controller;

import com.codefellows.demo.Model.ApplicationUser;
import com.codefellows.demo.infrastructure.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;

@Controller
public class MainController {

    @Autowired
     ApplicationUserRepository applicationUserRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    @GetMapping("/")
    public String homePage(){
        return "index";
    }

    @GetMapping(value = "/signup")
    public String getSignUp() {
        return "signup";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @PostMapping(value = "/signup")
    public RedirectView attemptSignUp(@RequestParam String username ,
                                      @RequestParam String password ,
                                      @RequestParam String firstname ,
                                      @RequestParam String lastname ,
                                      @RequestParam String dateofbirth ,
                                      @RequestParam String bio) {
        ApplicationUser newUser = new ApplicationUser(username , encoder.encode(password), firstname , lastname , dateofbirth , bio);
        newUser = applicationUserRepository.save(newUser);
        Authentication auth = new UsernamePasswordAuthenticationToken(newUser,null,new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(auth);
        System.out.println(SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
        return new RedirectView("/");
    }

    @GetMapping(value = "users/{id}")
    public String getUserData(@PathVariable Long id, Model chosenUser) {
        ApplicationUser userFound = applicationUserRepository.getById(id);
        chosenUser.addAttribute("user",userFound);
        System.out.println(userFound.getBio());
        return "/publicuser";
    }



}
