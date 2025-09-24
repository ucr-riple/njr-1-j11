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

import java.util.Map;
import java.util.TreeMap;
import edu.syr.bytecast.amd64.api.constants.RegisterType;

public class RegisterBank {
    
    private Map<RegisterType, Register> m_naturalRegs;
    private Map<RegisterType, RegisterOverlay> m_regOverlays;
    private boolean m_debug_enabled;
    
    public RegisterBank()
    {    
        m_debug_enabled = false;
        
        m_regOverlays = new TreeMap<RegisterType, RegisterOverlay>();
        m_regOverlays.put(RegisterType.RAX,    new RegisterOverlay(RegisterType.RAX,     63, 0));
        m_regOverlays.put(RegisterType.EAX,    new RegisterOverlay(RegisterType.RAX,     31, 0));
        m_regOverlays.put(RegisterType.AX,     new RegisterOverlay(RegisterType.RAX,     15, 0));
        m_regOverlays.put(RegisterType.AH,     new RegisterOverlay(RegisterType.RAX,     15, 8));
        m_regOverlays.put(RegisterType.AL,     new RegisterOverlay(RegisterType.RAX,      7, 0));
        m_regOverlays.put(RegisterType.RBX,    new RegisterOverlay(RegisterType.RBX,     63, 0));
        m_regOverlays.put(RegisterType.EBX,    new RegisterOverlay(RegisterType.RBX,     31, 0));
        m_regOverlays.put(RegisterType.BX,     new RegisterOverlay(RegisterType.RBX,     15, 0));
        m_regOverlays.put(RegisterType.BH,     new RegisterOverlay(RegisterType.RBX,     15, 8));
        m_regOverlays.put(RegisterType.BL,     new RegisterOverlay(RegisterType.RBX,      7, 0));
        m_regOverlays.put(RegisterType.RCX,    new RegisterOverlay(RegisterType.RCX,     63, 0));
        m_regOverlays.put(RegisterType.ECX,    new RegisterOverlay(RegisterType.RCX,     31, 0));
        m_regOverlays.put(RegisterType.CX,     new RegisterOverlay(RegisterType.RCX,     15, 0));
        m_regOverlays.put(RegisterType.CH,     new RegisterOverlay(RegisterType.RCX,     15, 8));
        m_regOverlays.put(RegisterType.CL,     new RegisterOverlay(RegisterType.RCX,      7, 0));
        m_regOverlays.put(RegisterType.RDX,    new RegisterOverlay(RegisterType.RDX,     63, 0));
        m_regOverlays.put(RegisterType.EDX,    new RegisterOverlay(RegisterType.RDX,     31, 0));
        m_regOverlays.put(RegisterType.DX,     new RegisterOverlay(RegisterType.RDX,     15, 0));
        m_regOverlays.put(RegisterType.DH,     new RegisterOverlay(RegisterType.RDX,     15, 8));
        m_regOverlays.put(RegisterType.DL,     new RegisterOverlay(RegisterType.RDX,      7, 0));
        m_regOverlays.put(RegisterType.RSP,    new RegisterOverlay(RegisterType.RSP,     63, 0));
        m_regOverlays.put(RegisterType.ESP,    new RegisterOverlay(RegisterType.RSP,     31, 0));
        m_regOverlays.put(RegisterType.SP,     new RegisterOverlay(RegisterType.RSP,     15, 0));
        m_regOverlays.put(RegisterType.SPL,    new RegisterOverlay(RegisterType.RSP,      7, 0));
        m_regOverlays.put(RegisterType.RBP,    new RegisterOverlay(RegisterType.RBP,     63, 0));
        m_regOverlays.put(RegisterType.EBP,    new RegisterOverlay(RegisterType.RBP,     31, 0));
        m_regOverlays.put(RegisterType.BP,     new RegisterOverlay(RegisterType.RBP,     15, 0));
        m_regOverlays.put(RegisterType.BPL,    new RegisterOverlay(RegisterType.RBP,      7, 0));
        m_regOverlays.put(RegisterType.RSI,    new RegisterOverlay(RegisterType.RSI,     63, 0));
        m_regOverlays.put(RegisterType.ESI,    new RegisterOverlay(RegisterType.RSI,     31, 0));
        m_regOverlays.put(RegisterType.SI ,    new RegisterOverlay(RegisterType.RSI,     15, 0));
        m_regOverlays.put(RegisterType.SIL,    new RegisterOverlay(RegisterType.RSI,      7, 0));
        m_regOverlays.put(RegisterType.RDI,    new RegisterOverlay(RegisterType.RDI,     63, 0));
        m_regOverlays.put(RegisterType.EDI,    new RegisterOverlay(RegisterType.RDI,     31, 0));
        m_regOverlays.put(RegisterType.DI,     new RegisterOverlay(RegisterType.RDI,     15, 0));
        m_regOverlays.put(RegisterType.DIL,    new RegisterOverlay(RegisterType.RDI,      7, 0));
        m_regOverlays.put(RegisterType.R8,     new RegisterOverlay(RegisterType.R8,      63, 0));
        m_regOverlays.put(RegisterType.R8D,    new RegisterOverlay(RegisterType.R8,      31, 0));
        m_regOverlays.put(RegisterType.R8W,    new RegisterOverlay(RegisterType.R8,      15, 0));
        m_regOverlays.put(RegisterType.R8B,    new RegisterOverlay(RegisterType.R8,       7, 0));
        m_regOverlays.put(RegisterType.R9,     new RegisterOverlay(RegisterType.R9,      63, 0));
        m_regOverlays.put(RegisterType.R9D,    new RegisterOverlay(RegisterType.R9,      31, 0));
        m_regOverlays.put(RegisterType.R9W,    new RegisterOverlay(RegisterType.R9,      15, 0));
        m_regOverlays.put(RegisterType.R9B,    new RegisterOverlay(RegisterType.R9,       7, 0));
        m_regOverlays.put(RegisterType.R10,    new RegisterOverlay(RegisterType.R10,     63, 0));
        m_regOverlays.put(RegisterType.R10D,   new RegisterOverlay(RegisterType.R10,     31, 0));
        m_regOverlays.put(RegisterType.R10W,   new RegisterOverlay(RegisterType.R10,     15, 0));
        m_regOverlays.put(RegisterType.R10B,   new RegisterOverlay(RegisterType.R10,      7, 0));
        m_regOverlays.put(RegisterType.R11,    new RegisterOverlay(RegisterType.R11,     63, 0));
        m_regOverlays.put(RegisterType.R11D,   new RegisterOverlay(RegisterType.R11,     31, 0));
        m_regOverlays.put(RegisterType.R11W,   new RegisterOverlay(RegisterType.R11,     15, 0));
        m_regOverlays.put(RegisterType.R11B,   new RegisterOverlay(RegisterType.R11,      7, 0));
        m_regOverlays.put(RegisterType.R12,    new RegisterOverlay(RegisterType.R12,     63, 0));
        m_regOverlays.put(RegisterType.R12D,   new RegisterOverlay(RegisterType.R12,     31, 0));
        m_regOverlays.put(RegisterType.R12W,   new RegisterOverlay(RegisterType.R12,     15, 0));
        m_regOverlays.put(RegisterType.R12B,   new RegisterOverlay(RegisterType.R12,      7, 0));
        m_regOverlays.put(RegisterType.R13,    new RegisterOverlay(RegisterType.R13,     63, 0));
        m_regOverlays.put(RegisterType.R13D,   new RegisterOverlay(RegisterType.R13,     31, 0));
        m_regOverlays.put(RegisterType.R13W,   new RegisterOverlay(RegisterType.R13,     15, 0));
        m_regOverlays.put(RegisterType.R13B,   new RegisterOverlay(RegisterType.R13,      7, 0));
        m_regOverlays.put(RegisterType.R14,    new RegisterOverlay(RegisterType.R14,     63, 0));
        m_regOverlays.put(RegisterType.R14D,   new RegisterOverlay(RegisterType.R14,     31, 0));
        m_regOverlays.put(RegisterType.R14W,   new RegisterOverlay(RegisterType.R14,     15, 0));
        m_regOverlays.put(RegisterType.R14B,   new RegisterOverlay(RegisterType.R14,      7, 0));
        m_regOverlays.put(RegisterType.R15,    new RegisterOverlay(RegisterType.R15,     63, 0));
        m_regOverlays.put(RegisterType.R15D,   new RegisterOverlay(RegisterType.R15,     31, 0));
        m_regOverlays.put(RegisterType.R15W,   new RegisterOverlay(RegisterType.R15,     15, 0));
        m_regOverlays.put(RegisterType.R15B,   new RegisterOverlay(RegisterType.R15,      7, 0));
        m_regOverlays.put(RegisterType.SS,     new RegisterOverlay(RegisterType.SS,      15, 0));
        m_regOverlays.put(RegisterType.CS,     new RegisterOverlay(RegisterType.CS,      15, 0));
        m_regOverlays.put(RegisterType.DS,     new RegisterOverlay(RegisterType.DS,      15, 0));
        m_regOverlays.put(RegisterType.ES,     new RegisterOverlay(RegisterType.ES,      15, 0));
        m_regOverlays.put(RegisterType.ES,     new RegisterOverlay(RegisterType.ES,      15, 0));
        m_regOverlays.put(RegisterType.GS,     new RegisterOverlay(RegisterType.GS,      15, 0));
        m_regOverlays.put(RegisterType.EFLAGS, new RegisterOverlay(RegisterType.EFLAGS,  31, 0));
        m_regOverlays.put(RegisterType.CF,     new RegisterOverlay(RegisterType.EFLAGS,   0, 0));
        m_regOverlays.put(RegisterType.PF,     new RegisterOverlay(RegisterType.EFLAGS,   2, 2));
        m_regOverlays.put(RegisterType.AF,     new RegisterOverlay(RegisterType.EFLAGS,   4, 4));
        m_regOverlays.put(RegisterType.ZF,     new RegisterOverlay(RegisterType.EFLAGS,   6, 6));
        m_regOverlays.put(RegisterType.SF,     new RegisterOverlay(RegisterType.EFLAGS,   7, 7));
        m_regOverlays.put(RegisterType.TF,     new RegisterOverlay(RegisterType.EFLAGS,   8, 8));
        m_regOverlays.put(RegisterType.IF,     new RegisterOverlay(RegisterType.EFLAGS,   9, 9));
        m_regOverlays.put(RegisterType.DF,     new RegisterOverlay(RegisterType.EFLAGS,  10,10));
        m_regOverlays.put(RegisterType.OF,     new RegisterOverlay(RegisterType.EFLAGS,  11,11));
        m_regOverlays.put(RegisterType.IOPL,   new RegisterOverlay(RegisterType.EFLAGS,  13,12));
        m_regOverlays.put(RegisterType.NT,     new RegisterOverlay(RegisterType.EFLAGS,  14,14));
        m_regOverlays.put(RegisterType.RF,     new RegisterOverlay(RegisterType.EFLAGS,  16,16));
        m_regOverlays.put(RegisterType.VM,     new RegisterOverlay(RegisterType.EFLAGS,  17,17));
        m_regOverlays.put(RegisterType.AC,     new RegisterOverlay(RegisterType.EFLAGS,  18,18));
        m_regOverlays.put(RegisterType.VIF,    new RegisterOverlay(RegisterType.EFLAGS,  19,19));
        m_regOverlays.put(RegisterType.VIP,    new RegisterOverlay(RegisterType.EFLAGS,  20,20));
        m_regOverlays.put(RegisterType.ID,     new RegisterOverlay(RegisterType.EFLAGS,  21,21));
        
        m_naturalRegs = new TreeMap<RegisterType, Register>();
        m_naturalRegs.put(RegisterType.RAX,     new Register());
        m_naturalRegs.put(RegisterType.RBX,     new Register());
        m_naturalRegs.put(RegisterType.RCX,     new Register());
        m_naturalRegs.put(RegisterType.RDX,     new Register());
        m_naturalRegs.put(RegisterType.RSP,     new Register());
        m_naturalRegs.put(RegisterType.RBP,     new Register());
        m_naturalRegs.put(RegisterType.RSI,     new Register());
        m_naturalRegs.put(RegisterType.RDI,     new Register());
        m_naturalRegs.put(RegisterType.RIP,     new Register());
        m_naturalRegs.put(RegisterType.R8,      new Register());
        m_naturalRegs.put(RegisterType.R9,      new Register());
        m_naturalRegs.put(RegisterType.R10,     new Register());
        m_naturalRegs.put(RegisterType.R11,     new Register());
        m_naturalRegs.put(RegisterType.R12,     new Register());
        m_naturalRegs.put(RegisterType.R13,     new Register());
        m_naturalRegs.put(RegisterType.R14,     new Register());
        m_naturalRegs.put(RegisterType.R15,     new Register());
        m_naturalRegs.put(RegisterType.SS,      new Register());
        m_naturalRegs.put(RegisterType.CS,      new Register());
        m_naturalRegs.put(RegisterType.DS,      new Register());
        m_naturalRegs.put(RegisterType.ES,      new Register());
        m_naturalRegs.put(RegisterType.ES,      new Register());
        m_naturalRegs.put(RegisterType.GS,      new Register());
        m_naturalRegs.put(RegisterType.EFLAGS,  new Register());
    }

