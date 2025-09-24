package edu.services.orgs;

import edu.communications.Address;
import edu.communications.Emailable;
import edu.services.docs.IncomingDocument;
import edu.services.docs.OutcomingDocument;
import edu.communications.EmailSender;
import edu.services.execution.ExecutionDefaults;
import edu.services.execution.ExecutionEnvironment;
import edu.services.execution.ServantsTasksDispatcher;
import edu.services.servants.PublicServant;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by yurii.pyvovarenko on 19.03.14.
 */
public class PublicServiceDepartment implements Emailable {
    private static long nextDepartmentId = 0;
    private long departmentId;
    private BlockingQueue<PublicServant> servants;
    private PublicService publicService;
    private String name;
    private String emailAddress;
    private ServantsTasksDispatcher servantsTasksDispatcher;
    private ExecutionEnvironment environment;
    private EmailSender emailSender;

    public PublicServiceDepartment() {
        this.setDepartmentId(this);
    }

    public PublicServiceDepartment(PublicService publicService, String name) {
        this();
        this.publicService = publicService;
        this.name = name;
        this.servants = new ArrayBlockingQueue<PublicServant>(ExecutionDefaults.MAX_SERVANTS_PER_DEP);
        this.environment = publicService.getEnvironment();
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void addPublicServant(PublicServant servant) {
        servants.add(servant);
    }

    public BlockingQueue<PublicServant> getPublicServants() {
        return servants;
    }

    public void addDocToProcess(IncomingDocument document) {
        servantsTasksDispatcher.addDocumentToProcess(document);
        document.setCurrentPublicServiceDepartment(this);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return this.getName();
    }

    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (! (other instanceof PublicServiceDepartment) )
            return false;
        PublicServiceDepartment otherPublicServiceDepartment = (PublicServiceDepartment) other;
        return (name == null) ?
                (otherPublicServiceDepartment.name == null) :
                (name.equals(otherPublicServiceDepartment.name) &&
                        publicService.equals(otherPublicServiceDepartment.publicService)
                );
    }

    public int hashCode() {
        return (name.hashCode() + publicService.toString().hashCode() + ((Long) departmentId).toString().hashCode());
    }

    public ServantsTasksDispatcher getServantsTasksDispatcher() {
        return servantsTasksDispatcher;
    }

    public void setServantsTasksDispatcher(ServantsTasksDispatcher servantsTasksDispatcher) {
        this.servantsTasksDispatcher = servantsTasksDispatcher;
    }

    public PublicService getPublicService() {
        return publicService;
    }

    public void setPublicService(PublicService publicService) {
        this.publicService = publicService;
    }

    public ExecutionEnvironment getEnvironment() {
        return environment;
    }

    public void sendDocumentToAddress(OutcomingDocument outcomingDocument, Address address) {
        publicService.sendDocumentToAddress(outcomingDocument, address);
    }

    private static void setDepartmentId(PublicServiceDepartment department) {
        department.departmentId = nextDepartmentId;
        nextDepartmentId++;
    }

    public EmailSender getEmailSender() {
        return emailSender;
    }

    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }
}
