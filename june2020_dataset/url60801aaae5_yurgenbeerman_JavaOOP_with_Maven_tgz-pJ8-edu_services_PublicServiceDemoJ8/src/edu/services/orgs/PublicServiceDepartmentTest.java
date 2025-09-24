package edu.services.orgs;

import edu.services.PublicServiceDemo;
import edu.services.docs.InformationRequest;
import edu.services.execution.ExecutionEnvironment;
import edu.services.execution.StubEmailSender;
import edu.services.servants.InformationResponsible;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Lena on 20.03.14.
 */
public class PublicServiceDepartmentTest {
    PublicServiceDepartment infoRequestsDep;
    PublicService publicService;

    @Before
    public void createEntities() {
        ExecutionEnvironment testEnvironment = new ExecutionEnvironment();
        PublicServiceDemo.setupEnvironment(testEnvironment);
        publicService = testEnvironment.getPublicService();

        //publicService = PublicServiceDemo.createValidPublicService();
        infoRequestsDep = new PublicServiceDepartment(publicService,"infoRequestsDep_0");
    }

    @Test
    public void testGetEmailAddress() throws Exception {
        //given
        //when
        infoRequestsDep.setEmailAddress("email");

        //then
        Assert.assertNotNull(infoRequestsDep.getEmailAddress());
    }

    @Test
    public void testSetEmailAddress() throws Exception {
        //given
        //when
        infoRequestsDep.setEmailAddress("email");

        //then
        Assert.assertEquals("email", infoRequestsDep.getEmailAddress());
    }

    @Test
    public void testAddPublicServant() throws Exception {
        //given
        InformationResponsible informationResponsibleServant =
                new InformationResponsible(infoRequestsDep, "Karpenko","Petro","Ivanovych");

        //when
        infoRequestsDep.addPublicServant(informationResponsibleServant);

        //then
        Assert.assertTrue(infoRequestsDep.getPublicServants().contains(informationResponsibleServant));
    }

    @Test
    public void testAddDocToProcess() throws Exception {
        //given
        ExecutionEnvironment testEnvironment = new ExecutionEnvironment();
        PublicServiceDemo.setupEnvironment(testEnvironment);
        PublicService publicService = testEnvironment.getPublicService();

        infoRequestsDep = testEnvironment.getInfoRequestsDepartment();
        InformationRequest infoRequest = PublicServiceDemo.createInformationRequest(
                testEnvironment.getInfoRequestDocType(),
                testEnvironment.getDocumentCreator(), publicService);

        infoRequestsDep.setEmailSender(new StubEmailSender());

        /*
        InformationRequest infoRequest = PublicServiceDemo.createInformationRequest(infoRequestDocType, requester, publicService);

        Map<String, PublicServant> infoRequestsDepDispatchingTable = new HashMap<String, PublicServant>();
        InformationResponsible publicServant0 = new InformationResponsible(infoRequestsDep, "Karpenko0","Petro0","Ivanovych0");
        infoRequestsDepDispatchingTable.put("edu.services.docs.InformationRequest", publicServant0);

        ServantsTasksDispatcher infoRequestsDepServantsTasksDispatcher = new ServantsTasksDispatcher(infoRequestsDep);
        infoRequestsDepServantsTasksDispatcher.setDocsToServantsDispatchingTable(infoRequestsDepDispatchingTable);
        infoRequestsDep.setServantsTasksDispatcher(infoRequestsDepServantsTasksDispatcher);
        */

        //when
        infoRequestsDep.addDocToProcess(infoRequest);

        //then
        Assert.assertEquals(infoRequestsDep, infoRequest.getCurrentPublicServiceDepartment());
    }

    @Test
    public void testGetName() throws Exception {
        //given

        //when
        infoRequestsDep.setName("New name");

        //then
        Assert.assertEquals("New name", infoRequestsDep.getName());
    }

    @Test
    public void testToString() throws Exception {
        //given

        //when
        infoRequestsDep.setName("New name");

        //then
        Assert.assertEquals("New name", infoRequestsDep.toString());
    }

    @Test
    public void testEquals() throws Exception {
        //given

        //when
        PublicServiceDepartment infoRequestsDepEqual = new PublicServiceDepartment(publicService,"infoRequestsDep_0");

        //then
        Assert.assertEquals(infoRequestsDepEqual, infoRequestsDep);
    }

    @Test
    public void testHashCode() throws Exception {
        //given

        //when
        PublicServiceDepartment infoRequestsDepEqual = new PublicServiceDepartment(publicService,"infoRequestsDep_0");

        //then
        Assert.assertFalse(infoRequestsDepEqual.hashCode() == infoRequestsDep.hashCode());
    }
}
