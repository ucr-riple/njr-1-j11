package edu.services.orgs;

/**
 * Created by Lena on 24.03.14.
 */
public class StubOfficialIDsHolder extends PublicService implements OfficialIDsHolder {
    public boolean ifOfficialIDExists(String officialID) {
        return true;
    }
}
