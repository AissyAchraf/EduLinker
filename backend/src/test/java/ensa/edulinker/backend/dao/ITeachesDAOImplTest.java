package ensa.edulinker.backend.dao;

import ensa.edulinker.backend.web.entities.ModuleElement;
import ensa.edulinker.backend.web.entities.Professor;
import ensa.edulinker.backend.web.entities.Teaches;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;

import java.util.List;

public class ITeachesDAOImplTest extends DBTestCase {

    private ITeachesDAO teachesDAO = new ITeachesDAOImpl();

    public ITeachesDAOImplTest(String name) {
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
        teachesDAO = new ITeachesDAOImpl();
    }

    @Test
    public void testSave() {
        Teaches teaches = new Teaches();
        teaches.setYear(2022);
        teaches.setStatus(true);

        IProfessorDAO professorDAO = new IProfessorDAOImpl();
        IModuleElementDAO moduleElementDAO = new IModuleElementDAOImpl();

        Professor professor = professorDAO.getById(1L);
        ModuleElement moduleElement = moduleElementDAO.getById(1L);

        teaches.setProfessor(professor);
        teaches.setModuleElement(moduleElement);

        Teaches savedTeaches = teachesDAO.save(teaches);
        assertNotNull(savedTeaches);
        assertNotNull(savedTeaches.getId());
        assertEquals(savedTeaches.getYear(), 2022);
        assertEquals(savedTeaches.getStatus(), Boolean.TRUE);
    }

    @Test
    public void testUpdate() {
        Teaches teaches = new Teaches();
        teaches.setId(1L);
        teaches.setYear(2023);
        teaches.setStatus(false);

        IProfessorDAO professorDAO = new IProfessorDAOImpl();
        IModuleElementDAO moduleElementDAO = new IModuleElementDAOImpl();

        Professor professor = professorDAO.getById(1L);
        ModuleElement moduleElement = moduleElementDAO.getById(1L);
        teaches.setProfessor(professor);
        teaches.setModuleElement(moduleElement);


        Teaches updatedTeaches = teachesDAO.update(teaches);

        assertNotNull(updatedTeaches);
        assertEquals(1L, (long) teaches.getId());
        assertEquals(2023, updatedTeaches.getYear());
        assertEquals(Boolean.FALSE, updatedTeaches.getStatus());
    }

    @Test
    public void testFindAll() {
        List<Teaches> teachesList = teachesDAO.findAll();

        assertNotNull(teachesList);
        assertFalse(teachesList.isEmpty());
        assertEquals(2, teachesList.size());
    }

    @Test
    public void testGetById() {
        Teaches teaches = teachesDAO.getById(1L);

        assertNotNull(teaches);
        assertEquals(1L, (long) teaches.getId());
        assertEquals(2022, teaches.getYear());
        assertEquals(Boolean.TRUE, teaches.getStatus());
    }

    @Test
    public void testDelete() {
        Teaches teaches = teachesDAO.getById(1L);

        assertNotNull(teaches);

        Long teachesId = teaches.getId();
        teachesDAO.delete(teachesId);

        Teaches deletedTeaches = teachesDAO.getById(teachesId);

        assertNull(deletedTeaches);
    }
}
