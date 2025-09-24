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

import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.constants.OperandTypeMemoryEffectiveAddress;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IOperand;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandMemoryEffectiveAddress;
import java.util.Stack;


public class AMD64Environment {
    
    public AMD64Environment(){
        m_memory    = new Memory();
        m_regbank   = new RegisterBank();
    }
    
    public void setDebugging(boolean debug_value){
        m_regbank.setDebugging(debug_value);
        m_memory.setDebugging(debug_value);
    }
    //Get the contents of a register
    public long getValue(RegisterType register) {
        long ret;
        ret = m_regbank.getValue(register);    
        return ret;
    }
    
    //Calculates a memory address from OperandTypeMemoryEffectiveAddress
    public long getMemoryAddress(OperandTypeMemoryEffectiveAddress addr)
    {
        RegisterType baseReg  = addr.getBase();
        RegisterType indexReg = addr.getIndex();
        long scale = addr.getScale();
        long offset = addr.getOffset();
        long base = 0;
        long index = 0;
        if(baseReg != null) {
            base = getValue(baseReg);
        }
        if(indexReg != null) {
            index = getValue(indexReg);
        }      
        return base+index*scale+offset;
    }

    //get the value from a memory address operand
    public long getValue(OperandTypeMemoryEffectiveAddress op, int width) {
        return m_memory.getValue(getMemoryAddress(op),width);
    }
 

    //get the value from a memory address
    public long getValue(long addr, int num_bytes) {
        return m_memory.getValue(addr,  num_bytes);
    }
    
    //Get a value from an operand
    public long getValue(IOperand op, int width) {
        long ret = 0;
        switch(op.getOperandType())
        {
            case REGISTER:
                RegisterType register = (RegisterType)op.getOperandValue();
                ret = getValue(register);   
                break;
            
            case CONSTANT:
                ret = (Long)op.getOperandValue();
                break;
                
            case NUMBER: 
                ret = (Long)op.getOperandValue();
                break;
            
            case MEMORY_EFFECITVE_ADDRESS:
                ret = getValue((OperandTypeMemoryEffectiveAddress)op.getOperandValue(), width);
                break; 
                
            default: break;
        }

        return ret;
    }

    //set the value of an operand
    public void setValue(IOperand op, long value, int width) {
        switch(op.getOperandType())
        {
            case REGISTER:
                RegisterType register = (RegisterType)op.getOperandValue();
                setValue(register, value);
                break;
                
            case MEMORY_EFFECITVE_ADDRESS:
                OperandTypeMemoryEffectiveAddress memOp = (OperandTypeMemoryEffectiveAddress)op.getOperandValue();
                setValue(getMemoryAddress(memOp),value,width);
                break;
                
            
            default: break;
        }
    }
    
    public void setValue(RegisterType reg, long value)
    {
        m_regbank.setValue(reg, value);        
    }
    
    //set a memory location to operand contents
    public void setValue(long address, IOperand operand, int width)
    {
        long value = getValue(operand, width);
        m_memory.setValue(address, value, width);
    }   

    //set a memory location to long value
    public void setValue(long address, long value, int width)
    {
        m_memory.setValue(address, value, width);
    } 
    
    public int getOperandWidth(IOperand op){
        int width = 64;
        switch(op.getOperandType())
        {
            case REGISTER:
                RegisterType register = (RegisterType)op.getOperandValue();
                width = m_regbank.getWidth(register);
                break;
            
            case MEMORY_EFFECITVE_ADDRESS:
                OperandTypeMemoryEffectiveAddress op_conv = (OperandTypeMemoryEffectiveAddress)op.getOperandValue();
                width = m_regbank.getWidth(op_conv.getBase());
                break;
                
            case CONSTANT:
                width = 4;
                break;
                
            default: 
                width = 8;
                break;
        }
        
        return width;
    }
    
    public Memory        m_memory;
    public RegisterBank  m_regbank;
}