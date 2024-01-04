package ensa.edulinker.backend.dao;

import ensa.edulinker.backend.web.entities.Grade;

import java.util.List;

public interface IGradeDAO extends GenericRepositoryTemplate<Grade, Long> {

    public List<Grade> findByModuleElement(Long moduleElementId);

    public Grade getByStudentAndProcedure(String studentId, Long procedureId);

    Boolean getGradeStatusByModuleElement(Long moduleElementId);
}

