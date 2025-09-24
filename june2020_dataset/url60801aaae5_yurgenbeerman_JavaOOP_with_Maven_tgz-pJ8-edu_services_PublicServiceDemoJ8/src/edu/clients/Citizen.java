package edu.clients;

import edu.communications.Address;
import edu.communications.Emailable;
import edu.services.docs.IncomingDocument;
import edu.services.docs.OutcomingDocument;
import edu.services.execution.ExecutionDefaults;
import edu.services.orgs.OfficialIDsHolder;

import java.util.ArrayList;

/**
 * Created by yurii.pyvovarenko on 3/4/14.
 *
 * Citizen objects contain information on Requests (IncomingDocs of Organization) and Responses (OutcomingDocs of Organization).
 * Also it has data to approve to Organization, that this is real citizen (officialId field = INN).
 */
public class Citizen implements DocumentCreator, Emailable {
    private long citizenId;
    private FullName fullName;
    private String emailAddress;
    private Address address;
    private ArrayList<IncomingDocument> requests;
    private ArrayList<OutcomingDocument> responses;
    private String officialId;

    private static long nextCitizenId;

    public Citizen() {
        setCitizenId(this);
    }

    public Citizen(FullName fullName) {
        this.fullName = fullName;
        setCitizenId(this);
        this.requests = new ArrayList<IncomingDocument>();
    }

    public Citizen(String surname, String name, String secondName) {
        this(new FullName(surname, name, secondName));
    }

    public void addRequest(IncomingDocument request) {
        this.requests.add(request);
    }

    public ArrayList<IncomingDocument> getRequests() {
        if ( requests != null ) {
            if (0 != requests.size()) {
                return this.requests;
            } else {
                throw new IllegalStateException(ClientsDefaults.NO_REQUESTS);
            }
        } else
            throw new IllegalStateException(ClientsDefaults.REQUESTS_ARRAY_IS_NULL);
    }

    public String getRequestsString() {
        String result = "";
        if ( requests != null ) {
            for (int i = 0; i < getRequests().size(); i++) {
                result = result + requests.get(i).toString();
            }
        }
        return result;
    }

    public FullName getFullName() {
        return fullName;
    }

    public String getFullNameString() {
        if (null != fullName) {
            return fullName.toString();
        } else {
            return null;
        }
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        /* TODO validate passed parameter:
         *   isEmailAddressValid(emailFromAddress)
         *   Found two best validators:
         *     1) http://commons.apache.org/proper/commons-validator/download_validator.cgi
         *     2) https://java.net/projects/javamail/pages/Home#Download_JavaMail_1.5.1_Release
         */
        this.emailAddress = emailAddress;
    }

    public Address getAddress() {
        return address;
    }

    public String getAddressString() {
        if ( null != address) {
            return address.toString();
        } else {
            throw new IllegalStateException(ClientsDefaults.NO_ADDRESS);
        }
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public long getId() {
        return citizenId;
    }

    public String getName() {
        return this.getFullNameString();
    }

    public String getOfficialId() {
        return officialId;
    }

    public void setOfficialId(String officialId) {
        this.officialId = officialId;
    }

    public ArrayList<OutcomingDocument> getResponses() {
        return responses;
    }

    public String getResponsesString() {
        String result = "";
        if ( getResponses() != null ) {
            for (int i = 0; i < getResponses().size(); i++) {
                result += getResponses().get(i);
            }
        }
        return result;
    }

    public void addResponse(OutcomingDocument response) {
        if (null == getResponses()) {
            this.responses = new ArrayList<OutcomingDocument>();
        }
        this.responses.add(response);
    }

    public String toString() {
        return this.getFullName().getName();
    }

    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (! (other instanceof Citizen) )
            return false;
        Citizen otherCitizen = (Citizen) other;
        return (fullName == null) ?
                (otherCitizen.fullName == null) :
        (fullName.equals(otherCitizen.fullName) && officialId.equals(otherCitizen.officialId));
    }

    public int hashCode() {
        return (officialId.hashCode() + fullName.hashCode());
    }

    private static synchronized void setCitizenId(Citizen citizen) {
        citizen.citizenId = nextCitizenId;
        Citizen.nextCitizenId++;
    }

    public long getCitizenId() {
        return this.citizenId;
    }

    public int compareTo(Citizen other) {
        if ( this.getCitizenId() == other.getCitizenId() )
            return 0;
        return ( this.getCitizenId() > other.getCitizenId() ) ? 1 : -1;
    }

    public boolean isRequesterOfficialIdValid(OfficialIDsHolder officialIDsHolder) {
        DocumentCreator documentCreator = this;
        if (( documentCreator.getOfficialId() != null) && (documentCreator.getOfficialId() != "")) {
            if ( documentCreator.getOfficialId().length() != ExecutionDefaults.REQUESTER_OFFICIAL_ID_LENGTH ) {
                return false;
            }
            return officialIDsHolder.ifOfficialIDExists(documentCreator.getOfficialId());
        } else {
            return false;
        }
    }
}
