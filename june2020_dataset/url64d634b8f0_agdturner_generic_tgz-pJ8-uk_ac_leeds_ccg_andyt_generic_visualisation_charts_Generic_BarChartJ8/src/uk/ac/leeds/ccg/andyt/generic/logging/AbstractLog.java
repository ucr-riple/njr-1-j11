/**
 * A component of a library for
 * <a href="http://www.geog.leeds.ac.uk/people/a.turner/projects/MoSeS">MoSeS</a>.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA.
 */
package uk.ac.leeds.ccg.andyt.generic.logging;

import uk.ac.leeds.ccg.andyt.generic.core.Generic_ErrorAndExceptionHandler;
import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
//import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Abstract class to be extended by any class requiring logging.
 */
public abstract class AbstractLog {

    /**
     * For logging
     */
    protected transient FileHandler _Logger_FileHandler;
    protected transient Logger _Logger;

    public void init_Logger(
            Level aLevel,
            File directory,
            String classname,
            String filename) {
        _Logger = Logger.getLogger(classname);
        try {
            if (_Logger_FileHandler != null) {
                _Logger.removeHandler(_Logger_FileHandler);
            }
            File logDirectory = new File(
                    directory.getCanonicalPath() +
                    System.getProperty("file.separator") + "logs");
            logDirectory.mkdirs();
            _Logger_FileHandler = new FileHandler(
                    logDirectory.getCanonicalPath() +
                    System.getProperty("file.separator") + filename);
        } catch (IOException aIOException) {
            aIOException.printStackTrace();
            System.exit(Generic_ErrorAndExceptionHandler.IOException);
        }
        _Logger.addHandler(_Logger_FileHandler);
        _Logger.setLevel(aLevel);
        //_Logger.setLevel(Level.ALL);
        _Logger.exiting(
                classname,
                "init_Logger(Level,File,String)");
    }

    public void init_Logger(
            Level aLevel,
            File directory,
            String filename) {
        _Logger = Logger.getAnonymousLogger();
        try {
//            if (_Logger_FileHandler != null) {
//                _Logger.removeHandler(_Logger_FileHandler);
//            }
            File logDirectory = new File(
                    directory.getCanonicalPath() +
                    System.getProperty("file.separator") + "logs");
            logDirectory.mkdirs();
            _Logger_FileHandler = new FileHandler(
                    logDirectory.getCanonicalPath() +
                    System.getProperty("file.separator") + filename);
        } catch (IOException aIOException) {
            aIOException.printStackTrace();
            System.exit(Generic_ErrorAndExceptionHandler.IOException);
        }
        _Logger.addHandler(_Logger_FileHandler);
        _Logger.setLevel(aLevel);
        //_Logger.setLevel(Level.ALL);
        _Logger.exiting(
                this.getClass().getCanonicalName(),
                "init_Logger(Level,File,String)");
    }

    public void init_Logger(
            Level aLevel,
            File directory) {
        String classname = this.getClass().getCanonicalName();
        String filename = classname + ".log";
        init_Logger(
                aLevel,
                directory,
                filename);
    }

    public void log(
            String aString) {
        System.out.println(aString);
        _Logger.log(
                _Logger.getLevel(),
                aString);
    }

    public void log(
            Level aLevel,
            String aString) {
        System.out.println(aString);
        _Logger.log(
                aLevel,
                aString);
    }
}
