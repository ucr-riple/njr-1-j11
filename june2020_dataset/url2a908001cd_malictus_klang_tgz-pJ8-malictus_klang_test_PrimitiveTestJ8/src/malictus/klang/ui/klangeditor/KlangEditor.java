/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.ui.klangeditor;

import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.util.*;
import malictus.klang.file.*;
import malictus.klang.*;
import malictus.klang.chunk.*;
import malictus.klang.ui.*;
import malictus.klang.primitives.*;

/**
 * Primary window for the Klang Editor application
 * @author Jim Halliday
 */
public class KlangEditor extends JFrame {
	
	protected JPanel contentPane = null;
	private KlangEditorMenuBar menuBar;
	private boolean hexMode = false;
	private static boolean manualTextMode = false;
	private boolean readOnly = false;
	private KlangFile currentFile = null;
	protected Chunk curChunk = null;
	private KlangEditorFileChooser opener = new KlangEditorFileChooser();
	private JFileChooser saver = new JFileChooser();
	private BasicInfoPanel basic;
	private ChunkInfoPanel pchunk;
	private RawEditPanel redit;
	private TreePanel tree;
	private PrimPanel prim;
	private JSplitPane topHSplit;
	private JSplitPane topVSplit;
	private JPanel bottomPanel;
	private JSplitPane bottomHSplit;
	//current byte will be -1 if no file is open
	protected long currentByte = -1;
	
