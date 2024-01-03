package ensa.edulinker.backend.service;

import ensa.edulinker.backend.dao.IProfessorDAO;
import ensa.edulinker.backend.dao.IProfessorDAOImpl;
import ensa.edulinker.backend.web.entities.Professor;
import org.springframework.stereotype.Service;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    private IProfessorDAO professorDAO = new IProfessorDAOImpl();

    @Override
    public Professor getByEmail(String email) {
        return professorDAO.getByEmail(email);
    }
}
