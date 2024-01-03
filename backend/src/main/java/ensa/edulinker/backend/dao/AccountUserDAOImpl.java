package ensa.edulinker.backend.dao;

import ensa.edulinker.backend.security.Account;
import ensa.edulinker.backend.security.Role;
import ensa.edulinker.backend.web.entities.Semester;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountUserDAOImpl implements AccountUserDAO {

    private Connection connection = MySQLDBConnection.getConnection();
    private IProfessorDAO professorDAO = new IProfessorDAOImpl();

    @Override
    public Account findByEmail(String email) {
        Account account = null;
        try {
            PreparedStatement ps = connection
                    .prepareStatement("SELECT * FROM ACCOUNTS WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                account = new Account();
                account.setId(rs.getLong("id"));
                account.setEmail(rs.getString("email"));
                account.setPassword(rs.getString("password"));
                account.setRole(Role.valueOf(rs.getString("role")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }
}
