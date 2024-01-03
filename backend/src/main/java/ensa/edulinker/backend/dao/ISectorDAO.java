package ensa.edulinker.backend.dao;

import ensa.edulinker.backend.web.entities.Sector;

interface ISectorDAO extends GenericRepositoryTemplate<Sector, Long> {

    Sector getByName(String name);
}
