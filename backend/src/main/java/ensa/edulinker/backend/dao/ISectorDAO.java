package ensa.edulinker.backend.dao;

import ensa.edulinker.backend.web.entities.Sector;

import java.util.List;

public interface ISectorDAO {

    public Sector save(Sector s);
    public List<Sector> findAll();
    public Sector getByName(String name);
    public Sector update(Sector s);
    public void delete(Long id);
}
