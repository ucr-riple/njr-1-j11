package edu.services.servants;

import edu.services.execution.ExecutionEnvironment;
import edu.services.orgs.PublicService;
import edu.services.orgs.PublicServiceDepartment;
import junit.framework.TestCase;
import org.omg.CORBA.Environment;
import org.junit.Test;

/**
 * Created by yurii.pyvovarenko on 14.03.14.
 */
public class PublicServantTest {
    @Test
    public void testToString() throws Exception {
        //given
        ExecutionEnvironment environment = new ExecutionEnvironment();
        PublicService publicService = new PublicService("Improvements service", environment);
        PublicServiceDepartment infoRequestsDep = new PublicServiceDepartment(publicService,"infoRequestsDep_0");

        //when
        PublicServant publicServant =
                new PublicServant(infoRequestsDep, "Karpenko","Petro","Ivanovych");

        //then
        org.junit.Assert.assertEquals("Karpenko Petro Ivanovych", publicServant.toString());
    }
}
