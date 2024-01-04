package ensa.edulinker.backend.service;

import ensa.edulinker.backend.dao.*;
import ensa.edulinker.backend.web.entities.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    private IStudentDAO studentDAO = new IStudentDAOImpl();
    private IModuleElementDAO moduleElementDAO = IModuleElementDAOFactory.getInstance();
    private IEvaluationProcedureDAO evaluationProceduresDAO = new IEvaluationProcedureDAOImpl();
    private IGradeDAO gradeDAO = new IGradeDAOImpl();

    @Override
    public List<Student> findBySectorAndSemester(Long sectorId, Semester semester) {
        return studentDAO.findBySectorAndSemester(sectorId, String.valueOf(semester));
    }

    @Override
    public void processGradesSubmission(Map<String, String[]> paramMap) {
        String[] statusValues = paramMap.get("status");
        boolean isFinal = statusValues != null && statusValues.length > 0 && "1".equals(statusValues[0]);
        paramMap.forEach((key, value) -> {
            if (key.startsWith("grade_")) {
                String[] parts = key.split("_");
                if (parts.length < 4) return;

                String CNE = parts[1];
                Long moduleElementId = Long.parseLong(parts[2]);
                String procedureType = parts[3];
                float gradeValue = Float.parseFloat(value[0]);

                List<EvaluationProcedure> proceduresList = evaluationProceduresDAO.findByModuleElement(moduleElementId);

                Student student = studentDAO.getById(CNE);
                EvaluationProcedure procedure = null;
                for (EvaluationProcedure e : proceduresList) {
                    if(e.getType().equals(EvaluationProcedureType.valueOf(procedureType))) {
                        procedure = e;
                    }
                }

                if(procedure != null) {
                    Grade grade = gradeDAO.getByStudentAndProcedure(
                            CNE,
                            procedure.getId()
                    );

                    if(grade != null) {
                        grade.setStudent(student);
                        grade.setProcedure(procedure);
                        grade.setGrade(gradeValue);
                        grade.setStatus(isFinal);
                        gradeDAO.update(grade);
                    } else {
                        Grade newGrade = new Grade();
                        newGrade.setStudent(student);
                        newGrade.setProcedure(procedure);
                        newGrade.setGrade(gradeValue);
                        newGrade.setStatus(isFinal);

                        gradeDAO.save(newGrade);
                    }
                }
            }
        });

    }


}
