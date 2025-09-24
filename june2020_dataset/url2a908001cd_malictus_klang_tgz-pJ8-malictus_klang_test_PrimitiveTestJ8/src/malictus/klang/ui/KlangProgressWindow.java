/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.ui;

import javax.swing.*;
import java.io.*;
import java.util.*;
import malictus.klang.chunk.*;
import malictus.klang.file.*;
import malictus.klang.ui.klangeditor.*;
import malictus.klang.*;

/**
 * A progress window to monitor tasks which take a long time to complete. Each task
 * that this window can control is assigned a different method. A static text field and a 
 * static integer value are used to control the progress window's text and progress bar.
 * These values are static and can be changed from anywhere within the code.
 */
public class KlangProgressWindow extends JDialog {

	private JPanel jContentPane = null;
	private JLabel lblProg = null;
	private JProgressBar prgProg = null;

	private boolean finished = false;
	private String errMsg = "";
	
	public static String currStatus = "";
	public static final int PROG_MAX = 100;
	public static int progVal = 0;

	private java.util.Timer theTimer = new java.util.Timer();

	/**
	 * Initialize the Progress Window.
	 * @param parent parent window to this dialog
	 */
	public KlangProgressWindow(java.awt.Window parent) {
		super();
        this.setSize(new java.awt.Dimension(KlangConstants.KLANGEDITOR_PROGRESSWINDOW_WIDTH, KlangConstants.KLANGEDITOR_PROGRESSWINDOW_HEIGHT));
        if (parent != null) {
        	//center over parent window
        	this.setLocation(parent.getX() + (parent.getWidth() / 2) - (this.getWidth() / 2),
        			parent.getY() + (parent.getHeight() / 2) - (this.getHeight() / 2));
        }
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setResizable(false);
        lblProg = new JLabel();
		lblProg.setBounds(new java.awt.Rectangle(7,5,300,16));
		lblProg.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
		lblProg.setText("");
		prgProg = new JProgressBar();
		prgProg.setMinimum(0);
		prgProg.setMaximum(PROG_MAX);
		prgProg.setValue(0);
		prgProg.setBounds(new java.awt.Rectangle(7,28,355,23));
		jContentPane = new JPanel();
		jContentPane.setLayout(null);
		jContentPane.add(lblProg, null);
		jContentPane.add(prgProg, null);
		this.setContentPane(jContentPane);
	}
	
	/**
	 * Return any error message that might have been generated while a backgroun process
	 * was occuring
	 * @return a string describing an error message, or an empty string if no error message
	 */
	public String getErrMsg() {
		return errMsg;
	}
	
	/**
	 * Edit a chunk name
	 * @param chunk the chunk to rename
	 * @param newname the new name
	 */
	public void editChunkName(Chunk chunk, String newname) {
		finished = false;
		errMsg = "";
		ProgressTask lTask = new ProgressTask(theTimer);
        theTimer.schedule(lTask, 0, 200);
        this.prgProg.setVisible(true);
        this.lblProg.setVisible(true);
        KlangProgressWindow.currStatus = "Changing chunk name...";
        KlangProgressWindow.progVal = 0;
        final String NEWNAME = newname;
        final Chunk CHUNK = chunk;
        Runnable q = new Runnable() {
            public void run() {
            	RandomAccessFile raf = null;
		        try {
		        	raf = new RandomAccessFile(CHUNK.getParentFile().getFile(), "rw");
		        	CHUNK.renameChunk(NEWNAME, raf);
		        	finished = true;
				} catch (Exception err) {
					if (raf != null) {
						try {
							raf.close();
						} catch (Exception e) {}
					}
					err.printStackTrace();
					finished = true;
					errMsg = err.getMessage();
					return;
				}
            }
        };
        Thread t = new Thread(q);
        t.start();
        this.setVisible(true);
	}
	
	/**
	 * Export bytes from one file to another. The new file should exist and be empty before this method is called.
	 * @param start start byte position
	 * @param end end byte position
	 * @param toFile the file to write to; should exist and have no content before this method is called
	 * @param fromFile the file to write from; start and end byte positions shoudl already have been checked when this method is called
	 */
	public void exportBytes(long start, long end, File toFile, File fromFile) {
		finished = false;
		errMsg = "";
		ProgressTask lTask = new ProgressTask(theTimer);
        theTimer.schedule(lTask, 0, 200);
        this.prgProg.setVisible(true);
        this.lblProg.setVisible(true);
        KlangProgressWindow.currStatus = "Exporting Bytes...";
        KlangProgressWindow.progVal = 0;
        final File TOFILE = toFile;
        final File FROMFILE = fromFile;
        final long START = start;
        final long END = end;
        Runnable q = new Runnable() {
            public void run() {
		        try {
		        	KlangUtil.writeToFile(FROMFILE, TOFILE, START, END);
		        	finished = true;
				} catch (Exception err) {
					err.printStackTrace();
					finished = true;
					errMsg = err.getMessage();
					return;
				}
            }
        };
        Thread t = new Thread(q);
        t.start();
        this.setVisible(true);
	}
	
