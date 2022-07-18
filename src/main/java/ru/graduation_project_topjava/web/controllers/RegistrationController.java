package ru.graduation_project_topjava.web.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
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
import ru.graduation_project_topjava.web.controllers.rest.AdminController;

@Controller
@RequestMapping(RegistrationController.URL)
public class RegistrationController {

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
        create(userTo);
        status.setComplete();
        return "redirect:/login";
    }

    public User create(UserTo userTo) {
        /*log.info("create {}", userTo);*/
        //todo добавить логирование во все контроллеры и сервисы
        ValidationUtil.checkNew(userTo);
        return userService.create(UserUtil.createNewFromTo(userTo));
    }

}
