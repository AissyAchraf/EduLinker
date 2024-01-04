package ensa.edulinker.backend.dao;

import ensa.edulinker.backend.web.entities.Administrator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class IAdministratorDAOImpl implements IAdministratorDAO {

    private Connection connection = MySQLDBConnection.getConnection();

    @Override
    public Administrator save(Administrator administrator) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO ADMINISTRATORS(last_name, first_name, email, phone_number) VALUES (?, ?, ?, ?)");
            ps.setString(1, administrator.getLastName());
            ps.setString(2, administrator.getFirstName());
            ps.setString(3, administrator.getEmail());
            ps.setString(4, administrator.getPhoneNumber());
            ps.executeUpdate();

            PreparedStatement ps2 = connection.prepareStatement("SELECT MAX(ID) AS MAX_ID FROM ADMINISTRATORS");
            ResultSet rs = ps2.executeQuery();
            if (rs.next()) {
                administrator.setId(rs.getLong("MAX_ID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return administrator;
    }

    @Override
    public Administrator update(Administrator administrator) {
        Administrator updatedAdministrator = null;
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE ADMINISTRATORS SET last_name = ?, first_name = ?, email = ?, phone_number = ?, WHERE id = ?");
            ps.setString(1, administrator.getLastName());
            ps.setString(2, administrator.getFirstName());
            ps.setString(3, administrator.getEmail());
            ps.setString(4, administrator.getPhoneNumber());
            ps.setLong(5, administrator.getId());
            ps.executeUpdate();

            PreparedStatement ps2 = connection.prepareStatement("SELECT * FROM ADMINISTRATORS WHERE id = ?");
            ps2.setLong(1, administrator.getId());
            ResultSet rs = ps2.executeQuery();
            if (rs.next()) {
                updatedAdministrator = new Administrator();
                updatedAdministrator.setId(rs.getLong("id"));
                updatedAdministrator.setLastName(rs.getString("last_name"));
                updatedAdministrator.setFirstName(rs.getString("first_name"));
                updatedAdministrator.setEmail(rs.getString("email"));
                updatedAdministrator.setPhoneNumber(rs.getString("phone_number"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updatedAdministrator;
    }

    @Override
    public List<Administrator> findAll() {
        List<Administrator> administrators = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM ADMINISTRATORS");
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                Administrator administrator = new Administrator();
                administrator.setId(rs.getLong("id"));
                administrator.setLastName(rs.getString("last_name"));
                administrator.setFirstName(rs.getString("first_name"));
                administrator.setEmail(rs.getString("email"));
                administrator.setPhoneNumber(rs.getString("phone_number"));
                administrators.add(administrator);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return administrators;
    }

    @Override
    public Administrator getById(Long id) {
        Administrator administrator = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM ADMINISTRATORS WHERE id = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                administrator = new Administrator();
                administrator.setId(rs.getLong("id"));
                administrator.setLastName(rs.getString("last_name"));
                administrator.setFirstName(rs.getString("first_name"));
                administrator.setEmail(rs.getString("email"));
                administrator.setPhoneNumber(rs.getString("phone_number"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return administrator;
    }

    @Override
    public void delete(Long id) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM ADMINISTRATORS WHERE id = ?");
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Administrator findByEmail(String email) {
        Administrator administrator = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM ADMINISTRATORS WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                administrator = new Administrator();
                administrator.setId(rs.getLong("id"));
                administrator.setLastName(rs.getString("last_name"));
                administrator.setFirstName(rs.getString("first_name"));
                administrator.setEmail(rs.getString("email"));
                administrator.setPhoneNumber(rs.getString("phone_number"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return administrator;
    }
}
