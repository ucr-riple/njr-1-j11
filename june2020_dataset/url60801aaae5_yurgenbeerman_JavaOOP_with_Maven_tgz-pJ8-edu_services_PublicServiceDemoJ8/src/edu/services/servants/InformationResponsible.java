package edu.services.servants;

import edu.clients.FullName;
import edu.services.docs.*;
import edu.services.execution.ExecutionDefaults;
import edu.services.orgs.PublicServiceDepartment;

/**
 * Created by yurii.pyvovarenko on 3/4/14.
 */
// I want to move this functionality to ProcessInfoRequests,
// the concrete decorator of WorkingPublicServant concrete component.
public class InformationResponsible extends IncomingDocServant {
    String informationForReply;

    public InformationResponsible(PublicServiceDepartment department,
                                  String surname,
                                  String name,
                                  String secondName) {

        super(department, surname, name, secondName);
    }

    public InformationResponsible(PublicServiceDepartment department, FullName fullName) {
        super(department, fullName);
    }

    public String getInformationForReply() {
        return informationForReply;
    }

    public void setInformationForReply(String info) {
        this.informationForReply = new String(info);
    }

    public OutcomingDocument createOutcomingDocument(IncomingDocument initiatingDocument) {
        if (getEnvironment() != null ) {
            if (getDepartment() != null ) {
                OutcomingDocument outcomingDocument =
                        new OutcomingDocument(
                                getEnvironment().getOutcomingDocType(),
                                this,
                                getDepartment().getPublicService()
                        );
                outcomingDocument.setText(this.getInformationForReply());
                initiatingDocument.setReactionDocument(outcomingDocument);
                outcomingDocument.setInitiatingDocument(initiatingDocument);
                outcomingDocument.setDocumentName(ExecutionDefaults.OUTCOMING_DOC_NAME);
                return outcomingDocument;
            } else
                throw new IllegalStateException(ExecutionDefaults.DEPARTMENT_IS_NULL);
        } else
                throw new IllegalStateException(ExecutionDefaults.ENVIRONMENT_IS_NULL);
    }

    public void replyByEmail(InformationRequest document, OutcomingDocument outcomingDocument) {
        Email email = new Email(getDepartment().getEmailAddress(), document.getAuthor().getEmailAddress(),
                this.getInformationForReply());
        email.setEmailSender(this.getDepartment().getEmailSender());
        email.sendEmail();
        outcomingDocument.setDocSentEmail(email);
    }

    public void addDocumentToProcess(IncomingDocument document) {
        super.addDocumentToProcess(document);
        document.setIncomingDocResponsible(this);
        document.setIncomingDocResponsible(this);
        //TODO remove the next call out of this method
        processDocument(((InformationRequest) document));
    }

    public void processDocument(InformationRequest document) {
        OutcomingDocument outcomingDocument = createOutcomingDocBasedOnInfoRequest(document);

        if (document.isToSendReplyToEmail()) {
            replyByEmail(document, outcomingDocument);
        }
        if (document.isToSendReplyToPostAddress()) {
            getDepartment().sendDocumentToAddress(outcomingDocument, document.getAuthor().getAddress());
        }
        outcomingDocument.setNextDocumentStatus();

        outcomingDocument.publishToRequester(document.getAuthor());
        getDocumentsToProcess().remove(document);
    }

    public OutcomingDocument createOutcomingDocBasedOnInfoRequest(InformationRequest document) {
        OutcomingDocument outcomingDocument =
                this.createOutcomingDocument(document);
        document.setReactionDocument(outcomingDocument);
        document.setFinalized(true);
        outcomingDocument.setNextDocumentStatus();
        return outcomingDocument;
    }
}
