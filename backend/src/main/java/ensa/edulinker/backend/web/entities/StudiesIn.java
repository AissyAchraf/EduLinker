package ensa.edulinker.backend.web.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudiesIn {

    private Long id;
    private int year;
    private Sector sector;
    private Semester semester;
    private Boolean status;
    private Student student;
}
