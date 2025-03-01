package com.stp.pacientes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.web.csrf.CsrfToken;

@RestController
@RequestMapping("/csrf")
public class CsrfController {

    @GetMapping
    public @ResponseBody CsrfToken csrf(CsrfToken token) {
        return token;
    }
}
