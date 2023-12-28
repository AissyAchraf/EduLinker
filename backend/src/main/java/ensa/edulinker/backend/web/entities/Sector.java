package ensa.edulinker.backend.web.entities;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Sector implements Serializable {

    private Long id;
    private String name;
    private String nameAbbreviation;
}
