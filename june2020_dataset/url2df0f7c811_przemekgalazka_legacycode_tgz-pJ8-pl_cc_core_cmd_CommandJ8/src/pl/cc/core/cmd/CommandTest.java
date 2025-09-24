package pl.cc.core.cmd;

import org.junit.Test;

import static org.fest.assertions.Assertions.*;

/**
 * @author Przemysław Gałązka
 * @since 25-07-2013
 */
public class CommandTest {


  @Test
  public void shouldWorkSmokeTest() throws Exception {
    //-------------------- GIVEN -------------------------------------------------------------------


    //-------------------- WHEN --------------------------------------------------------------------
    Command actualCommand = Command.factory("line", "1000");

    //-------------------- THEN --------------------------------------------------------------------
    assertThat(actualCommand).isInstanceOf(CommandUnknown.class);
  }


  @Test
  public void shouldWorkSmokeTest2() throws Exception {
    //-------------------- GIVEN -------------------------------------------------------------------


    //-------------------- WHEN --------------------------------------------------------------------
    Command actualCommand = Command.factory("Welcome. Please authorize.", "1000");

    //-------------------- THEN --------------------------------------------------------------------
    assertThat(actualCommand).isInstanceOf(CommandWelcome.class);
  }

  @Test
  public void shouldWorkSmokeTest3() throws Exception {
    //-------------------- GIVEN -------------------------------------------------------------------


    //-------------------- WHEN --------------------------------------------------------------------
    Command actualCommand = Command.factory("version", "1000");

    //-------------------- THEN --------------------------------------------------------------------
    assertThat(actualCommand).isInstanceOf(InfoVersion.class);
  }


}
