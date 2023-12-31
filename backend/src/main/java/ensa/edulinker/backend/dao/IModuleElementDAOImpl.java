package ensa.edulinker.backend.dao;

import ensa.edulinker.backend.web.entities.ModuleElement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

class IModuleElementDAOImpl implements IModuleElementDAO {

    private Connection connection = MySQLDBConnection.getConnection();
    private IModuleDAO moduleDAO = IModuleDAOFactory.getInstance();
    private IProfessorDAO professorDAO = new IProfessorDAOImpl();

    @Override
    public ModuleElement save(ModuleElement moduleElement) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO MODULE_ELEMENTS(name, coefficient, module_id, professor_id) VALUES (?, ?, ?, ?)");
            ps.setString(1, moduleElement.getName());
            ps.setFloat(2, moduleElement.getCoefficient());
            ps.setLong(3, moduleElement.getModule().getId());
            ps.setLong(4, moduleElement.getProfessor().getCode());
            ps.executeUpdate();
            PreparedStatement ps2 = connection
                    .prepareStatement("SELECT MAX(ID) AS MAX_ID FROM MODULE_ELEMENTS");
            ResultSet rs = ps2.executeQuery();
            if(rs.next()) {
                moduleElement.setId(rs.getLong("MAX_ID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return moduleElement;
    }

    @Override
    public ModuleElement update(ModuleElement m) {
        ModuleElement moduleElement = null;
        try {
            PreparedStatement ps = connection
                    .prepareStatement("UPDATE MODULE_ELEMENTS SET name = ?, coefficient = ?, module_id = ?, professor_id = ? WHERE id = ?");
            ps.setString(1, m.getName());
            ps.setFloat(2, m.getCoefficient());
            ps.setLong(3, m.getModule().getId());
            ps.setLong(4, m.getProfessor().getCode());
            ps.setLong(5, m.getId());
            ps.executeUpdate();
            PreparedStatement ps2 = connection.prepareStatement("SELECT * FROM MODULE_ELEMENTS WHERE id = ?");
            ps2.setLong(1, m.getId());
            ResultSet rs = ps2.executeQuery();
            if (rs.next()) {
                moduleElement = new ModuleElement();
                moduleElement.setId(rs.getLong("id"));
                moduleElement.setName(rs.getString("name"));
                moduleElement.setCoefficient(rs.getFloat("coefficient"));
                Long moduleId = rs.getLong("module_id");
                if(moduleId != null)
                {
                    moduleElement.setModule(moduleDAO.getById(moduleId));
                } else
                {
                    moduleElement.setModule(null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return moduleElement;
    }

    @Override
    public List<ModuleElement> findAll() {
        List<ModuleElement> moduleElements = new ArrayList<>();
        try {
            PreparedStatement ps = connection
                    .prepareStatement("SELECT * FROM MODULE_ELEMENTS");
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                ModuleElement moduleElement = new ModuleElement();
                moduleElement.setId(rs.getLong("id"));
                moduleElement.setName(rs.getString("name"));
                moduleElement.setCoefficient(rs.getFloat("coefficient"));
                Long moduleId = rs.getLong("module_id");
                if(moduleId != null)
                {
                    moduleElement.setModule(moduleDAO.getById(moduleId));
                } else
                {
                    moduleElement.setModule(null);
                }
                moduleElements.add(moduleElement);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return moduleElements;
    }

    @Override
    public ModuleElement getById(Long id) {
        ModuleElement moduleElement = null;
        try {
            PreparedStatement ps = connection
                    .prepareStatement("SELECT * FROM MODULE_ELEMENTS WHERE id = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                moduleElement = new ModuleElement();
                moduleElement.setId(rs.getLong("id"));
                moduleElement.setName(rs.getString("name"));
                moduleElement.setCoefficient(rs.getFloat("coefficient"));
                Long moduleId = rs.getLong("module_id");
                moduleElement.setModule(moduleDAO.getById(moduleId));
                Long professorId = rs.getLong("professor_id");
                moduleElement.setProfessor(professorDAO.getById(professorId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return moduleElement;
    }

    @Override
    public void delete(Long id) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("DELETE FROM MODULE_ELEMENTS WHERE id = ?");
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ModuleElement> findAllByModule(Long moduleId) {
        List<ModuleElement> moduleElements = new ArrayList<>();
        try {
            PreparedStatement ps = connection
                    .prepareStatement("SELECT * FROM MODULE_ELEMENTS WHERE module_id = ?");
            ps.setLong(1, moduleId);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                ModuleElement moduleElement = new ModuleElement();
                moduleElement.setId(rs.getLong("id"));
                moduleElement.setName(rs.getString("name"));
                moduleElement.setCoefficient(rs.getFloat("coefficient"));
                moduleElement.setModule(moduleDAO.getById(moduleId));
                moduleElements.add(moduleElement);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return moduleElements;
    }

    @Override
    public List<ModuleElement> findAllByProfessor(Long professorId) {
        List<ModuleElement> moduleElements = new ArrayList<>();
        try {
            PreparedStatement ps = connection
                    .prepareStatement("SELECT * FROM MODULE_ELEMENTS WHERE professor_id = ?");
            ps.setLong(1, professorId);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                ModuleElement moduleElement = new ModuleElement();
                moduleElement.setId(rs.getLong("id"));
                moduleElement.setName(rs.getString("name"));
                moduleElement.setCoefficient(rs.getFloat("coefficient"));
                Long moduleId = rs.getLong("module_id");
                if(moduleId != null)
                {
                    moduleElement.setModule(moduleDAO.getById(moduleId));
                } else
                {
                    moduleElement.setModule(null);
                }
                moduleElements.add(moduleElement);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return moduleElements;
    }
}
