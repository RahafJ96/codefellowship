package com.codefellows.demo.Controller;

import com.codefellows.demo.Model.ApplicationUser;
import com.codefellows.demo.infrastructure.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;


@Controller
public class MainController {

    @GetMapping("/")
    public String getCodefellowship(Principal p, Model m) {
        if(p != null){
            m.addAttribute("principal", p.getName());
        }else{
            m.addAttribute("principal", null);
        }

        return "codefellowship";
    }

}
