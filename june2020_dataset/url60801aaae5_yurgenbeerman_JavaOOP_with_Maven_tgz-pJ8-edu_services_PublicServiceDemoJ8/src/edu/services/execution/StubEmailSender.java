package edu.services.execution;

import edu.services.docs.Email;
import edu.communications.EmailSender;

/**
 * Created by Lena on 24.03.14.
 */
public class StubEmailSender implements EmailSender {
    @Override
    public boolean sendEmail(Email email) {
        return true;
    }
}
