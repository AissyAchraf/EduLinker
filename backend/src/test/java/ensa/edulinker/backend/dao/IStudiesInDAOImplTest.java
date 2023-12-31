package ensa.edulinker.backend.dao;

import ensa.edulinker.backend.web.entities.Sector;
import ensa.edulinker.backend.web.entities.Semester;
import ensa.edulinker.backend.web.entities.Student;
import ensa.edulinker.backend.web.entities.StudiesIn;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;

import java.util.List;

public class IStudiesInDAOImplTest extends DBTestCase {

    private IStudiesInDAO studiesInDAO = new IStudiesInDAOImpl();

    public IStudiesInDAOImplTest(String name) {
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
        studiesInDAO = new IStudiesInDAOImpl();
    }

    @Test
    public void testSave() {
        StudiesIn studiesIn = new StudiesIn();
        studiesIn.setYear(2022);
        studiesIn.setStatus(true);

        Sector sector = new Sector();
        sector.setId(3L);
        sector.setName("Master Big");
        sector.setNameAbbreviation("MBD");

        studiesIn.setSector(sector);

        studiesIn.setSemester(Semester.S3);
        Student student = new Student();
        student.setCNE("R1111111111");
        studiesIn.setStudent(student);


        StudiesIn savedStudiesIn = studiesInDAO.save(studiesIn);
        assertNotNull(savedStudiesIn);
        assertNotNull(savedStudiesIn.getId());
        assertEquals(2022, savedStudiesIn.getYear());
        assertEquals(Semester.S3, savedStudiesIn.getSemester());
    }

    @Test
    public void testUpdate() {
        StudiesIn studiesIn = studiesInDAO.getById(1L);

        assertNotNull(studiesIn);

        studiesIn.setYear(2023);
        studiesIn.setStatus(false);

        StudiesIn updatedStudiesIn = studiesInDAO.update(studiesIn);

        assertNotNull(updatedStudiesIn);
        assertEquals(2023, updatedStudiesIn.getYear());
        assertFalse(updatedStudiesIn.getStatus());
    }

    @Test
    public void testFindAll() {
        List<StudiesIn> studiesIns = studiesInDAO.findAll();

        assertNotNull(studiesIns);
        assertFalse(studiesIns.isEmpty());
        assertEquals(2, studiesIns.size());

    }

    @Test
    public void testGetById() {
        StudiesIn studiesIn = studiesInDAO.getById(1L);

        assertNotNull(studiesIn);
        assertEquals(2022, studiesIn.getYear());
        assertEquals(Semester.S3, studiesIn.getSemester());
        assertTrue(studiesIn.getStatus());
    }

    @Test
    public void testDelete() {
        StudiesIn studiesIn = studiesInDAO.getById(1L);

        assertNotNull(studiesIn);

        Long studiesInId = studiesIn.getId();
        studiesInDAO.delete(studiesInId);

        StudiesIn deletedStudiesIn = studiesInDAO.getById(studiesInId);
        assertNull(deletedStudiesIn);
    }
}
