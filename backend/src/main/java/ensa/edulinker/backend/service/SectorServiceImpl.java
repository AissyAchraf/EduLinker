package ensa.edulinker.backend.service;

import ensa.edulinker.backend.dao.ISectorDAO;
import ensa.edulinker.backend.dao.ISectorDAOImpl;
import ensa.edulinker.backend.web.entities.Sector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectorServiceImpl implements SectorService {

    private ISectorDAO sectorDAO = new ISectorDAOImpl();

    @Override
    public List<Sector> findAll() {
        return sectorDAO.findAll();
    }

    @Override
    public Sector getById(Long id) {
        return sectorDAO.getById(id);
    }
}
