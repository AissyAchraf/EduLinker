package ensa.edulinker.backend.dao;

import ensa.edulinker.backend.web.entities.Semester;
import ensa.edulinker.backend.web.entities.StudiesIn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class IStudiesInDAOImpl implements IStudiesInDAO {

    private Connection connection = MySQLDBConnection.getConnection();

    @Override
    public StudiesIn save(StudiesIn studiesIn) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO STUDIES_IN(year, sector_id, semester, status, student_id) " +
                            "VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, studiesIn.getYear());
            ps.setLong(2, studiesIn.getSector().getId());
            ps.setString(3, String.valueOf(studiesIn.getSemester()));
            ps.setBoolean(4, studiesIn.getStatus());
            ps.setString(5, studiesIn.getStudent().getCNE());
            ps.executeUpdate();

            PreparedStatement ps2 = connection.prepareStatement("SELECT MAX(id) AS MAX_ID FROM STUDIES_IN");
            ResultSet rs = ps2.executeQuery();
            if (rs.next()) {
                studiesIn.setId(rs.getLong("MAX_ID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studiesIn;
    }

    @Override
    public StudiesIn update(StudiesIn studiesIn) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE STUDIES_IN SET year = ?, sector_id = ?, semester = ?, status = ?, student_id = ? WHERE id = ?");
            ps.setInt(1, studiesIn.getYear());
            ps.setLong(2, studiesIn.getSector().getId());
            ps.setString(3, String.valueOf(studiesIn.getSemester()));
            ps.setBoolean(4, studiesIn.getStatus());
            ps.setString(5, studiesIn.getStudent().getCNE());
            ps.setLong(6, studiesIn.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studiesIn;
    }

    @Override
    public List<StudiesIn> findAll() {
        List<StudiesIn> studiesIns = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM STUDIES_IN");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                StudiesIn studiesIn = new StudiesIn();
                studiesIn.setId(rs.getLong("id"));
                studiesIn.setYear(rs.getInt("year"));
                studiesIn.setStatus(rs.getBoolean("status"));
                String semesterStr =  rs.getString("semester");
                studiesIn.setSemester(Semester.valueOf(semesterStr));
                studiesIns.add(studiesIn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studiesIns;
    }

    @Override
    public StudiesIn getById(Long id) {
        StudiesIn studiesIn = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM STUDIES_IN WHERE id = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                studiesIn = new StudiesIn();
                studiesIn.setId(rs.getLong("id"));
                studiesIn.setYear(rs.getInt("year"));
                studiesIn.setStatus(rs.getBoolean("status"));
                String semesterStr =  rs.getString("semester");
                studiesIn.setSemester(Semester.valueOf(semesterStr));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studiesIn;
    }

    @Override
    public void delete(Long id) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM STUDIES_IN WHERE id = ?");
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
