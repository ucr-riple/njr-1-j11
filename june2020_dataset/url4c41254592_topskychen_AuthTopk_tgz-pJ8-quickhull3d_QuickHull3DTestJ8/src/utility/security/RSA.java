/**
 * 
 */
package utility.security;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.BitSet;


/**
 * For RSA encryption and decryption.
 * 
 * @author qchen
 *
 */
public class RSA {

	private static String RSA_ALGORITHM =  "RSA"; 
	private static int DEFAULT_KEYSIZE = 1024;
	private final static BigInteger TWO = new BigInteger("2");
	private static BigInteger PRIME_P = TWO.pow(107).subtract(BigInteger.ONE);
	public BigInteger n, e, d;
	private static boolean isLoad = true;
	private static boolean DEBUG = false;
	public RSA(){
		initKey();
	}
	
	public RSA(int keySize){
		initKey(keySize);
	}
	
	public void initKey(){
		initKey(DEFAULT_KEYSIZE);
	}
	
	/**
	 * initialize public_key and private_key, if isLoad is true, the keys are fixed for experiments
	 * 
	 * @param keySize 
	 */
	
	public void initKey(int keySize){
		if(isLoad){
			/*n = new BigInteger("131884943464045003582223572764312780891162693721585194592930524324036800924838681762453491291771217265809865991455743941676406325061202522717805990960640954746194204966663488362888261025109842355102675226877485643946372964765916251796647608347313211787097206099009857242384502137752539619309766105281035929537");
			e = new BigInteger("162259276829213363391578010288127");
			d = new BigInteger("83449415258234725851576926589709492036325417822780187671214268219104557317967121765323271466150154985252796121180460609454398453624143877649330539413762235154006540684170816796948035994114344229672746485878680815922649054243988474628827807343372872130363224420020644927099041494725797963189924214734565337323");
			*/
			/*BigInteger p = new BigInteger("7804947815828153757360229819652411239902581336788694465424613098446329573400278070343537820396291896016908012945254519909616381770357121621312154337477549");
			BigInteger q = new BigInteger("12247127755728974923191139153732636477838906492686144861614769968099366746368758706323214837421927857281933445666383738321618845728660233681677187621034241");
	    	*/
			n = new BigInteger("9137102646213891968873960082305956179850467281348997924378425537822941327328353711486252262922644444840455727525901197729954054449881680414499250710131588148276328442397092515587006832309623836117942996692050118217219959552770621784470482084525859284361578517199520103365067471771138969454007315313886974448656193939941734279189230182934062065753837306411996014007893758334981553399496819364055499244835592090097892734327146277218349751641763468959032202918273753078465610458996364688468982697036369663280854824811270106731404956468032313803254120975168652977941430279563917186236894004867711790171585042367637685481");
			//BigInteger m = (p.subtract(BigInteger.ONE))
	        //         .multiply(q.subtract(BigInteger.ONE)).multiply(p).multiply(q);
			e =	RSA.PRIME_P;
		    d = new BigInteger("1134521025461381199780628738339629122047482384507794556518214256222766407939240641355301462595725727630403055841439610538167370181176024435785171895624139924670057634156010264713227319017584709542026521251989552610333061298960431812931715338334915441996250974446628953096609645461395560558237259379836497769063763957643894725229107010847869771811148741932466859705490123089946951666542994825250397188744462581617218567386450896987696984867870226835922454146094716321666838622444420976707886718463203408506658584309274617841897944797505918670232362861870371742229872090674318689314242776254164281194707978579029940223");
			//while(m.gcd(e).intValue() > 1) e = e.add(new BigInteger("2"));
		    //d = e.modInverse(m);
		    //System.out.println(e);
		    //System.out.println(d);
			DataInputStream dis = null;
			if(DEBUG){
				try {
					dis = new DataInputStream(new FileInputStream("RSA.seeds"));
				} catch (FileNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				//dis = new DataInputStream(Thread.currentThread().getContextClassLoader().getResourceAsStream("/RSA.seeds"));
				int len;
				try {
					len = dis.readInt();
					byte[] data = new byte[len];
					dis.read(data, 0, len);
					n = new BigInteger(data);
					System.out.println("\"" + n + "\"");
					len = dis.readInt();
					data = new byte[len];
					dis.read(data, 0, len);
					e = new BigInteger(data);
					System.out.println("\"" + e + "\"");
					len = dis.readInt();
					data = new byte[len];
					dis.read(data, 0, len);
					d = new BigInteger(data);
					System.out.println("\"" + d + "\"");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			return;
		}
		SecureRandom r = new SecureRandom();
	    BigInteger p = new BigInteger(keySize / 2, 100, r);
	    BigInteger q = new BigInteger(keySize / 2, 100, r);
	    n = p.multiply(q);
	    BigInteger m = (p.subtract(BigInteger.ONE))
	                   .multiply(q.subtract(BigInteger.ONE));
	    e =	RSA.PRIME_P;
	    while(m.gcd(e).intValue() > 1) e = e.add(new BigInteger("2"));
	    d = e.modInverse(m);
	    try {
			DataOutputStream dos = new DataOutputStream(new FileOutputStream("RSA.seeds"));
			byte[] data = n.toByteArray();
			try {
				dos.writeInt(data.length);
				dos.write(data);
				data = e.toByteArray();
				dos.writeInt(data.length);
				dos.write(data);
				data = d.toByteArray();
				dos.writeInt(data.length);
				dos.write(data);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public BigInteger getPublicKey(){
		return d;
	}
	
	public BigInteger getPrivateKey(){
		return e;
	}
	
	public String encrypt(String m){
		BigInteger messgae = null;
		try {
			messgae = new BigInteger(m.getBytes(DataIO.defaultCharSet));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			return new String(encrypt(messgae).toByteArray(), DataIO.defaultCharSet);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String decrypt(String m){
		BigInteger message = null;
		try {
			message = new BigInteger(m.getBytes(DataIO.defaultCharSet));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			return new String(decrypt(message).toByteArray(), DataIO.defaultCharSet);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public BigInteger encrypt(BigInteger message){
		if(message.compareTo(BigInteger.ZERO) < 0){
			message = message.mod(n);
		}
		return message.modPow(e, n);
	}
	
	public BigInteger decrypt(BigInteger message){
		if(message.compareTo(BigInteger.ZERO) < 0){
			message = message.mod(n);
		}
		return message.modPow(d, n);
	}
	
	public static String SignWithRsa(String message, String key, String N){
		BigInteger m = new BigInteger(message, 16);
		BigInteger pb = new BigInteger(key, 16);
		BigInteger n = new BigInteger(N, 16);
		BigInteger t = m.modPow(pb, n);
		return t.toString(16);
	}
	
	public BigInteger getCondensedRSA(BigInteger[] messages){
		BigInteger ans = BigInteger.ONE;
		for(BigInteger m : messages){
			ans.multiply(m).mod(n);
		}
		return ans;
	}
	
	public String getCondensedRSA(String[] messages){
		BigInteger[] m = new BigInteger[messages.length];
		for(int i = 0; i < messages.length; i++){
			try {
				m[i] = new BigInteger(messages[i].getBytes(DataIO.defaultCharSet));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			return new String(getCondensedRSA(m).toByteArray(), DataIO.defaultCharSet);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long start = System.currentTimeMillis();
		RSA condensedRSA = new RSA(1024);
		start = System.currentTimeMillis();
		BigInteger mes = BigIntegerUtility.PRIME_P;
		for(int i = 0; i < 1000; i++){
			mes = condensedRSA.encrypt(mes);
		}
		System.out.println("Time consume : " + (System.currentTimeMillis() - start));
		BigInteger mes1 = condensedRSA.encrypt(new BigInteger("123"));
		BigInteger mes2 = condensedRSA.encrypt(new BigInteger("2"));
		System.out.println(condensedRSA.decrypt(mes1.multiply(mes2)));
		System.out.println(mes);
	}

}
