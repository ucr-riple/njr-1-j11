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

public class IndexPair {
    
    public IndexPair(int index1, int index2){
        m_index1 = index1;
        m_index2 = index2;
    }

    public int getIndex1() {
        return m_index1;
    }

    public void setIndex1(int m_index1) {
        this.m_index1 = m_index1;
    }

    public int getIndex2() {
        return m_index2;
    }

    public void setIndex2(int m_index2) {
        this.m_index2 = m_index2;
    }
    int      m_index1;
    int      m_index2;
}
