package ensa.edulinker.backend.dao;

public class ISectorDAOFactory {

    private static ISectorDAO instance;

    static {
        instance = new ISectorDAOImpl();
    }

    private ISectorDAOFactory() {

    }

    public static synchronized ISectorDAO getInstance() {
        return instance;
    }
}
