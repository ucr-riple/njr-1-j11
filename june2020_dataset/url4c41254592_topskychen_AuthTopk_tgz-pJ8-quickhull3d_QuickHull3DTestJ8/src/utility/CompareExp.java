/**
 * 
 */
package utility;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import utility.Compare.DistanceCompare;
import utility.geo.Line;
import utility.security.Gfunction;
import utility.security.Point;

/**
 * @author chenqian
 *
 */
public class CompareExp {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		/**
		 * Paillier vs line vs gfuntion
		 * */
		/**
		 * paillier
		 * */
		ThreadMXBean bean = ManagementFactory.getThreadMXBean();
		long start = bean.getCurrentThreadCpuTime(), end, times = 1;
		Point q = new Point(100, 10000, 0);
		Point p1 = new Point(0, 1000000, 0), p2 = new Point(1000000, 0, 0);
		start = bean.getCurrentThreadCpuTime();
		DistanceCompare dc = new DistanceCompare(p1, p2);
		end = bean.getCurrentThreadCpuTime();
		System.out.println("DO Time consume:\t" + (end - start) / 1000000.0 + " ms");
		start = bean.getCurrentThreadCpuTime();
		for(int i = 0; i < times; i++){
			dc.GenerateVeryfyPart(q);
		}
		end = bean.getCurrentThreadCpuTime();
		System.out.println("SP Time consume:\t" + (end - start) / 1000000.0 + " ms");
		start = bean.getCurrentThreadCpuTime();
		for(int i = 0; i < times; i++){
			if(dc.ClientVerify(q)){
				//System.out.println("Pass!");
			}else{
				System.err.println("Fail!");
			}
		}
		end = bean.getCurrentThreadCpuTime();
		System.out.println("CL Time consume:\t" + (end - start) / 1000000.0 + " ms");
		System.out.println("================== end PPB ========================");
		
		/**
		 * line
		 * */
		start = bean.getCurrentThreadCpuTime();
		Line l = new Line(p1, p2);
		end = bean.getCurrentThreadCpuTime();
		System.out.println("DO Time consume:\t" + (end - start) / 1000000.0 + " ms");
		start = bean.getCurrentThreadCpuTime();
		for(int i = 0; i < times; i++){
			l.GenerateVeryfyPart(q, true);
		}
		end = bean.getCurrentThreadCpuTime();
		System.out.println("SP Time consume:\t" + (end - start) / 1000000.0 + " ms");
		start = bean.getCurrentThreadCpuTime();
		for(int i = 0; i < times; i++){
			if(l.ClientVerify(q)){
				//System.out.println("Pass!");
			}else{
				System.err.println("Fail!");
			}
		}
		end = bean.getCurrentThreadCpuTime();
		System.out.println("CL Time consume:\t" + (end - start) / 1000000.0 + " ms");
		System.out.println("====================end PLB======================");
		
		/**
		 * g function
		 * */
		start = bean.getCurrentThreadCpuTime();
		Gfunction gf = new Gfunction(0, 16);
		end = bean.getCurrentThreadCpuTime();
		System.out.println("DO Time consume:\t" + (end - start) / 1000000.0 + " ms");
		start = bean.getCurrentThreadCpuTime();
		String[] ServerReturned = null;
		for(int i = 0; i < times; i++){
			ServerReturned = gf.GenerateVeryfyPart(500000, false);
		}
		end = bean.getCurrentThreadCpuTime();
		System.out.println("SP Time consume:\t" + (end - start) / 1000000.0 + " ms");
		start = bean.getCurrentThreadCpuTime();
		for(int i = 0; i < times; i++){
			if(gf.ClientComputed(ServerReturned, gf.U - 500000).equals(gf.getDigest())){
				//System.out.println("Pass!");
			}else{
				System.err.println("Fail!");
			}
		}
		end = bean.getCurrentThreadCpuTime();
		
		System.out.println("CL Time consume:\t" + (end - start) / 1000000.0 + " ms");
	}

}
