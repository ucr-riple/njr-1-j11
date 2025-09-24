package edu.services.orgs;

import edu.communications.Address;
import edu.communications.Emailable;
import edu.services.docs.IncomingDocument;
import edu.services.docs.OrganizationDocument;
import edu.services.docs.OutcomingDocument;
import edu.services.execution.DepartmentsTasksDispatcher;
import edu.services.execution.ExecutionDefaults;
import edu.services.execution.ExecutionEnvironment;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by yurii.pyvovarenko on 3/4/14.
 */
public class PublicService implements Emailable {
    private static long lastOrgId;

    private long orgId;
    private Address address;
    private String orgName;
    private int hierarchyLevel;
    private long parentOrgId;
    private String emailAddress;
    private DepartmentsTasksDispatcher departmentsTasksDispatcher;
    private ExecutionEnvironment environment;

    public PublicService() {
        setOrgId();
    }

    public void setOrgId() {
        this.orgId = PublicService.lastOrgId;
        PublicService.lastOrgId++;
    }

    public PublicService(String orgName, ExecutionEnvironment environment) {
        setOrgId();
        setOrgName(orgName);
        this.environment = environment;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getHierarchyLevel() {
        return hierarchyLevel;
    }

    public void setHierarchyLevel(int hierarchyLevel) {
        this.hierarchyLevel = hierarchyLevel;
    }

    public Long getParentOrgId() {
        return parentOrgId;
    }

    public void setParentOrgId(Long parentOrgId) {
        this.parentOrgId = parentOrgId;
    }

    public void sendDocumentToAddress(OutcomingDocument outcomingDocument, Address address) {
        outcomingDocument.setDocSentAddress(address);
        outcomingDocument.setDocSentAddressDate(new GregorianCalendar());
    }

    public String toString() {
        return getOrgName() + ": " +
                "\n    address: " + this.getAddress() +
                "\n    emailAddress: " + this.getEmailAddress() +
                "\n    orgId: " + this.getOrgId() +
                "\n    parentOrgId: " + this.getParentOrgId() +
                "\n    hierarchyLevel: " + this.getHierarchyLevel();
    }

    public void addDocumentToProcess(IncomingDocument document) {
        document.setReceivedByPublicService(true);
        this.departmentsTasksDispatcher.addDocumentToProcess(document);
    }

    public DepartmentsTasksDispatcher getDepartmentsTasksDispatcher() {
        return departmentsTasksDispatcher;
    }

    public void setDepartmentsTasksDispatcher(DepartmentsTasksDispatcher departmentsTasksDispatcher) {
        this.departmentsTasksDispatcher = departmentsTasksDispatcher;
    }

    public ExecutionEnvironment getEnvironment() {
        return environment;
    }
}
