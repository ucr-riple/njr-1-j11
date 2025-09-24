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
package uk.ac.leeds.ccg.andyt.generic.core;

/**
 * A class for handling errors and exceptions.
 */
public abstract class Generic_ErrorAndExceptionHandler {

    // Java Errors
    public static int Error = 1;
    // Third party Errors
    // Java Exceptions
    public static int Exception = 20;
    public static int IOException = 21;
    public static int FileNotFoundException = 22;
    public static int ClassNotFoundException = 23;
    public static int NumberFormatException = 24;
    // Third party Exceptions
    public static int MPIException = 30;
}