    public void setDebugging(boolean debug_value){
        m_debug_enabled = debug_value;
    }
    //Sets a register in the register bank
    public void setValue(RegisterType name, long value){
        if(m_debug_enabled){
            System.out.println("Setting register " + name.name() + " to value " + value);
        }
        if(m_regOverlays.containsKey(name)){
            RegisterOverlay overlay = m_regOverlays.get(name); 
            m_naturalRegs.get(overlay.ParentRegisterName).setValue(value, overlay.MSB, overlay.LSB);
        } else {
            System.out.println("WARNING: Register " + name.name() + " not found");
        }
    }
    
    public long getValue(RegisterType name){
        RegisterOverlay overlay = m_regOverlays.get(name);
        
        if(!m_regOverlays.containsKey(name)){
            System.out.println("WARNING: Register " + name.name() + " not found");    
        }
    
        long value = m_naturalRegs.get(overlay.ParentRegisterName).getValue(overlay.MSB, overlay.LSB); 
        if(m_debug_enabled){
            System.out.println("Getting register " + name.name() + " with value " + value);  
        }
        
        
        return value;
    }
    
    //Get the number of bytes in a register
    public int getWidth(RegisterType name){
        if(name == null){
          return 4;
        }
        RegisterOverlay overlay = m_regOverlays.get(name);
        return (overlay.MSB - overlay.LSB + 1)/8;
    }
    
}
