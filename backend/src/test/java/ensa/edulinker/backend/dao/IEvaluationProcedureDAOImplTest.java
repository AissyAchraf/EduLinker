package ensa.edulinker.backend.dao;

import ensa.edulinker.backend.web.entities.EvaluationProcedure;
import ensa.edulinker.backend.web.entities.EvaluationProcedureType;
import ensa.edulinker.backend.web.entities.ModuleElement;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;

import java.util.List;

public class IEvaluationProcedureDAOImplTest extends DBTestCase {

    private IEvaluationProcedureDAO evaluationProcedureDAO;

    public IEvaluationProcedureDAOImplTest(String name) {
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
        evaluationProcedureDAO = new IEvaluationProcedureDAOImpl();
    }

    @Test
    public void testSave() {
        EvaluationProcedure evaluationProcedure = new EvaluationProcedure();
        evaluationProcedure.setType(EvaluationProcedureType.TP);
        evaluationProcedure.setCoefficient(0.8f);

        IModuleElementDAO moduleElementDAO = new IModuleElementDAOImpl();
        ModuleElement moduleElement = moduleElementDAO.getById(2L);
        evaluationProcedure.setElement(moduleElement);

        EvaluationProcedure savedProcedure = evaluationProcedureDAO.save(evaluationProcedure);

        assertNotNull(savedProcedure);
        assertNotNull(savedProcedure.getId());
        assertEquals(EvaluationProcedureType.TP, savedProcedure.getType());
        assertEquals(0.8f, savedProcedure.getCoefficient());
    }

    @Test
    public void testGetById() {
        EvaluationProcedure procedure = evaluationProcedureDAO.getById(1L);

        assertNotNull(procedure);
        assertEquals(1L, procedure.getId().longValue());
        assertEquals(EvaluationProcedureType.TP, procedure.getType());
        assertEquals(0.6f, procedure.getCoefficient(), 0.001);
    }

    @Test
    public void testUpdate() {
        EvaluationProcedure procedure = evaluationProcedureDAO.getById(1L);

        assertNotNull(procedure);

        procedure.setType(EvaluationProcedureType.Projet);
        procedure.setCoefficient(0.9f);

        IModuleElementDAO moduleElementDAO = new IModuleElementDAOImpl();
        ModuleElement updatedElement = moduleElementDAO.getById(2L);
        procedure.setElement(updatedElement);

        EvaluationProcedure updatedProcedure = evaluationProcedureDAO.update(procedure);

        assertNotNull(updatedProcedure);
        assertEquals(EvaluationProcedureType.Projet, updatedProcedure.getType());
        assertEquals(0.9f, updatedProcedure.getCoefficient(), 0.001);
        assertNotNull(updatedProcedure.getElement());
    }

    @Test
    public void testFindAll() {
        List<EvaluationProcedure> procedures = evaluationProcedureDAO.findAll();

        assertNotNull(procedures);
        assertEquals(2, procedures.size());
    }

    @Test
    public void testDelete() {
        EvaluationProcedure procedure = evaluationProcedureDAO.getById(1L);

        assertNotNull(procedure);

        Long idToDelete = procedure.getId();
        evaluationProcedureDAO.delete(idToDelete);

        EvaluationProcedure deletedProcedure = evaluationProcedureDAO.getById(idToDelete);

        assertNull(deletedProcedure);
    }
}