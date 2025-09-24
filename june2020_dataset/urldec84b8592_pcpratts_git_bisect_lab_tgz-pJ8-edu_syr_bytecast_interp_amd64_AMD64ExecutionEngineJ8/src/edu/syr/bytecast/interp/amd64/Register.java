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

public class Register {

    long m_value;

    //Returns a signed value of the binary contents between
    //the msb and lsb (single bits are left unsigned.) 
    public long getValue(int msb, int lsb) {
        
        // the upper mask is used to zero out all bits
        // above the msb while retaining the value of
        // all bits at positions less than or equal to the msb
        long upper_mask;
        if (msb != 63) {
            upper_mask = (1L << (msb + 1)) - 1;
        } else {
            upper_mask = -1;
        }
        
        // apply the upper mask and shift by the lsb
        long ret = (m_value & upper_mask) >> lsb;

        //find the value of the sign bit if the requested width is not
        //64 bits or 1 bit.
        if ((msb - lsb) != 63 && (msb-lsb) != 0) {

            long sign_bit = ret >> (msb - lsb);
            
            //if the sign bit is one, subtract 2^(num_bits);
            if (sign_bit == 1) {
                int num_bits = msb - lsb + 1;
                ret -= (1L << num_bits);
            }
        }

        //edge case for single bit and 63rd bit
        if(msb == lsb && msb == 63)
        {
            ret *=-1;
        }
        return ret;
    }

    //Returns the full 64-bit value
    public long getValue() {
        return m_value;
    }

    //sets a bit range to a specific value
    public void setValue(long value, int msb, int lsb) {
        
        int num_bits = msb-lsb+1;

        long erase_mask;
        if(num_bits < 63){
            erase_mask= ~((1L << num_bits)-1);
        } else {
            erase_mask = 0;
        }
 
        //erase the sign extension from the value (the sign extension
        //will cause issues
        long trimmed_value = value & ~erase_mask;
        
        //shift the erase mask to the lsft by the MSB and fill bits 
        //less than LSB with 1's
        if(lsb > 0) {
            erase_mask = (erase_mask << lsb) | ((1L << lsb)-1);
        }
        
        //erase the bit range to be written and then overwrite it with 
        //the new value.
        m_value = (m_value & erase_mask) | (trimmed_value << lsb);
    }

    //sets the entire bit range to a value
    public void setValue(long value) {
        m_value = value;
    }

    //test stub
    public static void main(String[] args) {
        Register test = new Register();
        test.setValue(0x0ff00);
        System.out.println(test.getValue(15, 8));
        
        //set the value to a known value (0xDEADBEEFDEADBEEF)
        test.setValue(-2401053088876216593L);
        System.out.println(Long.toHexString(test.getValue()));
        
        //Set a lower bits to a negative number
        test.setValue(-5, 3, 0);
        System.out.println(Long.toHexString(test.getValue()));
        
        //Set a lower bits to a negative number
        test.setValue(-3, 7, 4);
        System.out.println(Long.toHexString(test.getValue()));
              
        //Set a lower bits to a negative number
        test.setValue(-2, 63, 60);
        System.out.println(Long.toHexString(test.getValue()));
               
        //Set all bits to a negative number
        test.setValue(0xDEADBEEFDEADBEEFL, 63, 0);
        System.out.println(Long.toHexString(test.getValue()));       
        
        //Set a lower bits to a positive number
        test.setValue(0x32, 15, 0);
        System.out.println(Long.toHexString(test.getValue()));
        
        //Set middle bits to a negative number
        test.setValue(0x101010, 31, 16);
        System.out.println(Long.toHexString(test.getValue()));
              
        //Set all bits to a positive value
        test.setValue(0x7654321076543210L, 63, 0);
        System.out.println(Long.toHexString(test.getValue()));
        
        //Set all bits to zero
        test.setValue(0, 63, 0);
        System.out.println(Long.toHexString(test.getValue()));
       
        //Set low bit to 1
        test.setValue(1, 0, 0);
        System.out.println(Long.toHexString(test.getValue()));
        
        //Set middle bit to 1
        test.setValue(1, 32, 32);
        System.out.println(Long.toHexString(test.getValue()));
        
        //Set high bit to 1
        test.setValue(1, 63, 63);
        System.out.println(Long.toHexString(test.getValue()));
        
        
        //Return to deadbeef
        //Set high bit to 1
        test.setValue(0xDEADBEEFDEADBEEFL, 63, 0);
        System.out.println(Long.toHexString(test.getValue()));
        
        for(int i = 0; i < 63; i=i+4)
        {
            System.out.println(Long.toHexString(test.getValue(i+3,i)));
        }        
        for(int i = 0; i < 64; i++)
        {
            System.out.println(i + ")" + Long.toHexString(test.getValue(i,i)));
        }
    }
}
