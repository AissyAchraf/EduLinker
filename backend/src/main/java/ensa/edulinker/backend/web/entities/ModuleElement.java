package ensa.edulinker.backend.web.entities;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ModuleElement {

    private Long id;
    private String name;
    private float coefficient;
    private Module module;
    private List<EvaluationProcedure> evaluationProcedures;
    private Professor professor;

    public Integer getPercentage() {
        return (int) (coefficient*100);
    }

    public boolean equals(ModuleElement moduleElement) {
        return this.id.equals(moduleElement.getId())
                && this.name.equals(moduleElement.getName())
                && this.coefficient == moduleElement.getCoefficient();
    }
}
