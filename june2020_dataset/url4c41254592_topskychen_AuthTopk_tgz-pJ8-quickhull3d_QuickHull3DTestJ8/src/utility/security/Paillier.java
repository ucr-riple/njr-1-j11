package utility.security;

/*
 * 
 * This program is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version. 
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for 
 * more details. 
 * 
 * You should have received a copy of the GNU General Public License along with 
 * this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.math.*;
import java.util.*;

/**
 * Paillier Cryptosystem <br><br>
 * References: <br>
 * [1] Pascal Paillier, "Public-Key Cryptosystems Based on Composite Degree Residuosity Classes," EUROCRYPT'99.
 *    URL: <a href="http://www.gemplus.com/smart/rd/publications/pdf/Pai99pai.pdf">http://www.gemplus.com/smart/rd/publications/pdf/Pai99pai.pdf</a><br>
 * 
 * [2] Paillier cryptosystem from Wikipedia. 
 *    URL: <a href="http://en.wikipedia.org/wiki/Paillier_cryptosystem">http://en.wikipedia.org/wiki/Paillier_cryptosystem</a>
 * @author Kun Liu (kunliu1@cs.umbc.edu)
 * @version 1.0
 */
public class Paillier {

    /**
     * p and q are two large primes. 
     * lambda = lcm(p-1, q-1) = (p-1)*(q-1)/gcd(p-1, q-1).
     */
    public BigInteger p,  q,  lambda;
    /**
     * n = p*q, where p and q are two large primes.
     */
    public BigInteger n;
    /**
     * nsquare = n*n
     */
    public BigInteger nsquare;
    /**
     * a random integer in Z*_{n^2} where gcd (L(g^lambda mod n^2), n) = 1.
     */
    private BigInteger g;
    /**
     * number of bits of modulus
     */
    private int bitLength;

    private BigInteger eulorTotient = null;
    
    public BigInteger r2n = null;
    /**
     * Constructs an instance of the Paillier cryptosystem.
     * @param bitLengthVal number of bits of modulus
     * @param certainty The probability that the new BigInteger represents a prime number will exceed (1 - 2^(-certainty)). The execution time of this constructor is proportional to the value of this parameter.
     */
    public Paillier(int bitLengthVal, int certainty) {
        KeyGeneration(bitLengthVal, certainty);
    }

    /**
     * Constructs an instance of the Paillier cryptosystem with 512 bits of modulus and at least 1-2^(-64) certainty of primes generation.
     */
    public Paillier() {
        KeyGeneration(512, 64);
    }

