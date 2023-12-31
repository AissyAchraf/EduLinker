package ensa.edulinker.backend.dao;

import ensa.edulinker.backend.web.entities.Teaches;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ITeachesDAOImpl implements ITeachesDAO {

    private Connection connection = MySQLDBConnection.getConnection();

    @Override
    public Teaches save(Teaches teaches) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO TEACHES(year, professor_id, module_element_id, status) VALUES (?, ?, ?, ?)");
            ps.setInt(1, teaches.getYear());
            ps.setLong(2, teaches.getProfessor().getCode());
            ps.setLong(3, teaches.getModuleElement().getId());
            ps.setBoolean(4, teaches.getStatus());
            ps.executeUpdate();

            PreparedStatement ps2 = connection.prepareStatement("SELECT MAX(id) AS MAX_ID FROM TEACHES");
            ResultSet rs = ps2.executeQuery();
            if (rs.next()) {
                teaches.setId(rs.getLong("MAX_ID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teaches;
    }

    @Override
    public Teaches update(Teaches teaches) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE TEACHES SET year = ?, professor_id = ?, module_element_id = ?, status = ? WHERE id = ?");
            ps.setInt(1, teaches.getYear());
            ps.setLong(2, teaches.getProfessor().getCode());
            ps.setLong(3, teaches.getModuleElement().getId());
            ps.setBoolean(4, teaches.getStatus());
            ps.setLong(5, teaches.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teaches;
    }

    @Override
    public List<Teaches> findAll() {
        List<Teaches> teachesList = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM TEACHES");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Teaches teaches = new Teaches();
                teaches.setId(rs.getLong("id"));
                teaches.setYear(rs.getInt("year"));
                teaches.setStatus(rs.getBoolean("status"));
                teachesList.add(teaches);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teachesList;
    }

    @Override
    public Teaches getById(Long id) {
        Teaches teaches = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM TEACHES WHERE id = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                teaches = new Teaches();
                teaches.setId(rs.getLong("id"));
                teaches.setYear(rs.getInt("year"));
                teaches.setStatus(rs.getBoolean("status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teaches;
    }

    @Override
    public void delete(Long id) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM TEACHES WHERE id = ?");
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
