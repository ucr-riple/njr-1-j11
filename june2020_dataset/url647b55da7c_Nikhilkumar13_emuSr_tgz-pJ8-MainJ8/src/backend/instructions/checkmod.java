
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.instructions;

/**
 *
 * @author NIKHIL
 */
public class checkmod
{

    public static int checkmoduh(String s)
      {
        if (s.substring(s.length() - 1, s.length()).equals("u"))
        {
            return 1;

        } else if (s.substring(s.length() - 1, s.length()).equals("h"))
        {
            return 2;

        } else
        {
            return 3;
        }
      }

    public static int modify(String s, String n)
      {
          
        if (s.substring(s.length() - 1).equals("U"))
        {   
            return calcop.calcvalue(n)&0xffff;

        } else if (s.substring(s.length() - 1).equals("H"))
        {
           
            return calcop.calcvalue(n)<<16;

        } else
        {
            return calcop.calcvalue(n);
        }

      }
}
