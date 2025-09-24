package edu.services.docs;

/**
 * Created by yurii.pyvovarenko on 06.03.14.
 *
 * It is no allowed to modify DocumentType object when it is in use or is Finalised.
 * docTypeShortName is intended for creating specific documents numbers
 */
public class DocumentType {
    boolean isDocTypeInUse = false;
    boolean isFinalized = false;
    private String docTypeName;
    private String docTypeShortName;
    private DocumentLifecycle documentLifecycle;

    public DocumentType(String docTypeName, String docTypeShortName, DocumentLifecycle documentLifecycle) {
        setDocTypeName(docTypeName);
        setDocTypeShortName(docTypeShortName);
        setDocumentLifecycle(documentLifecycle);
        documentLifecycle.setInUse(true);
    }

    public String getDocTypeName() {
        return docTypeName;
    }

    public void setDocTypeName(String docTypeName) {
        if ((!isDocTypeInUse) && (!isFinalized)) {
            this.docTypeName = docTypeName;
        }
    }

    public String getDocTypeShortName() {
        return docTypeShortName;
    }

    public void setDocTypeShortName(String docTypeShortName) {
        if ((!isDocTypeInUse) && (!isFinalized)) {
            this.docTypeShortName = docTypeShortName;
        }
    }

    public DocumentLifecycle getDocumentLifecycle() {
        return documentLifecycle;
    }

    public void setDocumentLifecycle(DocumentLifecycle documentLifecycle) {
        if ((!isDocTypeInUse) && (!isFinalized)) {
            this.documentLifecycle = documentLifecycle;
        }
    }

    public void setDocTypeInUse(boolean isDocTypeInUse) {
        this.isDocTypeInUse = isDocTypeInUse;
    }

    public boolean isFinalized() {
        return isFinalized;
    }

    public void setFinalized(boolean isFinalized) {
        this.isFinalized = isFinalized;
    }
}
