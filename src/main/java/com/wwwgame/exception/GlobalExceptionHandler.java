package com.wwwgame.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNotFound(NoSuchElementException e, Model model) {
        logger.error("Объект не найден: {}", e.getMessage());
        model.addAttribute("error", "Объект не найден");
        return "error";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleException(RuntimeException e, Model model) {
        logger.error("Произошла ошибка: {}", e.getMessage());
        model.addAttribute("error", e.getMessage());
        return "error";
    }
}
