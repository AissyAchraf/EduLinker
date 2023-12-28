package ensa.edulinker.backend.web.entities;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ModuleElement {

    private Long id;
    private String name;
    private float coefficient;
    private boolean status;
}
