package ensa.edulinker.backend.dao;

import ensa.edulinker.backend.web.entities.Sector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ISectorDAOImpl implements ISectorDAO {

    private Connection connection = MySQLDBConnection.getConnection();

    @Override
    public Sector Save(Sector s) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO sectors(name) VALUES (?);");
            ps.setString(1, s.getName());
            ps.executeUpdate();
            PreparedStatement ps2 = connection
                    .prepareStatement("SELECT MAX(ID) AS MAX_ID FROM sectors");
            ResultSet rs = ps2.executeQuery();
            if(rs.next()) {
                s.setId(rs.getLong("MAX_ID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    @Override
    public List<Sector> findAll() {
        List<Sector> sectors = new ArrayList<>();
        try {
            PreparedStatement ps = connection
                    .prepareStatement("SELECT * FROM sectors");
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                Sector s = new Sector();
                s.setId(rs.getLong("id"));
                s.setName(rs.getString("name"));
                sectors.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sectors;
    }

    @Override
    public Sector getByName(String name) {
        Sector sector = null;
        try {
            PreparedStatement ps = connection
                    .prepareStatement("SELECT * FROM sectors WHERE name LIKE ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                sector = new Sector();
                sector.setId(rs.getLong("id"));
                sector.setName(rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sector;
    }

    @Override
    public Sector update(Sector s) {
        Sector sector = null;
        try {
            PreparedStatement ps = connection
                    .prepareStatement("UPDATE sectors SET name = ? WHERE id = ?");
            ps.setString(1, s.getName());
            ps.setLong(2, s.getId());
            ps.executeUpdate();
            PreparedStatement ps2 = connection.prepareStatement("SELECT * FROM sectors WHERE id = ?");
            ps2.setLong(1, s.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                sector = new Sector();
                sector.setId(rs.getLong("id"));
                sector.setName(rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sector;
    }

    @Override
    public void delete(Long id) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("DELETE sectors WHERE id = ?");
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
