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

import bytecast.interp.test.input.mockups.Test01InputMockup;
import edu.syr.bytecast.amd64.BytecastAmd64;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.output.IExecutableFile;
import edu.syr.bytecast.amd64.util.StringToIInstruction;
import edu.syr.bytecast.fsys.elf.ElfExeObjParser;
import edu.syr.bytecast.interfaces.fsys.ExeObj;
import edu.syr.bytecast.interfaces.fsys.ExeObjIOUtils;
import edu.syr.bytecast.interp.amd64.AMD64ExecutionEngine;
import java.io.File;
import java.io.FileNotFoundException;

public class TestInterp {

  public int test(String folder, String args[]) {
        ElfExeObjParser elf_parser = new ElfExeObjParser(true);
        
        String exe_file = folder + File.separator + args[0];
        BytecastAmd64 amd64 = new BytecastAmd64(elf_parser,exe_file);
        IExecutableFile amd64_output = amd64.buildInstructionObjects();
        AMD64ExecutionEngine eng = new AMD64ExecutionEngine();
        return (int)eng.runProgram(amd64_output, args);
  }
  
}
