package org.tecky.uaaservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @GetMapping("/auth")
    public String login(Model model, RedirectAttributes attr) {

        //attr.addAttribute("test", "kfdm");


        return "auth";
    }
}
