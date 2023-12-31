package ensa.edulinker.backend.web.entities;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Professor extends Person {

    private Long code;
    private String speciality;
}
