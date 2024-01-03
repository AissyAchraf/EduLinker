package ensa.edulinker.backend.service;

import ensa.edulinker.backend.web.entities.EvaluationProcedure;

import java.util.List;

public interface EvaluationProceduresService {

    public List<EvaluationProcedure> findByModuleElement(Long moduleElementId);
}
