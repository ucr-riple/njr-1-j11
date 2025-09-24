package org.ejml.example;
import org.apache.commons.lang.ArrayUtils;
import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;
import org.ejml.ops.MatrixIO;
import org.ejml.simple.SimpleMatrix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Compression_GPS {

    private static final double T = 1.0;
    private static String basePath = "/Users/orangemr/Desktop/GPS Collect/";
	private static String inputFileName=basePath+"JavaTest.csv";
	public static String lineSep=System.getProperty("line.separator");

    List<KalmanFilterOps> filters = new ArrayList<KalmanFilterOps>();

    public void run() throws IOException {
    	//generate matrix from csv file
    	ArrayList<ArrayList<String>> message=CSVReader.readFile(inputFileName);
    	double [] numLat = new double[message.get(0).size()];
    	double [] numLon = new double[message.get(1).size()];
    	
    	for(int i=0;i<message.get(0).size();i++){
    	
     	Double aa=Double.parseDouble(message.get(0).get(i));
     	Double bb=Double.parseDouble(message.get(1).get(i));
     	numLat[i]=aa; 
     	numLon[i]=bb;  
     	//System.out.println(aa);
    	}   	
    	
  	
    	double [] all = ArrayUtils.addAll(numLat,numLon);
    	//begin KF
    	DenseMatrix64F priorX = new DenseMatrix64F(4,1, true, numLat[0], 0, numLon[0], 0);//a column with 9 numbers
        DenseMatrix64F priorP = CommonOps.identity(4);// a matrix with diag are 1, others are 0
        DenseMatrix64F trueX = new DenseMatrix64F(2,message.get(0).size(), true, all);//Z, measurement
        List<DenseMatrix64F> meas = createSimulatedMeas(trueX);
//		for(int i=0;i<meas.size();i++) {
//			System.out.println(meas.get(i));
//		}
//       System.out.print(meas.size());


        DenseMatrix64F F = createF(T);
        DenseMatrix64F Q = createQ(T,99.0);
        DenseMatrix64F H = createH();
        DenseMatrix64F R = createR();

        for(KalmanFilterOps f : filters ) {

            f.configure(F,Q,H);
            f.setState(priorX,priorP);
            for( DenseMatrix64F z : meas ) {
               	
                    f.predict();
                    f.update(z,R);           
                    //f.y.print("%10.5f");
                    f.Residual.print("%10.5f");
                    
                 

            }
            System.gc();
        }
    }
//
    private List<DenseMatrix64F> createSimulatedMeas( DenseMatrix64F x ) {

       // List<DenseMatrix64F> ret = new ArrayList<DenseMatrix64F>();
    	SimpleMatrix A_ = SimpleMatrix.wrap(x);
    	int num=x.numCols;
        List<DenseMatrix64F> ret = new ArrayList<DenseMatrix64F>();
        SimpleMatrix z = new SimpleMatrix(2,1);       
        
        for(int i=0;i<num;i++){       	
        	z=A_.extractVector(false, i);       	
        	ret.add(z.getMatrix());
        }

        return ret;
    }

    
//A
    public static DenseMatrix64F createA( double T ) {

    	double []a=new double[]{
    			1,0,0,0,0,1,0,0
    	};

        return new DenseMatrix64F(4,2, true, a);
    }

//F
    public static DenseMatrix64F createF( double T ) {

    	double []a=new double[]{
    			1,T,0,0,0,1,0,0,0,0,1,T,0,0,0,1
    	};

        return new DenseMatrix64F(4,4, true, a);
    }
    
//R   
    public static DenseMatrix64F createR() {

    	DenseMatrix64F R = new DenseMatrix64F(2,2);
    	R.set(0, 0, 0.1446);
    	R.set(1, 1, 0.247);

        return  R;
    }
//Q
    public static DenseMatrix64F createQ( double T , double var ) {
        DenseMatrix64F Q = new DenseMatrix64F(4,4);
        
        double a00 = 33;
        double a01 = 0.5*T*var;
        double a22 = var;

        Q.set(0, 0, a00);
        Q.set(0, 1, a01);
        Q.set(1, 0, a01);
        Q.set(1, 1, a22);
        Q.set(2, 2, a00);
        Q.set(2, 3, a01);
        Q.set(3, 2, a01);
        Q.set(3, 3, a22);
        
        for( int y = 0; y < 4; y++ ) {
            for( int x = 0; x < 4; x++ ) {
                Q.set(x,y, Q.get(x,y));
            }
        }

        return Q;
    }
//H
    public static DenseMatrix64F createH() {
        DenseMatrix64F H = new DenseMatrix64F(2,4);
        H.set(0, 0, T);
        H.set(1, 2, T);
        return H;
    }


    public static void main( String args[] ) throws IOException {
        Compression_GPS benchmark = new Compression_GPS();

        benchmark.filters.add( new KalmanFilterOps());
     


        benchmark.run();
    	
    }
}
