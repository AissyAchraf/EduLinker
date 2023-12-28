package ensa.edulinker.backend.dao;

import ensa.edulinker.backend.web.entities.Sector;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;

import java.util.List;

public class ISectorDAOImplTest extends DBTestCase {

    private ISectorDAO sectorDAO;

    public ISectorDAOImplTest(String name) {
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
        sectorDAO = new ISectorDAOImpl();
    }

    @Test
    public void testGetByName() {
        Sector sector = sectorDAO.getByName("Informatique et Ingénierie des Données");

        assertNotNull(sector);
        assertEquals(1L, (long) sector.getId());
        assertEquals(sector.getName(), "Informatique et Ingénierie des Données");
        assertEquals(sector.getNameAbbreviation(), "IID");
    }

    @Test
    public void testFindAll() {
        List<Sector> sectors = sectorDAO.findAll();

        assertNotNull(sectors);
        assertEquals(3, sectors.size());
    }

    @Test
    public void testSave() {
        Sector sector = new Sector();
        sector.setName("GE");
        Sector savedSector = sectorDAO.save(sector);
        assertNotNull(savedSector);
        assertEquals(savedSector.getName(), "GE");
    }

    @Test
    public void testUpdate() {
        Sector sector = new Sector();
        sector.setId(3L);
        sector.setName("Master Big Data Updated");
        sector.setNameAbbreviation("MBD");

        Sector updatedSector = sectorDAO.update(sector);

        assertNotNull(updatedSector);
        assertEquals(updatedSector.getId(), sector.getId());
        assertEquals(updatedSector.getName(), sector.getName());
        assertEquals(updatedSector.getNameAbbreviation(), sector.getNameAbbreviation());
    }

    @Test
    public void testDelete() {
        Sector testSector = new Sector();
        testSector.setName("Master Big Data");
        Sector savedSector = sectorDAO.save(testSector);

        Long sectorId = savedSector.getId();
        sectorDAO.delete(sectorId);

        Sector deletedSector = sectorDAO.getByName("Test Sector");
        assertNull(deletedSector);
    }
}
