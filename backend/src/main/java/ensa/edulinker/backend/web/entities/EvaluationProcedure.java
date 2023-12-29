package ensa.edulinker.backend.web.entities;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationProcedure {

    private Long id;
    private EvaluationProcedureType type;
    private float coefficient;
    private ModuleElement element;
}
