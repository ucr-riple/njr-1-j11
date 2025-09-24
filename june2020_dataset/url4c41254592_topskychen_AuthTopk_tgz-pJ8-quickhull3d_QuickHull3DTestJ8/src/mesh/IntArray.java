package mesh;

import processing.core.*;

public class IntArray {

	int[] data;
	int length;

	public IntArray(){
		this(1);
	}

	public IntArray( int l ){
		data = new int[l];
		length = 0;
	}

	public void add( int d ){
		if( length==data.length ){
//			data = PApplet.expand(data);
			if(data == null || data.length == 0){
				data = new int[1];
			}else{
				int[] tmp = new int[data.length * 2];
				for(int i = 0; i < data.length; i++){
					tmp[i] = data[i];
				}
				data = tmp;
			}
		}
		data[length++] = d;
	}

	public int get( int i ){
		return data[i];
	}

	public boolean contains( int d ){
		for(int i=0; i<length; i++)
			if(data[i]==d)
				return true;
		return false;
	}

}