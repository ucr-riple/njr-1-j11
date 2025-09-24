package externalSort;

import global.AttrType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import relop.FileScan;
import relop.Iterator;
import relop.Schema;
import relop.Tuple;

public class SortMergeJoin extends Iterator{
Schema schema;
Iterator left;
Iterator right;
ArrayList<Tuple> leftTuples = new ArrayList<Tuple>();
ArrayList<Tuple> rightTuples = new ArrayList<Tuple>();
ArrayList<Tuple> result = new ArrayList<Tuple>();
public java.util.Iterator<Tuple> iter;
	public SortMergeJoin(FileScan fileScan, FileScan fileScan2, final int i, final int j) {
		left = fileScan;
		right = fileScan2;
		schema = Schema.join(left.getSchema(), right.getSchema());
		this.setSchema(schema);
		while(left.hasNext()){
			leftTuples.add(left.getNext());
		}
		while(right.hasNext()){
			rightTuples.add(right.getNext());
		}
		doWork(i,j);
	}
	
public void doWork(final int i, final int j){
	final int ft = schema.fieldType(i);
	
	Collections.sort(leftTuples, new Comparator<Tuple>(){
		@Override
		public int compare(Tuple o1, Tuple o2) {
			if(ft == AttrType.INTEGER){
				return Integer.compare(o1.getIntFld(i), o2.getIntFld(i));
			}
			else if(ft == AttrType.FLOAT){
				return Float.compare(o1.getFloatFld(i), o2.getFloatFld(i));
			}
			else{
				return o1.getStringFld(i).compareTo(o2.getStringFld(i));
			}
		}
	});
	
	Collections.sort(rightTuples, new Comparator<Tuple>(){
		@Override
		public int compare(Tuple o1, Tuple o2) {
			if(ft == AttrType.INTEGER){
				return Integer.compare(o1.getIntFld(j), o2.getIntFld(j));
			}
			else if(ft == AttrType.FLOAT){
				return Float.compare(o1.getFloatFld(j), o2.getFloatFld(j));
			}
			else{
				return o1.getStringFld(j).compareTo(o2.getStringFld(j));
			}
		}
	});
	
	java.util.Iterator<Tuple> leftIter = leftTuples.iterator();
	java.util.Iterator<Tuple> rightIter = rightTuples.iterator();
	Tuple lt = leftIter.next();
	Tuple rt = rightIter.next();
	while(true){
		if(ft == AttrType.INTEGER){
			if(lt.getIntFld(i) == rt.getIntFld(j)){
				result.add(Tuple.join(lt, rt, schema));
				if(!leftIter.hasNext() || !rightIter.hasNext()){
					break;
				}
				lt = leftIter.next();
				rt = rightIter.next();
			}
			else if(lt.getIntFld(i) < rt.getIntFld(j)){
				if(!leftIter.hasNext()){
					break;
				}
				lt = leftIter.next();
			}
			else{
				if(!rightIter.hasNext()){
					break;
				}
				rt = rightIter.next();
			}
		}
		else if(ft == AttrType.FLOAT){
			if(lt.getFloatFld(i) == rt.getFloatFld(j)){
				result.add(Tuple.join(lt, rt, schema));
				if(!leftIter.hasNext() || !rightIter.hasNext()){
					break;
				}
				lt = leftIter.next();
				rt = rightIter.next();
			}
			else if(lt.getFloatFld(i) < rt.getFloatFld(j)){
				if(!leftIter.hasNext()){
					break;
				}
				lt = leftIter.next();
			}
			else{
				if(!rightIter.hasNext()){
					break;
				}
				rt = rightIter.next();
			}
		}
		else if(ft == AttrType.STRING){
			if(lt.getStringFld(i).compareTo(rt.getStringFld(j)) == 0){
				result.add(Tuple.join(lt, rt, schema));
				if(!leftIter.hasNext() || !rightIter.hasNext()){
					break;
				}
				lt = leftIter.next();
				rt = rightIter.next();
			}
			else if(lt.getStringFld(i).compareTo(rt.getStringFld(j)) < 0){
				if(!leftIter.hasNext()){
					break;
				}
				lt = leftIter.next();
			}
			else{
				if(!rightIter.hasNext()){
					break;
				}
				rt = rightIter.next();
			}
		}
		else{
			System.err.println("ERROR!!!");
		}			
	}
	this.setSchema(schema);
	this.iter = result.iterator();
}

	public SortMergeJoin(SortMergeJoin join1, FileScan fileScan2, final int i, final int j) {
		//left = fileScan;
		right = fileScan2;
		schema = Schema.join(join1.getSchema(), right.getSchema());
		while(join1.iter.hasNext()){
			leftTuples.add(join1.iter.next());
		}
		while(right.hasNext()){
			rightTuples.add(right.getNext());
		}
	
		doWork(i,j);
	}

	@Override
	public void explain(int depth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restart() {
		this.iter = result.iterator();
	}

	@Override
	public boolean isOpen() {
		return iter != null;
	}

	@Override
	public void close() {
		iter = null;
	}

	@Override
	public boolean hasNext() {
		return iter.hasNext();
	}

	@Override
	public Tuple getNext() {
		if(this.hasNext()){
			  return iter.next();
		  }
		  else{
			  throw new IllegalStateException();
		  } 
	}

}
