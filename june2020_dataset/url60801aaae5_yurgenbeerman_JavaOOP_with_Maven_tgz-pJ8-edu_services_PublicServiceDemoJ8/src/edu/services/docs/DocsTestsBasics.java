package edu.services.docs;

import edu.clients.Citizen;
import edu.clients.DocumentCreator;
import edu.services.execution.ExecutionEnvironment;
import edu.services.orgs.PublicService;
import edu.services.orgs.PublicServiceDepartment;
import edu.services.servants.InformationResponsible;
import org.junit.Test;

/**
 * Created by yurii.pyvovarenko on 19.03.14.
 */
public class DocsTestsBasics {
    DocumentCreator documentCreator;
    DocumentType infoRequestDocType;
    PublicService publicService;
    PublicServiceDepartment infoRequestsDep;
    InformationResponsible informationResponsibleServant;

    public DocsTestsBasics() {
        documentCreator = new Citizen("Petrenko","Taras","Ivanovych");
        DocumentLifecycle infoRequestLifecycle = new DocumentLifecycle(new String[] {"Created"});
        infoRequestDocType = new DocumentType("Information_Request", "InfoReq_",infoRequestLifecycle);

        ExecutionEnvironment environment = new ExecutionEnvironment();
        publicService = new PublicService("Improvements service", environment);
        infoRequestsDep = new PublicServiceDepartment(publicService,"infoRequestsDep_0");
        informationResponsibleServant =
                new InformationResponsible(infoRequestsDep, "Karpenko","Petro","Ivanovych");
    }

    @Test
    public void shouldBeAssignedNotNullDocumentLifecycle () {
        //given
        DocumentLifecycle infoRequestLifecycle = new DocumentLifecycle(new String[] {"Created"});

        //when

        //then
        org.junit.Assert.assertNotNull("infoRequestLifecycle = " + infoRequestLifecycle, infoRequestLifecycle);
    }

}
