package edu.services.execution;

import edu.services.docs.IncomingDocument;
import edu.services.docs.OrganizationDocument;
import edu.services.orgs.PublicService;
import edu.services.orgs.PublicServiceDepartment;

import java.util.Map;

/**
 * Created by yurii.pyvovarenko on 19.03.14.
 */
public class DepartmentsTasksDispatcher {
    private PublicService publicService;
    private Map<String, PublicServiceDepartment> docsToDepartmentsDispatchingTable;

    public DepartmentsTasksDispatcher(PublicService publicService) {
        this.publicService = publicService;
        publicService.setDepartmentsTasksDispatcher(this);
    }

    public void addDocumentToProcess(IncomingDocument document) {
        if ( this.docsToDepartmentsDispatchingTable != null ) {
            if (0 != this.docsToDepartmentsDispatchingTable.size()) {
                PublicServiceDepartment department = this.docsToDepartmentsDispatchingTable
                        .get(document.getClass().getName());
                if ( department != null ) {
                    department.addDocToProcess(document);
                    document.setCurrentPublicServiceDepartment(department);
                } else
                    throw new IllegalStateException(ExecutionDefaults.DEP_IS_NULL);
            } else
                throw new IllegalStateException(ExecutionDefaults.DEPS_DISPATCHING_TABLE_IS_EMPTY);
        } else
            throw new IllegalStateException(ExecutionDefaults.NO_DEPS_DISPATCHING_TABLE);
    }

    public PublicService getPublicService() {
        return publicService;
    }

    public Map<String, PublicServiceDepartment> getDocsToDepartmentsDispatchingTable() {
        return this.docsToDepartmentsDispatchingTable;
    }

    public void setDocsToDepartmentsDispatchingTable(Map<String, PublicServiceDepartment> docsToDepartmentsDispatchingTable) {
        this.docsToDepartmentsDispatchingTable = docsToDepartmentsDispatchingTable;
    }
}
