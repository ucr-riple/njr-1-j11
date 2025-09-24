package edu.communications;

import edu.services.docs.Email;

/**
 * Created by yurii.pyvovarenko on 24.03.14.
 */
public interface EmailSender {
    public boolean sendEmail(Email email);
}
