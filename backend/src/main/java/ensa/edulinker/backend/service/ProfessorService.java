package ensa.edulinker.backend.service;

import ensa.edulinker.backend.web.entities.Professor;

public interface ProfessorService {

    public Professor getByEmail(String email);

}
