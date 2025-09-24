package edu.clients;

import edu.communications.Address;
import edu.communications.Emailable;
import edu.services.docs.IncomingDocument;
import edu.services.docs.OutcomingDocument;
import edu.services.orgs.OfficialIDsHolder;

/**
 * Created by yurii.pyvovarenko on 05.03.14.
 */
public interface DocumentCreator extends Emailable{
    public long getId();

    public void setOfficialId(String officialId);
    public String getOfficialId();

    public String getName();
    public String getFullNameString();

    public void addRequest(IncomingDocument request);
    public String getRequestsString();

    public void addResponse(OutcomingDocument response);
    public String getResponsesString();

    public Address getAddress();
    public String getAddressString();
    public void setAddress(Address requesterAddress);

    public boolean isRequesterOfficialIdValid(OfficialIDsHolder officialIDsHolder);
}

