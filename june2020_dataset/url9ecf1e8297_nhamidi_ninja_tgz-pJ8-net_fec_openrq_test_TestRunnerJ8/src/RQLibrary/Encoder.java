/* 
 * Copyright 2014 Jose Lopes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package RQLibrary;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.lang.Math;

public class Encoder {
    
    // TODO check patents for better default values
    public static final int MAX_PAYLOAD_SIZE = 192;// 1392; // P'
    public static final int ALIGN_PARAM = 4; // Al
    public static final int MAX_SIZE_BLOCK = 76800; // WS // B
    public static final int SSYMBOL_LOWER_BOUND = 8; // SS // 8
    public static final int KMAX = 56403;
    public static final byte ALPHA = 2;
    
    private static int T = MAX_PAYLOAD_SIZE; // symbol size
    public int Z; // number of source blocks
    private int N; // number of sub-blocks in each source block
    private int F; // transfer length
    private int Kt; // total number of symbols required to represent the source data of the object
    private byte[] data; // the data that will be sent
    private int numRepairSymbols = -1; // the number of repair symbols that should be sent (including the overhead symbols)
    
    public Encoder(byte[] file, float loss, int overhead) {
	F = file.length;
	
	T = derivateT(MAX_PAYLOAD_SIZE); // T = P'
	Kt = ceil((double) F / T);
	
	int N_max = floor((double) T / (SSYMBOL_LOWER_BOUND * ALIGN_PARAM));
	
	Z = derivateZ(N_max); // Z = ceil(Kt/KL(N_max))
	
	// how many repair symbols should be sent?
	int KL = Encoder.ceil((double) Kt / Z);
	numRepairSymbols = Utilities.necessaryRepairSymbols(KL, overhead, loss);
	
	N = derivateN(N_max); // N is the minimum n=1, ..., N_max such that ceil(Kt/Z) <= KL(n)
	
	/*
	 * If Kt*T > F, then, for encoding and decoding purposes, the last symbol of the last source block MUST be padded at the end with Kt*T-F zero octets.
	 */
	
	// FIXME is this copy really necessary?
	if ( F >= Kt * T ) {
	    data = Arrays.copyOf(file, F);
	} else {
	    data = Arrays.copyOf(file, Kt * T);
	}
    }
    
    /**
     * Normally used by the receiver.
     * 
     * @param fileSize
     */
    public Encoder(int fileSize) {
	F = fileSize;
	
	T = derivateT(MAX_PAYLOAD_SIZE); // T = P'
	Kt = ceil((double) F / T);
	
	int N_max = floor((double) T / (SSYMBOL_LOWER_BOUND * ALIGN_PARAM));
	
	Z = derivateZ(N_max); // Z = ceil(Kt/KL(N_max))
	
	N = derivateN(N_max); // N is the minimum n=1, ..., N_max such that ceil(Kt/Z) <= KL(n)
    }
    
    public int getKt() {
	return Kt;
    }
    
    public int getNumRepairSymbols() {
	return numRepairSymbols;
    }
    
    public void setNumRepairSymbols(int numRepairSymbols) {
	this.numRepairSymbols = numRepairSymbols;
    }
    
    private int derivateT(int pLinha) {
	return pLinha; // T = P'
    }
    
    private int derivateZ(int N_max) {
	// Z = ceil(Kt/KL(N_max))
	return (ceil((double) Kt / SystematicIndices.KL(N_max, MAX_SIZE_BLOCK, ALIGN_PARAM, T)));
    }
    
    private int derivateN(int N_max) {
	int n;
	
	// N is the minimum n=1, ..., N_max such that ceil(Kt/Z) <= KL(n)
	for (n = 1; n <= N_max && ceil((double) Kt / Z) > SystematicIndices.KL(n, MAX_SIZE_BLOCK, ALIGN_PARAM, T); n++) {
	}
	
	return n;
    }
    
    public static final int ceil(double x) {
	return ((int) Math.ceil(x));
    }
    
    public static final int floor(double x) {
	return ((int) Math.floor(x));
    }
    
    public SourceBlock[] partition() {
	/*
	 * TODO is sub-blocking really necessary for regular use cases? (if it isn't, many mem copies could be avoided)
	 * 
	 * TODO give the user the option to use sub-blocking or not
	 */
	
	// Partitioning parameters
	
	// (KL, KS, ZL, ZS) = Partition[Kt, Z]
	Partition KZ = new Partition(Kt, Z);
	int KL = KZ.get(1);
	int KS = KZ.get(2);
	int ZL = KZ.get(3);
	
	// (TL, TS, NL, NS) = Partition[T/Al, N]
	Partition TN = new Partition(T / ALIGN_PARAM, N);
	int TL = TN.get(1);
	int TS = TN.get(2);
	int NL = TN.get(3);
	
	// partitioned source blocks
	SourceBlock[] object = new SourceBlock[Z];
	
	// source block index
	int i;
	
	// original data buffer index
	int index_master = 0;
	
	/*
	 * the object MUST be partitioned into Z = ZL + ZS contiguous source blocks
	 */
	for (i = 0; i < ZL; i++) // first ZL
	{ // Source block i
	
	    // allocate memory for source block i
	    byte[] symbols = new byte[KL * T]; // a source block has KL*T octets
	    
	    // buffer index for source block i
	    int index_symbols = 0;
	    
	    // we need this so we don't lose the point in index_master
	    int aux_master = index_master;
	    
	    /*
	     * each source block with K source symbols MUST be divided into N = NL + NS contiguous sub-blocks
	     */
	    for (int k = 0; k < KL; k++) {
		// sub-block index
		int j = 0;
		
		// where we will get the data from
		int index_data = aux_master;
		
		/*
		 * the first NL sub-blocks each consisting of K contiguous sub-symbols of size of TL*Al octets
		 */
		for (; j < NL; j++, index_data += KL * TL * ALIGN_PARAM, index_symbols += TL * ALIGN_PARAM) {
		    System.arraycopy(data, index_data, symbols, index_symbols, TL * ALIGN_PARAM);
		}
		
		/*
		 * the remaining NS sub-blocks each consisting of K contiguous sub- symbols of size of TS*Al octets
		 */
		for (; j < N; j++, index_data += KL * TS * ALIGN_PARAM, index_symbols += TS * ALIGN_PARAM) {
		    System.arraycopy(data, index_data, symbols, index_symbols, TS * ALIGN_PARAM);
		}
		
		if ( NL > 0 ) {
		    aux_master += TL * ALIGN_PARAM; // we always start at a sub-block of size TL*Al
		} else {
		    aux_master += TS * ALIGN_PARAM; // we always start at a sub-block of size TS*Al
		}
	    }
	    
	    // partitioned source block i
	    object[i] = new SourceBlock(i, symbols, T, KL);
	    
	    // update index_master position to point to the next source block
	    index_master += (KL * T);
	}
	
	for (; i < Z; i++) // last ZS
	{ // Source block i
	
	    // allocate memory for source block i
	    byte[] symbols = new byte[KS * T]; // source blocks each having KS*T octets
	    
	    // buffer index for source block i
	    int index_symbols = 0;
	    
	    // we need this so we don't lose the point in index_master
	    int aux_master = index_master;
	    
	    /*
	     * each source block with K source symbols MUST be divided into N = NL + NS contiguous sub-blocks
	     */
	    for (int k = 0; k < KS; k++) {
		/*
		 * each source block with K source symbols MUST be divided into N = NL + NS contiguous sub-blocks
		 */
		
		int j = 0;
		
		int index_data = aux_master;
		
		for (; j < NL; j++, index_data += KS * TL * ALIGN_PARAM, index_symbols += TL * ALIGN_PARAM) {
		    /*
		     * the first NL sub-blocks each consisting of K contiguous sub-symbols of size of TL*Al octets
		     */
		    
		    System.arraycopy(data, index_data, symbols, index_symbols, TL * ALIGN_PARAM);
		}
		
		for (; j < N; j++, index_data += KS * TS * ALIGN_PARAM, index_symbols += TS * ALIGN_PARAM) {
		    /*
		     * the remaining NS sub-blocks each consisting of K contiguous sub- symbols of size of TS*Al octets
		     */
		    
		    System.arraycopy(data, index_data, symbols, index_symbols, TS * ALIGN_PARAM);
		}
		
		// where we will be getting our data from next?
		if ( NL > 0 ) {
		    aux_master += TL * ALIGN_PARAM; // we always start at a sub-block of size TL*Al
		} else {
		    aux_master += TS * ALIGN_PARAM; // we always start at a sub-block of size TS*Al
		}
	    }
	    // partitioned source block i
	    object[i] = new SourceBlock(i, symbols, T, KS);
	    
	    // update index_master position to point to the next source block
	    index_master += (KS * T);
	}
	
	// return partitioned source blocks
	return object;
    }
    
    public byte[] unPartition(SourceBlock[] object) {
	// original data buffer
	byte[] data;
	
	// original data buffer's index
	int index_master = 0;
	
	// allocate memory for original data's buffer
	if ( F >= Kt * T )
	    data = new byte[(int) F];
	else
	    data = new byte[(int) Kt * T];
	
	// for every source block
	for (int i = 0; i < object.length; i++) {
	    // source block i
	    SourceBlock sb = object[i];
	    
	    // K for source block i
	    int Kappa = sb.getK();
	    
	    // symbol size for source block i
	    int ssize = sb.getT();
	    
	    // symbols in source block i
	    byte[] symbols = sb.getSymbols();
	    
	    // partitioning parameters
	    Partition TN = new Partition(ssize / ALIGN_PARAM, N);
	    int TL = TN.get(1);
	    int TS = TN.get(2);
	    int NL = TN.get(3);
	    
	    // for every source symbol in source block i
	    for (int k = 0; k < Kappa; k++) {
		/*
		 * each source block with K source symbols MUST be divided into N = NL + NS contiguous sub-blocks
		 */
		
		// sub-block index
		int j = 0;
		
		// where we will put the data in
		int index_data = index_master + k * TL * ALIGN_PARAM; // we always start at a sub-block of size TL*Al
		
		// where we will get the data from
		int index_symbols = k * ssize;
		
		/*
		 * the first NL sub-blocks each consisting of K contiguous sub-symbols of size of TL*Al octets
		 */
		for (; j < NL; j++, index_data += Kappa * TL * ALIGN_PARAM, index_symbols += TL * ALIGN_PARAM) {
		    System.arraycopy(symbols, index_symbols, data, index_data, TL * ALIGN_PARAM);
		}
		
		/*
		 * the remaining NS sub-blocks each consisting of K contiguous sub- symbols of size of TS*Al octets
		 */
		for (; j < N; j++, index_data += Kappa * TS * ALIGN_PARAM, index_symbols += TS * ALIGN_PARAM) {
		    System.arraycopy(symbols, index_symbols, data, index_data, TS * ALIGN_PARAM);
		}
	    }
	    
	    // move index to the next symbol
	    index_master += Kappa * ssize;
	}
	
	// remove padding if it exists
	if ( F < Kt * T )
	    data = Arrays.copyOf(data, F);
	
	// return unpartitioned data
	return data;
    }
    
    /**
     * Decodes one encoded source block.
     * 
     * @param eb
     *            Encoded source block.
     * @return Decoded source block.
     * @throws SingularMatrixException
     */
    public static SourceBlock decode(EncodingPacket eb) throws SingularMatrixException {
	
	
	// encoding symbols in the encoded source block
	EncodingSymbol[] enc_symbols = eb.getEncoding_symbols(); // TODO these MUST be ordered, would be nice if it wasn't needed
	
	// number of encoding symbols in the encoded source block
	int num_symbols = enc_symbols.length;
	
	// number of source symbols in the source block
	int K = eb.getK();
	
	// size of each symbol the block
	int T = eb.getT();
	
	// number of symbols in the corresponding extended source block
	int kLinha = SystematicIndices.ceil(K);
	
	// constraint matrix parameters
	int Ki = SystematicIndices.getKIndex(K);
	int S = SystematicIndices.S(Ki);
	int H = SystematicIndices.H(Ki);
	int L = kLinha + S + H;
	
	/*
	 * Analyze received symbols "topology"
	 */
	
	// indexes of the missing source symbols
	Set<Integer> missing_symbols = new TreeSet<Integer>();
	
	// the received source symbols
	Set<EncodingSymbol> source_symbols = new TreeSet<EncodingSymbol>();
	
	// the received repair symbols
	Set<EncodingSymbol> repair_symbols = new TreeSet<EncodingSymbol>();
	
	// number of received source symbols
	int num_source_symbols = 0;
	
	// number of received repair symbols
	int num_repair_symbols = 0;
	
	// number of missing symbols
	int num_missing_symbols = 0;
	
	// for every encoding symbol received
	for (int symbol = 0; symbol < num_symbols; symbol++) {
	    // encoding symbol
	    EncodingSymbol enc_symbol = enc_symbols[symbol];
	    
	    // this shouldn't happen (depends on the ordering methodology.
	    // should be removed when unordered encoding symbols are tolerated
	    if ( enc_symbol == null ) {
		if ( symbol < K ) {
		    missing_symbols.add(symbol);
		    num_missing_symbols++;
		}
		
		continue;
	    }
	    
	    // source or repair symbol?
	    if ( enc_symbol.getESI() < K ) // is a source symbol
	    {
		// if this isn't the symbol we were expecting, // TODO re-write this code when unordered
		// add the expected symbol to the missing set // enconding symbols are tolerated
		if ( enc_symbol.getESI() != symbol ) {
		    missing_symbols.add(symbol + num_missing_symbols);
		    num_missing_symbols++;
		}
		
		// add to the set of source symbols
		source_symbols.add(enc_symbol);
		
		// increment the total number of source symbols
		num_source_symbols++;
	    } else // is a repair symbol
	    {
		// add to the set of repair symbols
		repair_symbols.add(enc_symbol);
		
		// increment the total number of repair symbols
		num_repair_symbols++;
	    }
	}
	
	
	
	/*
	 * print topology of the received encoding symbols
	 */
	StringBuilder st = new StringBuilder();
	st.append("Symbols topology: \n");
	st.append("# Source: ");
	st.append(num_source_symbols);
	st.append("\n# Repair: ");
	st.append(num_repair_symbols);
	st.append("\n Repair indexes:\n");
	for (EncodingSymbol i : repair_symbols)
	    st.append(i.getESI() + ", ");
	st.append("\n# Missing: ");
	st.append(num_missing_symbols);
	st.append("\n Missing indexes:\n");
	for (Integer i : missing_symbols)
	    st.append(i + ", ");
	// System.out.println(st.toString());
	/*
	 * end print
	 */
	
	// are there enough repair symbols to compensate the missing source symbols?
	if ( num_repair_symbols < num_missing_symbols ) {
	    throw new RuntimeException(Integer.toString(num_missing_symbols - num_repair_symbols)); // TODO shouldn't be runtime exception (too generic)
	    // throw new RuntimeException( Integer.toString(num_missing_symbols)); // TODO shouldn't be runtime exception (too generic)
	}
	
	// number of extra repair symbols to be used for the decoding process
	int overhead = num_repair_symbols - missing_symbols.size();
	
	// number of rows in the decoding matrix
	int M = L + overhead;
	
	// maps the ESI of a symbol to its line in the decoding matrix
	Map<Integer, byte[]> esiToLTCode = new TreeMap<Integer, byte[]>();
	
	// allocate buffer for the decoded data
	byte[] decoded_data = new byte[K * T];
	
	// all source symbols received! :D
	if ( num_source_symbols == K ) {
	    // collect all payloads from the source symbols
	    for (EncodingSymbol enc_symbol : source_symbols) {
		System.arraycopy(enc_symbol.getData(), 0, decoded_data, enc_symbol.getESI() * T, T);
	    }
	    
	    // return the "decoded" source block
	    return new SourceBlock(eb.getSBN(), decoded_data, T, K);
	}
	
	// not so lucky...
	else {
	    
	    // allocate memory for the decoding matrix
	    byte[][] constraint_matrix = new byte[M][];
	    
	    // generate the original constraint matrix
	    byte[][] lConstraint = generateConstraintMatrix(kLinha, T);
	    
	    // copy to our decoding matrix
	    for (int row = 0; row < L; row++)
		constraint_matrix[row] = lConstraint[row];
	    
	    // initialize D
	    byte[][] D = new byte[M][T];
	    
	    // populate D with the received source symbols
	    if ( num_source_symbols != 0 ) {
		
		Iterator<EncodingSymbol> it = source_symbols.iterator();
		
		do {
		    EncodingSymbol source_symbol = (EncodingSymbol) it.next();
		    
		    D[source_symbol.getESI() + S + H] = source_symbol.getData();
		    
		} while (it.hasNext());
	    }
	    
	    /*
	     * for every repair symbol received - replace a missing source symbol's decoding matrix line for its corresponding line - populate D accordingly
	     */
	    
	    Iterator<EncodingSymbol> repair_symbol = repair_symbols.iterator();
	    
	    // identify missing source symbols and replace their lines with "repair lines"
	    for (Integer missing_ESI : missing_symbols) {
		
		EncodingSymbol repair = (EncodingSymbol) repair_symbol.next();
		int row = S + H + missing_ESI;
		
		// replace line S + H + missing_ESI with the line for encIndexes
		Set<Integer> indexes = encIndexes(kLinha, new Tuple(kLinha, repair.getISI(K)));
		
		byte[] newLine = new byte[L];
		
		for (Integer col : indexes)
		    newLine[col] = 1;
		
		esiToLTCode.put(missing_ESI, constraint_matrix[row]);
		constraint_matrix[row] = newLine;
		
		// fill in missing source symbols in D with the repair symbols
		D[row] = repair.getData();
	    }
	    
	    
	    // insert the values for overhead (repair) symbols
	    for (int row = L; row < M; row++) {
		EncodingSymbol repair = (EncodingSymbol) repair_symbol.next();
		
		// generate the overhead lines
		Tuple tuple = new Tuple(K, repair.getISI(K));
		
		Set<Integer> indexes = encIndexes(K, tuple);
		
		byte[] newLine = new byte[L];
		
		for (Integer col : indexes)
		    newLine[col] = 1;
		
		constraint_matrix[row] = newLine;
		
		// update D with the data for that symbol
		D[row] = repair.getData();
	    }
	    
	    
	   
	    
	    
	    
	    
	    /*
	     * with the decoding matrix created and vector D populated, we have the system of linear equations ready to be solved
	     */
	    
	    byte[] intermediate_symbols = generateIntermediateSymbols(constraint_matrix, D, T, kLinha);
	      /*
	     * with the intermediate symbols calculated, one can recover every missing source symbol
	     */
	    
	    // recover missing source symbols
	    for (Map.Entry<Integer, byte[]> missing : esiToLTCode.entrySet()) {
		// multiply the constraint matrix line relative to the missing source symbol
		// by the vector of intermediate symbols to recover the missing source symbol
		byte[] original_symbol = Utilities.multiplyByteLineBySymbolVector(missing.getValue(), intermediate_symbols, T);
		
		// write to the decoded data buffer
		System.arraycopy(original_symbol, 0, decoded_data, missing.getKey() * T, T);
	    }
	    
	    // write the received source symbols to the decoded data buffer
	    for (EncodingSymbol enc_symbol : source_symbols) {
		System.arraycopy(enc_symbol.getData(), 0, decoded_data, enc_symbol.getESI() * T, T);
	    }
	}
	
	return new SourceBlock(eb.getSBN(), decoded_data, T, K);
    }
    
    /**
     * Decodes an array of encoded blocks
     * 
     * @param encoded_blocks
     * @return Array of source blocks.
     * @throws SingularMatrixException
     */
    public static SourceBlock[] decode(EncodingPacket[] encoded_blocks) throws SingularMatrixException {
	/*
	 * this is pretty much the code for decoding a source block inside a 'for' statement so refer to the original method for comments, as this one won't be
	 * fully commented
	 */
	
	// number of encoded blocks
	int num_blocks = encoded_blocks.length;
	
	// array of decoded source blocks
	SourceBlock[] recovered = new SourceBlock[num_blocks];
	
	// decode each block
	for (int source_block_index = 0; source_block_index < encoded_blocks.length; source_block_index++) {
	    EncodingPacket eb = encoded_blocks[source_block_index];
	    EncodingSymbol[] enc_symbols = eb.getEncoding_symbols();
	    int num_symbols = enc_symbols.length;
	    int K = eb.getK();
	    int T = eb.getT();
	    int kLinha = SystematicIndices.ceil(K);
	    int Ki = SystematicIndices.getKIndex(K);
	    int S = SystematicIndices.S(Ki);
	    int H = SystematicIndices.H(Ki);
	    int L = kLinha + S + H;
	    
	    // Analyze received symbols "topology"
	    Set<Integer> missing_symbols = new TreeSet<Integer>();
	    Set<EncodingSymbol> source_symbols = new TreeSet<EncodingSymbol>();
	    Set<EncodingSymbol> repair_symbols = new TreeSet<EncodingSymbol>();
	    int num_source_symbols = 0;
	    int num_repair_symbols = 0;
	    int missing_delta_index = 0;
	    
	    for (int symbol = 0; symbol < num_symbols; symbol++) {
		EncodingSymbol enc_symbol = enc_symbols[symbol];
		
		if ( enc_symbol == null ) // TODO think about this
		{
		    if ( symbol < K ) {
			missing_symbols.add(symbol);
			missing_delta_index++;
		    }
		    
		    continue;
		}
		
		if ( enc_symbol.getESI() < K ) {
		    if ( enc_symbol.getESI() != symbol ) {
			missing_symbols.add(symbol + missing_delta_index);
			missing_delta_index++;
		    }
		    
		    source_symbols.add(enc_symbol);
		    num_source_symbols++;
		} else {
		    repair_symbols.add(enc_symbol);
		    num_repair_symbols++;
		}
	    }
	    
	    if ( num_repair_symbols < missing_delta_index )
		throw new RuntimeException("Not enough repair symbols received."); // TODO shouldnt be runtime exception, too generic
		
	    int overhead = num_repair_symbols - missing_symbols.size();
	    int M = L + overhead;
	    
	    Map<Integer, byte[]> esiToLTCode = new TreeMap<Integer, byte[]>();
	    
	    byte[] decoded_data = new byte[K * T];
	    
	    // All source symbols received :D
	    if ( num_source_symbols == K ) {
		// Collect all payloads from the source symbols
		for (EncodingSymbol enc_symbol : source_symbols) {
		    System.arraycopy(enc_symbol.getData(), 0, decoded_data, enc_symbol.getESI() * T, T);
		}
		
		recovered[source_block_index] = new SourceBlock(eb.getSBN(), decoded_data, T, K);
	    }
	    
	    // Not so lucky
	    else {
		byte[][] constraint_matrix = new byte[M][];
		
		// Generate original constraint matrix
		byte[][] lConstraint = generateConstraintMatrix(kLinha, T);
		
		for (int row = 0; row < L; row++)
		    constraint_matrix[row] = lConstraint[row];
		
		// initialize D
		byte[][] D = new byte[M][T];
		
		if ( num_source_symbols != 0 ) {
		    Iterator<EncodingSymbol> it = source_symbols.iterator();
		    
		    do {
			EncodingSymbol source_symbol = (EncodingSymbol) it.next();
			
			D[source_symbol.getESI() + S + H] = source_symbol.getData();
			
		    } while (it.hasNext());
		}
		
		Iterator<EncodingSymbol> repair_symbol = repair_symbols.iterator();
		
		// Identify missing source symbols and replace their lines with "repair lines"
		for (Integer missing_ESI : missing_symbols) {
		    EncodingSymbol repair = (EncodingSymbol) repair_symbol.next();
		    int row = S + H + missing_ESI;
		    
		    // Substituir S + H + missing_ESI pela linha equivalente ao encIndexes do repair simbolo
		    Set<Integer> indexes = encIndexes(kLinha, new Tuple(kLinha, repair.getISI(K)));
		    
		    byte[] newLine = new byte[L];
		    
		    for (Integer col : indexes)
			newLine[col] = 1;
		    
		    esiToLTCode.put(missing_ESI, constraint_matrix[row]);
		    constraint_matrix[row] = newLine;
		    
		    // Fill in missing source symbols in D with the repair symbols
		    D[row] = repair.getData();
		}
		
		// add values for overhead symbols
		for (int row = L; row < M; row++) {
		    EncodingSymbol repair = (EncodingSymbol) repair_symbol.next();
		    
		    // Generate the overhead lines
		    Tuple tuple = new Tuple(K, repair.getISI(K));
		    
		    Set<Integer> indexes = encIndexes(K, tuple);
		    
		    byte[] newLine = new byte[L];
		    for (Integer col : indexes)
			newLine[col] = 1;
		    
		    constraint_matrix[row] = newLine;
		    
		    // update D with the data for that symbol
		    D[row] = repair.getData();
		}
		
		byte[] intermediate_symbols = generateIntermediateSymbols(constraint_matrix, D, T, kLinha);
		
		// Recover missing source symbols
		for (Map.Entry<Integer, byte[]> missing : esiToLTCode.entrySet()) {
		    byte[] original_symbol = Utilities.multiplyByteLineBySymbolVector(missing.getValue(), intermediate_symbols, T);
		    
		    System.arraycopy(original_symbol, 0, decoded_data, missing.getKey() * T, T);
		}
		
		// Merge with received source symbols
		for (EncodingSymbol enc_symbol : source_symbols) {
		    System.arraycopy(enc_symbol.getData(), 0, decoded_data, enc_symbol.getESI() * T, T);
		}
		
		recovered[source_block_index] = new SourceBlock(eb.getSBN(), decoded_data, T, K);
	    }
	}
	
	return recovered;
    }
    
    /**
     * Encodes a source block.
     * 
     * @param sb
     * @return
     * @throws SingularMatrixException
     */
    
    EncodingPacket encoded_symbols_reparation;
    
    public EncodingPacket get_encoded_symbols_reparation() {
	return encoded_symbols_reparation;
    }
    
    public void set_encoded_symbols_reparation(EncodingPacket symbols_reparation) {
	this.encoded_symbols_reparation = symbols_reparation;
    }
    
    public EncodingPacket encode(SourceBlock sb) {
	// block parameters
	int SBN = sb.getSBN();
	byte[] ssymbols = sb.getSymbols();
	int K = sb.getK();
	int kLinha = SystematicIndices.ceil(K);
	
	// allocate memory for the generated encoding symbols
	EncodingSymbol[] encoded_symbols = new EncodingSymbol[K + numRepairSymbols]; // FIXME arbitrary size ASAP
	EncodingSymbol[] encoded_symbols_reparation = new EncodingSymbol[numRepairSymbols];
	// generate intermediate symbols
	byte[] intermediate_symbols = null;
	try {
	    
	    intermediate_symbols = generateIntermediateSymbols(sb);
	} catch (SingularMatrixException e) {
	    // this never happens here
	    System.exit(-42437);
	}
	
	// add the source symbols to the encoding symbols
	int source_symbol;
	int source_symbol_index;
	
	for (source_symbol = 0, source_symbol_index = 0; source_symbol < K; source_symbol++, source_symbol_index += sb.getT()) {
	    encoded_symbols[source_symbol] = new EncodingSymbol(SBN, source_symbol, Arrays.copyOfRange(ssymbols, source_symbol_index,
		    (int) (source_symbol_index + sb.getT())));
	}
	
	// generate repair symbols
	for (int repair_symbol = 0; repair_symbol < numRepairSymbols; repair_symbol++) {
	    int isi = kLinha + repair_symbol;
	    int esi = K + repair_symbol;
	    
	    byte[] enc_data = enc(kLinha, intermediate_symbols, new Tuple(kLinha, isi));
	    
	    // add the repair symbol to the encoding symbols
	    encoded_symbols[source_symbol + repair_symbol] = new EncodingSymbol(SBN, esi, enc_data);
	    encoded_symbols_reparation[repair_symbol] = new EncodingSymbol(SBN, esi, enc_data);
	}
	
	// return the encoding symbols
	set_encoded_symbols_reparation(new EncodingPacket(SBN, encoded_symbols_reparation, K, sb.getT()));
	return (new EncodingPacket(SBN, encoded_symbols, K, sb.getT()));
    }
    
    /**
     * Encodes an array of source blocks.
     * 
     * @param object
     * @return An array of encoded blocks
     */
    public EncodingPacket[] encode(SourceBlock[] object) {
	// number of blocks to encode
	int num_src_symbols = object.length;
	
	// allocate memory for the encoded blocks
	EncodingPacket[] encoded_blocks = new EncodingPacket[num_src_symbols];
	
	// this is basically the same code as the function to encode a single block inside a for 'statement'
	// refer to the original method for comments
	for (int source_block_index = 0; source_block_index < num_src_symbols; source_block_index++) {
	    SourceBlock sb = object[source_block_index];
	    int SBN = sb.getSBN();
	    byte[] ssymbols = sb.getSymbols();
	    int K = sb.getK();
	    int kLinha = SystematicIndices.ceil(K);
	    
	    EncodingSymbol[] encoded_symbols = new EncodingSymbol[K + numRepairSymbols]; // FIXME arbitrary size ASAP /*//FIXME SIMULATION */
	    
	    /*
	     * First encoding step
	     */
	    
	    byte[] intermediate_symbols = null;
	    try {
		
		intermediate_symbols = generateIntermediateSymbols(sb);
		
	    } catch (SingularMatrixException e) {
		// this never happens here
		System.exit(-42437);
	    }
	    
	    /*
	     * Second encoding step
	     */
	    
	    // sending original source symbols
	    int source_symbol;
	    int source_symbol_index;
	    
	    for (source_symbol = 0, source_symbol_index = 0; source_symbol < K; source_symbol++, source_symbol_index += sb.getT()) {
		encoded_symbols[source_symbol] = new EncodingSymbol(SBN, source_symbol, Arrays.copyOfRange(ssymbols, source_symbol_index,
			(int) (source_symbol_index + sb.getT())));
	    }
	    
	    // generating/sending repair symbols
	    for (int repair_symbol = 0; repair_symbol < numRepairSymbols; repair_symbol++) {
		int isi = kLinha + repair_symbol;
		int esi = K + repair_symbol;
		
		byte[] enc_data = enc(kLinha, intermediate_symbols, new Tuple(kLinha, isi));
		
		encoded_symbols[source_symbol + repair_symbol] = new EncodingSymbol(SBN, esi, enc_data);
	    }
	    
	    encoded_blocks[source_block_index] = new EncodingPacket(SBN, encoded_symbols, K, sb.getT());
	}
	
	return encoded_blocks;
    }
    
    /**
     * Initializes the G_LDPC1 submatrix.
     * 
     * @param constraint_matrix
     * @param B
     * @param S
     */
    private static void initializeG_LPDC1(byte[][] constraint_matrix, int B, int S) {
	int circulant_matrix = -1;
	
	for (int col = 0; col < B; col++) {
	    int circulant_matrix_column = col % S;
	    
	    if ( circulant_matrix_column != 0 ) {
		// cyclic down-shift
		constraint_matrix[0][col] = constraint_matrix[S - 1][col - 1];
		
		for (int row = 1; row < S; row++) {
		    constraint_matrix[row][col] = constraint_matrix[row - 1][col - 1];
		}
	    } else { // if 0, then its the first column of the current circulant matrix
	    
		circulant_matrix++;
		
		// 0
		constraint_matrix[0][col] = 1;
		
		// (i + 1) mod S
		constraint_matrix[(circulant_matrix + 1) % S][col] = 1;
		
		// (2 * (i + 1)) mod S
		constraint_matrix[(2 * (circulant_matrix + 1)) % S][col] = 1;
	    }
	}
    }
    
    /**
     * Initializes the G_LPDC2 submatrix.
     * 
     * @param constraint_matrix
     * @param S
     * @param P
     * @param W
     */
    private static void initializeG_LPDC2(byte[][] constraint_matrix, int S, int P, int W) {
	
	for (int row = 0; row < S; row++) {
	    // consecutives 1's modulo P
	    constraint_matrix[row][(row % P) + W] = 1;
	    constraint_matrix[row][((row + 1) % P) + W] = 1;
	}
    }
    
    /**
     * Initializes the I_S submatrix.
     * 
     * @param constraint_matrix
     * @param S
     * @param B
     */
    private static void initializeIs(byte[][] constraint_matrix, int S, int B) {
	
	for (int row = 0; row < S; row++) {
	    for (int col = 0; col < S; col++) {
		if ( col != row )
		    continue;
		else
		    constraint_matrix[row][col + B] = 1;
	    }
	}
    }
    
    /**
     * Initializes the I_H submatrix.
     * 
     * @param constraint_matrix
     * @param W
     * @param U
     * @param H
     * @param S
     */
    private static void initializeIh(byte[][] constraint_matrix, int W, int U, int H, int S) {
	int lower_limit_col = W + U;
	
	for (int row = 0; row < H; row++) {
	    for (int col = 0; col < H; col++) {
		if ( col != row )
		    continue;
		else
		    constraint_matrix[row + S][col + lower_limit_col] = 1;
	    }
	}
    }
    
    /**
     * Generates the MT matrix that is used to generate G_HDPC submatrix.
     * 
     * @param H
     * @param K
     * @param S
     * @return MT
     */
    private static byte[][] generateMT(int H, int K, int S) {
	byte[][] MT = new byte[H][K + S];
	
	for (int row = 0; row < H; row++) {
	    for (int col = 0; col < K + S - 1; col++) {
		if ( row != (int) (Rand.rand(col + 1, 6, H)) && row != (((int) (Rand.rand(col + 1, 6, H)) + (int) (Rand.rand(col + 1, 7, H - 1)) + 1) % H) )
		    continue;
		else
		    MT[row][col] = 1;
	    }
	}
	
	for (int row = 0; row < H; row++)
	    MT[row][K + S - 1] = OctectOps.getExp(row);
	
	return (MT);
    }
    
    /**
     * Generates the GAMMA matrix that is used to generate G_HDPC submatrix.
     * 
     * @param K
     * @param S
     * @return GAMMA
     */
    private static byte[][] generateGAMMA(int K, int S) {
	byte[][] GAMMA = new byte[K + S][K + S];
	
	for (int row = 0; row < K + S; row++) {
	    for (int col = 0; col < K + S; col++) {
		if ( row >= col )
		    GAMMA[row][col] = OctectOps.getExp((row - col) % 256);
		else
		    continue;
	    }
	}
	
	return GAMMA;
    }
    
    /**
     * Initializes the G_ENC submatrix.
     * 
     * @param constraint_matrix
     * @param S
     * @param H
     * @param L
     * @param K
     */
    private static void initializeG_ENC(byte[][] constraint_matrix, int S, int H, int L, int K) {
	for (int row = S + H; row < L; row++) {
	    Tuple tuple = new Tuple(K, row - S - H);
	    
	    Set<Integer> indexes = encIndexes(K, tuple);
	    
	    for (Integer j : indexes) {
		constraint_matrix[row][j] = 1;
	    }
	}
    }
    
    /**
     * Generates the constraint matrix.
     * 
     * @param K
     * @param T
     * @return
     */
    public static byte[][] generateConstraintMatrix(int K, int T) {
	// calculate necessary parameters
	int Ki = SystematicIndices.getKIndex(K);
	int S = SystematicIndices.S(Ki);
	int H = SystematicIndices.H(Ki);
	int W = SystematicIndices.W(Ki);
	int L = K + S + H;
	int P = L - W;
	int U = P - H;
	int B = W - S;
	
	// allocate memory for the constraint matrix
	byte[][] constraint_matrix = new byte[L][L]; // A
	
	/*
	 * upper half
	 */
	
	// initialize G_LPDC2
	initializeG_LPDC2(constraint_matrix, S, P, W);
	
	// initialize G_LPDC1
	initializeG_LPDC1(constraint_matrix, B, S);
	
	// initialize I_s
	initializeIs(constraint_matrix, S, B);
	
	/*
	 * botton half
	 */
	
	// initialize I_h
	initializeIh(constraint_matrix, W, U, H, S);
	
	// initialize G_HDPC
	
	// MT
	byte[][] MT = generateMT(H, K, S);
	
	// GAMMA
	byte[][] GAMMA = generateGAMMA(K, S);
	
	// G_HDPC = MT * GAMMA
	byte[][] G_HDPC = Utilities.multiplyMatrices(MT, GAMMA);
	
	// initialize G_HDPC
	for (int row = S; row < S + H; row++)
	    for (int col = 0; col < W + U; col++)
		constraint_matrix[row][col] = G_HDPC[row - S][col];
	
	// initialize G_ENC
	initializeG_ENC(constraint_matrix, S, H, L, K);
	
	// return the constraint matrix
	return constraint_matrix;
    }
    
    /**
     * Generate the intermediate symbols
     * 
     * @param A
     * @param D
     * @param symbol_size
     * @param K
     * @return Byte array of intermediate symbols
     * @throws SingularMatrixException
     */
    private static byte[] generateIntermediateSymbols(byte[][] A, byte[][] D, int symbol_size, int K) throws SingularMatrixException {
	
	// Permanent Inactivation Decoding
	byte[] C = PInactivationDecoding(A, D, symbol_size, K);
	
	// return intermediate symbols
	return C;
    }
    
    /**
     * Solves the decoding system of linear equations using the permanent inactivation technique
     * 
     * @param A
     * @param D
     * @param symbol_size
     * @param K
     * @return
     * @throws SingularMatrixException
     */
    public static byte[] PInactivationDecoding(byte[][] A, byte[][] D, int symbol_size, int K) throws SingularMatrixException {
	
	// decoding parameters
	int Ki = SystematicIndices.getKIndex(K);
	int S = SystematicIndices.S(Ki);
	int H = SystematicIndices.H(Ki);
	int W = SystematicIndices.W(Ki);
	int L = K + S + H;
	int P = L - W;
	int M = A.length;
	
	/*
	 * initialize c and d vectors
	 */
	int[] c = new int[L];
	int[] d = new int[M];
	
	for (int i = 0; i < L; i++) {
	    c[i] = i;
	    d[i] = i;
	}
	
	for (int i = L; i < M; i++) {
	    d[i] = i;
	}
	
	// allocate X and copy A into X
	byte[][] X = new byte[M][L];
	
	for (int row = 0; row < M; row++)
	    System.arraycopy(A[row], 0, X[row], 0, L);
	
	// initialize i and u parameters, for the submatrices sizes
	int i = 0, u = P;
	
	/*
	 * DECODING
	 */
	
	/*
	 * First phase
	 */
	
	// counts how many rows have been chosen already
	int chosenRowsCounter = 0;
	
	// the number of rows that are not HDPC
	// (these should be chosen first)
	int nonHDPCRows = S + H;
	
	/*
	 * TODO Optimizacao: ao inves de percorrer isto todas as vezes, ver so as linhas que perderam um non-zero, e subtrair ao 'r' original (e ao grau). Como
	 * lidar com as novas dimensoes de V?
	 */
	
	// at most L steps
	while (i + u != L) {
	    
	    // number of non-zeros in the 'currently chosen' row
	    int r = L + 1;
	    
	    // the index of the 'currently chosen' row
	    int rLinha = 0;
	    
	    // the degree of the 'currently chosen' row
	    int minDegree = 256 * L;
	    
	    // maps the index of a row to an object Row (which stores that row's characteristics)
	    Map<Integer, Row> rows = new HashMap<Integer, Row>();
	    
	    // is the row HDPC or not?
	    boolean isHDPC, isHDPC2 = false;
	    
	    /*
	     * find r
	     */
	    
	    // go through all matrix rows counting non-zeros
	    for (int row = i, nonZeros = 0, degree = 0; row < M; row++, nonZeros = 0, degree = 0) {
		Set<Integer> edges = new HashSet<Integer>();
		
		// check all columns for non-zeros
		for (int col = i; col < L - u; col++) {
		    if ( A[row][col] == 0 ) // branch prediction
			continue;
		    else {
			// count the non-zero
			nonZeros++;
			
			// add to the degree of this row
			degree += OctectOps.UNSIGN(A[row][col]);
			
			edges.add(col);
		    }
		}
		
		// is this a HDPC row? this is not 100% accurate (false negatives) but is accurate enough)
		if ( degree <= nonZeros ) {
		    isHDPC = false;
		} else {
		    isHDPC = true;
		}
		
		if ( nonZeros == 2 && !isHDPC )
		    rows.put(row, new Row(row, nonZeros, degree, isHDPC, edges));
		else
		    rows.put(row, new Row(row, nonZeros, degree, isHDPC));
		
		if ( nonZeros > r || nonZeros == 0 || degree == 0 ) // branch prediction
		    continue;
		else {
		    if ( nonZeros == r ) {
			if ( degree < minDegree ) {
			    rLinha = row;
			    minDegree = degree;
			    isHDPC2 = isHDPC;
			} else
			    continue;
		    } else {
			r = nonZeros;
			rLinha = row;
			minDegree = degree;
			isHDPC2 = isHDPC;
		    }
		}
	    }
	    
	    if ( r == L + 1 ) // DECODING FAILURE
		throw new SingularMatrixException("Decoding Failure - PI Decoding @ Phase 1: All entries in V are zero.");
	    
	    /*
	     * choose the row
	     */
	    
	    if ( r != 2 ) {
		// "HDPC rows should not be chosen untill all non-HDPC rows have been processed"
		if ( isHDPC2 && chosenRowsCounter < nonHDPCRows ) { // if it is, then we must choose another line
		
		    int newDegree = 256 * L;
		    int newR = L + 1;
		    
		    // lets go search all the rows
		    for (Row row : rows.values()) {
			// if they're not HDPC rows
			if ( !row.isHDPC && row.degree != 0 ) {
			    // does it have less non-zeros than our 'currently chosen' one?
			    if ( row.nonZeros < newR ) {// if it does, since it's non-HDPC it'll also have a lower degree
			    
				// so lets update our 'currently chosen' row
				newR = row.nonZeros;
				rLinha = row.id;
				newDegree = row.degree;
			    }
			} else
			    continue;
		    }
		}
		
		// choose rLinha
		chosenRowsCounter++;
	    } else { // r = 2
	    
		// do we have a row with exactly two 1's? (remember we already know that there are rows with only 2 non-zeros)
		if ( minDegree == 2 ) {
		    /*
		     * create graph
		     */
		    
		    // allocate memory
		    Map<Integer, Set<Integer>> graph = new HashMap<Integer, Set<Integer>>();
		    
		    // lets go through all the rows... (yet again!)
		    for (Row row : rows.values()) {
			// is this row an edge?
			if ( row.edges != null ) {
			    // get the nodes connected through this edge
			    Integer[] edge = row.edges.toArray(new Integer[2]);
			    int node1 = edge[0];
			    int node2 = edge[1];
			    
			    // node1 already in graph?
			    if ( graph.keySet().contains(node1) ) { // it is
			    
				// then lets add node 2 to its neighbours
				graph.get(node1).add(node2);
			    } else { // it isn't
			    
				// allocate memory for its neighbours
				Set<Integer> edges = new HashSet<Integer>();
				
				// add node 2 to its neighbours
				edges.add(node2);
				
				// finally, add node 1 to the graph along with its neighbours
				graph.put(node1, edges);
			    }
			    
			    // node2 already in graph?
			    if ( graph.keySet().contains(node2) ) { // it is
			    
				// then lets add node 1 to its neighbours
				graph.get(node2).add(node1);
			    } else { // it isn't
			    
				// allocate memory for its neighbours
				Set<Integer> edges = new HashSet<Integer>();
				
				// add node 1 to its neighbours
				edges.add(node1);
				
				// finally, add node 2 to the graph along with its neighbours
				graph.put(node2, edges);
			    }
			} else
			    continue;
		    }
		    
		    /*
		     * the graph is complete, now we must find the maximum size component
		     */
		    
		    // have we found the maximum size component yet?
		    boolean found = false;
		    
		    // set of visited nodes
		    Set<Integer> visited = null;
		    
		    /*
		     * TODO Optimizacao: - ja procurei, e ha algoritmos optimizados para achar connected components e so depois ver qual o maior...
		     */
		    
		    // what is the size of the largest component we've already found
		    int maximumSize = 0;
		    
		    // the maximum size component
		    Set<Integer> greatestComponent = null; // TODO testar tempos com isto
		    
		    // which nodes have already been used (either in visited or in toVisit)
		    Set<Integer> used = new HashSet<Integer>();
		    
		    // iterates the nodes in the graph
		    Iterator<Map.Entry<Integer, Set<Integer>>> it = graph.entrySet().iterator();
		    
		    // let's iterate through the nodes in the graph, looking for the maximum
		    // size component. we will be doing a breadth first seach // TODO optimize this with a better algorithm?
		    while (it.hasNext() && !found) {
			// get our initial node
			Map.Entry<Integer, Set<Integer>> node = it.next();
			int initialNode = node.getKey();
			
			// we can't have used it before!
			if ( used.contains(initialNode) )
			    continue;
			
			// what are the edges of our initial node?
			Integer[] edges = (Integer[]) node.getValue().toArray(new Integer[1]);
			
			// allocate memory for the set of visited nodes
			visited = new HashSet<Integer>();
			
			// the set of nodes we must still visit
			List<Integer> toVisit = new LinkedList<Integer>();
			
			// add the initial node to the set of used and visited nodes
			visited.add(initialNode);
			used.add(initialNode);
			
			// add my edges to the set of nodes we must visit
			// and also put them in the used set
			for (Integer edge : edges) {
			    toVisit.add(edge);
			    used.add(edge);
			}
			
			// start the search!
			while (toVisit.size() != 0) {
			    // the node we are visiting
			    int no = toVisit.remove(0);
			    
			    // add node to visited set
			    visited.add(no);
			    
			    // queue edges to be visited (if they haven't been already
			    for (Integer edge : graph.get(no))
				if ( !visited.contains(edge) )
				    toVisit.add(edge);
			}
			
			// is the number of visited nodes, greater than the 'currently' largest component?
			if ( visited.size() > maximumSize ) { // it is! we've found a greater component then...
			
			    // update the maximum size
			    maximumSize = visited.size();
			    
			    // update our greatest component
			    greatestComponent = visited;
			} else
			    continue;
		    }
		    
		    /*
		     * we've found the maximum size connected component -- 'greatestComponent'
		     */
		    
		    // let's choose the row
		    for (Row row : rows.values()) {
			// is it a node in the graph?
			if ( row.edges != null ) { // it is
			
			    // get the nodes connected through this edge
			    Integer[] edge = row.edges.toArray(new Integer[2]);
			    int node1 = edge[0];
			    int node2 = edge[1];
			    
			    // is this row an edge in the maximum size component?
			    if ( greatestComponent.contains(node1) && greatestComponent.contains(node2) ) {
				rLinha = row.id;
				break;
			    } else
				continue;
			} else
			    continue;
		    }
		    
		    chosenRowsCounter++;
		} else { // no rows with 2 ones
		    chosenRowsCounter++;
		}
	    }
	    
	    /*
	     * a row has been chosen! -- 'rLinha'
	     */
	    
	    // get the chosen row
	    Row chosenRow = rows.get(rLinha);
	    
	    /*
	     * "After the row is chosen in this step, the first row of A that intersects V is exchanged with the chosen row so that the chosen row is the first
	     * row that intersects V."
	     */
	    
	    // if the chosen row is not 'i' already
	    if ( rLinha != i ) {
		// swap i with rLinha in A
		byte[] auxRow = A[i];
		A[i] = A[rLinha];
		A[rLinha] = auxRow;
		
		// swap i with rLinha in X
		auxRow = X[i];
		X[i] = X[rLinha];
		X[rLinha] = auxRow;
		
		// decoding process - swap i with rLinha in d
		int auxIndex = d[i];
		d[i] = d[rLinha];
		d[rLinha] = auxIndex;
	    }
	    
	    /*
	     * "The columns of A among those that intersect V are reordered so that one of the r nonzeros in the chosen row appears in the first column of V and
	     * so that the remaining r-1 nonzeros appear in the last columns of V."
	     */
	    
	    // if there are non-zeros
	    if ( chosenRow.degree > 0 ) {
		// stack of non-zeros in the chosen row
		Stack<Integer> nonZeros = new Stack();
		
		// search the chosen row for the positions of the non-zeros
		for (int nZ = 0, col = i; nZ < chosenRow.nonZeros; col++) // TODO the positions of the non-zeros could be stored as a Row attribute
		{ // this would spare wasting time in this for (little optimization)
		    if ( A[i][col] == 0 ) // a zero
			continue;
		    else { // a non-zero
			nZ++;
			
			// add this non-zero's position to the stack
			nonZeros.push(col);
		    }
		}
		
		/*
		 * lets start swapping columns!
		 */
		
		// swap a non-zero's column to the first column in V
		int column;
		if ( A[i][i] == 0 ) // is the first column in V already the place of a non-zero?
		{
		    // column to be swapped
		    column = nonZeros.pop();
		    
		    // swap columns
		    Utilities.swapColumns(A, column, i);
		    Utilities.swapColumns(X, column, i);
		    
		    // decoding process - swap i and column in c
		    int auxIndex = c[i];
		    c[i] = c[column];
		    c[column] = auxIndex;
		} else
		    // it is, so let's remove 'i' from the stack
		    nonZeros.remove((Integer) i);
		
		// swap the remaining non-zeros' columns so that they're the last columns in V
		for (int remainingNZ = nonZeros.size(); remainingNZ > 0; remainingNZ--) {
		    // column to be swapped
		    column = nonZeros.pop();
		    
		    // swap columns
		    Utilities.swapColumns(A, column, L - u - remainingNZ);
		    Utilities.swapColumns(X, column, L - u - remainingNZ);
		    
		    // decoding process - swap column with L-u-remainingNZ in c
		    int auxIndex = c[L - u - remainingNZ];
		    c[L - u - remainingNZ] = c[column];
		    c[column] = auxIndex;
		}
		
		/*
		 * "... if a row below the chosen row has entry beta in the first column of V, and the chosen row has entry alpha in the first column of V, then
		 * beta/alpha multiplied by the chosen row is added to this row to leave a zero value in the first column of V."
		 */
		
		// "the chosen row has entry alpha in the first column of V"
		byte alpha = A[i][i];
		
		// let's look at all rows below the chosen one
		for (int row = i + 1; row < M; row++) // TODO queue these row operations for when/if the row is chosen - Page35@RFC6330 1st Par.
		{
		    // if it's already 0, no problem
		    if ( A[row][i] == 0 )
			continue;
		    
		    // if it's a non-zero we've got to "zerofy" it
		    else {
			// "if a row below the chosen row has entry beta in the first column of V"
			byte beta = A[row][i];
			
			/*
			 * "then beta/alpha multiplied by the chosen row is added to this row"
			 */
			
			// division
			byte balpha = OctectOps.division(beta, alpha);
			
			// multiplication
			byte[] product = OctectOps.betaProduct(balpha, A[i]);
			
			// addition
			A[row] = Utilities.xorSymbol(A[row], product);
			
			// decoding process - (beta * D[d[i]]) + D[d[row]]
			product = OctectOps.betaProduct(balpha, D[d[i]]);
			D[d[row]] = Utilities.xorSymbol(D[d[row]], product);
		    }
		}
	    }
	    
	    /*
	     * "Finally, i is incremented by 1 and u is incremented by r-1, which completes the step."
	     */
	    i++;
	    u += r - 1;
	}
	// END OF FIRST PHASE
	
	/*
	 * Second phase
	 */
	
	/*
	 * "At this point, all the entries of X outside the first i rows and i columns are discarded, so that X has lower triangular form. The last i rows and
	 * columns of X are discarded, so that X now has i rows and i columns."
	 */
	
	/*
	 * "Gaussian elimination is performed in the second phase on U_lower either to determine that its rank is less than u (decoding failure) or to convert
	 * it into a matrix where the first u rows is the identity matrix (success of the second phase)."
	 */
	
	// reduce U_lower to row echelon form
	Utilities.reduceToRowEchelonForm(A, i, M, L - u, L, d, D);
	
	// check U_lower's rank, if it's less than 'u' we've got a decoding failure
	if ( !Utilities.validateRank(A, i, i, M, L, u) )
	    throw new SingularMatrixException("Decoding Failure - PI Decoding @ Phase 2: U_lower's rank is less than u.");
	
	/*
	 * "After this phase, A has L rows and L columns."
	 */
	
	// END OF SECOND PHASE
	
	/*
	 * Third phase
	 */
	
	/*
	 * "... the matrix X is multiplied with the submatrix of A consisting of the first i rows of A."
	 */
	byte[][] XA = Utilities.multiplyMatrices(X, 0, 0, i, i, A, 0, 0, i, L);
	
	// copy the product (XA) to A
	for (int row = 0; row < i; row++)
	    A[row] = XA[row];
	
	// decoding process
	byte[][] reorderD = new byte[L][];
	
	// create a copy of D
	for (int index = 0; index < L; index++)
	    reorderD[index] = D[d[index]];
	
	// multiply D by X
	for (int row = 0; row < i; row++)
	    // multiply X by D
	    D[d[row]] = Utilities.multiplyByteLineBySymbolVector(X[row], i, reorderD, T);
	
	/*
	 * Fourth phase
	 */
	
	/*
	 * "For each of the first i rows of U_upper, do the following: if the row has a nonzero entry at position j, and if the value of that nonzero entry is
	 * b, then add to this row b times row j of I_u."
	 */
	
	// "For each of the first i rows of U_upper"
	for (int row = 0; row < i; row++) {
	    for (int j = i; j < L; j++) {
		// "if the row has a nonzero entry at position j"
		if ( A[row][j] != 0 ) {
		    // "if the value of that nonzero entry is b"
		    byte b = A[row][j];
		    
		    // "add to this row b times row j" -- this would "zerofy" that position, thus we can save the complexity
		    A[row][j] = 0;
		    
		    // decoding process - (beta * D[d[j]]) + D[d[row]]
		    byte[] product = OctectOps.betaProduct(b, D[d[j]]);
		    D[d[row]] = Utilities.xorSymbol(D[d[row]], product);
		}
	    }
	}
	
	/*
	 * Fifth phase
	 */
	
	// TODO Optimizacao: acho que da para zerar directamente o A, e deixar apenas as operacoes em D...
	
	// "For j from 1 to i, perform the following operations:"
	for (int j = 0; j < i; j++) {
	    // "If A[j,j] is not one"
	    if ( A[j][j] != 1 ) {
		byte beta = A[j][j];
		
		// "then divide row j of A by A[j,j]."
		A[j] = OctectOps.betaDivision(A[j], beta);
		
		// decoding process - D[d[j]] / beta
		D[d[j]] = OctectOps.betaDivision(D[d[j]], beta);
	    }
	    
	    // "For l from 1 to j-1"
	    for (int l = 0; l < j; l++) {
		
		// "if A[j,l] is nonzero"
		if ( A[j][l] != 0 ) { // "then add A[j,l] multiplied with row l of A to row j of A."
		
		    byte beta = A[j][l];
		    
		    // multiply A[j][l] by row 'l' of A
		    byte[] product = OctectOps.betaProduct(beta, A[l]);
		    
		    // add the product to row 'j' of A
		    A[j] = Utilities.xorSymbol(A[j], product);
		    
		    // decoding process - D[d[j]] + (A[j][l] * D[d[l]])
		    product = OctectOps.betaProduct(beta, D[d[l]]);
		    D[d[j]] = Utilities.xorSymbol(D[d[j]], product);
		}
	    }
	}
	
	// allocate memory for the decoded symbols
	byte[] C = new byte[L * T];
	
	// copy the decoded source symbols from D to C
	for (int symbol = 0; symbol < L; symbol++)
	    System.arraycopy(D[d[symbol]], 0, C, c[symbol] * T, T);
	
	// return the decoded source symbols
	return C;
    }
    
    /**
     * Generates intermediate symbols for a given source block.
     * 
     * @param sb
     * @return Byte array of intermediate symbols
     * @throws SingularMatrixException
     */
    private byte[] generateIntermediateSymbols(SourceBlock sb) throws SingularMatrixException {
	// number source of symbols in the block
	byte[] ssymbols = sb.getSymbols();
	
	// source block's parameters
	int K = SystematicIndices.ceil((int) sb.getK());
	int Ki = SystematicIndices.getKIndex(K);
	int S = SystematicIndices.S(Ki);
	int H = SystematicIndices.H(Ki);
	int L = K + S + H;
	int k = sb.getK();
	int t = sb.getT();
	
	// generate LxL Constraint Matrix
	byte[][] constraint_matrix = generateConstraintMatrix(K, t); // A
	
	// allocate and initiallize vector D
	byte[][] D = new byte[L][t];
	for (int row = S + H, index = 0; row < k + S + H; row++, index += t)
	    D[row] = Arrays.copyOfRange(ssymbols, index, (index + t));
	
	// solve system of equations
	byte[] C = PInactivationDecoding(constraint_matrix, D, t, K);
	
	return C;
    }
    
    /**
     * Generate the tuples each source block in the given array.
     * 
     * @param object
     *            Array of source blocks.
     * @return Mapping of SourceBlock to array of tuples.
     */
    public Map<SourceBlock, Tuple[]> generateTuples(SourceBlock[] object) {
	// allocate memory for the map
	Map<SourceBlock, Tuple[]> sbTuple = new HashMap<SourceBlock, Tuple[]>();
	
	// for each block, calculate the tuples
	for (int i = 0; i < object.length; i++) {
	    // block i
	    SourceBlock sb = object[i];
	    
	    // number of source symbols (including padding)
	    int kLinha = sb.getSymbols().length;
	    
	    // allocate memory for the tuples
	    Tuple[] tuples = new Tuple[kLinha];
	    
	    // calculate the tuples
	    for (int j = 0; j < kLinha; j++) {
		tuples[j] = new Tuple(kLinha, j);
	    }
	    
	    // add to the map
	    sbTuple.put(sb, tuples);
	}
	
	return sbTuple;
    }
    
    /**
     * Encodes a source symbol.
     * 
     * @param K
     * @param C
     * @param tuple
     * @return
     */
    public byte[] enc(int K, byte[] C, Tuple tuple) {
	// necessary parameters
	int Ki = SystematicIndices.getKIndex(K);
	int S = SystematicIndices.S(Ki);
	int H = SystematicIndices.H(Ki);
	int W = SystematicIndices.W(Ki);
	long L = K + S + H;
	long P = L - W;
	int P1 = (int) Utilities.ceilPrime(P);
	long d = tuple.getD();
	int a = (int) tuple.getA();
	int b = (int) tuple.getB();
	long d1 = tuple.getD1();
	int a1 = (int) tuple.getA1();
	int b1 = (int) tuple.getB1();
	
	// allocate memory and initialize the encoding symbol
	byte[] result = Arrays.copyOfRange(C, (int) (b * T), (int) ((b + 1) * T));
	
	/*
	 * encoding -- refer to section 5.3.3.3 of RFC 6330
	 */
	
	for (long j = 0; j < d; j++) {
	    b = (b + a) % W;
	    result = Utilities.xorSymbol(result, 0, C, b * T, T);
	}
	
	while (b1 >= P)
	    b1 = (b1 + a1) % P1;
	
	result = Utilities.xorSymbol(result, 0, C, (W + b1) * T, T);
	
	for (long j = 1; j < d1; j++) {
	    do
		b1 = (b1 + a1) % P1;
	    while (b1 >= P);
	    
	    result = Utilities.xorSymbol(result, 0, C, (W + b1) * T, T);
	}
	
	return result;
    }
    
    /**
     * Returns the indexes of the intermediate symbols that should be XORed to encode the symbol for the given tuple.
     * 
     * @param K
     * @param tuple
     * @return Set of indexes.
     */
    public static Set<Integer> encIndexes(int K, Tuple tuple) {
	// allocate memory for the indexes
	Set<Integer> indexes = new TreeSet<Integer>();
	
	// parameters
	int Ki = SystematicIndices.getKIndex(K);
	int S = SystematicIndices.S(Ki);
	int H = SystematicIndices.H(Ki);
	int W = SystematicIndices.W(Ki);
	long L = K + S + H;
	long P = L - W;
	long P1 = Utilities.ceilPrime(P);
	
	// tuple parameters
	long d = tuple.getD();
	long a = tuple.getA();
	long b = tuple.getB();
	long d1 = tuple.getD1();
	long a1 = tuple.getA1();
	long b1 = tuple.getB1();
	
	/*
	 * simulated encoding -- refer to section 5.3.3.3 of RFC 6330
	 */
	
	indexes.add((int) b);
	
	for (long j = 0; j < d; j++) {
	    b = (b + a) % W;
	    indexes.add((int) b);
	}
	
	while (b1 >= P) {
	    b1 = (b1 + a1) % P1;
	}
	
	indexes.add((int) (W + b1));
	
	for (long j = 1; j < d1; j++) {
	    do
		b1 = (b1 + a1) % P1;
	    while (b1 >= P);
	    
	    indexes.add((int) (W + b1));
	}
	
	return indexes;
    }
    
    /*
     * IGNORE EVERYTHING BELOW THIS
     */
    
    /*
     * public static byte[] PInactivationDecoding(byte[][] A, byte[][] D, int symbol_size, int K) throws SingularMatrixException {
     * 
     * int Ki = SystematicIndices.getKIndex(K); int S = SystematicIndices.S(Ki); int H = SystematicIndices.H(Ki); int W = SystematicIndices.W(Ki); int L = K + S
     * + H; int P = L - W; int M = A.length;
     * 
     * 
     * /* SIMULATION
     */
    /*
     * K = 3; S = 1; H = 1; W = 4; L = 5; P = 1; M = 5; T = 3;
     * 
     * byte[][] zzz = { {1, 0, 1, 1, 1}, {0, 1, 0, 1, 1}, {1, 1, 1, 0, 1}, {1, 0, 1, 0, 0}, {1, 1, 0, 0, 1} }; A = zzz;
     * 
     * byte[][] sss = { {1, 0, 0}, {1, 0, 1}, {1, 1, 0}, {1, 1, 1}, {0, 1, 1} }; D = sss; /* END
     */
    /*
     * // initialize c and d int[] c = new int[L]; int[] d = new int[M];
     * 
     * for(int i = 0; i < L; i++){ c[i] = i; d[i] = i; }
     * 
     * for(int i = L; i < M; i++){ d[i] = i; }
     * 
     * // Allocate X and copy A into X byte[][] X = new byte[M][L]; for(int row = 0; row < M; row++) System.arraycopy(A[row], 0, X[row], 0, L);
     * 
     * int i = 0, u = P;
     * 
     * /* PRINTING BLOCK
     */
    /*
     * System.out.println("--------- A ---------"); (new Matrix(A)).show(); System.out.println("---------------------"); /* END OF PRINTING
     */
    
    /*
     * First phase
     */
    
    // firstPhase(i, u, S, H, L, A, X, D, c, d, M);
    
    // END OF FIRST PHASE
    
    /*
     * Second phase
     */
    // X is now ixi
    
    // secondPhase(A, i, M, L, u, d, D);
    
    // A is now LxL
    
    // END OF SECOND PHASE
    
    /*
     * Third phase
     */
    
    // thirdPhase(A, X, D, i, d, L);
    
    /* TEST BLOCK */
    /*
     * for(int row = 0; row < i; row++){ for(int col = 0; col < i; col++){ if(X[row][col] != A[row][col]){
     * System.out.println("ERRO NA FASE 3 (row: "+row+" ; col: "+col+" )"); System.out.println("--------- X ---------"); (new Matrix(X)).show();
     * System.out.println("---------------------"); System.out.println("--------- A ---------"); (new Matrix(A)).show();
     * System.out.println("---------------------"); System.exit(-543534); } } } /* END OF BLOCK
     */
    
    /* PRINTING BLOCK */
    /*
     * System.out.println("SPARSED U_upper"); System.out.println("--------- A ---------"); (new Matrix(A)).show(); System.out.println("---------------------");
     * /* System.out.println("--------- X ---------"); (new Matrix(X)).show(); System.out.println("---------------------"); /* END OF PRINTING
     */
    
    /*
     * Fourth phase
     */
    
    // fourthPhase(A, D, i, d, L);
    
    /* PRINTING BLOCK */
    /*
     * System.out.println("ZEROED U_upper"); System.out.println("--------- A ---------"); (new Matrix(A)).show(); System.out.println("---------------------");
     * /* END OF PRINTING
     */
    
    /*
     * Fifth phase
     */
    
    // fifthPhase(A, D, i, d);
    
    /* PRINTING BLOCK */
    /*
     * System.out.println("IDENTITY"); System.out.println("--------- A ---------"); (new Matrix(A)).show(); System.out.println("---------------------");
     * 
     * if(!checkIdentity(A,L)) System.exit(231); /* END OF PRINTING
     */
    /*
     * byte[] C = new byte[L*T]; for(int symbol = 0; symbol < L; symbol++) System.arraycopy(D[d[symbol]], 0, C, c[symbol]*T, T);
     * 
     * return C; }
     */
    
    private static void fifthPhase(byte[][] A, byte[][] D, int i, int[] d) {
	/*
	 * TODO Optimizacao: acho que da para zerar directamente o A, e deixar apenas as operacoes em D...
	 */
	
	for (int j = 0; j < i; j++) {
	    
	    if ( A[j][j] != 1 ) { // A[j][j] != 0
	    
		byte beta = A[j][j];
		A[j] = OctectOps.betaDivision(A[j], beta);
		
		// decoding process - D[d[j]] / beta
		D[d[j]] = OctectOps.betaDivision(D[d[j]], beta);
	    }
	    
	    for (int l = 0; l < j; l++) {
		
		if ( A[j][l] != 0 ) {
		    
		    byte beta = A[j][l];
		    byte[] product = OctectOps.betaProduct(beta, A[l]);
		    
		    A[j] = Utilities.xorSymbol(A[j], product);
		    
		    // decoding process - D[d[j]] + (A[j][l] * D[d[l]])
		    product = OctectOps.betaProduct(beta, D[d[l]]);
		    D[d[j]] = Utilities.xorSymbol(D[d[j]], product);
		    
		}
	    }
	}
    }
    
    private static void fourthPhase(byte[][] A, byte[][] D, int i, int[] d, int L) {
	
	for (int row = 0; row < i; row++) {
	    for (int j = i; j < L; j++) {
		if ( A[row][j] != 0 ) {
		    
		    byte b = A[row][j];
		    A[row][j] = 0;
		    
		    // decoding process - (beta * D[d[j]]) + D[d[row]]
		    byte[] product = OctectOps.betaProduct(b, D[d[j]]);
		    D[d[row]] = Utilities.xorSymbol(D[d[row]], product);
		}
	    }
	}
	
    }
    
    private static void thirdPhase(byte[][] A, byte[][] X, byte[][] D, int i, int[] d, int L) {
	
	// multiply X by A submatrix
	byte[][] XA = Utilities.multiplyMatrices(X, 0, 0, i, i, A, 0, 0, i, L);
	for (int row = 0; row < i; row++)
	    A[row] = XA[row];
	
	// decoding process
	byte[][] reordereD = new byte[L][];
	
	for (int index = 0; index < L; index++)
	    reordereD[index] = D[d[index]]; // reorder D
	
	for (int row = 0; row < i; row++)
	    // multiply X by D
	    D[d[row]] = Utilities.multiplyByteLineBySymbolVector(X[row], i, reordereD, T);
	
    }
    
    private static void secondPhase(byte[][] A, int i, int M, int L, int u, int[] d, byte[][] D) throws SingularMatrixException {
	
	Utilities.reduceToRowEchelonForm(A, i, M, L - u, L, d, D);
	
	/* PRINTING BLOCK */
	/*
	 * System.out.println("GAUSSIAN U_lower"); System.out.println("M: "+M+"\nL: "+L+"\ni: "+i+"\nu: "+u); System.out.println("--------- A ---------"); (new
	 * Matrix(A)).show(); System.out.println("---------------------"); /* END OF PRINTING
	 */
	
	if ( !Utilities.validateRank(A, i, i, M, L, u) ) // DECODING FAILURE
	    throw new SingularMatrixException("Decoding Failure - PI Decoding @ Phase 2: U_lower's rank is less than u.");
	
    }
    
    private static void firstPhase(int i, int u, int S, int H, int L, byte[][] A, byte[][] X, byte[][] D, int[] c, int[] d, int M)
	    throws SingularMatrixException {
	
	int chosenRowsCounter = 0;
	int nonHDPCRows = S + H;
	
	// Map<Integer, Integer> originalDegrees = new HashMap<Integer, Integer>();
	
	/*
	 * TODO Optimizaďż˝ďż˝o: ao inves de percorrer isto todas as vezes, ver sďż˝ as linhas quer perderam um non-zero, e subtrair ao 'r' original. Como lidar com
	 * as novas dimensoes de V?
	 */
	
	while (i + u != L) {
	    
	    /* PRINTING BLOCK */
	    // System.out.println("STEP: "+i);
	    /* END OF PRINTING */
	    
	    int r = L + 1, rLinha = 0;
	    Map<Integer, Row> rows = new HashMap<Integer, Row>();
	    int minDegree = 256 * L;
	    
	    // find r
	    
	    // r = findR();
	    
	    for (int row = i, nonZeros = 0, degree = 0; row < M; row++, nonZeros = 0, degree = 0) {
		
		Set<Integer> edges = new HashSet<Integer>();
		
		for (int col = i; col < L - u; col++) {
		    if ( A[row][col] == 0 ) // branch prediction
			continue;
		    else {
			nonZeros++;
			degree += OctectOps.UNSIGN(A[row][col]);
			edges.add(col);
		    }
		}
		
		// if(nonZeros == 2 && (row < S || row >= S+H))
		// rows.put(row, new Row(row, nonZeros, degree, edges));
		// else
		// rows.put(row, new Row(row, nonZeros, degree));
		
		/*
		 * // TODO testar tempos com isto o.O if(i != 0){
		 * 
		 * if(nonZeros == 2 && (row < S || row >= S+H)) // FIXME do some branch prediction rows.put(row, new Row(row, nonZeros,
		 * originalDegrees.get(d[row]), edges)); else rows.put(row, new Row(row, nonZeros, originalDegrees.get(d[row])));
		 * 
		 * } else{
		 * 
		 * if(nonZeros == 2 && (row < S || row >= S+H)) rows.put(row, new Row(row, nonZeros, degree, edges)); else rows.put(row, new Row(row, nonZeros,
		 * degree));
		 * 
		 * originalDegrees.put(row, degree); }
		 */
		
		if ( nonZeros > r || nonZeros == 0 || degree == 0 ) // branch prediction
		    continue;
		else {
		    if ( nonZeros == r ) {
			if ( degree < minDegree ) {
			    rLinha = row;
			    minDegree = degree;
			}
		    } else {
			r = nonZeros;
			rLinha = row;
			minDegree = degree;
		    }
		}
	    }
	    
	    /* PRINTING BLOCK */
	    /**
	     * System.out.println("r: "+r); /* END OF PRINTING
	     */
	    
	    if ( r == L + 1 ) // DECODING FAILURE
		throw new SingularMatrixException("Decoding Failure - PI Decoding @ Phase 1: All entries in V are zero.");
	    
	    // choose the row
	    if ( r != 2 ) {
		// check if rLinha is OK
		if ( rLinha >= S && rLinha < S + H && chosenRowsCounter != nonHDPCRows ) { // choose another line
		
		    int newDegree = 256 * L;
		    int newR = L + 1;
		    
		    for (Row row : rows.values()) {
			
			if ( (row.id < S || row.id >= S + H) && row.degree != 0 ) {
			    if ( row.nonZeros <= newR ) {
				if ( row.nonZeros == newR ) {
				    if ( row.degree < newDegree ) {
					rLinha = row.id;
					newDegree = row.degree;
				    }
				} else {
				    newR = row.nonZeros;
				    rLinha = row.id;
				    newDegree = row.degree;
				}
			    }
			} else
			    continue;
		    }
		}
		// choose rLinha
		chosenRowsCounter++;
	    } else {
		
		if ( minDegree == 2 ) {
		    
		    // create graph
		    Map<Integer, Set<Integer>> graph = new HashMap<Integer, Set<Integer>>();
		    
		    for (Row row : rows.values()) {
			
			// edge?
			if ( row.edges != null ) {
			    
			    Integer[] edge = row.edges.toArray(new Integer[2]);
			    int node1 = edge[0];
			    int node2 = edge[1];
			    
			    // node1 already in graph?
			    if ( graph.keySet().contains(node1) ) {
				
				graph.get(node1).add(node2);
			    } else {
				Set<Integer> edges = new HashSet<Integer>();
				
				edges.add(node2);
				graph.put(node1, edges);
			    }
			    
			    // node2 already in graph?
			    if ( graph.keySet().contains(node2) ) {
				
				graph.get(node2).add(node1);
			    } else {
				Set<Integer> edges = new HashSet<Integer>();
				
				edges.add(node1);
				graph.put(node2, edges);
			    }
			} else
			    continue;
		    } // graph'd
		    
		    // find largest component
		    // int maximumSize = graph.size();
		    boolean found = false;
		    Set<Integer> visited = null;
		    
		    /*
		     * TODO Optimizaďż˝ao: - tentar fazer sem este while... nao ha de ser dificil - jďż˝ procurei, e ha algoritmos optimizados para achar connected
		     * components ďż˝ sďż˝ depois ver qual o maior...
		     */
		    
		    // while(maximumSize != 0 && !found){
		    
		    int maximumSize = 0;
		    // Set<Integer> greatestComponent = null; // TODO testar tempos com isto o.O
		    
		    Set<Integer> used = new HashSet<Integer>();
		    Iterator<Map.Entry<Integer, Set<Integer>>> it = graph.entrySet().iterator();
		    
		    while (it.hasNext() && !found) { // going breadth first, TODO optimize this with a better algorithm
		    
			Map.Entry<Integer, Set<Integer>> node = it.next();
			int initialNode = node.getKey();
			
			if ( used.contains(initialNode) )
			    continue;
			
			Integer[] edges = (Integer[]) node.getValue().toArray(new Integer[1]);
			visited = new HashSet<Integer>();
			List<Integer> toVisit = new LinkedList<Integer>();
			
			// add self
			visited.add(initialNode);
			used.add(initialNode);
			
			// add my edges
			for (Integer edge : edges) {
			    toVisit.add(edge);
			    used.add(edge);
			}
			
			// start visiting
			while (toVisit.size() != 0) {
			    
			    int no = toVisit.remove(0);
			    
			    // add node to visited set
			    visited.add(no);
			    
			    // queue edges
			    for (Integer edge : graph.get(no))
				if ( !visited.contains(edge) )
				    toVisit.add(edge);
			}
			
			if ( visited.size() > maximumSize ) {
			    
			    maximumSize = visited.size();
			    // greatestComponent = visited;
			}
			
			/*
			 * // is it big? if(visited.size() >= maximumSize) // yes it is found = true;
			 */
		    }/*
		      * 
		      * maximumSize--; }
		      */
		    
		    // 'visited' is now our connected component
		    for (Row row : rows.values()) {
			
			if ( row.edges != null ) {
			    
			    Integer[] edge = row.edges.toArray(new Integer[2]);
			    int node1 = edge[0];
			    int node2 = edge[1];
			    
			    if ( visited.contains(node1) && visited.contains(node2) ) { // found 2 ones (edge) in component
				rLinha = row.id;
				break;
			    } else
				continue;
			} else
			    continue;
		    }
		    
		    chosenRowsCounter++;
		} else { // no rows with 2 ones
		    chosenRowsCounter++;
		}
	    }
	    
	    /* PRINT ROWS */
	    /*
	     * for(Row row : rows.values()){ System.out.println("id: "+row.id+"  nZ: "+row.nonZeros+"  deg: "+row.degree); } /* END OF PRINT
	     */
	    
	    // 'rLinha' is the chosen row
	    Row chosenRow = rows.get(rLinha);
	    
	    /* PRINTING BLOCK */
	    /*
	     * System.out.println("----- CHOSEN ROW -----"); System.out.println("id : "+chosenRow.id); System.out.println("nZ : "+chosenRow.nonZeros);
	     * System.out.println("deg: "+chosenRow.degree); System.out.println("----------------------"); /* END OF PRINTING
	     */
	    
	    if ( rLinha != i ) {
		
		// swap i with rLinha in A
		byte[] auxRow = A[i];
		A[i] = A[rLinha];
		A[rLinha] = auxRow;
		
		// swap i with rLinha in X
		auxRow = X[i];
		X[i] = X[rLinha];
		X[rLinha] = auxRow;
		
		// decoding process - swap i with rLinha in d
		int auxIndex = d[i];
		d[i] = d[rLinha];
		d[rLinha] = auxIndex;
		
		/* PRINTING BLOCK */
		/*
		 * System.out.println("TROCA DE LINHA: "+i+" by "+ rLinha); System.out.println("--------- A ---------"); (new Matrix(A)).show();
		 * System.out.println("---------------------"); /* END OF PRINTING
		 */
	    }
	    
	    // re-order columns
	    if ( chosenRow.degree > 0 ) {
		Stack<Integer> nonZeros = new Stack<Integer>();
		for (int nZ = 0, col = i; nZ < chosenRow.nonZeros; col++) {
		    
		    if ( A[i][col] == 0 )
			continue;
		    else {
			nZ++;
			nonZeros.push(col);
		    }
		}
		
		int coluna;
		if ( A[i][i] == 0 ) {
		    
		    coluna = nonZeros.pop();
		    Utilities.swapColumns(A, coluna, i);
		    Utilities.swapColumns(X, coluna, i);
		    
		    // decoding process - swap i and coluna in c
		    int auxIndex = c[i];
		    c[i] = c[coluna];
		    c[coluna] = auxIndex;
		    
		    /* PRINTING BLOCK */
		    /*
		     * System.out.println("TROCA DE COLUNA: "+i+" by "+ coluna); System.out.println("--------- A ---------"); (new Matrix(A)).show();
		     * System.out.println("---------------------"); /* END OF PRINTING
		     */
		} else
		    nonZeros.remove((Integer) i);
		
		for (int remainingNZ = nonZeros.size(); remainingNZ > 0; remainingNZ--) {
		    
		    coluna = nonZeros.pop();
		    
		    // swap
		    Utilities.swapColumns(A, coluna, L - u - remainingNZ);
		    Utilities.swapColumns(X, coluna, L - u - remainingNZ);
		    
		    // decoding process - swap coluna with L-u-remainingNZ in c
		    int auxIndex = c[L - u - remainingNZ];
		    c[L - u - remainingNZ] = c[coluna];
		    c[coluna] = auxIndex;
		    
		    /* PRINTING BLOCK */
		    /*
		     * System.out.println("TROCA DE COLUNA: "+(L-u-remainingNZ)+" by "+ coluna); System.out.println("--------- A ---------"); (new
		     * Matrix(A)).show(); System.out.println("---------------------"); /* END OF PRINTING
		     */
		    
		}
		
		// beta/alpha gewdness
		byte alpha = A[i][i];
		
		for (int row = i + 1; row < M; row++) {
		    
		    if ( A[row][i] == 0 )
			continue;
		    else { // TODO Queue these row operations for when (if) the row is chosen - RFC 6330 @ Page 35 1st Par.
		    
			// beta/alpha
			byte beta = A[row][i];
			byte balpha = OctectOps.division(beta, alpha);
			
			// multiplication
			byte[] product = OctectOps.betaProduct(balpha, A[i]);
			
			// addition
			A[row] = Utilities.xorSymbol(A[row], product);
			
			// decoding process - (beta * D[d[i]]) + D[d[row]]
			product = OctectOps.betaProduct(balpha, D[d[i]]);
			D[d[row]] = Utilities.xorSymbol(D[d[row]], product);
			
			/* PRINTING BLOCK */
			/*
			 * System.out.println("ELIMINATING"); System.out.println("--------- A ---------"); (new Matrix(A)).show();
			 * System.out.println("---------------------"); /* END OF PRINTING
			 */
		    }
		}
	    }
	    
	    /* PRINTING BLOCK */
	    /*
	     * System.out.println("END OF STEP "+i); System.out.println("--------- A ---------"); (new Matrix(A)).show();
	     * System.out.println("---------------------"); /* END OF PRINTING
	     */
	    
	    // update 'i' and 'u'
	    i++;
	    u += r - 1;
	}
    }
    
    /*
     * private static int[] findR() {
     * 
     * for(int row = i, nonZeros = 0, degree = 0; row < M; row++, nonZeros = 0, degree = 0){
     * 
     * Set<Integer> edges = new HashSet<Integer>();
     * 
     * for(int col = i; col < L-u; col++){ if(A[row][col] == 0) // branch prediction continue; else{ nonZeros++; degree += OctectOps.UNSIGN(A[row][col]);
     * edges.add(col); } }
     * 
     * 
     * if(nonZeros == 2 && (row < S || row >= S+H)) rows.put(row, new Row(row, nonZeros, degree, edges)); else rows.put(row, new Row(row, nonZeros, degree));
     * 
     * /* // TODO testar tempos com isto o.O if(i != 0){
     * 
     * if(nonZeros == 2 && (row < S || row >= S+H)) // FIXME do some branch prediction rows.put(row, new Row(row, nonZeros, originalDegrees.get(d[row]),
     * edges)); else rows.put(row, new Row(row, nonZeros, originalDegrees.get(d[row])));
     * 
     * } else{
     * 
     * if(nonZeros == 2 && (row < S || row >= S+H)) rows.put(row, new Row(row, nonZeros, degree, edges)); else rows.put(row, new Row(row, nonZeros, degree));
     * 
     * originalDegrees.put(row, degree); }
     */
    /*
     * if(nonZeros > r || nonZeros == 0 || degree == 0) // branch prediction continue; else{ if(nonZeros == r){ if(degree < minDegree){ rLinha = row; minDegree
     * = degree; } } else{ r = nonZeros; rLinha = row; minDegree = degree; } } }
     * 
     * int[] ret = new int[3]; ret[0] = r; ret[1] = rLinha; ret[2] = minDegree;
     * 
     * return ret; }
     */
}