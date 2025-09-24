package utility;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class Statistics {
	public boolean kind;
	public int num_of_Pailliar;
	public int num_of_Lines;
	public long construction_time;
	public long verify_time;
	public long generate_time;
	public long load_time;
	public int num_of_near_points;
	public int num_of_far_points;
	public int num_of_gf_points;
	public int size_of_VO;
	public int k_of_knn;
	public Statistics(){
		num_of_Pailliar = 0;
		num_of_Lines = 0;
		construction_time = 0;
		verify_time = 0;
		generate_time = 0;
		load_time = 0;
		num_of_near_points = 0;
		num_of_far_points = 0;
		num_of_gf_points = 0;
	}
	
	public int VOsize(){
		getvoSize();
		return size_of_VO;
	}
	
	public long Contruction_Time_For_Server(){
		return construction_time;
	}
	
	public long Verify_For_Client(){
		return verify_time;
	}
	public void getvoSize(){
		size_of_VO = 0;
		size_of_VO += //256 * 128 // seeds now don't need to be sent to the clients
					+ num_of_Pailliar * 2 * 5 * 128
					+ num_of_Lines * (10 * 128 + 12 + 2 * 128)
					+ num_of_gf_points * (10 * 128 + 12)
					+ num_of_gf_points * (3 * 128)
					+ k_of_knn * 4
					+ 128; //RSA
	}
	
	public void printinfo(){
		getvoSize();
		System.out.println("====================  info   ====================");
		System.out.println("Time of VO Construction : " + construction_time + " ms");
		System.out.println("Time of Client Verification : " + verify_time + " ms");
		System.out.println("Time of Generating Proof :  " + generate_time + " ms");
		System.out.println("Time of Loading :  " + load_time + " ms");
		System.out.println("Number of Pailliar : " + num_of_Pailliar);
		System.out.println("Number of lines : " + num_of_Lines);
		System.out.println("Number of near points : " + num_of_near_points);
		System.out.println("Number of far points : " + num_of_far_points);
		System.out.println("Number of gf points : " + num_of_gf_points);
		System.out.println("Size of VO : " + size_of_VO + " bytes = " + size_of_VO / 1000.0 + " KB");
	}
	
	public void printinfotofile(PrintWriter pw){
		pw.println(kind);
		pw.println(num_of_Pailliar);
		pw.println(num_of_Lines);
		pw.println(construction_time);
		pw.println(verify_time);
		pw.println(generate_time);
		pw.println(load_time);
		pw.println(num_of_near_points);
		pw.println(num_of_far_points);
		pw.println(num_of_gf_points);
		pw.println(size_of_VO);
		pw.println(k_of_knn);
	}
}
