package edu.services.servants;

import edu.clients.Citizen;
import edu.clients.FullName;
import edu.services.docs.IncomingDocument;
import edu.services.docs.OrganizationDocument;
import edu.services.docs.OutcomingDocument;
import edu.services.execution.ExecutionEnvironment;
import edu.services.orgs.PublicServiceDepartment;

import java.util.ArrayList;


/**
 * Created by yurii.pyvovarenko on 3/4/14.
 */
// Implementing Decorator Pattern.
// PublicServant is the main "abstract" class inherited by:
//  1) WorkingPublicServant (concrete component to be decorated)
//  2) PublicServantResponsibilities ("abstract" decorator) to be inherited by concrete decorators:
//      a) ProcessInfoRequests
//      b) ProcessThanksAndClaims
public class PublicServant extends Citizen {
    private static long lastPublicServantId;
    private long publicServantId;
    private PublicServiceDepartment department;
    private ArrayList<OrganizationDocument> documentsToProcess;
    private ExecutionEnvironment environment;

    protected PublicServant() {}

//    public abstract void doMainResponsibility();
//
//    public abstract void doBonusedResponsibility();
    public void doMainResponsibility(){

    }

    public void doBonusedResponsibility(){

    }

    public PublicServant(PublicServiceDepartment department, FullName fullName) {
        super(fullName);
        this.environment = department.getEnvironment();

        this.publicServantId = PublicServant.lastPublicServantId;
        PublicServant.lastPublicServantId++;

        this.department = department;
        department.addPublicServant(this);

        documentsToProcess = new ArrayList<OrganizationDocument>(); //(ExecutionDefaults.MAX_SERVANTS_PER_SERVANT);
    }

    public PublicServant(PublicServiceDepartment department, String surname, String name, String secondName) {
        this(department, new FullName(surname, name, secondName));
    }

    public String toString() {
        return this.getFullNameString();
    }

    //equals(), hashCode
    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (! (other instanceof PublicServant) )
            return false;
        PublicServant otherPublicServant = (PublicServant) other;
        return (
                    (publicServantId == otherPublicServant.publicServantId) &&
                    (department == otherPublicServant.department) &&
                    super.equals(otherPublicServant)
                );
    }

    public int hashCode() {
        return (super.hashCode() +
                ((Long) publicServantId).toString().hashCode() +
                department.toString().hashCode());
    }

    public ArrayList<OrganizationDocument> getDocumentsToProcess() {
        return documentsToProcess;
    }

    public int compareTo(PublicServant other) {
        if ( this.getId() == other.getId() )
            return 0;
        return ( this.getId() > other.getId() ) ? 1 : -1;
    }

    public ExecutionEnvironment getEnvironment() {
        return environment;
    }

    public void addDocumentToProcess(IncomingDocument document) {
        documentsToProcess.add(document);
    }

    public PublicServiceDepartment getDepartment() {
        return department;
    }
}
