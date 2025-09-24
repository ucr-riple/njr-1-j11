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

public class CTestCompiler {
  
  public String compile(String filename, String aout_fname) {
    try {
      File temp_folder = new File("temp");
      if(temp_folder.exists() == false){
        temp_folder.mkdirs();
      }
      
      String aout_filename = "temp" + File.separator + aout_fname;
      File aout_file = new File(aout_filename);
      if(aout_file.exists()){
        aout_file.delete();
      }
      
      //compile .s file
      RunProcess runner1 = new RunProcess();
      runner1.exec("gcc -static " + filename, new File("temp"));
      
      return new File("temp").getAbsolutePath();
      
    } catch(Exception ex){
      ex.printStackTrace(System.out);
      return "";
    }
  }
  
}
