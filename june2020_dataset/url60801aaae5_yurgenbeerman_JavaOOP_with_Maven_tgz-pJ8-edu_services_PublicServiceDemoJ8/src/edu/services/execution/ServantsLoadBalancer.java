package edu.services.execution;

import edu.services.docs.IncomingDocument;
import edu.services.docs.OrganizationDocument;
import edu.services.servants.PublicServant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lena on 20.03.14.
 */
public class ServantsLoadBalancer extends PublicServant{
    short nextServantIndex;
    List<PublicServant> publicServants;

    public ServantsLoadBalancer() {
        this.publicServants = new ArrayList<PublicServant>(ExecutionDefaults.MAX_SERVANTS_PER_DEP);
    }

    public void addServant(PublicServant publicServant) {
        publicServants.add(publicServant);
    }

    public void addDocumentToProcess(IncomingDocument document) {
        publicServants.get(nextServantIndex).addDocumentToProcess(document);
        if (nextServantIndex < (publicServants.size() - 1) ) {
            nextServantIndex++;
        } else
            nextServantIndex = 0;
    }

    public String toString() {
        return publicServants.toString();
    }
}
