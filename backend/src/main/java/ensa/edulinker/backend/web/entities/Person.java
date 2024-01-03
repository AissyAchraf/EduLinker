package ensa.edulinker.backend.web.entities;

import ensa.edulinker.backend.security.Account;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class Person {

    private String lastName;
    private String firstName;
    private String email;
    private String phoneNumber;
}
