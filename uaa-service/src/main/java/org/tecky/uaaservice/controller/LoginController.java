package org.tecky.uaaservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model, RedirectAttributes attr) {

        //attr.addAttribute("test", "kfdm");


        return "login";
    }

    @GetMapping("/loginim")
    public String loginAuth(RedirectAttributes attr) {

        //attr.addAttribute("test", "kfdm");


        return "login";
    }

}
