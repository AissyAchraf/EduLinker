package ensa.edulinker.backend.service;

import ensa.edulinker.backend.web.entities.Administrator;

public interface AdministratorService {

    Administrator getByEmail(String email);
}
