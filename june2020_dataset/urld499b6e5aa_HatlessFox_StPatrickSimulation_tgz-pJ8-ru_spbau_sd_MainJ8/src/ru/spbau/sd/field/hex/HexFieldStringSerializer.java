/**
  Copyright (c) 2013 Artur Huletski (hatless.fox@gmail.com)
  Permission is hereby granted, free of charge, to any person obtaining a copy 
  of this software and associated documentation files (the "Software"),
  to deal in the Software without restriction, including without limitation
  the rights to use, copy, modify, merge, publish, distribute, sublicense,
  and/or sell copies of the Software, and to permit persons
  to whom the Software is furnished to do so,
  subject to the following conditions:
  
  The above copyright notice and this permission notice shall be included
  in all copies or substantial portions of the Software.
  
  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
  OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
  OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package ru.spbau.sd.field.hex;

import ru.spbau.sd.model.framework.Field;
import ru.spbau.sd.model.framework.FieldStringSerializer;

public class HexFieldStringSerializer implements FieldStringSerializer {

    @Override
    public String serializeField(Field field) {
        StringBuilder sb = new StringBuilder();
        
        char[][] tableView = Field.getInstance().getTableView();
        int yMax = Field.getInstance().getYBound()+2;
        int xMax = Field.getInstance().getXBound()+2;
        
        char[][] rotatedView = new char[2*yMax-1][2*xMax-1];
        
        int middle = yMax - 1;
        
        for (int i = 0; i < yMax; i++) {
            int rotJ = middle - i;
            for (int k = 0; k <= i; k++, rotJ += 2) {
                rotatedView[i][rotJ] = tableView[i-k][k];
            }
        }
        
        for (int i = yMax - 1; i >= 0; i--) {
            int rotJ = middle + i;
            for (int k = 0; k <= i; k++, rotJ -= 2) {
                rotatedView[-i+2*yMax-2][rotJ] = tableView[yMax-1-(i-k)][yMax-1-k];
            }
        }
        
        for (int i = 0; i < 2*yMax - 1; i++) {
            for (int j = 0; j < 2*xMax - 1; j ++) {
                char charToAdd = rotatedView[i][j] == '\0' ?
                    ' ' : rotatedView[i][j];
                sb.append(charToAdd);
            }
            sb.append('\n');
        }
        return sb.toString();
    }

}
