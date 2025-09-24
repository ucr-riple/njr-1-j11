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

package edu.syr.bytecast.interp.amd64.instructions;


public class ISAUtil {
    
    public static boolean checkOverflow(long a, long b, long result)
    {
        if(a > 0 && b > 0 && result < 0)
            return true;
        else if (a < 0 && b < 0 && result > 0)
            return true;
        else
            return false;
    }
    
    public static boolean checkCarry(long a, long b, long result){
        if(a > 0 && b < 0 && result > 0)
            return true;
        else if(a < 0 && b < 0)
            return true;
        else
            return false;                              
    }
    
    public static boolean isEvenParity(long input){
        long tmp = input;
        int result=0;
        
        //x86 parity is always least significany byte
        for(int i = 0; i < 8; i++){
            
            //every time a "1" is seen, increment result.
            if((tmp & 0x01) == 1){
                result++;
            }
            
            //bite off an lsb
            tmp = tmp >> 1;
        }
        
        //if the result is odd, return true.
        if((result & 0x01) == 1)
            return true;
        else
            return false;
    }
}
