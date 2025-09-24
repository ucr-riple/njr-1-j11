/*
 * This file is part of Bytecast.
 *
 * Bytecast is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Bytecast is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Bytecast.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package bytecast.interp.test;

import edu.syr.bytecast.util.ReadFileAsString;
import edu.syr.bytecast.util.RunProcess;
import edu.syr.bytecast.util.WriteStringAsFile;
import java.io.File;
import java.util.List;

public class TestCPU {

  private ReadFileAsString m_fileReader;
  private WriteStringAsFile m_fileWriter;
  
  public TestCPU(){
    m_fileReader = new ReadFileAsString();
    m_fileWriter = new WriteStringAsFile();
  }
  
  public int test(String run_folder,String[] args) {
    try {
      //run test case
      RunProcess runner2 = new RunProcess();
      String exec_string = run_folder + File.separator + args[0];
      for(int i = 1; i < args.length; i++){
          exec_string += " " + args[i];
      }
      int ret = runner2.exec(exec_string, new File(run_folder));
      
      return ret;
    } catch(Exception ex){
      ex.printStackTrace(System.out);
      return -100;
    }
  }
  
}
