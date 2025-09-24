package edu.services.docs;

import edu.clients.DocumentCreator;
import edu.services.orgs.PublicService;
import edu.services.orgs.PublicServiceDepartment;
import edu.utils.PublicRequestsUtils;

import static edu.services.docs.DocDefaults.*;

import java.security.InvalidParameterException;
import java.util.GregorianCalendar;

/**
 * Created by yurii.pyvovarenko on 3/4/14.
 */
public class OrganizationDocument extends Text {
    private static long lastDocumentId;

    private DocumentLifecycle documentLifecycle;
    private long documentId;
    private String documentName;
    private long documentAuthorId;
    private DocumentCreator author;
    private GregorianCalendar documentCreationDate;
    private DocumentType documentType;
    private String documentNumber;
    private DocumentStatus documentStatus;
    private long orgId;
    private String text;
    private PublicServiceDepartment currentPublicServiceDepartment;

    public OrganizationDocument() {
        this.documentId = OrganizationDocument.lastDocumentId;
        OrganizationDocument.lastDocumentId++;
    }

    public OrganizationDocument(DocumentType documentType, DocumentCreator author, PublicService publicService) {
        this();
        if (documentType != null) {
            this.documentType = documentType;
        } else {
            throw new InvalidParameterException("documentType");
        }
        if (author != null) {
            this.author = author;
        } else {
            throw new InvalidParameterException("author");
        }
        if (publicService != null) {
            this.orgId = publicService.getOrgId();
        } else {
            throw new InvalidParameterException("publicService");
        }
        this.documentNumber = documentType.getDocTypeShortName() + this.documentId;
        this.documentStatus = new DocumentStatus(documentType.getDocumentLifecycle());
        this.setDocumentCreationDate(documentStatus.getZeroStatusDate());
        this.setDocumentAuthorId(author.getId());
    }

    public String getValidityString() {
        if (null == documentName) {
            return DOC_NAME_IS_NULL;
        } else if (documentName.length() == 0) {
            return DOC_NAME_IS_EMPTY;
        }

        if (documentId < 0) {
            return DOC_ID_SUBZERO;
        } else {
            // TODO ? check if there's no document with same id?
        }

        if (null == documentCreationDate) {
            return DOC_CREATION_DATE_IS_NULL;
        } else if (! documentCreationDate.after(new GregorianCalendar(2014, 0, 0))) {
            return DOC_CREATION_DATE_NOT_AFTER_2014_0_0;
        } else {
            if (! documentCreationDate.before(PublicRequestsUtils.nowPlusTenMinutes())) {
                return DOC_CREATION_DATE_IS_NOT_BEFORE_NOW;
            }
        }

        if (null == documentType) {
            return DOC_TYPE_IS_NULL;
        }

        if ( (null == documentNumber) || (0 == documentNumber.length()) ) {
            return DOC_TYPE_IS_NULL_ORZERO_LENGTH;
        }

        if (null == documentStatus) {
            return DOC_STATUS_IS_NULL;
        }

        if (0 > orgId) {
            return DOC_ORG_ID_IS_SUBZERO;
        } else {
            // TODO check if there exists Organization having the orgId?
        }

        if (null == author) {
            return DOC_AUTHOR_IS_NULL;
        } else if (author.getId() != documentAuthorId) {
            return DOC_AUTHOR_IDS_ARE_DIFFERENT;
        } else if ((documentAuthorId < 0)) {
            return DOC_AUTHOR_ID_IS_SUBZERO;
        }

        return VALID;
    }

    public long getDocumentId() {
        return documentId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        if (! isFinalized) {
            this.documentName = documentName;
        }
    }

    public long getDocumentAuthorId() {
        return documentAuthorId;
    }

    public void setDocumentAuthorId(long documentAuthorId) {
        if (! isFinalized) {
            this.documentAuthorId = documentAuthorId;
        }
    }

    public GregorianCalendar getDocumentCreationDate() {
        return documentCreationDate;
    }

    public void setDocumentCreationDate(GregorianCalendar documentCreationDate) {
        if (! isFinalized) {
            this.documentCreationDate = documentCreationDate;
        }
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        if (! isFinalized) {
            this.documentNumber = documentNumber;
        }
    }

    public DocumentStatus getDocumentStatus() {
        return documentStatus;
    }

    public String getDocumentStatusString() {
        return documentStatus.getCurrentDocumentStatus();
    }

    public void setNextDocumentStatus() {
        if (! isFinalized) {
            this.documentStatus.setNextDocumentStatus();
        } else
            throw new IllegalStateException(DocDefaults.DOC_IS_FINALIZED);
    }

    public void setPreviousDocumentStatus() {
        if (! isFinalized) {
            this.documentStatus.setPreviousDocumentStatus();
        }
    }

    public String getStatusesHistoryString() {
        return documentStatus.getDocumentStatusesHistoryString();
    }

    public long getOrgId() {
        return orgId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        if (! isFinalized) {
            this.text = text;
        }
    }

    public String toString() {
        return  "\n " + documentType.getDocTypeName() + ": " +
                "\n    DocumentNumber: " + this.getDocumentNumber() +
                "\n    author: " + this.getAuthorName() +
                "\n    text: " + this.getText() +
                "\n    orgId: " + this.getOrgId() +
                "\n    DocumentStatusesHistory: " + this.getStatusesHistoryString();
    }

    public String getDocumentTypeName() {
        return this.documentType.getDocTypeName();
    }

    public DocumentCreator getAuthor() {
        return author;
    }

    public String getAuthorName() {
        return this.getAuthor().getName();
    }

    public PublicServiceDepartment getCurrentPublicServiceDepartment() {
        return currentPublicServiceDepartment;
    }

    public void setCurrentPublicServiceDepartment(PublicServiceDepartment currentPublicServiceDepartment) {
        this.currentPublicServiceDepartment = currentPublicServiceDepartment;
    }
}
