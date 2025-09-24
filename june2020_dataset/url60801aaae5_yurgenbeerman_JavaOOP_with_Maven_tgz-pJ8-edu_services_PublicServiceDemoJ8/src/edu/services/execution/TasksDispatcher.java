package edu.services.execution;

import edu.services.docs.OrganizationDocument;
import edu.services.servants.PublicServant;

import java.util.Collection;
import java.util.List;
import java.util.Queue;

/**
 * Created by yurii.pyvovarenko on 19.03.14.
 */
public interface TasksDispatcher {
    public OrganizationDocument chooseDocument();
    public PublicServant choosePublicServant();
}
