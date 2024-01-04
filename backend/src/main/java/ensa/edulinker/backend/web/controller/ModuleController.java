package ensa.edulinker.backend.web.controller;

import ensa.edulinker.backend.security.AccountUserService;
import ensa.edulinker.backend.security.Role;
import ensa.edulinker.backend.service.ModuleElementService;
import ensa.edulinker.backend.service.ModuleService;
import ensa.edulinker.backend.service.SectorService;
import ensa.edulinker.backend.web.entities.Module;
import ensa.edulinker.backend.web.entities.ModuleElement;
import ensa.edulinker.backend.web.entities.Sector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ModuleController {

    @Autowired
    private ModuleService moduleService;
    @Autowired
    private ModuleElementService moduleElementService;
    @Autowired
    private SectorService sectorService;
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

    @GetMapping("/modules/addModule")
    public String addModel(Model model) {
        List<Sector> sectorsList = sectorService.findAll();
        model.addAttribute("module", new Module());
        model.addAttribute("sectorsList", sectorsList);

        model.addAttribute("title", "Ajouter Module");
        return "/module/add.html";
    }

    @PostMapping("/modules/addModule")
    public String processAddModel(Module module, @RequestParam("sectorId") Long sectorId, Model model) {
        module.setSector(sectorService.getById(sectorId));
        moduleService.save(module);
        return "redirect:/modules";
    }

    @GetMapping("/modules/addElement")
    public String addElementModel(Model model) {
        List<Module> modulesList = moduleService.findAll();
        model.addAttribute("element", new ModuleElement());
        model.addAttribute("modulesList", modulesList);

        model.addAttribute("title", "Ajouter élément du module");
        return "/module-element/add.html";
    }

    @PostMapping("/modules/addElement")
    public String processAddElementModel(ModuleElement moduleElement, @RequestParam("moduleId") Long moduleId, Model model) {
        moduleElement.setModule(moduleService.getById(moduleId));
        moduleElementService.save(moduleElement);
        return "redirect:/elements-module?id="+moduleId;
    }
}
