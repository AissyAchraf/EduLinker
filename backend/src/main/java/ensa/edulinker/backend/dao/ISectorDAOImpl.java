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
    public Sector save(Sector s) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO SECTORS(name, name_abbreviation) VALUES (?, ?)");
            ps.setString(1, s.getName());
            ps.setString(2, s.getNameAbbreviation());
            ps.executeUpdate();
            PreparedStatement ps2 = connection
                    .prepareStatement("SELECT MAX(ID) AS MAX_ID FROM SECTORS");
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
                    .prepareStatement("SELECT * FROM SECTORS");
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                Sector s = new Sector();
                s.setId(rs.getLong("id"));
                s.setName(rs.getString("name"));
                s.setNameAbbreviation(rs.getString("name_abbreviation"));
                sectors.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sectors;
    }

    @Override
    public Sector getById(Long id) {
        Sector sector = null;
        try {
            PreparedStatement ps = connection
                    .prepareStatement("SELECT * FROM SECTORS WHERE id = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                sector = new Sector();
                sector.setId(rs.getLong("id"));
                sector.setName(rs.getString("name"));
                sector.setNameAbbreviation(rs.getString("name_abbreviation"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sector;
    }

    @Override
    public Sector getByName(String name) {
        Sector sector = null;
        try {
            PreparedStatement ps = connection
                    .prepareStatement("SELECT * FROM SECTORS WHERE name LIKE ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                sector = new Sector();
                sector.setId(rs.getLong("id"));
                sector.setName(rs.getString("name"));
                sector.setNameAbbreviation(rs.getString("name_abbreviation"));
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
                    .prepareStatement("UPDATE SECTORS SET name = ?, name_abbreviation = ? WHERE id = ?");
            ps.setString(1, s.getName());
            ps.setString(2, s.getNameAbbreviation());
            ps.setLong(3, s.getId());
            ps.executeUpdate();
            PreparedStatement ps2 = connection.prepareStatement("SELECT * FROM SECTORS WHERE id = ?");
            ps2.setLong(1, s.getId());
            ResultSet rs = ps2.executeQuery();
            if (rs.next()) {
                sector = new Sector();
                sector.setId(rs.getLong("id"));
                sector.setName(rs.getString("name"));
                sector.setNameAbbreviation(rs.getString("name_abbreviation"));
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
                    .prepareStatement("DELETE FROM SECTORS WHERE id = ?");
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
