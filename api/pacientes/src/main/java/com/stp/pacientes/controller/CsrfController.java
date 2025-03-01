package com.stp.pacientes.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.web.csrf.CsrfToken;

@RestController
@RequestMapping("/api/csrf")
public class CsrfController {

    @GetMapping
    public void csrf(CsrfToken token, HttpServletResponse response) {
        response.setHeader("X-CSRF-Token", token.getToken());
    }
}
