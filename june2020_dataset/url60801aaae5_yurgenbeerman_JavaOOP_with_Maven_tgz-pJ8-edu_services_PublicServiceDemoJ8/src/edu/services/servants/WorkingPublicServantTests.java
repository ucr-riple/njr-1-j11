package edu.services.servants;

/**
 * Created by yurii.pyvovarenko on 01.05.14.
 */
import edu.services.execution.ExecutionEnvironment;
import edu.services.orgs.PublicService;
import edu.services.orgs.PublicServiceDepartment;
import org.junit.Test;

public class WorkingPublicServantTests {
    @Test
    public void MustCreateInstanceOfWorkingPublicServant() {
        //given
        ExecutionEnvironment environment = new ExecutionEnvironment();
        PublicService publicService = new PublicService("Improvements service", environment);
        PublicServiceDepartment infoRequestsDep = new PublicServiceDepartment(publicService,"infoRequestsDep_0");

        //when
        PublicServant workingPublicServant =
                new WorkingPublicServant(infoRequestsDep, "Karpenko","Petro","Ivanovych");

        //then
        org.junit.Assert.assertEquals("Karpenko Petro Ivanovych", workingPublicServant.toString());
    }

    @Test
    public void MustDecorateWorkingPublicServantWithResponsibility() {
        //given
        ExecutionEnvironment environment = new ExecutionEnvironment();
        PublicService publicService = new PublicService("Improvements service", environment);
        PublicServiceDepartment infoRequestsDep = new PublicServiceDepartment(publicService,"infoRequestsDep_0");
        PublicServant workingPublicServant =
                new WorkingPublicServant(infoRequestsDep, "Karpenko","Petro","Ivanovych");

        //when
        PublicServant infoRequestsPublicServant =
                new ProcessInfoRequests(workingPublicServant);
        ((ProcessInfoRequests)infoRequestsPublicServant).setInformationForReply("test info for reply");

        //then
        org.junit.Assert.assertEquals("test info for reply"
                , ((ProcessInfoRequests) infoRequestsPublicServant).getInformationForReply());
    }

    @Test
    public void MustFailWnenNotDecorated() throws ClassCastException {
        //given
        ExecutionEnvironment environment = new ExecutionEnvironment();
        PublicService publicService = new PublicService("Improvements service", environment);
        PublicServiceDepartment infoRequestsDep = new PublicServiceDepartment(publicService,"infoRequestsDep_0");
        PublicServant workingPublicServant =
                new WorkingPublicServant(infoRequestsDep, "Karpenko","Petro","Ivanovych");

        //when
        PublicServant infoRequestsPublicServant = workingPublicServant;

        //then
        //TODO must catch the exception properly as for a test
        org.junit.Assert.assertEquals("test info for reply"
                , ((ProcessInfoRequests) infoRequestsPublicServant).getInformationForReply());
    }

    @Test
    public void MustSetReplyForThankWnenDecoratedTwice() {
        //given
        ExecutionEnvironment environment = new ExecutionEnvironment();
        PublicService publicService = new PublicService("Improvements service", environment);
        PublicServiceDepartment infoRequestsDep = new PublicServiceDepartment(publicService,"infoRequestsDep_0");
        PublicServant workingPublicServant =
                new WorkingPublicServant(infoRequestsDep, "Karpenko","Petro","Ivanovych");
        PublicServant infoRequestsPublicServant =
                new ProcessInfoRequests(workingPublicServant);
        ((ProcessInfoRequests)infoRequestsPublicServant).setInformationForReply("test info for reply");

        //when
        PublicServant infoRequestsAndThanksPublicServant =
                new ProcessThanksAndClaims(infoRequestsPublicServant);
        ((ProcessThanksAndClaims)infoRequestsAndThanksPublicServant).setReplyToThank("test info for reply");

        //then
        org.junit.Assert.assertEquals("test info for reply"
                , ((ProcessThanksAndClaims)infoRequestsAndThanksPublicServant).getReplyToThank());
    }

    @Test
    public void MustSetReplyForClaimWnenDecoratedTwice() {
        //given
        ExecutionEnvironment environment = new ExecutionEnvironment();
        PublicService publicService = new PublicService("Improvements service", environment);
        PublicServiceDepartment infoRequestsDep = new PublicServiceDepartment(publicService,"infoRequestsDep_0");
        PublicServant workingPublicServant =
                new WorkingPublicServant(infoRequestsDep, "Karpenko","Petro","Ivanovych");
        PublicServant infoRequestsPublicServant =
                new ProcessInfoRequests(workingPublicServant);
        ((ProcessInfoRequests)infoRequestsPublicServant).setInformationForReply("test info for reply");

        //when
        PublicServant infoRequestsAndThanksPublicServant =
                new ProcessThanksAndClaims(infoRequestsPublicServant);
        ((ProcessThanksAndClaims)infoRequestsAndThanksPublicServant).setReplyToClaim("test info for reply");

        //then
        org.junit.Assert.assertEquals("test info for reply"
                , ((ProcessThanksAndClaims)infoRequestsAndThanksPublicServant).getReplyToClaim());
    }

    @Test
    public void MustFailWnenNotDecoratedTwice() throws ClassCastException {
        //given
        ExecutionEnvironment environment = new ExecutionEnvironment();
        PublicService publicService = new PublicService("Improvements service", environment);
        PublicServiceDepartment infoRequestsDep = new PublicServiceDepartment(publicService,"infoRequestsDep_0");
        PublicServant workingPublicServant =
                new WorkingPublicServant(infoRequestsDep, "Karpenko","Petro","Ivanovych");
        PublicServant infoRequestsPublicServant =
                new ProcessInfoRequests(workingPublicServant);
        ((ProcessInfoRequests)infoRequestsPublicServant).setInformationForReply("test info for reply");

        //when
        PublicServant infoRequestsAndThanksPublicServant = infoRequestsPublicServant;

        //then
        //TODO must catch the exception properly as for a test
        org.junit.Assert.assertEquals("test info for reply"
                , ((ProcessThanksAndClaims)infoRequestsAndThanksPublicServant).getReplyToClaim());
    }
}
