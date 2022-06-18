package ru.graduation_project_topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class mainController {
private static final Logger log = LoggerFactory.getLogger(mainController.class);
    @GetMapping("/")
    public String indexPage(Model model)  {
        log.debug("test_logger");
        return "index";
    }
}
