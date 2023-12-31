package ensa.edulinker.backend.dao;

import ensa.edulinker.backend.web.entities.Professor;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;

import java.util.List;

public class IProfessorDAOImplTest extends DBTestCase {

    private IProfessorDAO professorDAO = new IProfessorDAOImpl();

    public IProfessorDAOImplTest(String name) {
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
        professorDAO = new IProfessorDAOImpl();
    }

    @Test
    public void testSave() {
        Professor professor = new Professor();
        professor.setLastName("Doe");
        professor.setFirstName("John");
        professor.setEmail("john.doe@example.com");
        professor.setPhoneNumber("123456789");
        professor.setSpeciality("Computer Science");

        Professor savedProfessor = professorDAO.save(professor);
        assertNotNull(savedProfessor);
        assertNotNull(savedProfessor.getCode());
        assertEquals(savedProfessor.getLastName(), "Doe");
        assertEquals(savedProfessor.getFirstName(), "John");
        assertEquals(savedProfessor.getEmail(), "john.doe@example.com");
        assertEquals(savedProfessor.getPhoneNumber(), "123456789");
        assertEquals(savedProfessor.getSpeciality(), "Computer Science");
    }

    @Test
    public void testUpdate() {
        Professor professor = professorDAO.getById(1L);

        assertNotNull(professor);

        professor.setFirstName("UpdatedFirstName");
        professor.setEmail("updatedemail@example.com");

        Professor updatedProfessor = professorDAO.update(professor);

        assertNotNull(updatedProfessor);
        assertEquals("UpdatedFirstName", updatedProfessor.getFirstName());
        assertEquals("updatedemail@example.com", updatedProfessor.getEmail());
    }

    @Test
    public void testFindAll() {
        List<Professor> professors = professorDAO.findAll();

        assertNotNull(professors);
        assertFalse(professors.isEmpty());
        assertEquals(2, professors.size());
    }

    @Test
    public void testGetById() {
        Professor professor = professorDAO.getById(1L);

        assertNotNull(professor);
        assertEquals((long) professor.getCode(), 1);
        assertEquals(professor.getLastName(), "Marouane");
        assertEquals(professor.getFirstName(), "Kamal");
        assertEquals(professor.getEmail(), "marouanekamal@ensa.ma");
        assertEquals(professor.getPhoneNumber(), "0645434563");
        assertEquals(professor.getSpeciality(), "Software Engineering");
    }

    @Test
    public void testDelete() {
        Professor professor = professorDAO.getById(1L);

        assertNotNull(professor);

        Long professorId = professor.getCode();
        professorDAO.delete(professorId);

        Professor deletedProfessor = professorDAO.getById(professorId);

        assertNull(deletedProfessor);
    }
}
