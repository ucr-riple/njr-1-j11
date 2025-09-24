package edu.services.servants;

import edu.clients.FullName;
import edu.services.docs.Email;
import edu.services.docs.IncomingDocument;
import edu.services.docs.InformationRequest;
import edu.services.docs.OutcomingDocument;
import edu.services.execution.ExecutionDefaults;
import edu.services.orgs.PublicServiceDepartment;

/**
 * Created by yurii.pyvovarenko on 27.03.14.
 */
public abstract class IncomingDocServant extends PublicServant {
    public IncomingDocServant(PublicServiceDepartment department, String surname, String name, String secondName) {
        super(department, surname, name, secondName);
    }

    public IncomingDocServant(PublicServiceDepartment department, FullName fullName) {
        super(department, fullName);
    }

    public String getInformationResponsibleName() {
        return super.toString();
    }

    public String toString() {
        return super.toString();
    }

    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (! (other instanceof InformationResponsible) )
            return false;
        return super.equals(other);
    }

    public int hashCode() {
        return super.hashCode();
    }

    public int compareTo(PublicServant other) {
        return super.compareTo(other);
    }

    public void addDocumentToProcess(IncomingDocument document) {
        super.addDocumentToProcess(document);
    }

    abstract public OutcomingDocument createOutcomingDocBasedOnInfoRequest(InformationRequest document);
    abstract public void processDocument(InformationRequest document);
    abstract public void replyByEmail(InformationRequest document, OutcomingDocument outcomingDocument);
    abstract public OutcomingDocument createOutcomingDocument(IncomingDocument initiatingDocument);
}
