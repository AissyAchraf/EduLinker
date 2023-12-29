package ensa.edulinker.backend.dao;

public class IModuleDAOFactory {

    private static IModuleDAO instance;

    static {
        instance = new IModuleDAOImpl();
    }

    private IModuleDAOFactory() {

    }

    public static synchronized IModuleDAO getInstance() {
        return instance;
    }
}
