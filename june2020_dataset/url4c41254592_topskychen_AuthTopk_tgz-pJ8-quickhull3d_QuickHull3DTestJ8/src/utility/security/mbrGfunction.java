/**
 * 
 */
package utility.security;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import spatialindex.core.Region;

/**
 * @author chenqian
 *
 */
public class mbrGfunction {
	public Gfunction[][] gfs = null;
	public boolean isleaf = false;
	public void setisLeaf(boolean _isleaf){
		isleaf = _isleaf;
	}
	public mbrGfunction(){
		
	}
	public mbrGfunction(Gfunction[][] gfs){
		this.gfs = gfs;
	}
	
	public void setGf(Gfunction[][] gfs){
		this.gfs = gfs;
	}
	
	public void writeToFile(DataOutputStream dos) throws IOException{
		dos.writeBoolean(isleaf);
		if(isleaf){
			gfs[0][0].writeToFile(dos);
			gfs[0][1].writeToFile(dos);
		}else{
			for(int i = 0; i < 2; i++){
				for(int j = 0; j < 2; j++){
					gfs[i][j].writeToFile(dos);
				}
			}
		}
	}
	
	public void readFromFile(DataInputStream dis) throws IOException{
		gfs = new Gfunction[2][2];
		gfs[0] = new Gfunction[2];
		gfs[1] = new Gfunction[2];
		isleaf = dis.readBoolean();
		if(isleaf){
				gfs[0][0] = new Gfunction();
				gfs[0][0].readFromFile(dis);
				gfs[1][0] = gfs[0][0].getcopy();
				gfs[0][1] = new Gfunction();
				gfs[0][1].readFromFile(dis);
				gfs[1][1] = gfs[0][1].getcopy();
		}else{
			for(int i = 0; i < 2; i++){
				for(int j = 0; j < 2; j++){
					gfs[i][j] = new Gfunction();
					gfs[i][j].readFromFile(dis);
				}
			}
		}
	}
}
