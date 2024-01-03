package ensa.edulinker.backend.service;

import ensa.edulinker.backend.dao.IStudentDAO;
import ensa.edulinker.backend.dao.IStudentDAOImpl;
import ensa.edulinker.backend.web.entities.Semester;
import ensa.edulinker.backend.web.entities.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private IStudentDAO studentDAO = new IStudentDAOImpl();

    @Override
    public List<Student> findBySectorAndSemester(Long sectorId, Semester semester) {
        return studentDAO.findBySectorAndSemester(sectorId, String.valueOf(semester));
    }
}
