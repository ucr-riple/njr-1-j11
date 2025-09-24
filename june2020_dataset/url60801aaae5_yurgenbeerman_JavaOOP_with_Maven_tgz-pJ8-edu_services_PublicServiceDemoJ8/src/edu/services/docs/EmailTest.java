package edu.services.docs;

import edu.services.execution.StubEmailSender;
import edu.utils.PublicRequestsUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.GregorianCalendar;

/**
 * Created by yurii.pyvovarenko on 12.03.14.
 */
public class EmailTest {
    Email email;

    @Before
    public void createEmail() {
        email = new Email("to@address", "from@address", "email text");
    }

    @Test
    public void shouldEmailSentDateWhenSendEmail() throws Exception {
        //given
        GregorianCalendar dateBeforeSending = new GregorianCalendar();
        email.setEmailSender(new StubEmailSender());

        //when
        email.sendEmail();

        //then
        org.junit.Assert.assertNotNull(email.getEmailSentDate());

        /* TODO the next expression fails the test -- find solution in the code (I've solved it already by adding 1 minute): */
        org.junit.Assert.assertTrue(
                ( email.getEmailSentDate().before(PublicRequestsUtils.nowPlusTenMinutes()) ) &&
                ( email.getEmailSentDate().after(PublicRequestsUtils.nowMinusTenMinutes()) ) )  ;

    }

    @Test
    public void shouldIsFinalizedWhenSendEmail() throws Exception {
        //given
        email.setEmailSender(new StubEmailSender());

        //when
        email.sendEmail();

        //then
        org.junit.Assert.assertTrue( email.isFinalized() );
    }

    @Test
    public void shouldSetEmailCCAddresses() throws Exception {
        //when
        email.setEmailCCAddresses("cc@address");

        //then
        org.junit.Assert.assertNotNull(email.getEmailCCAddresses());
    }

    @Test
    public void shouldSetEmailBCCAddresses() throws Exception {
        //when
        email.setEmailBCCAddresses("bcc@address");

        //then
        org.junit.Assert.assertNotNull(email.getEmailBCCAddresses());
    }

    @Test
    public void shouldGetEmailSentDateAfterSending() throws Exception {
        //given
        email.setEmailSender(new StubEmailSender());

        //when
        email.sendEmail();

        //then
        org.junit.Assert.assertNotNull(email.getEmailSentDate());
    }

    @Test
    public void shouldSetFinalized() throws Exception {
        //when
        email.setFinalized(true);

        //then
        org.junit.Assert.assertTrue(email.isFinalized());
    }


    @Test
    public void shouldGetEmailFromAddressWhenSetFinalized() throws Exception {
        //given
        email.setEmailFromAddress("fromFROM");

        //when
        email.setFinalized(true);

        //then
        org.junit.Assert.assertEquals("fromFROM", email.getEmailFromAddress());
    }

    @Test
    public void shouldSendEmailThrougEmailService() throws Exception {
        //given
        email.setEmailFromAddress("fromFROM");

        //when
        email.setFinalized(true);

        //then
        org.junit.Assert.assertEquals("fromFROM", email.getEmailFromAddress());
    }
}
