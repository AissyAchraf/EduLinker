package ensa.edulinker.backend.web.controller;

import ensa.edulinker.backend.service.ProfessorService;
import ensa.edulinker.backend.service.ProfessorServiceImpl;
import ensa.edulinker.backend.web.entities.Person;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    private ProfessorService professorService = new ProfessorServiceImpl();

    @ModelAttribute("authenticatedUser")
    public Person addGlobalAuthenticatedUser(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return professorService.getByEmail(authentication.getName());
        }
        return null;
    }
}
