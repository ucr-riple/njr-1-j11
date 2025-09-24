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

package edu.syr.bytecast.interp.amd64;

import bytecast.interp.test.input.mockups.Test01InputMockup;
import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.instruction.IOperand;
import edu.syr.bytecast.amd64.api.output.IExecutableFile;
import edu.syr.bytecast.amd64.api.output.ISection;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.amd64.impl.instruction.AMD64Instruction;
import edu.syr.bytecast.interfaces.fsys.ExeObjSegment;
import edu.syr.bytecast.interfaces.interp.IBytecastInterp;
import edu.syr.bytecast.interp.amd64.instructions.*;

import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;


public class AMD64ExecutionEngine implements IBytecastInterp {

    AMD64Environment m_env;
    
    public static final Map<InstructionType, IISAInstruction> m_instructions = new TreeMap();
    static {
        m_instructions.put(InstructionType.ADD, new ISAInstructionADD());
        m_instructions.put(InstructionType.CALLQ, new ISAInstructionCALLQ()); 
        m_instructions.put(InstructionType.CMP, new ISAInstructionCMP()); 
        m_instructions.put(InstructionType.CMPL, new ISAInstructionCMPL()); 
        m_instructions.put(InstructionType.JGE, new ISAInstructionJGE()); 
        m_instructions.put(InstructionType.JMP, new ISAInstructionJMP()); 
        m_instructions.put(InstructionType.JNE, new ISAInstructionJNE()); 
        m_instructions.put(InstructionType.LEA, new ISAInstructionLEA()); 
        m_instructions.put(InstructionType.LEAVE, new ISAInstructionLEAVEQ()); 
        m_instructions.put(InstructionType.LEAVEQ, new ISAInstructionLEAVEQ()); 
        m_instructions.put(InstructionType.MOV, new ISAInstructionMOV()); 
        m_instructions.put(InstructionType.MOVSBL, new ISAInstructionMOVSBL()); 
        m_instructions.put(InstructionType.MOVZBL, new ISAInstructionMOVZBL()); 
        m_instructions.put(InstructionType.NOP, new ISAInstructionNOP()); 
        m_instructions.put(InstructionType.POP, new ISAInstructionPOP()); 
        m_instructions.put(InstructionType.PUSH, new ISAInstructionPUSH()); 
        m_instructions.put(InstructionType.RET, new ISAInstructionRETQ()); 
        m_instructions.put(InstructionType.RETQ, new ISAInstructionRETQ()); 
        m_instructions.put(InstructionType.SAR, new ISAInstructionSAR()); 
        m_instructions.put(InstructionType.SHR, new ISAInstructionSHR()); 
        m_instructions.put(InstructionType.SUB, new ISAInstructionSUB());         
    }
    
    @Override
    public long runProgram(IExecutableFile input, String[] args) {
        m_env = new AMD64Environment();

        
        //Load all loadable content provided by fsys to the AMD64Environment 
        //memory
        loadAllSegmentsToMemory(input.getSectionsWithRawData());
        
//        m_env.setDebugging(true);
          
        //allocate arguments in memory
        allocArgs(args);
        
        List<ISection> sections = input.getSectionsWithInstructions();
        int entry_point_index = getEntryPointIndex(sections);
        
        //Create the call stack.
        Stack<IndexPair> call_stack = new Stack();
        
        //Push the entry point onto the call stack
        call_stack.push(new IndexPair(entry_point_index, 0));
        
        //Start execution from the call stack
        executeCallStack(call_stack, sections);
        
        //Return value of EAX
        return m_env.getValue(RegisterType.EAX);
    }
   
