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
package uk.ac.leeds.ccg.andyt.generic.io;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Random;
import java.util.logging.Level;
import uk.ac.leeds.ccg.andyt.generic.logging.AbstractLog;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_ErrorAndExceptionHandler;

/**
 * Abstract class for handling  <code>AbstractDataRecords</code>
 * @version 2.0.0
 */
public abstract class AbstractDataHandler 
        extends AbstractLog
        implements Serializable {

    /**
	 * Serializable class version number for swapping
	 * (serialization/deserialization)
	 */
//	private static final long serialVersionUID = 1L;

    /**
     * For storing the length of an <code>AbstractDataRecord</code> that this
     * Handler handles in measured in <code>byte</code> units and stored as a
     * <code>long</code>.
     */
    protected long _RecordLength;

    /**
     * Returns a copy of <code>_RecordLength</code>.
     * @return 
     */
    public long get_RecordLength() {
        return this._RecordLength;
    }
    /**
     * Formatted <code>File</code> for
     * <code>AbstractDataRecords</code>.
     */
    protected File _File;
    /**
     * The workspace directory.
     */
    protected File _Directory;

    public File get_Directory() {
        return _Directory;
    }

    public void init(
            Level aLevel,
            File _Directory) {
        this._Directory = _Directory;
        init_Logger(
                aLevel,
                _Directory);
    }
    public void init(
            File _Directory) {
        init( Level.FINE,
              _Directory);
    }
    /**
     * <code>RandomAccessFile<code> of <code>_File</code> for
     * <code>AbstractDataRecords</code>
     */
    protected transient RandomAccessFile _RandomAccessFile;

    /**
     * Set:
     * <code>this._File = _File</code>
     * <code>this.tRandomAccessFile = new
     * RandomAccessFile(_File,"r" )</code>
     * @param _File
     *            Formatted <code>File</code> containing
     *            <code>AbstractDataRecords</code>.
     */
    protected void load(File _File) {
        _Logger.entering(this.getClass().getCanonicalName(), "load(File)");
        this._File = _File;
        if (!_File.exists()) {
            try {
                _File.createNewFile();
                this._RandomAccessFile = new RandomAccessFile(this._File, "rw");
            } catch (IOException aIOException) {
                log(aIOException.getLocalizedMessage());
                System.exit(Generic_ErrorAndExceptionHandler.IOException);
            }
        } else {
            try {
                this._RandomAccessFile = new RandomAccessFile(this._File, "r");
            } catch (IOException aIOException) {
                log(aIOException.getLocalizedMessage());
                System.exit(Generic_ErrorAndExceptionHandler.IOException);
            }
        }
        _Logger.exiting(this.getClass().getCanonicalName(), "load(File)");
    }

    /**
     * @return
     * The number of <code>AbstractDataRecords</code> in
     * <code>this.tRandomAccessFile</code>
     */
    public long getNDataRecords() {
        _Logger.entering(this.getClass().getCanonicalName(), "getNDataRecords()");
        try {
            BigInteger aBigInteger = new BigInteger(
                    Long.toString(this._RandomAccessFile.length()));
            BigInteger bBigInteger = new BigInteger(
                    Long.toString(this._RecordLength));
            return aBigInteger.divide(bBigInteger).longValue();
        } catch (IOException _IOException) {
            _IOException.printStackTrace();
        }
        _Logger.exiting(this.getClass().getCanonicalName(), "getNDataRecords()");
        return Long.MIN_VALUE;
    }

    /**
     * @return An <code>AbstractDataRecord</code> for the given RecordID
     * @param RecordID
     * The RecordID of the <code>AbstractDataRecord</code> to be returned.
     */
    public abstract AbstractDataRecord getDataRecord(
            long RecordID);

    /**
     * Prints a random set of <code>n</code> <code>AbstractDataRecords</code>
     * via <code>System.out.println()</code>
     * @param n
     * the number of <code>AbstractDataRecords</code> to print out
     * @param random 
     * the <code>Random</code> used for selecting
     * <code>AbstractDataRecords</code> to print
     * @throws java.io.IOException
     */
    protected void print(
            int n,
            Random random)
            throws IOException {
        _Logger.entering(this.getClass().getCanonicalName(), "print(int,Random)");
        long nDataRecords = getNDataRecords();
        double double0;
        AbstractDataRecord aDataRecord;
        for (int i0 = 0; i0 < n; i0++) {
            double0 = random.nextDouble() * nDataRecords;
            aDataRecord = getDataRecord((long) double0);
            log(aDataRecord.toString());
        }
        _Logger.exiting(this.getClass().getCanonicalName(), "print(int,Random)");
    }
}