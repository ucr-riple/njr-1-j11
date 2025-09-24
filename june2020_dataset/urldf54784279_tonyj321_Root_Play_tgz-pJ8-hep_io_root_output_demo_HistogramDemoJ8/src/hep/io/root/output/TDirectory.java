package hep.io.root.output;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import hep.io.root.output.annotations.ClassDef;
import hep.io.root.output.classes.TDatime;
import hep.io.root.output.classes.TNamed;
import hep.io.root.output.classes.TUUID;
import java.util.HashMap;

/**
 * Represents a directory within a root file. There is always a top-level
 * directory associated with a Root file, and may or may not be subdirectories
 * within the file.
 */
@ClassDef(version = 5, hasStandardHeader = false, suppressTStreamerInfo = true)
public class TDirectory extends TNamed {

    private static Date timeWarp;
    private static UUID uuidWarp;
    private TDatime fDatimeC;
    private TDatime fDatimeF;
    private int fNbytesKeys;
    private int fNbytesName;
    private Pointer fSeekDir;
    private Pointer fSeekParent;
    // The record containing this directory
    private TKey directoryRecord;
    // This is the record that contains the keys for this directory.
    // For the TFile this is written at fSeekKeys
    private TKey seekKeysRecord;
    private Pointer fSeekKeys;
    private TUUID fUUID = new TUUID(uuidWarp);
    private KeyList keyList = new KeyList();
    private final TDirectory parent;
    private final HashMap<String, TDirectory> subdirectories = new HashMap<>();

    TDirectory(String name, String title, TDirectory parent) {
        super(name, title);
        this.parent = parent;
        fDatimeC = fDatimeF = new TDatime(timeWarp);
        fSeekParent = parent == null ? Pointer.ZERO : parent.fSeekDir;
    }

    /**
     * Each directory has two records in the corresponding TFile, one containing
     * the TDirectory itself, and the other containing the key list.
     *
     * @param tFile The TFile in which the records will be created.
     */
    void addOwnRecords(Pointer parent) {
        TFile tFile = getTFile();
        directoryRecord = tFile.addRecord(getClass(), getName(), getTitle(), parent, true);
        directoryRecord.add(this);
        this.fSeekDir = directoryRecord.getSeekKey();
        seekKeysRecord = tFile.addKeyListRecord(getClass(), getName(), getTitle(), fSeekDir, true);
        seekKeysRecord.add(keyList);
        seekKeysRecord.setCompressionLevel(0);
        fSeekKeys = seekKeysRecord.getSeekKey();
    }

    /**
     * Add an object to a directory. Note that this just registers the object
     * with the file, the data is not extracted from the object and written to
     * disk until flush() or close() is called.
     *
     * @param object The object to be written to disk.
     */
    public void add(Object object) {
        String fName, fTitle;
        if (object instanceof TNamed) {
            TNamed tNamed = (TNamed) object;
            fName = tNamed.getName();
            fTitle = tNamed.getTitle();
        } else {
            fName = StreamerUtilities.getClassInfo(object.getClass()).getName();
            fTitle = "";
        }
        TKey record = getTFile().addRecord(object.getClass(), fName, fTitle, fSeekDir, false);
        record.add(object);
        keyList.add(record);
    }
    /**
     * Search this directory for a named subdirectory. The search is not recursive.
     * @param name The name of the subdirectory to search for.
     * @return The TDirectory if found, or <code>null</code> if not found.
     */
    public TDirectory findDir(String name) {
        return findDir(name, false);
    }

    /**
     * Search this directory for a subdirectory
     *
     * @param path The path to search for, / delimited.
     * @param recursive If <code>true</code> then search recursively into
     * subdirectories.
     * @return The TDirectory found, or <code>null</code> if no directory found.
     */
    public TDirectory findDir(String path, boolean recursive) {
        if (recursive) {
            String[] pathElements = parsePath(path);
            TDirectory current = this;
            for (String element : pathElements) {
                current = current.findDir(element, false);
                if (current == null) {
                    return null;
                }
            }
            return current;
        } else {
            return subdirectories.get(path);
        }
    }

    private String[] parsePath(String path) {
        return path.split("/");
    }

    /**
     * Add a new subdirectory to this directory. The returned TDirectory should
     * be used to add items to the newly created subdirectory.
     *
     * @param name The name of the new directory
     * @return The newly created subdirectory
     * @throws IllegalArgumentException If the named directory already exists
     */
    public TDirectory mkdir(String name) throws IllegalArgumentException {
        return mkdir(name, false);
    }
    
    /**
     * Add a new subdirectory specified by path. 
     * @param path Path to the directory, / delimited
     * @param recursive If <code>true</code> any intermediate directories in the
     * path will be created if necessary.
     * @return The newly created TDirectory. 
     * @throws IllegalArgumentException If an attempt is made to create a directory 
     * which already exists. 
     */
    public TDirectory mkdir(String path, boolean recursive) {
        if (recursive) {
            String[] pathElements = parsePath(path);
            TDirectory current = this;
            for (String element : pathElements) {
                TDirectory next = current.findDir(element, false);
                if (next == null) {
                    next = current.mkdir(element,false);
                }
                current = next;
            }
            return current;
        } else {
            if (subdirectories.containsKey(path)) {
                throw new IllegalArgumentException("Directory "+path+" already exists");
            }
            TDirectory newDir = new TDirectory(path, "", this);
            newDir.addOwnRecords(fSeekDir);
            keyList.add(newDir.directoryRecord);
            subdirectories.put(path, newDir);
            return newDir;
        }
    }

    /**
     * Search up the TDirectory tree until reach the top level, which must be a
     * TFile.
     */
    private TFile getTFile() {
        for (TDirectory dir = this;; dir = dir.parent) {
            if (dir instanceof TFile) {
                return (TFile) dir;
            }
        }
    }

    int getNBytesName() {
        return fNbytesName;
    }

    Object getUUID() {
        return fUUID;
    }

    void streamer(RootOutput out) throws IOException {
        fNbytesKeys = seekKeysRecord.size;
        fNbytesName = (int) ((RootOutputNonPublic) out).getFilePointer();
        out.writeShort(TDirectory.class.getAnnotation(ClassDef.class).version());
        out.writeObject(fDatimeC);
        out.writeObject(fDatimeF);
        out.writeInt(fNbytesKeys);
        out.writeInt(fNbytesName);
        out.writeObject(fSeekDir);
        out.writeObject(fSeekParent);
        out.writeObject(fSeekKeys);
        out.writeObject(fUUID);
        if (!out.isLargeFile()) {
            for (int i = 0; i < 3; i++) {
                out.writeInt(0);
            }
        }
    }

    /**
     * Used to remove unpredictable elements from the file during testing
     *
     * @param testMode <code>true</code> to used fixed timestamps/uuid
     */
    static void setTimeWarp(boolean testMode) {
        if (testMode) {
            timeWarp = new Date(1362336450390L);
            uuidWarp = UUID.fromString("3e3260c7-303a-4ea9-83b9-f43c34c96908");
        } else {
            timeWarp = null;
            uuidWarp = null;
        }
    }

    static Date getTimeWarp() {
        return timeWarp;
    }

    @ClassDef(hasStandardHeader = false)
    private static class KeyList {

        private ArrayList<TKey> list = new ArrayList<>();

        private void write(RootOutput out) throws IOException {
            out.writeInt(list.size());
            for (TKey o : list) {
                out.writeObject(o);
            }
        }

        private void add(TKey record) {
            list.add(record);
        }
    }
}
