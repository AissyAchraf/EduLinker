package ensa.edulinker.backend.dao;

import ensa.edulinker.backend.web.entities.Administrator;

public interface IAdministratorDAO extends GenericRepositoryTemplate<Administrator, Long> {

    public Administrator findByEmail(String email);
}
