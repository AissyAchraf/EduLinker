package ensa.edulinker.backend.dao;

import ensa.edulinker.backend.web.entities.Module;
import ensa.edulinker.backend.web.entities.Semester;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

class IModuleDAOImpl implements IModuleDAO {

    private Connection connection = MySQLDBConnection.getConnection();
    private ISectorDAO sectorDAO = ISectorDAOFactory.getInstance();

    @Override
    public Module save(Module module) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO MODULES(name, sector_id, semester) VALUES (?, ?, ?)");
            ps.setString(1, module.getName());
            ps.setLong(2, module.getSector().getId());
            ps.setString(3, String.valueOf(module.getSemester()));
            ps.executeUpdate();
            PreparedStatement ps2 = connection
                    .prepareStatement("SELECT MAX(ID) AS MAX_ID FROM MODULES");
            ResultSet rs = ps2.executeQuery();
            if(rs.next()) {
                module.setId(rs.getLong("MAX_ID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return module;
    }

    @Override
    public Module update(Module m) {
        Module module = null;
        try {
            PreparedStatement ps = connection
                    .prepareStatement("UPDATE MODULES SET name = ?, semester = ? WHERE id = ?");
            ps.setString(1, m.getName());
            ps.setString(2, String.valueOf(m.getSemester()));
            ps.setLong(3, m.getId());
            ps.executeUpdate();
            PreparedStatement ps2 = connection.prepareStatement("SELECT * FROM MODULES WHERE id = ?");
            ps2.setLong(1, m.getId());
            ResultSet rs = ps2.executeQuery();
            if (rs.next()) {
                module = new Module();
                module.setId(rs.getLong("id"));
                module.setName(rs.getString("name"));
                String semesterStr = rs.getString("semester");
                module.setSemester(Semester.valueOf(semesterStr));
                Long sectorId = rs.getLong("sector_id");
                if(sectorId != null)
                {
                    module.setSector(sectorDAO.getById(sectorId));
                } else
                {
                    module.setSector(null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return module;
    }

    @Override
    public List<Module> findAll() {
        List<Module> modules = new ArrayList<>();
        try {
            PreparedStatement ps = connection
                    .prepareStatement("SELECT * FROM MODULES");
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                Module module = new Module();
                module.setId(rs.getLong("id"));
                module.setName(rs.getString("name"));
                String semesterStr = rs.getString("semester");
                module.setSemester(Semester.valueOf(semesterStr));
                Long sectorId = rs.getLong("sector_id");
                if(sectorId != null)
                {
                    module.setSector(sectorDAO.getById(sectorId));
                } else
                {
                    module.setSector(null);
                }
                modules.add(module);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modules;
    }

    @Override
    public Module getById(Long id) {
        Module module = null;
        try {
            PreparedStatement ps = connection
                    .prepareStatement("SELECT * FROM MODULES WHERE id = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                module = new Module();
                module.setId(rs.getLong("id"));
                module.setName(rs.getString("name"));
                String semesterStr = rs.getString("semester");
                module.setSemester(Semester.valueOf(semesterStr));
                Long sectorId = rs.getLong("sector_id");
                if(sectorId != null)
                {
                    module.setSector(sectorDAO.getById(sectorId));
                } else
                {
                    module.setSector(null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return module;
    }

    @Override
    public void delete(Long id) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("DELETE FROM MODULES WHERE id = ?");
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
