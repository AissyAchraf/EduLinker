package ensa.edulinker.backend.web.entities;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Sector implements Serializable {

    private Long id;
    private String name;
    private String nameAbbreviation;
    private List<Module> modules;
}
