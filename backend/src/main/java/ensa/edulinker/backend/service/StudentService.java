package ensa.edulinker.backend.service;

import ensa.edulinker.backend.web.entities.Semester;
import ensa.edulinker.backend.web.entities.Student;

import java.util.List;
import java.util.Map;

public interface StudentService {

    public List<Student> findBySectorAndSemester(Long sectorId, Semester semester);

    void processGradesSubmission(Map<String, String[]> paramMap);

}
