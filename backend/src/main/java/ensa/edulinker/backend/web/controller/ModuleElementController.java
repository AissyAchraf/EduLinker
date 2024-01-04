package ensa.edulinker.backend.web.controller;

import ensa.edulinker.backend.security.AccountUserService;
import ensa.edulinker.backend.security.Role;
import ensa.edulinker.backend.service.*;
import ensa.edulinker.backend.web.entities.*;
import ensa.edulinker.backend.web.entities.Module;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class ModuleElementController {

    @Autowired
    private ModuleElementService moduleElementService;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private AccountUserService accountUserService;
    @Autowired
    private ProfessorService professorService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private EvaluationProceduresService evaluationProceduresService;
    @Autowired
    private GradeService gradeService;

    @GetMapping("/elements-module")
    private String index(Model model,
                         @RequestParam(name = "id") Long id,
                         Authentication authentication) {
        if(authentication != null && accountUserService.loadAccountByEmail(authentication.getName()).getRole() == Role.ADMIN) {
            Module module = moduleService.getById(id);
            List<ModuleElement> moduleElementsList = moduleElementService.findByModule(id);
            model.addAttribute("moduleElementsList", moduleElementsList);
            model.addAttribute("title", "Éléments de modules - " + module.getName());
            return "/module-element/listing.html";
        } else {
            model.addAttribute("errorMessage", "Vous n'etes pas authoriser pour accèder à cette ressource!");
            return "error_.html";
        }
    }

    @GetMapping("/mes-elements-module")
    public String showProfessorModuleElements(Model model, Authentication authentication) {
        if(authentication != null && accountUserService.loadAccountByEmail(authentication.getName()).getRole() == Role.PROF) {
            Professor professor = professorService.getByEmail(authentication.getName());
            List<ModuleElement> moduleElementsList = moduleElementService.findByProfessor(professor.getCode());
            model.addAttribute("moduleElementsList", moduleElementsList);
            model.addAttribute("title", "Mes éléments de modules");
            return "/module-element/listing.html";
        } else {
            model.addAttribute("errorMessage", "Vous n'êtes pas autorisé pour accèder à cette ressource!");
            return "error_.html";
        }
    }

    @GetMapping("/etudiants")
    public String showModuleElementStudents(Model model,
                                            @RequestParam("moduleElementId") Long moduleElementId,
                                            Authentication authentication) {
        List<Student> studentsList;
        ModuleElement moduleElement = moduleElementService.getById(moduleElementId);
        if(moduleElement == null) {
            model.addAttribute("errorMessage", "Vous n'êtes pas autorisé pour accèder à cette ressource!");
            return "error_.html";
        }
        if(authentication != null && accountUserService.loadAccountByEmail(authentication.getName()).getRole() == Role.PROF) {
            Professor professor = professorService.getByEmail(authentication.getName());
            if(professor.getCode() != moduleElement.getProfessor().getCode()) {
                    model.addAttribute("errorMessage", "Vous n'êtes pas autorisé pour accèder à cette ressource!");
                    return "error_.html";
            }
            studentsList = studentService.findBySectorAndSemester(moduleElement.getModule().getSector().getId(), moduleElement.getModule().getSemester());
            model.addAttribute("studentsList", studentsList);
            model.addAttribute("title", "Étudiants");

        } else {
            studentsList = studentService.findBySectorAndSemester(moduleElement.getModule().getSector().getId(), moduleElement.getModule().getSemester());
            model.addAttribute("studentsList", studentsList);
            model.addAttribute("title", "Étudiants");
        }
        model.addAttribute("moduleElement", moduleElement);
        return "/student/listing.html";
    }

    @GetMapping("/notes")
    String showModuleElementNotes(Model model,
                                     @RequestParam("moduleElementId") Long moduleElementId,
                                     Authentication authentication) {
        List<Grade> gradesList;
        ModuleElement moduleElement = moduleElementService.getById(moduleElementId);
        List<Student> studentsList = studentService.findBySectorAndSemester(moduleElement.getId(), moduleElement.getModule().getSemester());
        List<EvaluationProcedure> proceduresList = evaluationProceduresService.findByModuleElement(moduleElementId);
        if(moduleElement == null) {
            model.addAttribute("errorMessage", "Vous n'êtes pas autorisé pour accèder à cette ressource!");
            return "error_.html";
        }
        if(authentication != null && accountUserService.loadAccountByEmail(authentication.getName()).getRole() == Role.PROF) {
            Professor professor = professorService.getByEmail(authentication.getName());
            if (professor.getCode() != moduleElement.getProfessor().getCode()) {
                model.addAttribute("errorMessage", "Vous n'êtes pas autorisé pour accèder à cette ressource!");
                return "error_.html";
            }
            gradesList = gradeService.findByModuleElement(moduleElementId);
            model.addAttribute("gradesList", gradesList);
            model.addAttribute("studentsList", studentsList);
            model.addAttribute("proceduresList", proceduresList);
            model.addAttribute("title", "Notes - "+moduleElement.getName());
            model.addAttribute("moduleElement", moduleElement);
            return "/student/notes.html";

        } else {
            gradesList = gradeService.findByModuleElement(moduleElementId);
            model.addAttribute("gradesList", gradesList);
            model.addAttribute("studentsList", studentsList);
            model.addAttribute("proceduresList", proceduresList);
            model.addAttribute("title", "Notes - "+moduleElement.getName());
            model.addAttribute("moduleElement", moduleElement);
            return "/student/notes.html";
        }
    }

    @GetMapping("/insertion-notes")
    public String showGradeForm(Model model,
                                @RequestParam("moduleElementId") Long moduleElementId,
                                Authentication authentication) {
        List<Grade> gradesList;
        ModuleElement moduleElement = moduleElementService.getById(moduleElementId);
        List<Student> studentsList = studentService.findBySectorAndSemester(moduleElement.getId(), moduleElement.getModule().getSemester());
        List<EvaluationProcedure> proceduresList = evaluationProceduresService.findByModuleElement(moduleElementId);
        if(moduleElement == null) {
            model.addAttribute("errorMessage", "Vous n'êtes pas autorisé pour accèder à cette ressource!");
            return "error_.html";
        }
        if(authentication != null && accountUserService.loadAccountByEmail(authentication.getName()).getRole() == Role.PROF) {
            Professor professor = professorService.getByEmail(authentication.getName());
            if (professor.getCode() != moduleElement.getProfessor().getCode()) {
                model.addAttribute("errorMessage", "Vous n'êtes pas autorisé pour accèder à cette ressource!");
                return "error_.html";
            }
            gradesList = gradeService.findByModuleElement(moduleElementId);
            model.addAttribute("gradesList", gradesList);
            model.addAttribute("studentsList", studentsList);
            model.addAttribute("proceduresList", proceduresList);
            model.addAttribute("title", "Notes - "+moduleElement.getName());
            model.addAttribute("moduleElement", moduleElement);
            return "/student/insert_notes.html";

        } else {
            gradesList = gradeService.findByModuleElement(moduleElementId);
            model.addAttribute("gradesList", gradesList);
            model.addAttribute("title", "Notes - "+moduleElement.getName());
            model.addAttribute("moduleElement", moduleElement);
            return "/student/insert_notes.html";
        }
    }

    @PostMapping("/insertion-notes")
    public String submitGrades(HttpServletRequest request) {
        Map<String, String[]> paramMap = request.getParameterMap();
        studentService.processGradesSubmission(paramMap);
        return "redirect:/notes?moduleElementId="+request.getParameter("moduleElementId");
    }
}
