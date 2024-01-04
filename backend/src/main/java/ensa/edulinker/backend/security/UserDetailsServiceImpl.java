package ensa.edulinker.backend.security;

import ensa.edulinker.backend.web.entities.Person;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountUserService accountUserService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountUserService.loadAccountByEmail(email);
        if(account == null) throw new UsernameNotFoundException(String.format("Account %s not found", email));
        UserDetails userDetails = User
                .withUsername(email)
                .password(account.getPassword())
                .roles(String.valueOf(account.getRole()))
                .build();
        return userDetails;
    }
}
