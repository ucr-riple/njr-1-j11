package ru.spbau.tinydb.table;

import ru.spbau.tinydb.bufferManager.BufferManager;
import ru.spbau.tinydb.bufferManager.BufferView;
import ru.spbau.tinydb.common.DBException;

import java.util.Iterator;
import java.util.concurrent.ExecutionException;

/**
 * Created by Sergey on 27.10.2014.
 */
public class TableBase implements Iterable<ViewWithId> {
    private final static int POINTERS_SIZE = 8;
    private final BufferManager bufferManager;
    private final int firstPage;
    private final int recordsPerPage;
    private final int recordSize;
    private final int bitSetSize;
    // Header {
    private int firstFullPage;
    private int firstNotFullPage;
    private int rowsCount;
    // } Header

    public TableBase(BufferManager bufferManager, int firstPage, int recordSize) {
        this.bufferManager = bufferManager;
        this.firstPage = firstPage;
        this.recordSize = recordSize;
        firstFullPage = 0;
        firstNotFullPage = 0;
        rowsCount = 0;
        // (Available bits count) / (bits per record)
        // bits per record is 8 * recordSize plus 2 bits for 
        // 2 bitSets(availability and delete flag)
        recordsPerPage = (int)(8 * (BufferManager.PAGE_SIZE - POINTERS_SIZE)) / (8 * recordSize + 2);
        bitSetSize = (recordsPerPage + recordsPerPage + 7) / 8;
        loadHeader();
    }


    private void writeHeader() {
        try (BufferView firstPageView = bufferManager.getPage(firstPage)) {
            firstPageView.setInt(0, firstNotFullPage);
            firstPageView.setInt(4, firstFullPage);
            firstPageView.setInt(8, rowsCount);
            firstPageView.setChanged();
        }

    }

    private void loadHeader() {
        try (BufferView firstPageView = bufferManager.getPage(firstPage)) {
            firstNotFullPage = firstPageView.getInt(0);
            firstFullPage = firstPageView.getInt(4);
            rowsCount = firstPageView.getInt(8);
        }
    }

    public int insert(byte[] row) throws ExecutionException {
        if(row.length != recordSize) {
            throw new IllegalArgumentException();
        }
        BufferView pageView;
        if(firstNotFullPage == 0) {
            firstNotFullPage = bufferManager.getFreePage();
            writeHeader();
            pageView = bufferManager.getPage(firstNotFullPage);
            pageView.setChanged();
        } else {
            pageView = bufferManager.getPage(firstNotFullPage);
        }
        int pageIndex = firstNotFullPage;


        // writing row
        BufferView bitSetView = pageView.getSubView(POINTERS_SIZE, bitSetSize);
        BufferView recordsView = pageView.getSubView(POINTERS_SIZE + bitSetSize, recordsPerPage * recordSize);
        int recordPosition;
        // Hand made BitSet
        for(recordPosition = 0; checkBit(bitSetView, recordPosition); recordPosition ++) {
            if(recordPosition >= recordsPerPage) {
                throw new RuntimeException("No empty position when page is not full");
            }
        }
        setBit(bitSetView, recordPosition);
        recordsView.setBytes(recordPosition * recordSize, row);
        recordsView.close();

        boolean bitSetFull = true;
        for(int i = 0; i < recordsPerPage; i ++) {
            if(!checkBit(bitSetView, i)) {
                bitSetFull = false;
                break;
            }
        }
        bitSetView.close();
        if(bitSetFull) {
            // move to full
            int prevNotFull = pageView.getInt(0);
            int nextNotFull = pageView.getInt(4);
            if(prevNotFull != 0) {
                BufferView prevView = bufferManager.getPage(prevNotFull);
                prevView.setInt(4, nextNotFull);
                prevView.setChanged();
                prevView.close();
            } else {
                firstNotFullPage = 0;
                writeHeader();
            }
            if(nextNotFull != 0) {
                BufferView nextView = bufferManager.getPage(nextNotFull);
                nextView.setInt(0, prevNotFull);
                nextView.setChanged();
                nextView.close();
            }
            pageView.setInt(0, 0);
            pageView.setInt(4, firstFullPage);

            if(firstFullPage != 0) {
                BufferView fullView = bufferManager.getPage(firstFullPage);
                fullView.setInt(0, pageIndex);
                fullView.setChanged();
                fullView.close();
            }
            firstFullPage = pageIndex;
            writeHeader();
        }

        pageView.setChanged();
        pageView.close();
        rowsCount += 1;
        writeHeader();
        return pageIndex * recordsPerPage + recordPosition;
    }


