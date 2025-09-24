package edu.services.servants;

import edu.clients.FullName;
import edu.services.orgs.PublicServiceDepartment;

/**
 * Created by yurii.pyvovarenko on 28.04.14.
 */
// Implementing Decorator Pattern.
// PublicServant is the main "abstract" class inherited by:
//  1) WorkingPublicServant (concrete component to be decorated)
//  2) PublicServantResponsibilities ("abstract" decorator) to be inherited by concrete decorators:
//      a) ProcessInfoRequests
//      b) ProcessThanksAndClaims
public class WorkingPublicServant extends PublicServant {

    public WorkingPublicServant(PublicServiceDepartment department, FullName fullName) {
        super(department, fullName);
    }

    public WorkingPublicServant(PublicServiceDepartment department,
                                  String surname,
                                  String name,
                                  String secondName) {

        super(department, surname, name, secondName);
    }

    public void doMainResponsibility(){

    }

    public void doBonusedResponsibility(){

    }
}
