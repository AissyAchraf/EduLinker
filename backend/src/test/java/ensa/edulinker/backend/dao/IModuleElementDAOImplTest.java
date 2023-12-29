package ensa.edulinker.backend.dao;

import ensa.edulinker.backend.web.entities.Module;
import ensa.edulinker.backend.web.entities.ModuleElement;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;

import java.util.List;

public class IModuleElementDAOImplTest extends DBTestCase {

    private IModuleElementDAO moduleElementDAO;

    public IModuleElementDAOImplTest(String name) {
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
        moduleElementDAO = new IModuleElementDAOImpl();
    }

    @Test
    public void testGetById() {
        ModuleElement moduleElement = moduleElementDAO.getById(2L);

        assertNotNull(moduleElement);
        assertEquals(2L, (long) moduleElement.getId());
        assertEquals(moduleElement.getName(), "Modélisation orientée objet");
        assertEquals(moduleElement.getCoefficient(), 0.5f);
        assertEquals(moduleElement.getStatus(), Boolean.TRUE);
        assertEquals((long) moduleElement.getModule().getId(), 2L);
    }

    @Test
    public void testFindAll() {
        List<ModuleElement> moduleElements = moduleElementDAO.findAll();

        assertNotNull(moduleElements);
        assertEquals(3, moduleElements.size());
    }

    @Test
    public void testSave() {
        ModuleElement moduleElement = new ModuleElement();
        moduleElement.setName("Storm");
        moduleElement.setCoefficient(0.7f);
        moduleElement.setStatus(Boolean.TRUE);
        Module module = new Module();
        module.setName("Big Data");
        module.setId(3L);
        moduleElement.setModule(module);
        ModuleElement savedModule = moduleElementDAO.save(moduleElement);
        assertNotNull(savedModule);
        assertEquals(savedModule.getName(), "Storm");
        assertEquals(savedModule.getCoefficient(), 0.7f);
        assertEquals(savedModule.getStatus(), Boolean.TRUE);
        assertEquals((long) savedModule.getModule().getId(), 3L);
    }

    @Test
    public void testUpdate() {
        ModuleElement moduleElement = new ModuleElement();
        moduleElement.setId(3L);
        moduleElement.setName("NoSQL - MongoDB");
        moduleElement.setCoefficient(0.3f);
        moduleElement.setStatus(Boolean.TRUE);

        Module module = new Module();
        module.setId(3L);
        module.setName("Big Data");
        moduleElement.setModule(module);

        ModuleElement updatedModuleElement = moduleElementDAO.update(moduleElement);

        assertNotNull(updatedModuleElement);
        assertEquals(updatedModuleElement.getId(), moduleElement.getId());
        assertEquals(updatedModuleElement.getName(), moduleElement.getName());
    }

    @Test
    public void testDelete() {
        moduleElementDAO.delete(3L);
        ModuleElement deletedModuleElement = moduleElementDAO.getById(3L);
        assertNull(deletedModuleElement);
    }
}
