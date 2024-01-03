package ensa.edulinker.backend.service;

import ensa.edulinker.backend.dao.IModuleElementDAO;
import ensa.edulinker.backend.dao.IModuleElementDAOFactory;
import ensa.edulinker.backend.web.entities.ModuleElement;

import java.util.List;

public interface ModuleElementService {

    public List<ModuleElement> findAll();
    public List<ModuleElement> findByModule(Long moduleId);
    public List<ModuleElement> findByProfessor(Long professorId);
    public ModuleElement getById(Long id);
}
