package ensa.edulinker.backend.service;

import ensa.edulinker.backend.web.entities.Grade;

import java.util.List;

public interface GradeService {

    public List<Grade> findByModuleElement(Long moduleElementId);
}