	/**
	 * Initiate Klang Editor window
	 */
	public KlangEditor() {
		super();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setTitle(KlangConstants.KLANGEDITOR_TITLE + " " + KlangConstants.KLANG_VERSION);
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        this.setContentPane(contentPane);
        menuBar = new KlangEditorMenuBar(this);
        this.setJMenuBar(menuBar);
        //initialize panels and place them where they go
        basic = new BasicInfoPanel(this);
        basic.setPreferredSize(new Dimension(KlangConstants.KLANGEDITOR_BASICPANEL_WIDTH, KlangConstants.KLANGEDITOR_BASICPANEL_HEIGHT));
        basic.setSize(new Dimension(KlangConstants.KLANGEDITOR_BASICPANEL_WIDTH, KlangConstants.KLANGEDITOR_BASICPANEL_HEIGHT));
        pchunk = new ChunkInfoPanel(this);
        pchunk.setPreferredSize(new Dimension(KlangConstants.KLANGEDITOR_CHUNKEDITPANEL_WIDTH, KlangConstants.KLANGEDITOR_CHUNKEDITPANEL_HEIGHT));
        pchunk.setSize(new Dimension(KlangConstants.KLANGEDITOR_CHUNKEDITPANEL_WIDTH, KlangConstants.KLANGEDITOR_CHUNKEDITPANEL_HEIGHT));
        topHSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, basic, pchunk);
        tree = new TreePanel(this);
        redit = new RawEditPanel(this);
        redit.setMaximumSize(new Dimension(10000, (int)redit.getPreferredSize().getHeight()));
        prim = new PrimPanel(this);
        bottomHSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tree, prim);
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(redit, BorderLayout.NORTH);
        bottomPanel.add(bottomHSplit, BorderLayout.CENTER);
        bottomHSplit.setDividerLocation(KlangConstants.KLANGEDITOR_CHUNKTREE_PANELWIDTH);
        topVSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topHSplit, bottomPanel);
        contentPane.add(topVSplit, BorderLayout.CENTER);
        topVSplit.setDividerLocation(KlangConstants.KLANGEDITOR_CHUNKEDITPANEL_HEIGHT);
        this.setSize(KlangConstants.KLANGEDITOR_INITIAL_WIDTH, KlangConstants.KLANGEDITOR_INITIAL_HEIGHT);
        KlangUtil.centerWindow(this);
        this.setVisible(true);
        doFileChange(null);
	}
	
	/**
	 * Set the current klang file. Normally, this should only be called by the 
	 * progress window.
	 * @param kf the new KlangFile object
	 * @param newByte the byte to navigate to, or null if no file or beginning
	 */
	public void setKlangFile(KlangFile kf, Long newByte) {
		this.currentFile = kf;
		doFileChange(newByte);
	}
	
	/**
	 * Open a file for reading/editing
	 */
	protected void openFile() {
		int response = opener.showOpenDialog(this);
		if (response != JFileChooser.CANCEL_OPTION) {
			File x = opener.getSelectedFile();
			KlangProgressWindow prog = new KlangProgressWindow(this);
			prog.openKlangFile(x, this, null);
			if (!(prog.getErrMsg().equals(""))) {
				JOptionPane.showMessageDialog(this, prog.getErrMsg(), KlangConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	/**
	 * Close the currently open file
	 */
	protected void closeFile() {
		this.currentFile = null;
		//reset encoding
		TextEncodingDialog.currentEncoding = "";
		doFileChange(null);
	}
	
	/**
	 * Quit the application. For now, just close any open file and quit the app
	 */
	protected void doQuit() {
		closeFile();
		this.setVisible(false);
		System.exit(0);
	}
	
	/**
	 * Toggle for read only option
	 * @param newSetting true if app should be in read-only mode; false otherwise
	 */
	protected void setReadOnlyOption(boolean newSetting) {
		this.readOnly = newSetting;		
		//update panels that need updating
		if (basic != null) {
			basic.updatePanel();
		}
		if (prim != null) {
			prim.updatePanel(true);
		}
		if (redit != null) {
			redit.updatePanel();
		}
	}
	
	/**
	 * Tell whether the app is currently in read only mode
	 * @return true if read only mode, and false otherwise
	 */
	protected boolean isReadOnlyMode() {
		return this.readOnly;
	}
	
	/**
	 * Toggle for hex/dec option
	 * @param newSetting true if app should display byte locations in hex, false for decimal
	 */
	protected void setHexMode(boolean newSetting) {
		this.hexMode = newSetting;
		//update panels that need updating
		if (basic != null) {
			basic.updatePanel();
		}
		if (pchunk != null) {
			pchunk.updatePanel();
		}
		if (prim != null) {
			prim.updatePanel(true);
		}
		if (redit != null) {
			redit.updatePanel();
		}
	}
	
	/**
	 * Toggle for text encoding warn option
	 * @param newSetting true if app should display a text encoding dialog whenever content is unknown
	 * 		and might be text; if false, app will try to guess instead 
	 */
	protected void setTextMode(boolean manual) {
		KlangEditor.manualTextMode = manual;
	}
	
	/**
	 * Retrieval method for text mode
	 * @return true if app should display a text encoding dialog whenever content is unknown
	 * 		and might be text; if false, app will try to guess instead 
	 */
	public static boolean isManualTextMode() {
		return KlangEditor.manualTextMode;
	}
	
	/**
	 * Tell whether the app is currently in hex mode
	 * @return true if hex mode, and false if decimal mode
	 */
	protected boolean isHexMode() {
		return this.hexMode;
	}

	/**
	 * Retrieve the KlangFile object that is currently open
	 * @return the KlangFile object that is currently open (or null if no object is open)
	 */
	protected KlangFile getCurrentFile() {
		return currentFile;
	}
	
	/**
	 * Retrieve the current chunk
	 * @return the current chunk, or null if no chunk is currently selected
	 */
	protected Chunk getCurrentChunk() {
		return curChunk;
	}
	
	/**
	 * Called anytime the user clicks on a new node in the chunk tree.
	 */
	protected void doTreePanelChange() {
		if (basic != null) {
			basic.updatePanel();
		}
		if (pchunk != null) {
			pchunk.updatePanel();
		}
		if (prim != null) {
			prim.updatePanel(false);
		}
		if (redit != null) {
			redit.updatePanel();
		}
	}
	
	/**
	 * Called whenever the 'export' button is pressed for exporting the current chunk
	 */
	protected void exportCurrentChunk() {
		if (curChunk == null) {
			return;
		}
		//suggest an appropriate file name
		String suggest = getCurrentFile().getFile().getPath();
		suggest = suggest.substring(0, suggest.length() - 4);
		suggest = suggest + "_" + curChunk.getChunkName() + "_" + "chunk.dat";
		File suggestFile = new File(suggest);
		saver.setSelectedFile(suggestFile);
		int response = saver.showSaveDialog(this);
		if (response != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File x = saver.getSelectedFile();
		if (x.exists()) {
			int response2 = JOptionPane.showConfirmDialog(this, KlangConstants.KLANGEDITOR_WARNING_FILE_EXISTS, KlangConstants.KLANGEDITOR_WARNING_FILE_EXISTS_TITLE, JOptionPane.OK_CANCEL_OPTION);
			if (response2 != JOptionPane.OK_OPTION) {
				return;
			}
		}	
		KlangProgressWindow prog = new KlangProgressWindow(this);
		prog.exportChunk(currentFile, curChunk, x);
		if (!(prog.getErrMsg().equals(""))) {
			JOptionPane.showMessageDialog(this, prog.getErrMsg(), KlangConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Export raw bytes from the currently open file
	 * @param start start byte position in the file
	 * @param end end byte position in the file
	 * @param toFile the file to write the bytes to
	 */
	protected void exportBytes(long start, long end, File toFile) {
		KlangProgressWindow prog = new KlangProgressWindow(this);
		prog.exportBytes(start, end, toFile, this.currentFile.getFile());
		if (!(prog.getErrMsg().equals(""))) {
			JOptionPane.showMessageDialog(this, prog.getErrMsg(), KlangConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Insert an entire file into the current file
	 * @param pos byte position to begin inserting the file
	 * @param fromFile the file to insert
	 */
	protected void insertFile(long pos, File fromFile) {
		if (this.isReadOnlyMode()) {
			//shouldn't happen, but double-check here
			return;
		}
		Long cur = new Long(this.currentByte);
		KlangProgressWindow prog = new KlangProgressWindow(this);
		prog.insertFile(pos, fromFile, this.currentFile.getFile());
		if (!(prog.getErrMsg().equals(""))) {
			JOptionPane.showMessageDialog(this, prog.getErrMsg(), KlangConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
		}
		//reopen current file
		prog = new KlangProgressWindow(this);
		prog.openKlangFile(this.getCurrentFile().getFile(), this, cur);
		if (!(prog.getErrMsg().equals(""))) {
			JOptionPane.showMessageDialog(this, prog.getErrMsg(), KlangConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Insert empty bytes at the given byte position
	 * @param pos the byte position to insert bytes
	 * @param numbytes number of empty (zero value) bytes to add
	 */
	protected void insertBytes(long pos, long numbytes) {
		if (this.isReadOnlyMode()) {
			//shouldn't happen, but double-check here
			return;
		}
		Long cur = new Long(this.currentByte);
		KlangProgressWindow prog = new KlangProgressWindow(this);
		prog.insertBytes(pos, numbytes, this.currentFile.getFile());
		if (!(prog.getErrMsg().equals(""))) {
			JOptionPane.showMessageDialog(this, prog.getErrMsg(), KlangConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
		}
		//reopen current file
		prog = new KlangProgressWindow(this);
		prog.openKlangFile(this.getCurrentFile().getFile(), this, cur);
		if (!(prog.getErrMsg().equals(""))) {
			JOptionPane.showMessageDialog(this, prog.getErrMsg(), KlangConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Delete raw bytes (without attempting to reconcile chunks)
	 * @param start the start byte position to delete
	 * @param end the end byte position to delete
	 */
	protected void deleteBytes(long start, long end) {
		if (this.isReadOnlyMode()) {
			//shouldn't happen, but double-check here
			return;
		}
		Long cur = new Long(this.currentByte);
		if (cur > end) {
			cur = cur - (end - start);
		} else if (cur > start) {
			cur = start;
		}
		if (cur < 0) {
			cur = 0L;
		}
		KlangProgressWindow prog = new KlangProgressWindow(this);
		prog.deleteBytes(start, end, this.currentFile.getFile());
		if (!(prog.getErrMsg().equals(""))) {
			JOptionPane.showMessageDialog(this, prog.getErrMsg(), KlangConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
		}
		//reopen current file
		prog = new KlangProgressWindow(this);
		prog.openKlangFile(this.getCurrentFile().getFile(), this, cur);
		if (!(prog.getErrMsg().equals(""))) {
			JOptionPane.showMessageDialog(this, prog.getErrMsg(), KlangConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Called whenever the 'delete' button is pressed for deleting the current chunk.
	 */
	protected void deleteCurrentChunk() {
		int response = JOptionPane.showConfirmDialog(this, KlangConstants.KLANGEDITOR_REALLY_DELETE, KlangConstants.KLANGEDITOR_REALLY_DELETE_TITLE, JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
		if (response != JOptionPane.OK_OPTION) {
			return;
		}
		if (this.isReadOnlyMode()) {
			return;
		}
		Long cur = new Long(this.currentByte);
		KlangProgressWindow prog = new KlangProgressWindow(this);
		prog.deleteChunk(curChunk);
		if (!(prog.getErrMsg().equals(""))) {
			JOptionPane.showMessageDialog(this, prog.getErrMsg(), KlangConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
		}
		//reopen current file
		prog = new KlangProgressWindow(this);
		prog.openKlangFile(this.getCurrentFile().getFile(), this, cur);
		if (!(prog.getErrMsg().equals(""))) {
			JOptionPane.showMessageDialog(this, prog.getErrMsg(), KlangConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Add a new chunk. This will add a new chunk near the current chunk (either before, after, or beneath the current chunk), depending
	 * on the user's choices and the available options.
	 */
	protected void addNewChunk() {
		if (this.isReadOnlyMode()) {
			return;
		}
		AddNewChunkDialog anc = new AddNewChunkDialog(this, this.getCurrentChunk());
		if (anc.wasCanceled()) {
			return;
		}
		Long cur = new Long(this.currentByte);
		KlangProgressWindow prog = new KlangProgressWindow(this);
		File fromFile = null;
		if (anc.bUseFile.isSelected()) {
			String fromFileString = anc.pnlFile.getSelectedFilename();
			fromFile = new File(fromFileString);
			if ((!fromFile.exists()) || (!fromFile.isFile())) {
				JOptionPane.showMessageDialog(this,KlangConstants.ERROR_FILE_IS_INCORRECT_TYPE, KlangConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		try {
			if (anc.bUnder.isSelected()) {
				prog.addChunk(this.currentFile, curChunk, anc.txtfChunkName.getText(), 1, fromFile);
				if (!(prog.getErrMsg().equals(""))) {
					JOptionPane.showMessageDialog(this, prog.getErrMsg(), KlangConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
				}
			} else if (anc.bAfter.isSelected()) {
				prog.addChunk(this.currentFile, curChunk.getParentChunk(), anc.txtfChunkName.getText(), this.getCurrentChunkPosition() + 1, fromFile);
				if (!(prog.getErrMsg().equals(""))) {
					JOptionPane.showMessageDialog(this, prog.getErrMsg(), KlangConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
				}
			} else {
				prog.addChunk(this.currentFile, curChunk.getParentChunk(), anc.txtfChunkName.getText(), this.getCurrentChunkPosition(), fromFile);
				if (!(prog.getErrMsg().equals(""))) {
					JOptionPane.showMessageDialog(this, prog.getErrMsg(), KlangConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
				}
			}
		} catch (Exception err) {
			JOptionPane.showMessageDialog(this, err.getMessage(), KlangConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
		}
		//reopen current file
		prog = new KlangProgressWindow(this);
		prog.openKlangFile(this.getCurrentFile().getFile(), this, cur);
		if (!(prog.getErrMsg().equals(""))) {
			JOptionPane.showMessageDialog(this, prog.getErrMsg(), KlangConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Reparse the current chunk, then reload the entire file. This should be called
	 * when the data in a chunk has been edited and the changes need to be written
	 * out to file.
	 */
	protected void reparseCurrentChunk() {
		Long cur = new Long(this.currentByte);
		KlangProgressWindow prog = new KlangProgressWindow(this);
		prog.reparseChunk(curChunk);
		if (!(prog.getErrMsg().equals(""))) {
			JOptionPane.showMessageDialog(this, prog.getErrMsg(), KlangConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
			return;
		}
		reparseEntireFile(cur);
	}
	
	/**
	 * Reopen the currently open file. Called after changes have been made to the file
	 * @param cur the new byte to navigate to when the file is reopened
	 */
	protected void reparseEntireFile(long cur) {
		KlangProgressWindow prog = new KlangProgressWindow(this);
		prog.openKlangFile(this.getCurrentFile().getFile(), this, cur);
		if (!(prog.getErrMsg().equals(""))) {
			JOptionPane.showMessageDialog(this, prog.getErrMsg(), KlangConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Called whenever the 'edit chunk name' button is pressed for editing the current chunk's name.
	 */
	protected void editCurrentChunkName() {
		if (this.isReadOnlyMode()) {
			return;
		}
		if (this.getCurrentChunk() instanceof ContainerChunk) {
			JOptionPane.showMessageDialog(this, KlangConstants.KLANGEDITOR_CONTAINER_NAME_WARN, KlangConstants.KLANGEDITOR_CONTAINER_NAME_WARN_TITLE, JOptionPane.WARNING_MESSAGE);
		}
		String newname = (String)JOptionPane.showInputDialog(this, KlangConstants.KLANGEDITOR_CHUNKNAME_PROMPT, 
				KlangConstants.KLANGEDITOR_CHUNKNAME_PROMPT_TITLE, JOptionPane.PLAIN_MESSAGE, null,
				null, this.getCurrentChunk().getChunkName());
		if (newname == null) {
			return;
		}
		if (newname.length() <= 0) {
			return;
		}   
		Long cur = new Long(this.currentByte);
		KlangProgressWindow prog = new KlangProgressWindow(this);
		prog.editChunkName(curChunk, newname);
		if (!(prog.getErrMsg().equals(""))) {
			JOptionPane.showMessageDialog(this, prog.getErrMsg(), KlangConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
		}
		//reopen current file
		prog = new KlangProgressWindow(this);
		prog.openKlangFile(this.getCurrentFile().getFile(), this, cur);
		if (!(prog.getErrMsg().equals(""))) {
			JOptionPane.showMessageDialog(this, prog.getErrMsg(), KlangConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Called anytime the 'jump to' button is pressed from the primitive edit panel. Moves
	 * the current byte position to this primitive's byte position.
	 * @param prim the primitive to jump to
	 */
	protected void jumpToPrim(PrimitiveData prim) {
		if (prim.getStartBytePosition() == null) {
			return;
		}
		if (prim.getStartBytePosition() < 0) {
			return;
		}
		this.currentByte = prim.getStartBytePosition();
		this.redit.updatePanel(prim.getPrimitive().getClass().getCanonicalName());
	}
	
	/**
	 * Called by the RawEditPanel when the jump button is pressed
	 * @param newbyte the new byte to jump to
	 */
	protected void jumpToByte(long newbyte) {
		this.currentByte = newbyte;
		try {
			this.curChunk = KlangFile.findDeepestChunkFor(getCurrentFile().getChunks(), currentByte);
		} catch (Exception err) {
			err.printStackTrace();
			this.curChunk = null;
		}
		if (redit != null) {
			this.redit.updatePanel();
		}
		if (tree != null) {
			tree.updatePanelToNewByte();
		}
		if (pchunk != null) {
			pchunk.updatePanel();
		}
		if (prim != null) {
			prim.updatePanel(false);
		}
	}
	
	/**
	 * Called anytime the entire user interface should be regenerated from scratch
	 * because a new file is being opened or closed, or the current file changed. 
	 * Resets everything.
	 * 
	 * @param byteToOpen the byte to navigate to once the file if opened; if set to null,
	 * and a file is open, the file will default to opening at the beginning
	 */
	private void doFileChange(Long byteToOpen) {
		if ((this.currentFile == null) || (this.currentFile.getFile().length() == 0)) {
			byteToOpen = new Long(-1);
		} else {
			if (byteToOpen == null) {
				byteToOpen = new Long(0);
			} else if (this.currentFile.getFile().length() <= (byteToOpen + 1)) {
				byteToOpen = new Long(this.currentFile.getFile().length() - 2);
			}
		}
		final Long BYTETOOPEN = byteToOpen;
		Runnable updateTheUI = new Runnable() {
		     public void run() {
		    	//update all panels
		 		if (basic != null) {
		 			basic.updatePanel();
		 		}
		 		if (tree != null) {
		 			tree.updatePanel();
		 		}
		 		if (pchunk != null) {
		 			pchunk.updatePanel();
		 		}
		 		if (prim != null) {
		 			prim.updatePanel(true);
		 		}
		 		if (redit != null) {
		 			redit.updatePanel();
		 		}
		 		//update menus and title
		 		if (currentFile == null) {
		 			setTitle(KlangConstants.KLANGEDITOR_TITLE + " " + KlangConstants.KLANG_VERSION);
		 			menuBar.setFileIsOpen(false);
		 		} else {
		 			setTitle(KlangConstants.KLANGEDITOR_TITLE + " " + KlangConstants.KLANG_VERSION + " - " + currentFile.getFile().getName());
		 			menuBar.setFileIsOpen(true);
		 		}
		 		if (currentFile != null) {
		 			jumpToByte(BYTETOOPEN.longValue());
		 		}
		     }
		 };
		 SwingUtilities.invokeLater(updateTheUI);
	}
	
	/**
	 * Return the current chunk's position within its parent (chunk or file). Note that the
	 * first chunk's position is '1'
	 * @return the current chunk's position within its parent (chunk or file)
	 * @throws Exception if error occurs
	 */
	private int getCurrentChunkPosition() throws Exception {
		ContainerChunk cc = this.curChunk.getParentChunk();
		if (cc != null) {
			Vector<Chunk> vec = cc.getSubChunks();
			int counter = 0;
			while (counter < vec.size()) {
				Chunk x = vec.get(counter);
				if (x.equals(curChunk)) {
					return counter + 1;
				}
				counter = counter + 1;
			}
			throw new Exception();
		} else {
			Vector<Chunk> vec = this.currentFile.getChunks();
			int counter = 0;
			while (counter < vec.size()) {
				Chunk x = vec.get(counter);
				if (x.equals(curChunk)) {
					return counter + 1;
				}
				counter = counter + 1;
			}
			throw new Exception();
		}
	}

}
