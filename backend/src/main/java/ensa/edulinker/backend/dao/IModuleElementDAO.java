package ensa.edulinker.backend.dao;

import ensa.edulinker.backend.web.entities.ModuleElement;

import java.util.List;

public interface IModuleElementDAO extends GenericRepositoryTemplate<ModuleElement, Long> {

    public List<ModuleElement> findAllByModule(Long moduleId);
    public List<ModuleElement> findAllByProfessor(Long professorId);
}
