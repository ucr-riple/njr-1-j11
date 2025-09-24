package edu.services.docs;

import org.junit.Test;

/**
 * Created by yurii.pyvovarenko on 13.03.14.
 */
public class OrganizationDocumentTest extends DocsTestsBasics {

    @Test
    public void shouldGetDocumentCreationDate() throws Exception {
        //given
        OrganizationDocument doc = new OrganizationDocument(infoRequestDocType,informationResponsibleServant,publicService);

        //when

        //then
        org.junit.Assert.assertNotNull("doc.getDocumentCreationDate() = " + doc.getDocumentCreationDate(),
                doc.getDocumentCreationDate());
    }
}