	/**
	 * Export the specified chunk.
	 * @param currentFile the KlangFile object to export from
	 * @param chunk the chunk to export
	 * @param target the file to export to (will overwrite any existing file)
	 */
	public void exportChunk(KlangFile currentFile, Chunk chunk, File target) {
		finished = false;
		errMsg = "";
		ProgressTask lTask = new ProgressTask(theTimer);
        theTimer.schedule(lTask, 0, 200);
        this.prgProg.setVisible(true);
        this.lblProg.setVisible(true);
        KlangProgressWindow.currStatus = "Exporting...";
        KlangProgressWindow.progVal = 0;
        final File TARGET = target;
        final KlangFile CURFILE = currentFile;
        final Chunk CHUNK = chunk;
        Runnable q = new Runnable() {
            public void run() {
		        try {
		        	CURFILE.exportChunk(CHUNK, TARGET);
		        	finished = true;
				} catch (Exception err) {
					err.printStackTrace();
					finished = true;
					errMsg = err.getMessage();
					return;
				}
            }
        };
        Thread t = new Thread(q);
        t.start();
        this.setVisible(true);
	}
	
	/**
	 * Add the specified chunk.
	 * @param theFile the KlangFile object that the chunk should be added to
	 * @param parentChunk the parent chunk (that the new chunk will be added under); 
	 * 		will be null if new chunk should be at top level
	 * @param newChunkName the name for the new chunk
	 * @param position the position of the new chunk (1 = first child)
	 * @param fromFile this file will be read in its entirety and inserted into the chunk as data;
	 * 		will be null if a new empty chunk should be added instead
	 */
	public void addChunk(KlangFile theFile, Chunk parentChunk, String newChunkName, int position, File fromFile) {
		finished = false;
		errMsg = "";
		ProgressTask lTask = new ProgressTask(theTimer);
        theTimer.schedule(lTask, 0, 200);
        this.prgProg.setVisible(true);
        this.lblProg.setVisible(true);
        KlangProgressWindow.currStatus = "Adding Chunk...";
        KlangProgressWindow.progVal = 0;
        final KlangFile THEFILE = theFile;
        final Chunk PARENTCHUNK = parentChunk;
        final String NEWCHUNKNAME = newChunkName;
        final Integer POSITION = position;
        final File FROMFILE = fromFile;
        Runnable q = new Runnable() {
            public void run() {
            	RandomAccessFile raf = null;
		        try {
		        	raf = new RandomAccessFile(THEFILE.getFile(), "rw");
		        	if (PARENTCHUNK != null) {
		        		if (FROMFILE != null) {
		        			((EditableContainerChunk)PARENTCHUNK).addChunkFromFile(FROMFILE, NEWCHUNKNAME, POSITION, raf);
		        		} else {
		        			((EditableContainerChunk)PARENTCHUNK).addChunkFromArray(new byte[0], NEWCHUNKNAME, POSITION, raf);
		        		}
		        	} else {
		        		EditableFileBase efb = (EditableFileBase)THEFILE;
		        		if (FROMFILE != null) {
		        			efb.addChunkFromFile(FROMFILE, NEWCHUNKNAME, POSITION, raf);
		        		} else {
		        			efb.addChunkFromArray(new byte[0], NEWCHUNKNAME, POSITION, raf);
		        		}
		        	}
		        	raf.close();
		        	finished = true;
				} catch (Exception err) {
					if (raf != null) {
						try {
							raf.close();
						} catch (Exception errr) {}
					}
					err.printStackTrace();
					finished = true;
					errMsg = err.getMessage();
					return;
				}
            }
        };
        Thread t = new Thread(q);
        t.start();
        this.setVisible(true);
	}
	
	/**
	 * Delete the specified chunk.
	 * @param chunk the chunk to delete
	 */
	public void deleteChunk(Chunk chunk) {
		finished = false;
		errMsg = "";
		ProgressTask lTask = new ProgressTask(theTimer);
        theTimer.schedule(lTask, 0, 200);
        this.prgProg.setVisible(true);
        this.lblProg.setVisible(true);
        KlangProgressWindow.currStatus = "Deleting Chunk...";
        KlangProgressWindow.progVal = 0;
        final Chunk CHUNK = chunk;
        Runnable q = new Runnable() {
            public void run() {
            	RandomAccessFile raf = null;
		        try {
		        	if (!(CHUNK.chunkCanBeDeleted())) {
		        		throw new IOException(KlangConstants.ERROR_CHUNK_ISNT_EDITABLE);
		        	}
		        	raf = new RandomAccessFile(CHUNK.getParentFile().getFile(), "rw");
		        	CHUNK.deleteChunk(raf);
		        	raf.close();
		        	finished = true;
				} catch (Exception err) {
					if (raf != null) {
						try {
							raf.close();
						} catch (Exception errr) {}
					}
					err.printStackTrace();
					finished = true;
					if (err.getMessage() != null) {
						errMsg = err.getMessage();
					} else {
						errMsg = KlangConstants.ERROR_GENERAL_FILE;
					}
					return;
				}
            }
        };
        Thread t = new Thread(q);
        t.start();
        this.setVisible(true);
	}
	
