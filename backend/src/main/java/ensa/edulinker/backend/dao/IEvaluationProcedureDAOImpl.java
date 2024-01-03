package ensa.edulinker.backend.dao;

import ensa.edulinker.backend.web.entities.EvaluationProcedure;
import ensa.edulinker.backend.web.entities.EvaluationProcedureType;
import ensa.edulinker.backend.web.entities.ModuleElement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class IEvaluationProcedureDAOImpl implements IEvaluationProcedureDAO {

    private Connection connection = MySQLDBConnection.getConnection();
    private IModuleElementDAO moduleElementDAO = new IModuleElementDAOImpl();

    @Override
    public EvaluationProcedure save(EvaluationProcedure evaluationProcedure) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO EVALUATION_PROCEDURES(type, coefficient, module_element_id) VALUES (?, ?, ?)");
            ps.setString(1, evaluationProcedure.getType().name());
            ps.setFloat(2, evaluationProcedure.getCoefficient());
            ps.setLong(3, evaluationProcedure.getElement().getId());
            ps.executeUpdate();

            PreparedStatement ps2 = connection.prepareStatement("SELECT MAX(ID) AS MAX_ID FROM EVALUATION_PROCEDURES");
            ResultSet rs = ps2.executeQuery();
            if (rs.next()) {
                evaluationProcedure.setId(rs.getLong("MAX_ID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return evaluationProcedure;
    }

    @Override
    public EvaluationProcedure update(EvaluationProcedure evaluationProcedure) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE EVALUATION_PROCEDURES SET type = ?, coefficient = ?, module_element_id = ? WHERE id = ?");
            ps.setString(1, evaluationProcedure.getType().name());
            ps.setFloat(2, evaluationProcedure.getCoefficient());
            ps.setLong(3, evaluationProcedure.getElement().getId());
            ps.setLong(4, evaluationProcedure.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return evaluationProcedure;
    }

    @Override
    public List<EvaluationProcedure> findAll() {
        List<EvaluationProcedure> evaluationProcedures = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM EVALUATION_PROCEDURES");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                EvaluationProcedure evaluationProcedure = new EvaluationProcedure();
                evaluationProcedure.setId(rs.getLong("id"));
                evaluationProcedure.setType(EvaluationProcedureType.valueOf(rs.getString("type")));
                evaluationProcedure.setCoefficient(rs.getFloat("coefficient"));
                evaluationProcedures.add(evaluationProcedure);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return evaluationProcedures;
    }

    @Override
    public EvaluationProcedure getById(Long id) {
        EvaluationProcedure evaluationProcedure = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM EVALUATION_PROCEDURES WHERE id = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                evaluationProcedure = new EvaluationProcedure();
                evaluationProcedure.setId(rs.getLong("id"));
                evaluationProcedure.setType(EvaluationProcedureType.valueOf(rs.getString("type")));
                evaluationProcedure.setCoefficient(rs.getFloat("coefficient"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return evaluationProcedure;
    }

    @Override
    public void delete(Long id) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM EVALUATION_PROCEDURES WHERE id = ?");
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<EvaluationProcedure> findByModuleElement(Long moduleElementId) {
        List<EvaluationProcedure> evaluationProcedures = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM EVALUATION_PROCEDURES WHERE element_id");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                EvaluationProcedure evaluationProcedure = new EvaluationProcedure();
                evaluationProcedure.setId(rs.getLong("id"));
                evaluationProcedure.setType(EvaluationProcedureType.valueOf(rs.getString("type")));
                evaluationProcedure.setCoefficient(rs.getFloat("coefficient"));
                evaluationProcedure.setElement(moduleElementDAO.getById(moduleElementId));
                evaluationProcedures.add(evaluationProcedure);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return evaluationProcedures;
    }
}
