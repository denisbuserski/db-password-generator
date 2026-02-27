package com.db.password.generator.controller;


import com.db.password.generator.service.PasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/password-generator")
@RequiredArgsConstructor
@Slf4j
public class PasswordController {
    private final PasswordService passwordService;

    @GetMapping("/generate")
    public String showForm() {
        return "index";
    }

    @PostMapping("/generate")
    public String generatePassword(
            @RequestParam("length") int length,
            @RequestParam(value = "upper", required = false) boolean useUpper,
            @RequestParam(value = "lower", required = false) boolean useLower,
            @RequestParam(value = "digits", required = false) boolean useDigits,
            @RequestParam(value = "symbols", required = false) boolean useSymbols,
            Model model) {
        log.info("Generating password");
        try {
            String password = passwordService.generatePassword(length, useUpper, useLower, useDigits, useSymbols);
            model.addAttribute("password", password);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        }

        return "result";
    }
}
