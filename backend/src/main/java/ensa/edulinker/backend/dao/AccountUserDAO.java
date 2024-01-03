package ensa.edulinker.backend.dao;

import ensa.edulinker.backend.security.Account;

public interface AccountUserDAO {

    public Account findByEmail(String email);
}
