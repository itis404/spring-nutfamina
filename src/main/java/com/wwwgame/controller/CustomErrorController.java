package com.wwwgame.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (statusCode != null) {
            int status = Integer.parseInt(statusCode.toString());
            if (status == 404) {
                model.addAttribute("error", "Страница не найдена (404)");
            } else if (status == 403) {
                model.addAttribute("error", "Доступ запрещён (403)");
            } else {
                model.addAttribute("error", "Произошла ошибка на сервере (" + status + ")");
            }
        } else {
            model.addAttribute("error", "Произошла непредвиденная ошибка");
        }

        return "error";
    }
}
