package edu.services.docs;

import edu.clients.DocumentCreator;
import edu.services.orgs.PublicService;

/**
 * Created by yurii.pyvovarenko on 05.03.14.
 */
public final class InformationRequest extends IncomingDocument {
    private String addressForReply;
    private String emailForReply;
    private boolean ifSendReplyToPostAddress;
    private boolean ifSendReplyToEmail;

    public InformationRequest(DocumentType documentType, DocumentCreator documentCreator, PublicService publicService) {
        super(documentType, documentCreator, publicService);
        documentType.setDocTypeInUse(true);
        setDocumentName(documentType.getDocTypeName() + " #" + this.getDocumentId());
        documentCreator.addRequest(this);
    }

    public String getValidityString() {
        return super.getValidityString();
    }

    public String getAddressForReply() {
        return addressForReply;
    }

    public void setAddressForReply(String addressForReply) {
        if (! isReceivedByPublicService()) {
            this.addressForReply = addressForReply;
        }
    }

    public String getEmailForReply() {
        return emailForReply;
    }

    public void setEmailForReply(String emailForReply) {
        if (! isReceivedByPublicService()) {
            this.emailForReply = emailForReply;
        }
    }

    public boolean isToSendReplyToPostAddress() {
        return ifSendReplyToPostAddress;
    }

    public void setToSendReplyToPostAddress(boolean ifSendReplyToPostAddress) {
        this.ifSendReplyToPostAddress = ifSendReplyToPostAddress;
    }

    public boolean isToSendReplyToEmail() {
        return ifSendReplyToEmail;
    }

    public void setToSendReplyToEmail(boolean ifSendReplyToEmail) {
        this.ifSendReplyToEmail = ifSendReplyToEmail;
    }

    public String toString() {
        String result =
                "\n    addressForReply: " + this.getAddressForReply() +
                "\n    emailForReply: " + this.getEmailForReply();
        return super.toString() + result;
    }
}
