package ensa.edulinker.backend.service;

import ensa.edulinker.backend.dao.IAdministratorDAO;
import ensa.edulinker.backend.dao.IAdministratorDAOImpl;
import ensa.edulinker.backend.web.entities.Administrator;
import org.springframework.stereotype.Service;

@Service
public class AdministratorServiceImpl implements AdministratorService {

    private IAdministratorDAO administratorDAO = new IAdministratorDAOImpl();

    @Override
    public Administrator getByEmail(String email) {
        return administratorDAO.findByEmail(email);
    }
}