    public Paillier(boolean load){
    	if(load == false){
    		//new Paillier();
    		return ;
    	}
    	p = new BigInteger("7804947815828153757360229819652411239902581336788694465424613098446329573400278070343537820396291896016908012945254519909616381770357121621312154337477549");
    	q = new BigInteger("12247127755728974923191139153732636477838906492686144861614769968099366746368758706323214837421927857281933445666383738321618845728660233681677187621034241");
    	lambda = new BigInteger("23897048256811305356682045961098076786292173600974335232853047236427426860997538273435469219307501447308630774059079381490857865988655519830051295175731202351291051736924333527027538179374428889748390624825767880885082903960257661909128492544371247481923266299867219139736585751737232577347292008887134810880");
    	n = new BigInteger("95588193027245221426728183844392307145168694403897340931412188945709707443990153093741876877230005789234523096236317525963431463954622079320205180702924829457239778504826014659479126102545433300481391974142398562923398161537350416673290636930142808147446364040927488197204574242176429326744471024890497755309");
    	nsquare = new BigInteger("9137102646213891968873960082305956179850467281348997924378425537822941327328353711486252262922644444840455727525901197729954054449881680414499250710131588148276328442397092515587006832309623836117942996692050118217219959552770621784470482084525859284361578517199520103365067471771138969454007315313886974448656193939941734279189230182934062065753837306411996014007893758334981553399496819364055499244835592090097892734327146277218349751641763468959032202918273753078465610458996364688468982697036369663280854824811270106731404956468032313803254120975168652977941430279563917186236894004867711790171585042367637685481");
    	r2n = new BigInteger("5862054643044006691035916703556809634394809871126793029911228341828268038449482090151176147585627882005085807734157121225795385053071464474144291397114464990277835555900234879516494932338522370564657206023838448402274816446457064071062172370157201978934959654485687953760431835548578924353568848395944516118137712573444692265431684217984385223813320387588501916499801894629547497454545070113827808435794445424908739769512520325401356598629357757966188272589664371905609866540967507599271264488288838310613806628647824484113446913102882841971492429518119994642467234113496195046955119675541370749628654614565511428743");
    	g = new BigInteger("2");
    	bitLength = 1024;
    }
    /**
     * Sets up the public key and private key.
     * @param bitLengthVal number of bits of modulus.
     * @param certainty The probability that the new BigInteger represents a prime number will exceed (1 - 2^(-certainty)). The execution time of this constructor is proportional to the value of this parameter.
     */
    public void KeyGeneration(int bitLengthVal, int certainty) {
        bitLength = bitLengthVal;
        /*Constructs two randomly generated positive BigIntegers that are probably prime, with the specified bitLength and certainty.*/
        p = new BigInteger(bitLength / 2, certainty, new Random());
        q = new BigInteger(bitLength / 2, certainty, new Random());

        n = p.multiply(q);
        nsquare = n.multiply(n);

        g = new BigInteger("2");
        lambda = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)).divide(
                p.subtract(BigInteger.ONE).gcd(q.subtract(BigInteger.ONE)));
        /* check whether g is good.*/
        if (g.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).gcd(n).intValue() != 1) {
            System.out.println("g is not good. Choose g again.");
            System.exit(1);
        }
    }

    /**
     * Encrypts plaintext m. ciphertext c = g^m * r^n mod n^2. This function explicitly requires random input r to help with encryption.
     * @param m plaintext as a BigInteger
     * @param r random plaintext to help with encryption
     * @return ciphertext as a BigInteger
     */
    public BigInteger Encryption(BigInteger m, BigInteger r) {
        return g.modPow(m, nsquare).multiply(r.modPow(n, nsquare)).mod(nsquare);
    }

    /**
     * Encrypts plaintext m. ciphertext c = g^m * r^n mod n^2. This function automatically generates random input r (to help with encryption).
     * @param m plaintext as a BigInteger
     * @return ciphertext as a BigInteger
     */
    public BigInteger Encryption(BigInteger m) {
        //BigInteger r = new BigInteger(bitLength, new Random());
    	//BigInteger r = BigIntegerUtility.PRIME_Q;// I need same value after encryption of same input x @qchen
    	if(m.signum() == -1){
    		System.out.println("get it");
    		m = m.add(GetEulorTotient());
    	}
        return g.modPow(m, nsquare).multiply(r2n).mod(nsquare);

    }

    /**
     * by qchen
     * This function encrypt the message without considering r
     * */
    public BigInteger EncryptionWithoutR(BigInteger m){
    	return g.modPow(m, nsquare);
    }
    
    /**
     * Get Eulor Totient Function.
     * */
    public BigInteger GetEulorTotient(){
    	if(eulorTotient == null){
    		eulorTotient = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)).multiply(n);
    	}
    	return eulorTotient;
    }
    
    public BigInteger GetG(){
    	return g;
    }
    /**
     * Decrypts ciphertext c. plaintext m = L(c^lambda mod n^2) * u mod n, where u = (L(g^lambda mod n^2))^(-1) mod n.
     * @param c ciphertext as a BigInteger
     * @return plaintext as a BigInteger
     */
    public BigInteger Decryption(BigInteger c) {
        BigInteger u = g.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).modInverse(n);
        return c.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).multiply(u).mod(n);
    }
    
    public static void geneateconst(int bitlen){
    	Paillier paillier = new Paillier(bitlen, 64);
    	System.out.println("p = new BigInteger(" + "\"" + paillier.p + "\");");
    	System.out.println("q = new BigInteger(" + "\"" + paillier.q + "\");");
    	System.out.println("lambda = new BigInteger(" + "\"" + paillier.lambda + "\");");
    	System.out.println("n = new BigInteger(" + "\"" + paillier.n + "\");");
    	System.out.println("nsquare = new BigInteger(" + "\"" + paillier.nsquare + "\");");
    	System.out.println("g = new BigInteger(" + "\"" + paillier.g + "\");");
    	System.out.println("bitLength = " + bitlen + ";");
    	System.out.println("length of n: " + paillier.n.bitLength());
    }
    /**
     * main function
     * @param str intput string
     */
    public static void main(String[] str) {
        /* instantiating an object of Paillier cryptosystem*/
    	geneateconst(1024);
        Paillier paillier = new Paillier(true);
        /* instantiating two plaintext msgs*/
        BigInteger m1 = new BigInteger("20");
        BigInteger m2 = new BigInteger("60");
        /* encryption*/
        BigInteger em1 = paillier.Encryption(m1);
        BigInteger em2 = paillier.Encryption(m2);
        /* printout encrypted text*/
        System.out.println(em1);
        System.out.println(em2);
        /* printout decrypted text */
        System.out.println(paillier.Decryption(em1).toString());
        System.out.println(paillier.Decryption(em2).toString());

        /* test homomorphic properties -> D(E(m1)*E(m2) mod n^2) = (m1 + m2) mod n */
        BigInteger product_em1em2 = em1.multiply(em2).mod(paillier.nsquare);
        BigInteger sum_m1m2 = m1.add(m2).mod(paillier.n);
        System.out.println("original sum: " + sum_m1m2.toString());
        System.out.println("decrypted sum: " + paillier.Decryption(product_em1em2).toString());

        /* test homomorphic properties -> D(E(m1)^m2 mod n^2) = (m1*m2) mod n */
        BigInteger expo_em1m2 = em1.modPow(m2, paillier.nsquare);
        BigInteger prod_m1m2 = m1.multiply(m2).mod(paillier.n);
        System.out.println("original product: " + prod_m1m2.toString());
        System.out.println("decrypted product: " + paillier.Decryption(expo_em1m2).toString());
        
        //System.out.println(BigIntegerUtility.PRIME_Q.modPow(paillier.n, paillier.nsquare));

    }
}
