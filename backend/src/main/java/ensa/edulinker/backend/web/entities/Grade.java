package ensa.edulinker.backend.web.entities;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Grade {

    private Long id;
    private float grade;
    private EvaluationProcedure procedure;
    private Boolean status;
    private Student student;
}
