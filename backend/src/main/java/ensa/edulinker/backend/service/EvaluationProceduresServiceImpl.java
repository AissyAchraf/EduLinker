package ensa.edulinker.backend.service;

import ensa.edulinker.backend.dao.IEvaluationProcedureDAO;
import ensa.edulinker.backend.dao.IEvaluationProcedureDAOImpl;
import ensa.edulinker.backend.web.entities.EvaluationProcedure;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationProceduresServiceImpl implements EvaluationProceduresService {

    private IEvaluationProcedureDAO evaluationProcedureDAO = new IEvaluationProcedureDAOImpl();

    @Override
    public List<EvaluationProcedure> findByModuleElement(Long moduleElementId) {
        return evaluationProcedureDAO.findByModuleElement(moduleElementId);
    }
}
