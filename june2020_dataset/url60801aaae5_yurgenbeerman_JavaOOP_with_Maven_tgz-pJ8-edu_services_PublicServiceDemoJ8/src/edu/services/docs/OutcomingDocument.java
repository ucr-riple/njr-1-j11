package edu.services.docs;

import edu.clients.DocumentCreator;
import edu.communications.Address;
import edu.services.orgs.PublicService;
import edu.services.servants.PublicServant;

import java.util.GregorianCalendar;

/**
 * Created by yurii.pyvovarenko on 05.03.14.
 *
 * Most of doc field setters (modifier methods) body must be enclosed by "if (!isFinalized) {}" !
 */

public class OutcomingDocument extends OrganizationDocument {
    private long initiatingDocId;
    private OrganizationDocument initiatingDocument;
    private Email docSentEmail;
    private Address docSentAddress;
    private GregorianCalendar docSentAddressDate;
    private boolean isEmailed = false;
    private boolean isSentViaPost = false;

    public String getValidityString() {
        return super.getValidityString();
    }

    public void setDocSentEmail(Email email) {
        this.docSentEmail = email;
        isEmailed = true;
    }

    public GregorianCalendar getDocSentEmailDate() {
        if (null != docSentEmail) {
            return docSentEmail.getEmailSentDate();
        } else {
            throw new IllegalStateException(DocDefaults.DOC_NOT_SENT_VIA_EMAIL);
        }
    }

    public String getDocSentEmailAddress() {
        if (null != docSentEmail) {
            return docSentEmail.getEmailToAddresses();
        } else {
            throw new IllegalStateException(DocDefaults.DOC_NOT_SENT_VIA_EMAIL);
        }
    }

    public void setDocSentAddress(Address address) {
        this.docSentAddress = address;
    }

    public Address getDocSentAddress() {
        return this.docSentAddress;
    }

    public GregorianCalendar getDocSentAddressDate() {
        return docSentAddressDate;
    }

    public void setDocSentAddressDate(GregorianCalendar docSentAddressDate) {
        this.docSentAddressDate = docSentAddressDate;
    }

    public OutcomingDocument(DocumentType documentType, PublicServant publicServant, PublicService publicService) {
        super(documentType, publicServant, publicService);
    }

    public void publishToRequester(DocumentCreator documentCreator) {
        documentCreator.addResponse(this);
        this.isFinalized = true;
    }

    public long getInitiatingDocId() {
        return initiatingDocId;
    }

    public OrganizationDocument getInitiatingDocument() {
        return initiatingDocument;
    }

    public void setInitiatingDocument(OrganizationDocument initiatingDocument) {
        if (!isFinalized) {
            this.initiatingDocument = initiatingDocument;
            this.initiatingDocId = initiatingDocument.getDocumentId();
        } else {
            throw new IllegalStateException(DocDefaults.DOC_IS_FINALIZED);
        }
    }

    public String getInitiatingDocumentNumber() {
        if (null != initiatingDocument) {
            return this.initiatingDocument.getDocumentNumber();
        } else {
            return DocDefaults.NONE;
        }
    }

    public String toString() {
        String result =
                        "\n    initiatingDocument Number: " + getInitiatingDocumentNumber();
        return super.toString() + result;
    }

    public boolean isSentViaPost() {
        return isSentViaPost;
    }

    public void setSentViaPost(boolean isSentViaPost) {
        this.isSentViaPost = isSentViaPost;
    }

    public boolean isEmailed() {
        return isEmailed;
    }

    public void setEmailed(boolean isEmailed) {
        this.isEmailed = isEmailed;
    }
}
