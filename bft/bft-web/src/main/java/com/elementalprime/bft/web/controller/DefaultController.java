package com.elementalprime.bft.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class DefaultController {
	
	@RequestMapping("/")
    public RedirectView home() {
        return new RedirectView("/beliefs");
    }

    @RequestMapping("/belief")
    public ModelAndView deltaList() {
        return new ModelAndView("beliefSearch");
    }
}
