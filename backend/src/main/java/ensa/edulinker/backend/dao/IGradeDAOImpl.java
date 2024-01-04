package ensa.edulinker.backend.dao;

import ensa.edulinker.backend.web.entities.Grade;

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
    public Grade save(Grade grade) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO GRADES(grade, evaluation_procedure_id, status, student_id) VALUES (?, ?, ?, ?)");
            ps.setFloat(1, grade.getGrade());
            ps.setLong(2, grade.getProcedure().getId());
            ps.setBoolean(3, grade.getStatus());
            ps.setString(4, grade.getStudent().getCNE());
            ps.executeUpdate();
            PreparedStatement ps2 = connection
                    .prepareStatement("SELECT MAX(ID) AS MAX_ID FROM GRADES");
            ResultSet rs = ps2.executeQuery();
            if(rs.next()) {
                grade.setId(rs.getLong("MAX_ID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return grade;
    }

    @Override
    public Grade update(Grade grade) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE GRADES SET grade = ?, evaluation_procedure_id = ?, status = ?, student_id = ? WHERE id = ?");

            ps.setFloat(1, grade.getGrade());
            ps.setLong(2, grade.getProcedure().getId());
            ps.setBoolean(3, grade.getStatus());
            ps.setString(4, grade.getStudent().getCNE());
            ps.setLong(5, grade.getId());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return grade;
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
                            "        WHERE m.id = ?");
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

    @Override
    public Grade getByStudentAndProcedure(String studentId, Long procedureId) {
        Grade grade = null;
        try {
            PreparedStatement ps = connection
                    .prepareStatement("SELECT *\n" +
                            "        FROM GRADES \n"+
                            "        WHERE student_id = ? AND evaluation_procedure_id = ?");
            ps.setString(1, studentId);
            ps.setLong(2, procedureId);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            if (rs.next()) {
                grade = new Grade();
                grade.setId(rs.getLong("id"));
                grade.setGrade(rs.getFloat("grade"));
                Long evaluationProceduresId = rs.getLong("evaluation_procedure_id");
                grade.setProcedure(evaluationProcedureDAO.getById(evaluationProceduresId));
                grade.setStudent(studentDAO.getById(studentId));
                grade.setStatus(rs.getBoolean("status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return grade;
    }

    @Override
    public Boolean getGradeStatusByModuleElement(Long moduleElementId) {
        Boolean status = false;
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT g.status FROM GRADES g " +
                            "JOIN EVALUATION_PROCEDURES e ON g.evaluation_procedure_id = e.id " +
                            "JOIN MODULE_ELEMENTS m ON e.element_id = m.id " +
                            "WHERE m.id = ? LIMIT 1");
            ps.setLong(1, moduleElementId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                status = rs.getBoolean("status");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
}
