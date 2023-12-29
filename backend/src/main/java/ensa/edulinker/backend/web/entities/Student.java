package ensa.edulinker.backend.web.entities;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Student extends Person {

    private String CNE;
    private String appoge;
    private Date birthday;
}
