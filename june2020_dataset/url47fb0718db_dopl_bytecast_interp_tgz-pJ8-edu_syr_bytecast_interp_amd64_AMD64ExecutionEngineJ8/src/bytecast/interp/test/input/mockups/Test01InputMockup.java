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

package bytecast.interp.test.input.mockups;

import edu.syr.bytecast.amd64.api.constants.IBytecastAMD64;
import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.instruction.IOperand;
import edu.syr.bytecast.amd64.api.output.IExecutableFile;
import edu.syr.bytecast.amd64.api.output.ISection;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.amd64.impl.instruction.AMD64Instruction;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandConstant;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandMemoryEffectiveAddress;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandRegister;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandSectionName;
import edu.syr.bytecast.amd64.impl.output.AMD64ExecutableFile;
import edu.syr.bytecast.amd64.impl.output.AMD64Section;
import edu.syr.bytecast.interfaces.fsys.ExeObj;
import edu.syr.bytecast.interfaces.fsys.ExeObjSegment;
import edu.syr.bytecast.interfaces.fsys.IBytecastFsys;
import edu.syr.bytecast.test.mockups.MockBytecastFsys;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;




/**
 *
 * @author bytecast
 */
public class Test01InputMockup implements IBytecastAMD64{

    @Override
    public IExecutableFile buildInstructionObjects() {
 
        List<ISection> sections = new ArrayList<ISection>();
        List<MemoryInstructionPair> memtoins = new ArrayList<MemoryInstructionPair>();
        /*
         * ###################### IMPORTANT #################################################################################
         * The order of operands you see in the code below seems - OP1,OP2. This is coded as seen in the objectdump output.
         * BUT, actually the operands are added right to left in the operands list. This is done here in the method
         * instruction (...)
         * #################################################################################################################
         */
        //<main> STARTS
        memtoins.add(new MemoryInstructionPair((long) 0x400584, instruction("55",   InstructionType.PUSH,   new OperandRegister(RegisterType.RBP)                               , null)));
        memtoins.add(new MemoryInstructionPair((long) 0x400585, instruction("89",   InstructionType.MOV,    new OperandRegister(RegisterType.RSP)                               , new OperandRegister(RegisterType.RBP))));
        memtoins.add(new MemoryInstructionPair((long) 0x400588, instruction("89",   InstructionType.MOV,    new OperandRegister(RegisterType.EDI)                               , new OperandMemoryEffectiveAddress(RegisterType.RBP, null, 1, -0x4))));
        memtoins.add(new MemoryInstructionPair((long) 0x40058b, instruction("89",   InstructionType.MOV,    new OperandRegister(RegisterType.RSI)                               , new OperandMemoryEffectiveAddress(RegisterType.RBP, null, 1, -0x10))));
        memtoins.add(new MemoryInstructionPair((long) 0x40058f, instruction("75",   InstructionType.MOV,    new OperandMemoryEffectiveAddress(RegisterType.RBP, null, 1, -0x4)  , new OperandRegister(RegisterType.EAX))));
        memtoins.add(new MemoryInstructionPair((long) 0x400592, instruction("83",   InstructionType.ADD,    new OperandConstant((long) 0x01)                                    , new OperandRegister(RegisterType.EAX))));
        memtoins.add(new MemoryInstructionPair((long) 0x400595, instruction("C9",   InstructionType.LEAVEQ, null                                                                , null)));
        memtoins.add(new MemoryInstructionPair((long) 0x400596, instruction("c3",   InstructionType.RETQ,   null                                                                , null)));
        memtoins.add(new MemoryInstructionPair((long) 0x400597, instruction("",     InstructionType.NOP,    null                                                                , null)));
        memtoins.add(new MemoryInstructionPair((long) 0x400598, instruction("",     InstructionType.NOP,    null                                                                , null)));
        memtoins.add(new MemoryInstructionPair((long) 0x400599, instruction("",     InstructionType.NOP,    null                                                                , null)));
        memtoins.add(new MemoryInstructionPair((long) 0x40059a, instruction("",     InstructionType.NOP,    null                                                                , null)));
        memtoins.add(new MemoryInstructionPair((long) 0x40059b, instruction("",     InstructionType.NOP,    null                                                                , null)));
        memtoins.add(new MemoryInstructionPair((long) 0x40059c, instruction("",     InstructionType.NOP,    null                                                                , null)));
        memtoins.add(new MemoryInstructionPair((long) 0x40059d, instruction("",     InstructionType.NOP,    null                                                                , null)));
        memtoins.add(new MemoryInstructionPair((long) 0x40059e, instruction("",     InstructionType.NOP,    null                                                                , null)));
        memtoins.add(new MemoryInstructionPair((long) 0x40059f, instruction("",     InstructionType.NOP,    null                                                                , null)));


        ISection section = new AMD64Section(memtoins, (long) 0x400584, true);
        sections.add(section);
        ExeObj fsysObj = new ExeObj();
        fsysObj.setSegments(new ArrayList<ExeObjSegment>());
        IExecutableFile exeFile = new AMD64ExecutableFile(fsysObj.getSegments(), sections, "TEST_EXE_FILE", "ELF", null);
        return exeFile;
    }
    
    private AMD64Instruction instruction(String opcode,InstructionType ins, IOperand op1, IOperand op2){
        List<IOperand> operands = new ArrayList<IOperand>();
        
        if(op1!=null) {
            operands.add(op1);
        }
        
        if(op2!=null) {
            operands.add(op2);
        }
        AMD64Instruction amD64Instruction = new AMD64Instruction(ins, operands);
        amD64Instruction.setOpCode(opcode);
        return amD64Instruction;

    }
    
}



