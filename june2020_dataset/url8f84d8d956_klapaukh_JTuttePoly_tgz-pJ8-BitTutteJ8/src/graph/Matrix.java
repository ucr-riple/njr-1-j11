package graph;

public class Matrix {
	private final int[][] data;
	private final int nRows;
	private final int nCols;
	
	public Matrix(int rows, int cols) {
		data = new int[rows][cols];
		nRows = rows;
		nCols = cols;
	}
	
	public int get(int row, int col) {
		return data[row][col];
	}
	
	public void set(int value, int row, int col) {
		data[row][col] = value;
	}
	
	public boolean equals(Object o) {
		if(o instanceof Matrix) {
			Matrix m = (Matrix) o;
			if(m.nRows != nRows || m.nCols != nCols) {
				return false;
			}
			for(int i=0;i!=nRows;i++) {
				for(int j=0;j!=nCols;j++) {
					if(data[i][j] != m.data[i][j]) {
						return false;
					}
				}	
			}
			return true;
		}
		return false;
	}
}
