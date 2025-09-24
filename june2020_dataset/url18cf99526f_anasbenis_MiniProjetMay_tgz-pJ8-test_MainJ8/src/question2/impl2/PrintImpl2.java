package question2.impl2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import question2.PrintingRequest;
import compDefQ2.Print;

public class PrintImpl2 extends Print {

	@Override
	protected PrintingRequest make_pReq() {
		// TODO Auto-generated method stub
		return new PrintingRequest() {
			
			@Override
			public void requestToPrint(String info) {
				BufferedWriter out = null;
				try {
					 
				    // Accee au fichier pour le modifier
				    FileWriter fstream = new FileWriter("log.txt",true);
				    out = new BufferedWriter(fstream);
				    out.newLine();
				    out.write(info);
				    } catch (IOException e) {
				      System.err.println("Error: " + e.getMessage());
				    } finally {
				    //Close the output stream
				    try {
						out.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    }
				System.out.println(info);
			}
		};
	}


}
