package org.tecky.viewserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ViewController {

    @GetMapping("/oauth/authorize")
    public String getAuthorize(Model model, RedirectAttributes redirectAttributes) {

//
//        RedirectView redirectView = new RedirectView();
//
//        redirectView.setContextRelative(true);
//        redirectView.setUrl("authorize");
//        redirectAttributes.addFlashAttribute("alert", alertObj);
//

        return "authorize";
    }


}
