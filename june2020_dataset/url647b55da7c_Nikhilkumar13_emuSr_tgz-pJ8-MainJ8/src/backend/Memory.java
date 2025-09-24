/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import frontend.FrontEnd;
import java.awt.Color;
import java.util.AbstractCollection;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Nikhil
 */
public class Memory
{

    public static HashMap memory;
    public static Object[][] disp_mem;
    public static Integer n;

    public Memory()
      {
        memory = null;
        memory = new HashMap();
        n = new Integer(0);

      }

    public static int get(int address)
      {
        try
        {// Integer data=new Integer(0);
            Integer data = (Integer) memory.get(address);
            return data;

        } catch (Exception e)
        {
            FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + ScanFile.current_line + "): Blank Memory Accesed Zero Is Being Retuned\n",Color.RED);
//            frontend.FrontEnd.statuswindow.append("ERROR in line " + ScanFile.current_line + "Blank Memory Accesed zero is being retuned\n");
            //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
            frontend.FrontEnd.exceptionraised++;
            return 0;
        }
      }

    public static int getsize()
      {
        return memory.size();
      }

    public static void put(int b, int address)
      {
        memory.put(address, b);
        n = memory.size();

      }

    public static Object[][] display(int ch)
      {
        // int ch=0;

//        n=memory.size();
        if (n > 0)
        {
            disp_mem = new Object[n][2];

            Set keySet = memory.keySet();
            Object[] keys = keySet.toArray();
            AbstractCollection values = (AbstractCollection) memory.values();
            Object[] val = values.toArray();
            if (ch == 0)
            {
                for (int i = 0; i < n && i < 512; i++)
                {
                    disp_mem[i][0] = keys[i];
                    disp_mem[i][1] = val[i];
                }

            } else if (ch == 1)
            {
                for (int i = 0; i < n && i < 512; i++)
                {
                    disp_mem[i][0] = keys[i];
                    disp_mem[i][1] = Integer.toBinaryString((Integer) val[i]);
                }
            } else if (ch == 2)
            {
                for (int i = 0; i < n && i < 512; i++)
                {
                    disp_mem[i][0] = keys[i];
                    disp_mem[i][1] = Integer.toHexString((Integer) val[i]);
                }
            } else if (ch == 3)
            {
                for (int i = 0; i < n && i < 512; i++)
                {
                    disp_mem[i][0] = keys[i];
                    disp_mem[i][1] = Integer.toOctalString((Integer) val[i]);
                }
            }

        }
        return disp_mem;
      }
}
