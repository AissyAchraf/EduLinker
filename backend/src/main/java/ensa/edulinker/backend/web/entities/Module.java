package ensa.edulinker.backend.web.entities;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Module {

    private Long id;
    private String name;
    private List<ModuleElement> moduleElements;
}