	/**
	 * Insert empty bytes into a file
	 * @param pos byte position to begin the insert
	 * @param numbytes the number of empty bytes to insert
	 * @param toFile the file to add bytes to
	 */
	public void insertBytes(long pos, long numbytes, File toFile) {
		finished = false;
		errMsg = "";
		ProgressTask lTask = new ProgressTask(theTimer);
        theTimer.schedule(lTask, 0, 200);
        this.prgProg.setVisible(true);
        this.lblProg.setVisible(true);
        KlangProgressWindow.currStatus = "Inserting Bytes...";
        KlangProgressWindow.progVal = 0;
        final long POS = pos;
        final long NUMBYTES = numbytes;
        final File TOFILE = toFile;
        Runnable q = new Runnable() {
            public void run() {
		        try {
		        	KlangUtil.insertBlankBytes(POS, NUMBYTES, TOFILE);
		        	finished = true;
				} catch (Exception err) {
					err.printStackTrace();
					finished = true;
					if (err.getMessage() != null) {
						errMsg = err.getMessage();
					} else {
						errMsg = KlangConstants.ERROR_GENERAL_FILE;
					}
					return;
				}
            }
        };
        Thread t = new Thread(q);
        t.start();
        this.setVisible(true);
	}
	
	/**
	 * Insert one file into another
	 * @param pos byte position to begin the insert
	 * @param fromFile the file to add bytes from
	 * @param toFile the file to add bytes to
	 */
	public void insertFile(long pos, File fromFile, File toFile) {
		finished = false;
		errMsg = "";
		ProgressTask lTask = new ProgressTask(theTimer);
        theTimer.schedule(lTask, 0, 200);
        this.prgProg.setVisible(true);
        this.lblProg.setVisible(true);
        KlangProgressWindow.currStatus = "Inserting File...";
        KlangProgressWindow.progVal = 0;
        final long POS = pos;
        final File FROMFILE = fromFile;
        final File TOFILE = toFile;
        Runnable q = new Runnable() {
            public void run() {
		        try {
		        	KlangUtil.insertIntoFile(FROMFILE, TOFILE, POS);
		        	finished = true;
				} catch (Exception err) {
					err.printStackTrace();
					finished = true;
					if (err.getMessage() != null) {
						errMsg = err.getMessage();
					} else {
						errMsg = KlangConstants.ERROR_GENERAL_FILE;
					}
					return;
				}
            }
        };
        Thread t = new Thread(q);
        t.start();
        this.setVisible(true);
	}
	
	/**
	 * Delete the specified bytes
	 * @param start start byte position
	 * @param end end byte position
	 * @param theFile the file to delete bytes from
	 */
	public void deleteBytes(long start, long end, File theFile) {
		finished = false;
		errMsg = "";
		ProgressTask lTask = new ProgressTask(theTimer);
        theTimer.schedule(lTask, 0, 200);
        this.prgProg.setVisible(true);
        this.lblProg.setVisible(true);
        KlangProgressWindow.currStatus = "Deleting Bytes...";
        KlangProgressWindow.progVal = 0;
        final long START = start;
        final long END = end;
        final File THEFILE = theFile;
        Runnable q = new Runnable() {
            public void run() {
		        try {
		        	KlangUtil.deleteFromFile(THEFILE, START, END);
		        	finished = true;
				} catch (Exception err) {
					err.printStackTrace();
					finished = true;
					if (err.getMessage() != null) {
						errMsg = err.getMessage();
					} else {
						errMsg = KlangConstants.ERROR_GENERAL_FILE;
					}
					return;
				}
            }
        };
        Thread t = new Thread(q);
        t.start();
        this.setVisible(true);
	}
	