    private void allocArgs(String[] args){
        //Store argc
        m_env.setValue(RegisterType.EDI, args.length);
        
        //Set start pointer to list of argument pointers
        long curr_arg_ptr_addr = 0x00000000ff000000l;
        m_env.setValue(RegisterType.RSI, curr_arg_ptr_addr);
        
        //Set where the actual strings start to be stored in memory
        long curr_arg_addr = 0x00000000fff00000l;
        for(int i = 0; i < args.length; i++){
            
            //Set this argument pointer to the start of the string
            m_env.setValue(curr_arg_ptr_addr, curr_arg_addr, 8);

            //Copy the string into memory
            for(int j = 0; j < args[i].length();j++){
                m_env.setValue(curr_arg_addr, (long)args[i].charAt(j), 1);
                curr_arg_addr++;
            }
            
            //End the string with a null terminator
            m_env.setValue(curr_arg_addr, (long)'\0', 1);
            curr_arg_addr++;
            
            //Move to next argument pointer
            curr_arg_ptr_addr+=8;
        }
    }
    private void executeCallStack(Stack<IndexPair> call_stack, List<ISection> sections) {


        //loop until all instructions have ben executed
        while(!call_stack.empty()){
            IndexPair curr_pair = call_stack.pop();
            
            int curr_section_idx = curr_pair.getIndex1();
            int curr_instr_idx = curr_pair.getIndex2();
            
            //Get all instructions in this section
            List<MemoryInstructionPair> instructions = sections.get(curr_section_idx).getAllInstructionObjects();
  
            //A jump address of zero means no jump
            long jump_addr=0;
            
            //Loop until a branch or the end of the section is reached.
            while(jump_addr == 0 && jump_addr != -1 && curr_instr_idx < instructions.size())
            {
                long mem_loc = instructions.get(curr_instr_idx).getmInstructionAddress();
                //Fetch the current instruction and instruction type
                IInstruction curr_inst = instructions.get(curr_instr_idx).getInstruction();
                curr_inst = reverseOpcodes(curr_inst);
                
                InstructionType curr_inst_type = curr_inst.getInstructiontype();
                
                //Execute the instruction
  //              System.out.println("----------------------------------");
 //               System.out.println("Running instruction " + curr_inst_type.name() + " at " + Long.toHexString(mem_loc));
                jump_addr = m_instructions.get(curr_inst_type).execute(m_env, curr_inst);
                
                //If the instruction caused a jump, then push where to return
                //after the jump has completed and then push the instruction
                //being jumped to.
                if(jump_addr > 0) {
  //                  System.err.println("Jumping to " + Long.toHexString(jump_addr));
                    if(curr_inst_type == InstructionType.CALL || curr_inst_type == InstructionType.CALLQ){
                        call_stack.push(new IndexPair(curr_section_idx, curr_instr_idx+1));
                    }
                    
                    call_stack.push(findInstruction(sections, jump_addr));
                   
                }
                else{
                    curr_instr_idx++;
                }
            }          
        }       
        
    }
    
    private IndexPair findInstruction(List<ISection> input, long addr){
        //Right now everything is in the first section...
        int instructionIndex = input.get(0).indexOfInstructionAt(addr);
        return new IndexPair(0,instructionIndex);
    }
    private void loadAllSegmentsToMemory(List <ExeObjSegment> segments){
        for(ExeObjSegment segment : segments) {
            long current_address = segment.getStartAddress();
            List<Byte> raw_data = segment.getBytes();
            for(int i = 0; i < segment.getSize(); i++)
            {
                m_env.setValue(current_address+i,(long)raw_data.get(i),1);
            }
        }
    }
    
    private int getEntryPointIndex(List<ISection> sections){
        for(int i = 0; i < sections.size(); i++) {
            if(sections.get(i).isEntryPoint()) {
                return i;
            }
        }
        return -1;
    }
    
    private IInstruction reverseOpcodes(IInstruction instr){
        List<IOperand> ops = instr.getOperands();
        if (ops.size() > 1) {
            IOperand op1 = ops.get(0);
            IOperand op2 = ops.get(1);

            if (op2 != null && op2.getOperandType() != OperandType.SECTION_NAME) {
                ops.set(0, op2);
                ops.set(1, op1);
            }
        }
        return new AMD64Instruction(instr.getInstructiontype(),ops);        
    }
    public static void main(String args[])
    {
        Test01InputMockup mock = new Test01InputMockup();
        AMD64ExecutionEngine eng = new AMD64ExecutionEngine();
        String[] arguments = {"test1", "test2", "test3"};
        System.out.println(eng.runProgram(mock.buildInstructionObjects() , arguments));
        
    }

}
