package edu.services.servants;

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
public class PublicServantResponsibilities extends PublicServant {
    protected PublicServant decoratedPublicServant;

    public PublicServantResponsibilities(PublicServant decoratedPublicServant) {
        super(decoratedPublicServant.getDepartment(),
                decoratedPublicServant.getFullName());
        this.decoratedPublicServant = decoratedPublicServant;
        //super(department, surname, name, secondName);
    }
}
