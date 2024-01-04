package ensa.edulinker.backend.service;

import ensa.edulinker.backend.web.entities.Module;

import java.util.List;

public interface ModuleService {

    public Module save(Module module);
    public List<Module> findAll();
    public Module getById(Long id);
    public List<Module> findByProfessor(Long professorId);
}
