package ensa.edulinker.backend.security;

import ensa.edulinker.backend.dao.AccountUserDAO;
import ensa.edulinker.backend.dao.AccountUserDAOImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountUserServiceImpl implements AccountUserService {

    private PasswordEncoder passwordEncoder;
    private AccountUserDAO accountUserDAO = new AccountUserDAOImpl();

    @Override
    public Account loadAccountByEmail(String email) {
        return accountUserDAO.findByEmail(email);
    }
}
