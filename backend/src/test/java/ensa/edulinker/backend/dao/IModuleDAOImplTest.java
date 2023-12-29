package ensa.edulinker.backend.dao;

import ensa.edulinker.backend.web.entities.Module;
import ensa.edulinker.backend.web.entities.Sector;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;

import java.util.List;

public class IModuleDAOImplTest extends DBTestCase {

    private IModuleDAO moduleDAO;

    public IModuleDAOImplTest(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.cj.jdbc.Driver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:mysql://localhost:3306/edulinker_db");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "root");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "my-secret-pw");
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(getClass().getClassLoader().getResourceAsStream("static/sectors_datasets/dataset.xml"));
    }

    @Override
    protected DatabaseOperation getTearDownOperation() {
        return DatabaseOperation.DELETE_ALL;
    }

    @Override
    protected DatabaseOperation getSetUpOperation() {
        return DatabaseOperation.REFRESH;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        moduleDAO = new IModuleDAOImpl();
    }

    @Test
    public void testGetByName() {
        Module module = moduleDAO.getById(1L);

        assertNotNull(module);
        assertEquals(1L, (long) module.getId());
        assertEquals(module.getName(), "Analyse pour l'ingénieur");
        assertEquals((long) module.getSector().getId(), 1L);
    }

    @Test
    public void testFindAll() {
        List<Module> modules = moduleDAO.findAll();

        assertNotNull(modules);
        assertEquals(3, modules.size());
    }

    @Test
    public void testSave() {
        Module module = new Module();
        module.setName("BI");
        Sector sector = new Sector();
        sector.setName("Informatique et Ingénierie des Données");
        sector.setId(1L);
        sector.setNameAbbreviation("IID");
        module.setSector(sector);
        Module savedModule = moduleDAO.save(module);
        assertNotNull(savedModule);
        assertEquals(savedModule.getName(), "BI");
        assertEquals((long) savedModule.getSector().getId(), 1L);
    }

    @Test
    public void testUpdate() {
        Module module = new Module();
        module.setId(3L);
        module.setName("Module updated");

        Module updatedModule = moduleDAO.update(module);

        assertNotNull(updatedModule);
        assertEquals(updatedModule.getId(), module.getId());
        assertEquals(updatedModule.getName(), module.getName());
    }

    @Test
    public void testDelete() {
        moduleDAO.delete(1L);

        Module deletedModule = moduleDAO.getById(1L);
        assertNull(deletedModule);
    }
}
