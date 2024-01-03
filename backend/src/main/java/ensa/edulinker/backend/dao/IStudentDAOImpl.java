package ensa.edulinker.backend.dao;

import ensa.edulinker.backend.web.entities.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class IStudentDAOImpl implements IStudentDAO {
    private Connection connection = MySQLDBConnection.getConnection();

    @Override
    public Student save(Student student) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO STUDENTS(last_name, first_name, email, phone_number, cne, appoge, birth_day, sector_id, semester) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, student.getLastName());
            ps.setString(2, student.getFirstName());
            ps.setString(3, student.getEmail());
            ps.setString(4, student.getPhoneNumber());
            ps.setString(5, student.getCNE());
            ps.setString(6, student.getAppoge());
            ps.setDate(7, new java.sql.Date(student.getBirthday().getTime()));
            ps.setLong(8, student.getSector().getId());
            ps.setString(9, String.valueOf(student.getSemester()));
            ps.executeUpdate();

            PreparedStatement ps2 = connection.prepareStatement("SELECT * FROM STUDENTS WHERE cne = ?");
            ps2.setString(1, student.getCNE());
            ResultSet rs = ps2.executeQuery();
            if (rs.next()) {
                student.setLastName(rs.getString("last_name"));
                student.setFirstName(rs.getString("first_name"));
                student.setEmail(rs.getString("email"));
                student.setPhoneNumber(rs.getString("phone_number"));
                student.setCNE(rs.getString("cne"));
                student.setAppoge(rs.getString("appoge"));
                student.setBirthday(rs.getDate("birth_day"));
            } else {
                student = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public Student update(Student student) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE STUDENTS SET last_name = ?, first_name = ?, email = ?, " +
                            "phone_number = ?, appoge = ?, birth_day = ?, sector_id = ?, semester = ? WHERE cne = ?");
            ps.setString(1, student.getLastName());
            ps.setString(2, student.getFirstName());
            ps.setString(3, student.getEmail());
            ps.setString(4, student.getPhoneNumber());
            ps.setString(5, student.getAppoge());
            ps.setDate(6, new java.sql.Date(student.getBirthday().getTime()));
            ps.setLong(7, student.getSector().getId());
            ps.setString(8, String.valueOf(student.getSemester()));

            ps.setString(9, student.getCNE());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM STUDENTS");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.setLastName(rs.getString("last_name"));
                student.setFirstName(rs.getString("first_name"));
                student.setEmail(rs.getString("email"));
                student.setPhoneNumber(rs.getString("phone_number"));
                student.setCNE(rs.getString("cne"));
                student.setAppoge(rs.getString("appoge"));
                student.setBirthday(rs.getDate("birth_day"));
                students.add(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public Student getById(String cne) {
        Student student = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM STUDENTS WHERE cne = ?");
            ps.setString(1, cne);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                student = new Student();
                student.setLastName(rs.getString("last_name"));
                student.setFirstName(rs.getString("first_name"));
                student.setEmail(rs.getString("email"));
                student.setPhoneNumber(rs.getString("phone_number"));
                student.setCNE(rs.getString("cne"));
                student.setAppoge(rs.getString("appoge"));
                student.setBirthday(rs.getDate("birth_day"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public void delete(String cne) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM STUDENTS WHERE cne = ?");
            ps.setString(1, cne);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> findBySectorAndSemester(Long sectorId, String semester){
        List<Student> students = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM STUDENTS WHERE sector_id = ? AND semester = ?");
            ps.setLong(1, sectorId);
            ps.setString(2, semester);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.setLastName(rs.getString("last_name"));
                student.setFirstName(rs.getString("first_name"));
                student.setEmail(rs.getString("email"));
                student.setPhoneNumber(rs.getString("phone_number"));
                student.setCNE(rs.getString("cne"));
                student.setAppoge(rs.getString("appoge"));
                student.setBirthday(rs.getDate("birth_day"));
                students.add(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }
}
