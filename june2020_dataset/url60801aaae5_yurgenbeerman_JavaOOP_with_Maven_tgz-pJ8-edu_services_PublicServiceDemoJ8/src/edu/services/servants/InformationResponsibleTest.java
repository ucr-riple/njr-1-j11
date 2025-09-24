package edu.services.servants;

import edu.services.execution.ExecutionEnvironment;
import edu.services.orgs.PublicService;
import edu.services.orgs.PublicServiceDepartment;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by yurii.pyvovarenko on 14.03.14.
 */
public class InformationResponsibleTest {
    InformationResponsible informationResponsibleServant;
    PublicService publicService;
    PublicServiceDepartment infoRequestsDep;

    @Before
    public void setUp() {
        ExecutionEnvironment environment = new ExecutionEnvironment();
        publicService = new PublicService("Improvements service", environment);
        infoRequestsDep = new PublicServiceDepartment(publicService,"infoRequestsDep_0");
        informationResponsibleServant =
                new InformationResponsible(infoRequestsDep, "Karpenko","Petro","Ivanovych");
    }

    @Test
    public void shouldReturnNullWhenGetInformationForReplyNotSet() throws Exception {
        //given
        //when

        //then
        assertNull(informationResponsibleServant.getInformationForReply());
    }

    @Test
    public void shouldGetInformationForReplyWhenItIsSet() throws Exception {
        //given
        //when
        informationResponsibleServant.setInformationForReply("ку-ку");

        //then
        assertEquals("ку-ку", informationResponsibleServant.getInformationForReply());
    }

    @Test
    public void shouldSetInformationForReply() throws Exception {
        //given
        //when
        informationResponsibleServant.setInformationForReply("000");
        informationResponsibleServant.setInformationForReply("ку-ку");

        //then
        assertEquals("ку-ку", informationResponsibleServant.getInformationForReply());
    }

    @Test
    public void testGetInformationResponsibleName() throws Exception {

    }

    @Test
    public void testToString() throws Exception {
        //given

        //when

        //then
        assertEquals("Karpenko Petro Ivanovych", informationResponsibleServant.toString());
    }
}
