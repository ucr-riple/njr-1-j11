package edu.services.docs;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by yurii.pyvovarenko on 13.03.14.
 */


public class OutcomingDocumentTest extends DocsTestsBasics {
    IncomingDocument incomingDoc;
    OutcomingDocument outcomingDoc;

    @Before
    public void createDocsForTests() {
        incomingDoc = new InformationRequest(infoRequestDocType, documentCreator,publicService);
        outcomingDoc = new OutcomingDocument(infoRequestDocType,informationResponsibleServant,publicService);
    }

    @Test
    public void shouldGetInitiatingDocIdAppropriateValue() throws Exception {
        //given

        //when
        outcomingDoc.setInitiatingDocument(incomingDoc);

        //then
        org.junit.Assert.assertEquals(outcomingDoc.getInitiatingDocId(), incomingDoc.getDocumentId());
    }

    @Test
    public void shouldGetInitiatingDocumentSameObject() throws Exception {
        //given

        //when
        outcomingDoc.setInitiatingDocument(incomingDoc);

        //then
        org.junit.Assert.assertSame(outcomingDoc.getInitiatingDocument(), incomingDoc);
    }


}
