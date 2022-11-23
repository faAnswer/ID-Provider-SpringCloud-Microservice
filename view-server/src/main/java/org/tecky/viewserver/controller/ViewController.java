package org.tecky.viewserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.faAnswer.web.util.Url2ParamMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@Slf4j
public class ViewController {

    @GetMapping("/oauth/authorize")
    public String getAuthorize(@RequestParam Map<String, String> map, Model model, RedirectAttributes redirectAttribute, Authentication authentication) {



        return "authorize";
    }

    @GetMapping("/user/login")
    public String login(@RequestParam Map<String, String> map, Model model, RedirectAttributes redirectAttributes) {

        log.info("Controller /user/login");


        return "login";
    }

    @GetMapping("/payment")
    public String payment(@RequestParam Map<String, String> map, Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {


        String invoice_id = map.get("invoice_id");

        model.addAttribute("invoice_id", invoice_id);

        return "payment";
    }
}
