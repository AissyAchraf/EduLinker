package ensa.edulinker.backend.dao;

import ensa.edulinker.backend.web.entities.EvaluationProcedure;

import java.util.List;

public interface IEvaluationProcedureDAO extends GenericRepositoryTemplate<EvaluationProcedure, Long> {

    public List<EvaluationProcedure> findByModuleElement(Long moduleElementId);
}
