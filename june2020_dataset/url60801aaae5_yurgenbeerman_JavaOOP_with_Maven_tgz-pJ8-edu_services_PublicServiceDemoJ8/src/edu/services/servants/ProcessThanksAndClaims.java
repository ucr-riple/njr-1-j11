package edu.services.servants;

import edu.services.docs.IncomingDocument;

import java.util.LinkedList;

/**
 * Created by yurii.pyvovarenko on 28.04.14.
 */
// Implementing Decorator Pattern.
// PublicServant is the main "abstract" class inherited by:
//  1) WorkingPublicServant (concrete component to be decorated)
//  2) PublicServantResponsibilities ("abstract" decorator) to be inherited by concrete decorators:
//      a) ProcessInfoRequests
//      b) ProcessThanksAndClaims
public class ProcessThanksAndClaims extends PublicServantResponsibilities {
    private PublicServant decoratedPublicServant;
    private LinkedList<IncomingDocument> ThanksQueue;
    private LinkedList<IncomingDocument> ClaimsQueue;
    String replyToThank;
    String replyToClaim;

    public ProcessThanksAndClaims(PublicServant decoratedPublicServant) {
        super(decoratedPublicServant);
    }

    public void doMainResponsibility(){

    }

    public void doBonusedResponsibility(){

    }

    public String getReplyToThank() {
        return replyToThank;
    }

    public void setReplyToThank(String replyToThank) {
        this.replyToThank = replyToThank;
    }

    public String getReplyToClaim() {
        return replyToClaim;
    }

    public void setReplyToClaim(String replyToClaim) {
        this.replyToClaim = replyToClaim;
    }

}