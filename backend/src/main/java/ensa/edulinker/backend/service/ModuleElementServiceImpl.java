package ensa.edulinker.backend.service;

import ensa.edulinker.backend.dao.IModuleElementDAO;
import ensa.edulinker.backend.dao.IModuleElementDAOFactory;
import ensa.edulinker.backend.web.entities.ModuleElement;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleElementServiceImpl implements ModuleElementService {


    private IModuleElementDAO moduleElementDAO = IModuleElementDAOFactory.getInstance();

    @Override
    public List<ModuleElement> findAll() {
        return moduleElementDAO.findAll();
    }

    @Override
    public List<ModuleElement> findByModule(Long moduleId) {
        return moduleElementDAO.findAllByModule(moduleId);
    }

    @Override
    public List<ModuleElement> findByProfessor(Long professorId) {
        List<ModuleElement> moduleElements = moduleElementDAO.findAllByProfessor(professorId);
        return moduleElements;
    }

        @Override
    public ModuleElement getById(Long id) {
        return moduleElementDAO.getById(id);
    }
}
