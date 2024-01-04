package ensa.edulinker.backend.web.controller;

import ensa.edulinker.backend.service.AdministratorService;
import ensa.edulinker.backend.service.AdministratorServiceImpl;
import ensa.edulinker.backend.service.ProfessorService;
import ensa.edulinker.backend.service.ProfessorServiceImpl;
import ensa.edulinker.backend.web.entities.Administrator;
import ensa.edulinker.backend.web.entities.Person;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    private ProfessorService professorService = new ProfessorServiceImpl();
    private AdministratorService adminService = new AdministratorServiceImpl();

    @ModelAttribute("authenticatedUser")
    public Person addGlobalAuthenticatedUser(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            if(authentication.getAuthorities().stream()
                    .anyMatch(r -> r.getAuthority().equals("ROLE_PROF")))
                return professorService.getByEmail(authentication.getName());
            if(authentication.getAuthorities().stream()
                    .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN")))
                return adminService.getByEmail(authentication.getName());
        }
        return null;
    }
}
