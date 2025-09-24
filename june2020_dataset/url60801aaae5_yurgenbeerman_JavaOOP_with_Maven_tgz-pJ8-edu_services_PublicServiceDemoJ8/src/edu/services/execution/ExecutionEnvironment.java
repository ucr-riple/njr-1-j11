package edu.services.execution;

import edu.clients.DocumentCreator;
import edu.communications.EmailSender;
import edu.services.docs.DocumentType;
import edu.services.orgs.PublicService;
import edu.services.orgs.PublicServiceDepartment;

/**
 * Created by yurii.pyvovarenko on 20.03.14.
 */
public class ExecutionEnvironment {
    private DocumentType outcomingDocType;
    private DocumentType infoRequestDocType;
    private DocumentCreator documentCreator;
    PublicService publicService;
    PublicServiceDepartment infoRequestsDepartment;
    private EmailSender emailSender;

    public EmailSender getEmailSender() {
        return emailSender;
    }

    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    public DocumentType getInfoRequestDocType() {
        return infoRequestDocType;
    }

    public void setInfoRequestDocType(DocumentType infoRequestDocType) {
        this.infoRequestDocType = infoRequestDocType;
    }

    public DocumentCreator getDocumentCreator() {
        return documentCreator;
    }

    public void setDocumentCreator(DocumentCreator documentCreator) {
        this.documentCreator = documentCreator;
    }

    public PublicService getPublicService() {
        return publicService;
    }

    public void setPublicService(PublicService publicService) {
        this.publicService = publicService;
    }

    public DocumentType getOutcomingDocType() {
        return outcomingDocType;
    }

    public void setOutcomingDocType(DocumentType outcomingDocType) {
        this.outcomingDocType = outcomingDocType;
    }

    public PublicServiceDepartment getInfoRequestsDepartment() {
        return infoRequestsDepartment;
    }

    public void setInfoRequestsDepartment(PublicServiceDepartment infoRequestsDepartment) {
        this.infoRequestsDepartment = infoRequestsDepartment;
    }

}