	/**
	 * Reparse the specified chunk (which also rebuilds the entire file).
	 * @param chunk the chunk to reparse
	 */
	public void reparseChunk(Chunk chunk) {
		finished = false;
		errMsg = "";
		ProgressTask lTask = new ProgressTask(theTimer);
        theTimer.schedule(lTask, 0, 200);
        this.prgProg.setVisible(true);
        this.lblProg.setVisible(true);
        KlangProgressWindow.currStatus = "Reparsing Chunk...";
        KlangProgressWindow.progVal = 0;
        final Chunk CHUNK = chunk;
        Runnable q = new Runnable() {
            public void run() {
            	RandomAccessFile raf = null;
		        try {
		        	raf = new RandomAccessFile(CHUNK.getParentFile().getFile(), "rw");
		        	CHUNK.reparseChunkPrimitives(raf);
		        	raf.close();
		        	finished = true;
				} catch (Exception err) {
					if (raf != null) {
						try {
							raf.close();
						} catch (Exception errr) {}
					}
					err.printStackTrace();
					finished = true;
					if (err.getMessage() != null) {
						errMsg = err.getMessage();
					} else {
						errMsg = KlangConstants.ERROR_GENERAL_FILE;
					}
					return;
				}
            }
        };
        Thread t = new Thread(q);
        t.start();
        this.setVisible(true);
	}
	
	/**
	 * Generate a checksum for the given chunk. Will also generate a data checksum if 
	 * necessary
	 * @param chunk the chunk that should be used to generate a checksum
	 */
	public void calculateChecksumFor(Chunk chunk) {
		finished = false;
		errMsg = "";
		ProgressTask lTask = new ProgressTask(theTimer);
        theTimer.schedule(lTask, 0, 200);
        this.prgProg.setVisible(true);
        this.lblProg.setVisible(true);
        KlangProgressWindow.currStatus = "Generating Checksum...";
        final Chunk CHUNK = chunk;
		Runnable q = new Runnable() {
            public void run() {
            	try {
            		CHUNK.calculateChecksum();
					if (!CHUNK.chunkIsAllData()) {
						KlangProgressWindow.currStatus = "Generating Data Checksum...";
						CHUNK.calculateDataChecksum();
					}
					finished = true;
				} catch (Exception err) {
					err.printStackTrace();
					finished = true;
					errMsg = err.getMessage();
					return;
				}
            }
		};
		Thread t = new Thread(q);
        t.start();
        this.setVisible(true);
	}
	
	/**
	 * Generate a checksum for a complete file. 
	 * @param file the file that should be used to generate a checksum
	 */
	public void calculateChecksumFor(KlangFile file) {
		finished = false;
		errMsg = "";
		ProgressTask lTask = new ProgressTask(theTimer);
        theTimer.schedule(lTask, 0, 200);
        this.prgProg.setVisible(true);
        this.lblProg.setVisible(true);
        KlangProgressWindow.currStatus = "Generating Checksum...";
        final KlangFile FILE = file;
		Runnable q = new Runnable() {
            public void run() {
            	try {
            		FILE.calculateChecksum();
					finished = true;
				} catch (Exception err) {
					err.printStackTrace();
					finished = true;
					errMsg = err.getMessage();
					return;
				}
            }
		};
		Thread t = new Thread(q);
        t.start();
        this.setVisible(true);
	}
	
	/**
	 * Open a file and initialize it as a KlangFile object.
	 * @param file the file object to initialize
	 * @param parent the KlangEditor window that called this
	 * @param newByte the byte to navigate to, or null if no file or beginning
	 */
	public void openKlangFile(File file, KlangEditor parent, Long newByte) {
		finished = false;
		errMsg = "";
		ProgressTask lTask = new ProgressTask(theTimer);
        theTimer.schedule(lTask, 0, 200);
        this.prgProg.setVisible(false);
        this.lblProg.setVisible(true);
        KlangProgressWindow.currStatus = "Opening File...";
        final File FILE = file;
        final KlangEditor PARENT = parent;
        final Long NEWBYTE = newByte;
		Runnable q = new Runnable() {
            public void run() {
            	RandomAccessFile raf = null;
            	try {
            		raf = new RandomAccessFile(FILE, "r");
    				KlangFile kf = KlangFileFactory.makeNewKlangFile(FILE, raf);
    				PARENT.setKlangFile(kf, NEWBYTE);
    				raf.close();
    				finished = true;
				} catch (Exception err) {
					try {
						raf.close();
					} catch (Exception er) {}
					err.printStackTrace();
					finished = true;
					errMsg = err.getMessage();
					return;
				}
            }
		};
        Thread t = new Thread(q);
        t.start();
        this.setVisible(true);
	}

	/**
	 * This class monitors for updates to the window text and progress bar, and closes the window
	 * when the task is finished.
	 */
	private class ProgressTask extends TimerTask {

        public ProgressTask(java.util.Timer aTimer) {
            super();
        }

        public void run() {
            if (finished) {
            	theTimer.cancel();
            	setVisible(false);
            }
            if (!(currStatus.equals(lblProg.getText()))) {
            	lblProg.setText(currStatus);
            }
            if (progVal != prgProg.getValue()) {
            	prgProg.setValue(progVal);
            }
        }
	}
}
