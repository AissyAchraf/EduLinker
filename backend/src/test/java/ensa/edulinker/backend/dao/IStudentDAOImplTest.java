package ensa.edulinker.backend.dao;

import ensa.edulinker.backend.web.entities.Student;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;

import java.util.List;

public class IStudentDAOImplTest extends DBTestCase {

    private IStudentDAO studentDAO;

    public IStudentDAOImplTest(String name) {
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
        studentDAO = new IStudentDAOImpl();
    }

    @Test
    public void testSave() {
        Student student = new Student();
        student.setLastName("El Alami");
        student.setFirstName("Aymane");
        student.setEmail("aymaneelalami@ensa.ma");
        student.setPhoneNumber("0676767688");
        student.setCNE("R1111111113");
        student.setAppoge("121213");
        student.setBirthday(new java.util.Date(2002, 3, 17));

        Student savedStudent = studentDAO.save(student);
        assertNotNull(savedStudent);
        assertNotNull(savedStudent.getCNE());
        assertEquals(savedStudent.getLastName(), "El Alami");
    }

    @Test
    public void testGetById() {
        Student student = studentDAO.getById("R1111111111");

        assertNotNull(student);
        assertEquals(student.getLastName(), "Aissy");
        assertEquals(student.getFirstName(), "Achraf");
    }

    @Test
    public void testUpdate() {
        Student student = studentDAO.getById("R1111111111");

        assertNotNull(student);

        // Update student details
        student.setFirstName("UpdatedFirstName");
        student.setEmail("updatedemail@example.com");

        Student updatedStudent = studentDAO.update(student);

        assertNotNull(updatedStudent);
        assertEquals("UpdatedFirstName", updatedStudent.getFirstName());
        assertEquals("updatedemail@example.com", updatedStudent.getEmail());
    }

    @Test
    public void testFindAll() {
        List<Student> students = studentDAO.findAll();

        assertNotNull(students);
        assertEquals(2, students.size());
    }

    @Test
    public void testDelete() {
        Student student = studentDAO.getById("R1111111111");

        assertNotNull(student);

        String cneToDelete = student.getCNE();
        studentDAO.delete(cneToDelete);

        Student deletedStudent = studentDAO.getById(cneToDelete);

        assertNull(deletedStudent);
    }
}
