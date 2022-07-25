package ru.graduation_project_topjava.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import ru.graduation_project_topjava.View;
import ru.graduation_project_topjava.model.User;
import ru.graduation_project_topjava.service.UserService;
import ru.graduation_project_topjava.to.UserTo;
import ru.graduation_project_topjava.util.UserUtil;
import ru.graduation_project_topjava.util.validation.ValidationUtil;

@Controller
@RequestMapping(RegistrationController.URL)
public class RegistrationController {

    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);

    public static final String URL = "/register";

    private UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String saveRegister(@Validated(View.Web.class) UserTo userTo,
                                                                   BindingResult result,
                                                                   SessionStatus status,
                                                                   ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("register", true);
            return "registration";
        }
        User user = create(userTo);
        log.info("create {}", user);
        status.setComplete();
        return "redirect:/login";
    }

    public User create(UserTo userTo) {
        log.info("create {}", userTo);
        ValidationUtil.checkNew(userTo);
        return userService.create(UserUtil.createNewFromTo(userTo));
    }

}
