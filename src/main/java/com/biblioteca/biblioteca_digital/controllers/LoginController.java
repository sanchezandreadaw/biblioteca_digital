package com.biblioteca.biblioteca_digital.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.biblioteca.biblioteca_digital.dtos.LoginDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        if (!model.containsAttribute("login")) {
            model.addAttribute("login", new LoginDTO());
        }
        return "login/login";
    }

    @PostMapping("/validar_credenciales")
    public String validarCredenciales(@Valid @ModelAttribute("login") LoginDTO loginDTO,
            BindingResult bindingResult,
            Model model,
            HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Datos de login inválidos");
            return "login/login";
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getCorreo(),
                            loginDTO.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext());

            return "redirect:/home";

        } catch (AuthenticationException e) {
            model.addAttribute("error", "Correo electrónico o contraseña incorrectos");
            return "login/login";
        }
    }

}
