package edu.services.execution;

import edu.services.docs.IncomingDocument;
import edu.services.docs.OrganizationDocument;
import edu.services.orgs.PublicServiceDepartment;
import edu.services.servants.PublicServant;

import java.util.Map;

/**
 * Created by yurii.pyvovarenko on 20.03.14.
 */
public class ServantsTasksDispatcher {
    private PublicServiceDepartment department;
    private Map<String, PublicServant> docsToServantsDispatchingTable;

    public ServantsTasksDispatcher(PublicServiceDepartment department) {
        this.department = department;
        department.setServantsTasksDispatcher(this);
    }

    public void setDocsToServantsDispatchingTable(Map<String, PublicServant> docsToServantsDispatchingTable) {
        this.docsToServantsDispatchingTable = docsToServantsDispatchingTable;
    }

    public void addDocumentToProcess(IncomingDocument document) {
        if ( this.docsToServantsDispatchingTable != null ) {
            if (0 != this.docsToServantsDispatchingTable.size()) {
                PublicServant servant =
                        this.docsToServantsDispatchingTable.get(document.getClass().getName());
                servant.addDocumentToProcess(document);
            } else
                throw new IllegalStateException(ExecutionDefaults.DOCS_DISPATCHING_TABLE_IS_EMPTY);
        } else
            throw new IllegalStateException(ExecutionDefaults.NO_DOCS_DISPATCHING_TABLE);
    }

    public PublicServiceDepartment getPublicServiceDepartment() {
        return department;
    }
}
