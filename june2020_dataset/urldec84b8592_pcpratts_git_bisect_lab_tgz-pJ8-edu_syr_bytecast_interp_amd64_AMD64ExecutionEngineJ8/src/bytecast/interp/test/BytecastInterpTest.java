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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BytecastInterpTest {
  
  private List<TestCase> m_testCases;
  private String m_template;
  
  private void test() {
    loadTestCases();
    int count = 0;
    List<String> failing = new ArrayList<String>();
    for(TestCase test_case : m_testCases){
      String filename = test_case.getFilename();
      String[] args = test_case.getArgs();
      
      int index = count + 1;
      File file = new File(filename);
      System.out.println("[Test "+index+"/"+m_testCases.size()+"]: "+file.getName());
      String compiled_path;
      if(filename.endsWith(".c")){
          compiled_path = new CTestCompiler().compile(filename, args[0]);
      } else {
          compiled_path = new ASMTestCompiler().compile(filename, m_template,args[0]);
      }
      int cpu_ret = runCpu(compiled_path,  args);
      int interp_ret;
      try {
        interp_ret = runInterp(compiled_path, args);
      } catch(Exception ex){
        failing.add(file.getName());
        System.out.println("  FAILED");
        System.out.println("    cpu_ret:    "+cpu_ret);
        System.out.println("    interp_ret: CRASHED.");
        ++count;
        continue;
      }
      
      if(cpu_ret == interp_ret){
        System.out.println("  PASSED");
      } else {
        failing.add(file.getName());
        System.out.println("  FAILED");
        System.out.println("    cpu_ret:    "+cpu_ret);
        System.out.println("    interp_ret: "+interp_ret);
      }
      ++count;
    }
    int passing = m_testCases.size() - failing.size(); 
    System.out.println();
    System.out.println("Results: ");
    System.out.println("  ["+passing+"/"+m_testCases.size()+"] passing");
    for(String failed : failing){
      System.out.println("  failed: "+failed);
    }
  }
  
  private int runCpu(String filename, String[] args){
    TestCPU tester = new TestCPU();
    return tester.test(filename, args);
  }

  private int runInterp(String filename, String[] args) {
    TestInterp tester = new TestInterp();
    return tester.test(filename, args );
  }
  
  private void loadTestCases() {
    m_testCases = new ArrayList<TestCase>();
    File folder = new File("test_cases");
    if(folder.exists() == false){
      return;
    }
    File[] children = folder.listFiles();
    for(File child : children){
      if(child.isFile()){
        String name = child.getName();
        if(name.startsWith(".")){
          continue;
        }
        if(!(name.endsWith("c"))){
          continue;
        }
        if(name.startsWith("test04")){
          m_testCases.add(new TestCase(child.getAbsolutePath(), new String[]{"a.out", "a","b"}));
        }
        else if(name.startsWith("test05")){
          m_testCases.add(new TestCase(child.getAbsolutePath(), new String[]{"a.out", "b","a"}));
        }
        else if(name.startsWith("test06")){
          m_testCases.add(new TestCase(child.getAbsolutePath(), new String[]{"a.out", "a","b"}));
        }
        else if(name.startsWith("test07")){
          m_testCases.add(new TestCase(child.getAbsolutePath(), new String[]{"a.out", "b","a"}));
        }
        else if(name.startsWith("test")){
          m_testCases.add(new TestCase(child.getAbsolutePath(), new String[]{"a.out"}));
        }
        if(name.startsWith("template")){
          m_template = child.getAbsolutePath();
        }
      }
    }
    for(int i = 0; i < m_testCases.size(); ++i){
      for(int j = i + 1; j < m_testCases.size(); ++j){
        TestCase lhs = m_testCases.get(i);
        TestCase rhs = m_testCases.get(j);
        if(rhs.getFilename().compareTo(lhs.getFilename()) < 0){
          m_testCases.set(i, rhs);
          m_testCases.set(j, lhs);
        }
      }
    }
  }
  
  public static void main(String[] args){
    BytecastInterpTest tester = new BytecastInterpTest();
    tester.test();
  }
}
