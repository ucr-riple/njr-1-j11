package ru.spbau.tinydb.bufferManager;

import ru.spbau.tinydb.common.DBException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sergey on 27.10.2014.
 */
public class BufferManager {
	// TODO add some header(magic constants, version, other very useful info)
	// TODO add check sum(methods write/load page)
	
    public final static long PAGE_SIZE = 1 << 12; // 4KB
    private final static int BUFFER_SIZE = 1 << 10; // in PAGE_SIZE's
    public final static int METAINF_FIRST_PAGE = 1;
    public final static int INDEXES_FIRST_PAGE = 2;

    private final RandomAccessFile dbFile;
    private final ByteBuffer buffer = ByteBuffer.allocate((int) (PAGE_SIZE * BUFFER_SIZE));
    // 'i' page is used now if 'i' bit is set
    private final BitSet bufferIsUsed = new BitSet(BUFFER_SIZE);
    // 'i' page was recently used if 'i' bit is set
    private final BitSet bufferWasUsed = new BitSet(BUFFER_SIZE);
    // 'i' page should be written to disk if 'i' bit is set
    private final BitSet bufferChanged = new BitSet(BUFFER_SIZE);
    private final int[] usedPages = new int[BUFFER_SIZE];
    private final Map<Integer, Integer> pageIdxToBufferPos = new HashMap<>(); // pageIndex -> buffer position
    private final int[] bufferPosToPageIdx = new int[BUFFER_SIZE]; // buffer position -> pageIndex  
    private int currentPos = -1;
    private int length;

    public BufferManager(String dbFileName) throws DBException {
        this(new File(dbFileName));
    }

    public BufferManager(File dbFile) throws DBException {
        try {
            this.dbFile = new RandomAccessFile(dbFile, "rw");
        } catch (FileNotFoundException e) {
            throw new DBException(e);
        }

        init();
    }

    private void init() throws DBException {
        Arrays.fill(bufferPosToPageIdx, -1);
        try {
            length = (int) (dbFile.length() / PAGE_SIZE);
            if(length == 0) {
                increaseLength();
                //writeHeader(there is no header now)
                increaseLength(); // for MetaInf Page
                increaseLength(); // for Indexes Page
            } else {
                //loadHeader(there is no header now)
            }
        } catch (IOException e) {
            throw new DBException("IOException in BufferManager init method(getting file lenght)");
        }
    }

    private void increaseLength() {
        length += 1;
        try {
            dbFile.setLength(length * PAGE_SIZE);
        } catch (IOException e) {
            throw new RuntimeException("IOException when increasing file size. No space left");
        }
    }

    public int getFreePage() {
        increaseLength();
        return length - 1;
    }

    public BufferView getPage(int pageIndex) {
        if(pageIdxToBufferPos.containsKey(pageIndex)) {
            int bufferPos = pageIdxToBufferPos.get(pageIndex);
            return new BufferView(buffer, bufferPos, (int) PAGE_SIZE, this);
        }
        
        // Clock algorithm
        while(true) {
	        currentPos = bufferIsUsed.nextClearBit(currentPos + 1);
	        if(currentPos == BUFFER_SIZE) {
	        	currentPos = bufferIsUsed.nextClearBit(0);
	        }
	        if(currentPos == BUFFER_SIZE) {
	            throw new RuntimeException("All buffer positions are in use! Panic!");
	        }
	        if(bufferWasUsed.get(currentPos)) {
	        	bufferWasUsed.clear(currentPos);
	        } else {
	        	break;
	        }
        }
        if(bufferChanged.get(currentPos)) {
            writePage(currentPos);
        }
        pageIdxToBufferPos.remove(bufferPosToPageIdx[currentPos]);
        bufferPosToPageIdx[currentPos] = -1;
        loadPage(pageIndex, currentPos);
        return new BufferView(buffer, currentPos, (int) PAGE_SIZE, this);
    }

    private void writePage(int bufferPos) {
        try {
            int pageIndex = bufferPosToPageIdx[bufferPos];
            dbFile.seek(pageIndex * PAGE_SIZE);
            dbFile.write(buffer.array(), (int) (bufferPos * PAGE_SIZE), (int) PAGE_SIZE);
            bufferChanged.clear(bufferPos);
        } catch (IOException e) {
            throw new RuntimeException("IOException when writing page " + bufferPosToPageIdx[bufferPos] + " to " + bufferPos);
        }
    }

    private void loadPage(int pageIndex, int bufferPos) {
        try {
            dbFile.seek(pageIndex * PAGE_SIZE);
            dbFile.read(buffer.array(), (int) (bufferPos * PAGE_SIZE), (int) PAGE_SIZE);
            bufferPosToPageIdx[bufferPos] = pageIndex;
            pageIdxToBufferPos.put(pageIndex, bufferPos);
        } catch (IOException e) {
            throw new RuntimeException("IOException when reading page " + pageIndex + " to " + bufferPos);
        }
    }

    void onBufferViewCreated(int bufferPos) {
        usedPages[bufferPos] += 1;
        bufferIsUsed.set(bufferPos);
        bufferWasUsed.set(bufferPos);
    }

    void onBufferViewRemoved(int bufferPos) {
        usedPages[bufferPos] -= 1;
        if(usedPages[bufferPos] == 0) {
            bufferIsUsed.clear(bufferPos);
        }
    }

    public void onChanged(int bufferPos) {
        bufferChanged.set(bufferPos);
    }

    public void flushBuffer() {
        for(int i = bufferChanged.nextSetBit(0); i != -1; i = bufferChanged.nextSetBit(i + 1)) {
            writePage(i);
        }
    }
}
