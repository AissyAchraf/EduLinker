package ensa.edulinker.backend.security;

public interface AccountUserService {
    Account loadAccountByEmail(String email);
}
