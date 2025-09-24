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

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

/**
 * A class to be extended so as to handle OutOfMemoryErrors.
 */
public abstract class Generic_OutOfMemoryErrorHandler
        implements Generic_OutOfMemoryErrorHandlerInterface, Serializable {

    public transient Generic_TestMemory _Generic_TestMemory;

    public boolean _HandleOutOfMemoryError_boolean;

    public static final int Default_Memory_Threshold = 6000000;//4000000;
    /**
     * For ease of search and replace coding.
     */
    public static final boolean HandleOutOfMemoryErrorFalse = false;
    /**
     * For ease of search and replace coding.
     */
    public static final boolean HandleOutOfMemoryErrorTrue = true;
    /**
     * Reserve memory that can be set to null and garbage collected
     * so as to handle OutOfMemoryErrors.
     */
    protected transient int[] _MemoryReserve;
    //protected transient int[] _MemoryReserve;

    public abstract boolean swapToFile_DataAny();
    public abstract boolean swapToFile_DataAny(boolean handleOutOfMemoryError);

    /**
     * May initialise _Generic_TestMemory and _Generic_TestMemory._Runtime.
     * @return _Generic_TestMemory._Runtime
     */
    public Runtime getRuntime() {
        return get_Generic_TestMemory()._Runtime;
    }

    /**
     * Initialises _MemoryReserve as an array of size given by size.
     *
     * @param size Size that _MemoryReserve is initialised to.
     */
    protected final void init_MemoryReserve(
            int size) {
        if (this._MemoryReserve == null) {
            this._MemoryReserve = new int[size];
            Arrays.fill(
                    this._MemoryReserve,
                    Integer.MIN_VALUE);
        }
    }

    /**
     * @return this._MemoryReserve.
     */
    public int[] get_MemoryReserve() {
        return this._MemoryReserve;
    }

    /**
     * this._MemoryReserve = a_MemoryReserve;
     * @param a_MemoryReserve
     */
    public void set_MemoryReserve(int[] a_MemoryReserve) {
        this._MemoryReserve = a_MemoryReserve;
    }

    /**
     * Initialises _MemoryReserve.
     * @param handleOutOfMemoryError
     *   If true then OutOfMemoryErrors are caught, swap operations are initiated,
     *     then the method is re-called.
     *   If false then OutOfMemoryErrors are caught and thrown.
     */
//    public abstract void init_MemoryReserve(
//            boolean handleOutOfMemoryError);
    public void init_MemoryReserve(
            boolean handleOutOfMemoryError) {
        try {
            init_MemoryReserve();
            tryToEnsureThereIsEnoughMemoryToContinue();
        } catch (OutOfMemoryError a_OutOfMemoryError) {
            if (handleOutOfMemoryError) {
                clear_MemoryReserve();
                if (swapToFile_DataAny()){
                    init_MemoryReserve(
                        HandleOutOfMemoryErrorFalse);
                } else {
                    throw a_OutOfMemoryError;
                }
            } else {
                throw a_OutOfMemoryError;
            }
        }
    }

    /**
     * Initialises _MemoryReserve as an int[] of size Default_Memory_Threshold.
     * _MemoryReserve is for use when an OutOfMemoryError is handled. This
     * involves clear_MemoryReserve() which sets _MemoryReserve to null 
     * providing memory for swapping operations that move data from one location
     * (memory/RAM) to another location (disk). The swapping may involve
     * accounting the amount and details of the data swapped and the locations
     * and returning this information.
     */
    protected void init_MemoryReserve() {
        init_MemoryReserve(Default_Memory_Threshold);
    }

    /**
     * Clears _MemoryReserve by setting it to null and calling the garbage
     * collector. After this method is executed then no further method calls 
     * will handle OutOfMemoryError until _MemoryReserve is initialised again.
     * Generally, the assumption is that _MemoryReserve is not null before this
     * is called.
     */
    public final void clear_MemoryReserve() {
        //log(Runtime.getRuntime().freeMemory());
        this._MemoryReserve = null;
        System.gc();
    }

    /**
     * For ensuring robustness in handling OutOfMemoryError and to try to
     * prevent them being thrown. If this method returns false then it may be
     * best to write all data to persistent memory and restart the JVM with a
     * increased heap size before attempting to continue.
     * @return true if there is enough memory to continue and false otherwise.
     */
    protected abstract boolean tryToEnsureThereIsEnoughMemoryToContinue();

    /**
     * For initialising and returning _Generic_TestMemory.
     * @return _Generic_TestMemory (after default construction if null)
     */
    protected Generic_TestMemory get_Generic_TestMemory() {
        if (_Generic_TestMemory == null) {
            _Generic_TestMemory = new Generic_TestMemory();
        }
        return _Generic_TestMemory;
    }

    /**
     * A method to ensure there is enough memory to continue.
     * @param handleOutOfMemoryError
     * @return true if ensured there is enough memory to continue
     */
    public abstract boolean tryToEnsureThereIsEnoughMemoryToContinue(
            boolean handleOutOfMemoryError);

    /**
     * <code>
     * return get_Generic_TestMemory().getTotalFreeMemory();
     * </code>
     * @return 
     */
    protected long getTotalFreeMemory() {
        return get_Generic_TestMemory().getTotalFreeMemory();
    }

    /**
     * For returning the total free memory available to the JVM. This method
     * will try to swap data if handleOutOfMemory is true, but if it finds no
     * data to swap will throw an OutOfMemoryError.
     * @param handleOutOfMemoryError
     * @return 
     */
    public long getTotalFreeMemory(
            boolean handleOutOfMemoryError) {
        try {
            long result = getTotalFreeMemory();
            tryToEnsureThereIsEnoughMemoryToContinue();
            return result;
        } catch (OutOfMemoryError _OutOfMemoryError) {
            if (handleOutOfMemoryError) {
                clear_MemoryReserve();
                boolean swapSuccess = swapToFile_DataAny();
                if (!swapSuccess){
                        throw new OutOfMemoryError();
                }
                init_MemoryReserve(HandleOutOfMemoryErrorFalse);
                return getTotalFreeMemory(
                        handleOutOfMemoryError);
            } else {
                throw _OutOfMemoryError;
            }
        }
    }

    /**
     * For initialising a String of length length.
     * @param length
     * @param handleOutOfMemoryError
     * @return 
     */
    public String initString(
            int length,
            boolean handleOutOfMemoryError) {
        try {
            String result = new String();
            for (int i = 0; i < length; i++) {
                result += "X";
            }
            tryToEnsureThereIsEnoughMemoryToContinue();
            return result;
        } catch (OutOfMemoryError a_OutOfMemoryError) {
            if (handleOutOfMemoryError) {
                clear_MemoryReserve();
                if (swapToFile_DataAny()){
                    init_MemoryReserve(
                       HandleOutOfMemoryErrorFalse);
                } else {
                    throw a_OutOfMemoryError;
                }
                return initString(
                        length,
                        handleOutOfMemoryError);
            } else {
                throw a_OutOfMemoryError;
            }
        }
    }

    /**
     * @param a_String
     * @param b_String
     * @param handleOutOfMemoryError
     * @return String which is a_String appended to the end of b_String
     */
    public String initString(
            String a_String,
            String b_String,
            boolean handleOutOfMemoryError) {
        try {
            String result = a_String + b_String;
            tryToEnsureThereIsEnoughMemoryToContinue();
            return result;
        } catch (OutOfMemoryError a_OutOfMemoryError) {
            if (handleOutOfMemoryError) {
                clear_MemoryReserve();
                if (swapToFile_DataAny()){
                    init_MemoryReserve(
                       HandleOutOfMemoryErrorFalse);
                } else {
                    throw a_OutOfMemoryError;
                }
                return initString(
                        a_String,
                        b_String,
                        handleOutOfMemoryError);
            } else {
                throw a_OutOfMemoryError;
            }
        }
    }

    /**
     * For initialising a File from String _String.
     * @param a_String
     * @param handleOutOfMemoryError
     * @return 
     * @throws java.io.IOException 
     */
    public File initFile(
            String a_String,
            boolean handleOutOfMemoryError)
            throws IOException {
        try {
            File result = new File(a_String);
            result.getParentFile().mkdirs();
            result.createNewFile();
            tryToEnsureThereIsEnoughMemoryToContinue();
            return result;
        } catch (OutOfMemoryError a_OutOfMemoryError) {
            if (handleOutOfMemoryError) {
                clear_MemoryReserve();
                if (swapToFile_DataAny()){
                    init_MemoryReserve(
                       HandleOutOfMemoryErrorFalse);
                } else {
                    throw a_OutOfMemoryError;
                }
                init_MemoryReserve(
                        handleOutOfMemoryError);
                return initFile(
                        a_String,
                        handleOutOfMemoryError);
            } else {
                throw a_OutOfMemoryError;
            }
        }
    }

    /**
     * For initialising a File from File _File and String _String.
     * @param a_File
     * @param a_String
     * @param handleOutOfMemoryError
     * @return 
     * @throws java.io.IOException 
     */
    public File initFile(
            File a_File,
            String a_String,
            boolean handleOutOfMemoryError)
            throws IOException {
        try {
            File result = new File(a_File, a_String);
            a_File.mkdirs();
            result.createNewFile();
            tryToEnsureThereIsEnoughMemoryToContinue();
            return result;
        } catch (OutOfMemoryError a_OutOfMemoryError) {
            if (handleOutOfMemoryError) {
                clear_MemoryReserve();
                if (swapToFile_DataAny()){
                    init_MemoryReserve(
                       HandleOutOfMemoryErrorFalse);
                } else {
                    throw a_OutOfMemoryError;
                }
                return initFile(
                        a_String,
                        handleOutOfMemoryError);
            } else {
                throw a_OutOfMemoryError;
            }
        }
    }

    /**
     * For initialising a File Directory from File _ParentFile and String _String.
     * @param _ParentFile
     * @param _String
     * @param handleOutOfMemoryError
     * @return 
     * @throws java.io.IOException 
     */
    public File initFileDirectory(
            File _ParentFile,
            String _String,
            boolean handleOutOfMemoryError)
            throws IOException {
        try {
            File result = new File(_ParentFile, _String);
            result.mkdirs();
            tryToEnsureThereIsEnoughMemoryToContinue();
            return result;
        } catch (OutOfMemoryError a_OutOfMemoryError) {
            if (handleOutOfMemoryError) {
                clear_MemoryReserve();
                if (swapToFile_DataAny()){
                    init_MemoryReserve(
                       HandleOutOfMemoryErrorFalse);
                } else {
                    throw a_OutOfMemoryError;
                }
                return initFileDirectory(
                        _ParentFile,
                        _String,
                        handleOutOfMemoryError);
            } else {
                throw a_OutOfMemoryError;
            }
        }
    }

    public String println(
            String a_String,
            String a_StringToDuplicateAndReturn,
            boolean handleOutOfMemoryError) {
        try {
            System.out.println(a_String);
            String result = a_StringToDuplicateAndReturn;
            return result;
        } catch (OutOfMemoryError a_OutOfMemoryError) {
            if (handleOutOfMemoryError) {
                clear_MemoryReserve();
                if (swapToFile_DataAny()){
                    init_MemoryReserve(
                       HandleOutOfMemoryErrorFalse);
                } else {
                    throw a_OutOfMemoryError;
                }
                return println(
                        a_String,
                        a_StringToDuplicateAndReturn,
                        handleOutOfMemoryError);
            } else {
                throw a_OutOfMemoryError;
            }
        }
    }

    public double sin(
            double value,
            boolean handleOutOfMemoryError) {
        try {
            return Math.sin(value);
        } catch (OutOfMemoryError a_OutOfMemoryError) {
            if (handleOutOfMemoryError) {
                clear_MemoryReserve();
                if (swapToFile_DataAny()){
                    init_MemoryReserve(
                       HandleOutOfMemoryErrorFalse);
                } else {
                    throw a_OutOfMemoryError;
                }
                init_MemoryReserve(
                        handleOutOfMemoryError);
                return sin(
                        value,
                        handleOutOfMemoryError);
            } else {
                throw a_OutOfMemoryError;
            }
        }
    }

    public String toString(
            double a_double,
            boolean handleOutOfMemoryError) {
        try {
            return Double.toString(a_double);
        } catch (OutOfMemoryError a_OutOfMemoryError) {
            if (handleOutOfMemoryError) {
                clear_MemoryReserve();
                if (swapToFile_DataAny()){
                    init_MemoryReserve(
                       HandleOutOfMemoryErrorFalse);
                } else {
                    throw a_OutOfMemoryError;
                }
                init_MemoryReserve(
                        handleOutOfMemoryError);
                return toString(
                        a_double,
                        handleOutOfMemoryError);
            } else {
                throw a_OutOfMemoryError;
            }
        }
    }
}
