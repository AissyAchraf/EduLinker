package ensa.edulinker.backend.security;

import ensa.edulinker.backend.web.entities.Person;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    private Long id;
    private String email;
    private String password;
    private Role role;
}