	private void setBit(BufferView bitSetView, int recordPosition) {
		bitSetView.setByte(recordPosition / 8, (byte) (bitSetView.getByte(recordPosition / 8) | (1 << (recordPosition % 8))));
	}


	private boolean checkBit(BufferView bitSetView, int recordPosition) {
		return (bitSetView.getByte(recordPosition / 8) & (1 << (recordPosition % 8))) != 0;
	}
    
    public boolean remove(int recordId) {
        int pageIndex = recordId / recordsPerPage;
        int recordPosition = recordId % recordsPerPage;
        try (BufferView pageView = bufferManager.getPage(pageIndex)) {
            BufferView bitSetView = pageView.getSubView(POINTERS_SIZE, bitSetSize);
            boolean recordAvailable = checkBit(bitSetView, recordPosition);
            boolean recordDeleted = checkBit(bitSetView, recordsPerPage + recordPosition);
            if(!recordDeleted) {
            	setBit(bitSetView, recordsPerPage + recordPosition);
            	bitSetView.setChanged();
            }
            bitSetView.close();
            
            if (!recordAvailable || recordDeleted) {
                return false;
            }
            
            rowsCount -= 1;
            writeHeader();
            return true;
        }
    }

    public BufferView get(int recordId) throws ExecutionException {
        int pageIndex = recordId / recordsPerPage;
        int recordPosition = recordId % recordsPerPage;
        try (BufferView pageView = bufferManager.getPage(pageIndex)) {
            BufferView bitSetView = pageView.getSubView(POINTERS_SIZE, bitSetSize);
            boolean recordAvailable = checkBit(bitSetView, recordPosition);
            boolean recordDeleted = checkBit(bitSetView, recordsPerPage + recordPosition);
            bitSetView.close();
            if (recordAvailable && !recordDeleted) {
                return pageView.getSubView(POINTERS_SIZE + bitSetSize + recordPosition * recordSize, recordSize);
            }
            return null;
        }
    }

    public Iterator<ViewWithId> iterator() {
        return new Iterator<ViewWithId>() {
            private int curPageNotFull = firstNotFullPage;
            private int curPageFull = firstFullPage;
            private int recordPos = 0;
            private int lastRecordId = -1;
            private int currentRecordId = -1;
            private ViewWithId value = getNextView();

            private ViewWithId getNextView() {
                lastRecordId = currentRecordId;
                BufferView pageView;
                boolean found;
                do {
                    found = true;
                    pageView = null;
                    if(curPageNotFull != 0) {
                        pageView = bufferManager.getPage(curPageNotFull);
                    } else {
                        if(curPageFull != 0) {
                            pageView = bufferManager.getPage(curPageFull);
                        }
                    }
                    if(pageView == null) {
                        return null;
                    }
                    BufferView bitSetView = pageView.getSubView(POINTERS_SIZE, bitSetSize);
                    while(recordPos != recordsPerPage) {
                        boolean recordAvailable = checkBit(bitSetView, recordPos);
                        boolean recordDeleted = checkBit(bitSetView, recordsPerPage + recordPos);
                        if(recordAvailable && !recordDeleted) {
                            break;
                        }
                        recordPos += 1;
                    }
                    bitSetView.close();
                    if(recordPos == recordsPerPage) {
                        found = false;
                    } else {
                        int pageIndex = curPageNotFull != 0 ? curPageNotFull : curPageFull;
                        currentRecordId = recordPos + recordsPerPage * pageIndex;
                    }
                    if(!found) {
                        recordPos = 0;
                        int newPage = pageView.getInt(4);
                        if(curPageNotFull != 0) {
                            curPageNotFull = newPage;
                        } else {
                            if(curPageFull != 0) {
                                curPageFull = newPage;
                            }
                        }
                        pageView.close();
                    }
                } while(!found);

                BufferView recordView = pageView.getSubView(POINTERS_SIZE + bitSetSize + recordPos * recordSize, recordSize);
                recordPos += 1;
                pageView.close();
                return new ViewWithId(recordView, currentRecordId);
            }

            @Override
            public boolean hasNext() {
                return value != null;
            }

            @Override
            public ViewWithId next() {
                if(!hasNext()) {
                    throw new IllegalStateException();
                }
                ViewWithId old = value;
                value = getNextView();
                return old;
            }

            @Override
            public void remove() {
                if(lastRecordId == -1) {
                    throw new DBException(new IllegalStateException("Remove from iterator before calling next"));
                }
                TableBase.this.remove(lastRecordId);
                lastRecordId = -1;
            }
        };
    }
}
