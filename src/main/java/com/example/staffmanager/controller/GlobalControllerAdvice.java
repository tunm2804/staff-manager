package com.example.staffmanager.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {
    @Value("${app.verify.user}")
    private String verifyUser;
    @Value("${app.verify.type}")
    private String verifyType;
    @Value("${app.verify.key}")
    private String verifyKey;
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("user", verifyUser);
        model.addAttribute("type", verifyType);
        model.addAttribute("key", verifyKey);
    }
}