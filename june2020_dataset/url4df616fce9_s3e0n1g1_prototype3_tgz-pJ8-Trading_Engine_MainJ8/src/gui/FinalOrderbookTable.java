package gui;

import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;

public class FinalOrderbookTable extends AbstractTableModel {

	private String[] columnNames =  {"BidID", "AskId", "Price", "Volume", "Timestamp"};
	private LinkedList<Object[]> data2;
	
	 public FinalOrderbookTable() {
	        data2 = new LinkedList<Object[]>();
	 }
	
	public boolean isCellEditable(int row, int col) {
		return false;
	}
	
    public int getColumnCount() {
        return columnNames.length;
      
    }

	public int getRowCount() {
		return data2.size();
	}
	public String getColumnName(int col){
		return columnNames[col];
	}
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data2.get(rowIndex)[columnIndex];
	}
	
	public void setValueAt(Object value, int row, int col) {
		data2.get(row)[col] = value;
	    fireTableCellUpdated(row, col);
	}

	public void setData(LinkedList<Object[]> fakedata) {
		data2 = fakedata;
		fireTableDataChanged();

	}
	   public void addElement(Object[] e) {
	        data2.add(e);
	        fireTableRowsInserted(data2.size()-1, data2.size()-1);
	    }

}
