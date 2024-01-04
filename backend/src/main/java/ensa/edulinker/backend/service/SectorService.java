package ensa.edulinker.backend.service;

import ensa.edulinker.backend.web.entities.Sector;

import java.util.List;

public interface SectorService {

    public List<Sector> findAll();
    public Sector getById(Long id);
}
