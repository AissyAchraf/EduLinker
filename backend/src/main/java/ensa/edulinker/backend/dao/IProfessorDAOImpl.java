package ensa.edulinker.backend.dao;

import ensa.edulinker.backend.web.entities.Professor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class IProfessorDAOImpl implements IProfessorDAO {

    private Connection connection = MySQLDBConnection.getConnection();

    @Override
    public Professor save(Professor professor) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO PROFESSORS(last_name, first_name, email, phone_number, speciality) " +
                            "VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, professor.getLastName());
            ps.setString(2, professor.getFirstName());
            ps.setString(3, professor.getEmail());
            ps.setString(4, professor.getPhoneNumber());
            ps.setString(5, professor.getSpeciality());
            ps.executeUpdate();

            PreparedStatement ps2 = connection.prepareStatement("SELECT MAX(code) as MAX_CODE FROM PROFESSORS");
            ResultSet rs = ps2.executeQuery();
            if (rs.next()) {
                professor.setCode(rs.getLong("MAX_CODE"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return professor;
    }

    @Override
    public Professor update(Professor professor) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE PROFESSORS SET last_name = ?, first_name = ?, email = ?, " +
                            "phone_number = ?, speciality = ? WHERE code = ?");
            ps.setString(1, professor.getLastName());
            ps.setString(2, professor.getFirstName());
            ps.setString(3, professor.getEmail());
            ps.setString(4, professor.getPhoneNumber());
            ps.setString(5, professor.getSpeciality());
            ps.setLong(6, professor.getCode());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return professor;
    }

    @Override
    public List<Professor> findAll() {
        List<Professor> professors = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM PROFESSORS");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Professor professor = new Professor();
                professor.setCode(rs.getLong("code"));
                professor.setLastName(rs.getString("last_name"));
                professor.setFirstName(rs.getString("first_name"));
                professor.setEmail(rs.getString("email"));
                professor.setPhoneNumber(rs.getString("phone_number"));
                professor.setSpeciality(rs.getString("speciality"));
                professors.add(professor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return professors;
    }

    @Override
    public Professor getById(Long code) {
        Professor professor = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM PROFESSORS WHERE code = ?");
            ps.setLong(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                professor = new Professor();
                professor.setCode(rs.getLong("code"));
                professor.setLastName(rs.getString("last_name"));
                professor.setFirstName(rs.getString("first_name"));
                professor.setEmail(rs.getString("email"));
                professor.setPhoneNumber(rs.getString("phone_number"));
                professor.setSpeciality(rs.getString("speciality"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return professor;
    }

    @Override
    public void delete(Long code) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM PROFESSORS WHERE code = ?");
            ps.setLong(1, code);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Professor getByEmail(String email) {
        Professor professor = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM PROFESSORS WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                professor = new Professor();
                professor.setCode(rs.getLong("code"));
                professor.setLastName(rs.getString("last_name"));
                professor.setFirstName(rs.getString("first_name"));
                professor.setEmail(rs.getString("email"));
                professor.setPhoneNumber(rs.getString("phone_number"));
                professor.setSpeciality(rs.getString("speciality"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return professor;
    }
}
