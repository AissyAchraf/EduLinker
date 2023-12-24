import ensa.edulinker.backend.dao.ISectorDAO;
import ensa.edulinker.backend.web.entities.Sector;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

import java.util.List;

public class ISectorDAOImplTest extends DBTestCase {

    private ISectorDAO sectorDAO;

    public ISectorDAOImplTest(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.cj.jdbc.Driver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:mysql://localhost:3306/your_database");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "your_username");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "your_password");
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(getClass().getClassLoader().getResourceAsStream("dataset.xml"));
    }

    @Override
    protected void setUpDatabaseConfig() {
        
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        sectorDAO = new ISectorDAOImpl();
    }

    public void testSave() {
        Sector testSector = new Sector();
        testSector.setName("Test Sector");
        Sector savedSector = sectorDAO.Save(testSector);

        assertNotNull(savedSector.getId());
        assertEquals(testSector.getName(), savedSector.getName());
    }

    public void testFindAll() {
        List<Sector> sectors = sectorDAO.findAll();

        assertNotNull(sectors);
        assertEquals(2, sectors.size()); // Adjust based on your dataset
    }

    public void testGetByName() {
        Sector sector = sectorDAO.getByName("Sector1");

        assertNotNull(sector);
        assertEquals(1, sector.getId().intValue()); // Adjust based on your dataset
    }

    public void testUpdate() {
        Sector testSector = new Sector();
        testSector.setName("Test Sector");
        Sector savedSector = sectorDAO.Save(testSector);

        savedSector.setName("Updated Sector");
        Sector updatedSector = sectorDAO.update(savedSector);

        assertNotNull(updatedSector);
        assertEquals(savedSector.getId(), updatedSector.getId());
        assertEquals(savedSector.getName(), updatedSector.getName());
    }

    public void testDelete() {
        Sector testSector = new Sector();
        testSector.setName("Test Sector");
        Sector savedSector = sectorDAO.Save(testSector);

        Long sectorId = savedSector.getId();
        sectorDAO.delete(sectorId);

        Sector deletedSector = sectorDAO.getByName("Test Sector");
        assertNull(deletedSector);
    }
}
