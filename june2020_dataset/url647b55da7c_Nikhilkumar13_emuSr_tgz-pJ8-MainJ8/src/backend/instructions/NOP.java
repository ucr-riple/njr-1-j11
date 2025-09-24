/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.instructions;

/**
 *
 * @author ROCK
 */
public class NOP implements InsInterface1
{

    /**
     *
     * @param ins
     */
    @Override
    public void execute(String ins)
      {
        backend.StepRun.callbbeq = false;
        //System.out.println("\n chill mar londe \n ");

      }
}
