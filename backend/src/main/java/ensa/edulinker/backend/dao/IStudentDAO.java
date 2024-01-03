package ensa.edulinker.backend.dao;

import ensa.edulinker.backend.web.entities.Student;

import java.util.List;

public interface IStudentDAO extends GenericRepositoryTemplate<Student, String> {

    List<Student> findBySectorAndSemester(Long sectorId, String semester);
}
