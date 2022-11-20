package org.tecky.viewserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.faAnswer.web.util.Url2ParamMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@Controller
@Slf4j
public class ViewController {

    @GetMapping("/oauth/authorize")
    public String getAuthorize(@RequestParam Map<String, String> map, Model model, RedirectAttributes redirectAttributes) {




        return "authorize";
    }

    @GetMapping("/user/login")
    public String login(@RequestParam Map<String, String> map, Model model, RedirectAttributes redirectAttributes) {

        log.info("Controller /user/login");


        return "login";
    }

}
