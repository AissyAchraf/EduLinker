package ensa.edulinker.backend.web.entities;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Professor extends Person {

    private Long code;
    private String speciality;
    private List<ModuleElement> moduleElements;
}
