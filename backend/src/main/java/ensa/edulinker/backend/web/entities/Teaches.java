package ensa.edulinker.backend.web.entities;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Teaches {

    private Long id;
    private Professor professor;
    private ModuleElement moduleElement;
    private int year;
    private Boolean status;
}
