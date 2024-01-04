package ensa.edulinker.backend.service;

import ensa.edulinker.backend.dao.IGradeDAO;
import ensa.edulinker.backend.dao.IGradeDAOImpl;
import ensa.edulinker.backend.web.entities.Grade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeServiceImpl implements GradeService {

    private IGradeDAO gradeDAO = new IGradeDAOImpl();

    @Override
    public List<Grade> findByModuleElement(Long moduleElementId) {
        return gradeDAO.findByModuleElement(moduleElementId);
    }
    @Override
    public Boolean getGradeStatusByModuleElement(Long moduleElementId) {
        Boolean status = gradeDAO.getGradeStatusByModuleElement(moduleElementId);
        return status;
    }
}
