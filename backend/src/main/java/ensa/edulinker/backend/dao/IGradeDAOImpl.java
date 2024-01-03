package ensa.edulinker.backend.dao;

import ensa.edulinker.backend.web.entities.Grade;
import ensa.edulinker.backend.web.entities.ModuleElement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class IGradeDAOImpl implements IGradeDAO {

    private Connection connection = MySQLDBConnection.getConnection();
    private IEvaluationProcedureDAO evaluationProcedureDAO = new IEvaluationProcedureDAOImpl();
    private IStudentDAO studentDAO = new IStudentDAOImpl();

    @Override
    public Grade save(Grade e) {
        return null;
    }

    @Override
    public Grade update(Grade e) {
        return null;
    }

    @Override
    public List<Grade> findAll() {
        return null;
    }

    @Override
    public Grade getById(Long aLong) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public List<Grade> findByModuleElement(Long moduleElementId) {
        List<Grade> grades = new ArrayList<>();
        try {
            PreparedStatement ps = connection
                    .prepareStatement("SELECT g.*\n" +
                            "        FROM GRADES g\n" +
                            "        JOIN EVALUATION_PROCEDURES e ON g.evaluation_procedure_id = e.id\n" +
                            "        JOIN MODULE_ELEMENTS m ON e.element_id = m.id\n" +
                            "        WHERE m.id = ? GROUP BY g.id");
            ps.setLong(1, moduleElementId);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                Grade grade = new Grade();
                grade.setId(rs.getLong("id"));
                grade.setGrade(rs.getFloat("grade"));
                Long evaluationProceduresId = rs.getLong("evaluation_procedure_id");
                grade.setProcedure(evaluationProcedureDAO.getById(evaluationProceduresId));
                String studentId = rs.getString("student_id");
                grade.setStudent(studentDAO.getById(studentId));
                grade.setStatus(rs.getBoolean("status"));
                grades.add(grade);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return grades;
    }
}
