package edu.clients;

import edu.services.PublicServiceDemo;
import edu.services.docs.*;
import edu.services.orgs.PublicService;
import edu.services.orgs.PublicServiceDepartment;
import edu.services.servants.InformationResponsible;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by yurii.pyvovarenko on 3/11/14.
 */
public class CitizenResponsesTest extends CitizenTestsBasics {
    OutcomingDocument outcomingDocument;

    @Before
    public void createInfoReqDocType_Citizen_PubService() {
        initCitizenAndPublicService();

        DocumentType infoRequestDocType = PublicServiceDemo.createInfoRequestDocType();
        DocumentType outcomingDocType = PublicServiceDemo.createOutcomingDocType();
        InformationRequest infoRequest = PublicServiceDemo.createInformationRequest(infoRequestDocType, citizen, publicService);
        PublicServiceDepartment infoRequestsDep = new PublicServiceDepartment(publicService,"infoRequestsDep_0");
        InformationResponsible informationResponsibleServant =
                new InformationResponsible(infoRequestsDep, "Karpenko","Petro","Ivanovych");
        outcomingDocument = PublicServiceDemo.createOutcomingDocument(outcomingDocType,
                publicService, infoRequest, informationResponsibleServant);

    }

    @Test
    public void shouldGetResponses() throws Exception {
        //given

        //when
        citizen.addResponse(outcomingDocument);

        //then
        org.junit.Assert.assertTrue("Should be not empty string of response", citizen.getResponses().size() > 0);
    }

    @Test
    public void shouldGetValidityString() throws Exception {
        //given
        outcomingDocument.setText("10000000000000000");

        //when
        citizen.addResponse(outcomingDocument);

        //then
        org.junit.Assert.assertEquals(DocDefaults.VALID, citizen.getResponses().get(0).getValidityString());
    }

    @Test
    public void shouldResponseText() throws Exception {
        //given

        //when
        outcomingDocument.setText("1");
        citizen.addResponse(outcomingDocument);

        //then
        org.junit.Assert.assertFalse("Response text can not be empty string: " + citizen.getResponses().get(0).getText() + ".",citizen.getResponses().get(0).getText().equals(""));
    }

    @Test
    public void shouldGetResponsesString() throws Exception {
        //given
        outcomingDocument.setText("responce text");

        //when
        citizen.addResponse(outcomingDocument);

        //then
        org.junit.Assert.assertTrue("Response must contain '" + outcomingDocument.getText() +
                "' but its value is '" + citizen.getResponsesString() + "'",
                citizen.getResponsesString().contains(outcomingDocument.getText()));
    }


    @Test
    public void shouldAddResponse() throws Exception {
        //given
        citizen.addResponse(outcomingDocument);
        int result = citizen.getResponses().size();

        //when
        citizen.addResponse(outcomingDocument);
        citizen.addResponse(outcomingDocument);

        //then
        org.junit.Assert.assertEquals("Size of responses array should be " + (result + 2) + " but is " + citizen.getResponses().size(), result + 2, citizen.getResponses().size());
    }
}
