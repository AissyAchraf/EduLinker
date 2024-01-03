package ensa.edulinker.backend.web.controller;

import ensa.edulinker.backend.security.AccountUserService;
import ensa.edulinker.backend.security.Role;
import ensa.edulinker.backend.service.ModuleService;
import ensa.edulinker.backend.web.entities.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ModuleController {

    @Autowired
    private ModuleService moduleService;
    @Autowired
    private AccountUserService accountUserService;

    @GetMapping("/modules")
    private String index(Model model, Authentication authentication) {
        if(authentication != null && accountUserService.loadAccountByEmail(authentication.getName()).getRole() == Role.ADMIN) {
            List<Module> modulesList = moduleService.findAll();
            model.addAttribute("modulesList", modulesList);
            model.addAttribute("title", "Modules");
            return "/module/listing.html";
        } else {
            model.addAttribute("errorMessage", "Vous n'êtes pas autorisé pour accèder à cette ressource!");
            return "error_.html";
        }
    }
}
