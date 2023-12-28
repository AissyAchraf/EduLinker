package ensa.edulinker.backend.dao;

import ensa.edulinker.backend.web.entities.Sector;

public interface ISectorDAO extends GenericRepositoryTemplate<Sector, Long> {

    Sector getByName(String name);
}
