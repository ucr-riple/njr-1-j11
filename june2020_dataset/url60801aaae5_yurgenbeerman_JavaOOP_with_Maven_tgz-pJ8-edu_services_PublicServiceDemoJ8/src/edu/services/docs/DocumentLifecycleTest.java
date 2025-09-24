package edu.services.docs;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by yurii.pyvovarenko on 13.03.14.
 */
public class DocumentLifecycleTest {
    DocumentLifecycle infoRequestLifecycle;

    @Before
    public void createLinearLifecycleFinalized() {
        String[] infoRequestLifecycleString = {"Created", "Assigned", "Replied"};
        infoRequestLifecycle = new DocumentLifecycle(infoRequestLifecycleString);
        infoRequestLifecycle.setFinalized(true);
    }

    @Test (expected = IllegalStateException.class)
    public void shouldExceptionWhenInUseAndTryingToSetSomething() {
        //given

        //when

        //then
        infoRequestLifecycle.setFinalStatusIndex(9);  //BUG
    }

    @Test
    public void shouldReturnTrueWhenIsInUse() throws Exception {
        //given

        //when
        infoRequestLifecycle.setInUse(true);

        //then
        org.junit.Assert.assertTrue(infoRequestLifecycle.isInUse());
    }

    @Test
    public void shouldReturnTrueWhenIsFinalized() throws Exception {
        //when
        infoRequestLifecycle.setFinalized(true);

        //then
        assertTrue(infoRequestLifecycle.isFinalized());
    }

    @Test
    public void shouldReturnFalseWhenNotFinalized() throws Exception {
        //when
        infoRequestLifecycle.setFinalized(false);

        //then
        assertFalse(infoRequestLifecycle.isFinalized());
    }
}
