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


public class Memory {
    Map<Long, Byte> m_memory;
    boolean m_debug_enabled;
    
    Memory(){
        m_memory = new TreeMap<Long,Byte>();
        m_debug_enabled = false;
    }
    
    public void setDebugging(boolean debug_value){
        m_debug_enabled = debug_value;
    }
    //Stores a value of num_bytes in a little endian format
    public void setValue(long address, long value, long num_bytes){
        if(m_debug_enabled){
            System.out.println("Setting address " + address + " to value " + value + " with length " + num_bytes);
        }
        long mask = 0x00000000000000ff;
        for(int i = 0; i < num_bytes; i++)
        {
            byte byte_val = (byte)((value & (mask << 8*i)) >> 8*i); 
            m_memory.put(address+i, byte_val);
        }
    }
    
    //Retrieves a value from memory
    public long getValue(long address, long num_bytes){
        long ret=0;
        long mask = 0x00000000000000ffL;
        if(m_debug_enabled){
            System.out.print("Getting address " + address);
        }
        for(int i = 0; i < num_bytes; i++){
            Byte byte_val = m_memory.get(address+i);
            long shifted_val = (mask & byte_val) << (i*8);
            ret |= shifted_val;
        }        
        if(m_debug_enabled){
            System.out.println(" with value " + ret + " of length " + num_bytes);
        }
        
        return ret;
    }        
    
    public static void main(String args[]){
        Memory test = new Memory();
        test.setValue(11, 0x123456789ABCL, 6);
        System.out.println(Long.toHexString(test.getValue(11, 6)));
        test.setValue(32, 0xFDCBA9876543210L, 8);
        System.out.println(Long.toHexString(test.getValue(32, 8)));
    }
}
