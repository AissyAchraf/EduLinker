package ensa.edulinker.backend.service;

import ensa.edulinker.backend.dao.IModuleDAO;
import ensa.edulinker.backend.dao.IModuleDAOFactory;
import ensa.edulinker.backend.dao.IModuleElementDAO;
import ensa.edulinker.backend.dao.IModuleElementDAOFactory;
import ensa.edulinker.backend.web.entities.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleServiceImpl implements ModuleService {

    private IModuleDAO moduleDAO = IModuleDAOFactory.getInstance();
    private IModuleElementDAO moduleElementDAO = IModuleElementDAOFactory.getInstance();
    @Override
    public List<Module> findAll() {
        List<Module> moduleList = moduleDAO.findAll();
        for (Module m : moduleList) {
            m.setModuleElements(moduleElementDAO.findAllByModule(m.getId()));
        }
        return moduleDAO.findAll();
    }

    @Override
    public Module getById(Long id) {
        return moduleDAO.getById(id);
    }

    @Override
    public List<Module> findByProfessor(Long professorId) {
        return null;
    }
}
