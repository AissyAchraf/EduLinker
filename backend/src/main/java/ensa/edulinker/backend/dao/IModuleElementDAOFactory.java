package ensa.edulinker.backend.dao;

public class IModuleElementDAOFactory {

    private static IModuleElementDAO instance;

    static {
        instance = new IModuleElementDAOImpl();
    }

    private IModuleElementDAOFactory() {

    }

    public static synchronized IModuleElementDAO getInstance() {
        return instance;
    }
}
