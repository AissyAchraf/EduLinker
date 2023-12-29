package ensa.edulinker.backend.web.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

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
