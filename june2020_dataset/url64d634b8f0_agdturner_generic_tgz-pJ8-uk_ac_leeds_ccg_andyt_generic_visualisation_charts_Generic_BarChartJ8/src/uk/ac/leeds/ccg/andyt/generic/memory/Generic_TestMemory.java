/**
 * Copyright 2010 Andy Turner, The University of Leeds, UK
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package uk.ac.leeds.ccg.andyt.generic.memory;

/**
 * A class with methods that help in testing computer memory.
 */
public class Generic_TestMemory {

    protected transient Runtime _Runtime;
    private long _maxMemory;

    /** Creates a new instance of Generic_TestMemory */
    public Generic_TestMemory() {
        this._Runtime = Runtime.getRuntime();
        this._maxMemory = this._Runtime.maxMemory();
    }
    
    /** Creates a new instance of Generic_TestMemory
     * @param aRuntime */
    public Generic_TestMemory(Runtime aRuntime) {
        this._Runtime = aRuntime;
        this._maxMemory = this._Runtime.maxMemory();
    }
    
    /**
     * For returning the total free memory.
     * @return The TotalFreeMemory available as calculated from _Runtime.
     */
    public long getTotalFreeMemory() {
        long result;
        long allocatedMemory = _Runtime.totalMemory();
        long freeMemory = _Runtime.freeMemory();
        result = freeMemory + (_maxMemory - allocatedMemory);
        return result;
    }
}
