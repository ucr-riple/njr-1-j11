/**
 * 
 */
package utility;

import java.io.PrintWriter;
/**
 * @author chenqian
 *
 */
public class StatisticForAuth {

	public double pre_time_SP;//ms
	public double con_time_SP;//ms
	public double vrf_time_CL;//ms
	public double size_VO;//byte
	public double num_PPB;
	public double num_PLB;
	public double num_Gf;
	public int num_node;
	public int num_miss_far;
	public int num_miss_near;
	public int num_miss_1n;
	public int num_dataio;
	
	public void reset(){
		con_time_SP = 0;
		vrf_time_CL = 0;
		size_VO = 0;
		num_PPB = 0;
		num_PLB = 0;
		num_Gf = 0;
		num_miss_far = 0;
		num_miss_near = 0;
		num_miss_1n = 0;
		num_node = 0;
		num_dataio = 0;
	}
	
	public StatisticForAuth(){
		reset();
	}
	
	public void print(){
		System.out.println("===============================================");
		System.out.println("Preparation time for server: " + pre_time_SP + " ms");
		System.out.println("Construction time for server: " + con_time_SP + " ms");
		System.out.println("Verification time for clients: " + vrf_time_CL + " ms");
		System.out.println("Size of VO: " + size_VO + " byte = " + (size_VO / 1024) + " KB");
		System.out.println("Number of PPB function calls: " + num_PPB);
		System.out.println("Number of PLB function calls: " + num_PLB);
		System.out.println("Number of G function calls: " + num_Gf);
		System.out.println("Number of miss PLB funtion when verifying far than kth point: " + num_miss_far);
		System.out.println("Number of miss PLB funtion when verifying near than kth point: " + num_miss_near);
		System.out.println("Number of miss PLB funtion when verifying 1st neighbors: " + num_miss_1n);
		System.out.println("Number of dataio : " + num_dataio);
	}
	
	public void printtoffile(PrintWriter pw){
		//pw.print(pre_time_SP + "\t");
		pw.print(con_time_SP + "\t");
		pw.print(vrf_time_CL + "\t");
		pw.print(size_VO + "\t");
		pw.print(num_PPB + "\t");
		pw.print(num_PLB + "\t");
		pw.print(num_Gf + "\t");
		pw.print(num_dataio);
		pw.println("");
	}
	
	public void update(StatisticForAuth stat){
		pre_time_SP += stat.pre_time_SP;
		con_time_SP += stat.con_time_SP;
		vrf_time_CL += stat.vrf_time_CL;
		size_VO += stat.size_VO;
		num_PLB += stat.num_PLB;
		num_PPB += stat.num_PPB;
		num_Gf += stat.num_Gf;
		num_dataio += stat.num_dataio;
	}
	
	public void getAveage(int num){
		pre_time_SP /= num;
		con_time_SP /= num;
		vrf_time_CL /= num;
		size_VO /= num;
		num_PLB /= num;
		num_PPB /= num;
		num_Gf /= num;
		num_dataio /= num;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
