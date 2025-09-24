import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import RQLibrary.Encoder;
import RQLibrary.EncodingPacket;
import RQLibrary.EncodingSymbol;
import RQLibrary.Partition;
import RQLibrary.SingularMatrixException;
import RQLibrary.SourceBlock;

public class Thread_traitement {
	private int overhead;
	private int Kt;
	private int no_blocks;
	private int total_symbols;
	private int KL;
	private int KS;
	private int ZL;
	private Encoder encoder;
	private boolean successfulDecoding;
	private byte[] decoded_data;
	private SourceBlock[] blocks;
	private String fileName;

	final static int size_int = 4;
	final static int ACK = -2;
	final static int NACK = -1;

	private Set<EncodingSymbol> received_packets;

	Thread_traitement(int fileSize, String fileName) {
		this.fileName=fileName;
		this.overhead = 0;
		this.encoder = new Encoder((int) fileSize);
		this.Kt = encoder.getKt();
		this.no_blocks = encoder.Z;
		this.set_total_symbols(Kt + no_blocks * overhead);
		Partition KZ = new Partition(Kt, no_blocks);
		this.KL = KZ.get(1);
		this.KS = KZ.get(2);
		this.ZL = KZ.get(3);
		received_packets = new HashSet<EncodingSymbol>();
		decoded_data = null;
		blocks = new SourceBlock[no_blocks];

	}

	public int get_total_symbols() {
		return total_symbols;
	}

	public void set_total_symbols(int total_symbols) {
		this.total_symbols = total_symbols;
	}

	public void treatement(byte[] packetData) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(packetData);
		ObjectInput in = null;
		try {
			in = new ObjectInputStream(bis);
			this.received_packets.add((EncodingSymbol) in.readObject());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public int decodage() {

		int maxESI = -1;

		for (EncodingSymbol es : received_packets)
			if (es.getESI() > maxESI)
				maxESI = es.getESI();

		Iterator<EncodingSymbol> it = received_packets.iterator();
		EncodingSymbol[][] aux = new EncodingSymbol[no_blocks][maxESI + 1];
		while (it.hasNext()) {
			EncodingSymbol pack = it.next();
			aux[pack.getSBN()][pack.getESI()] = pack;
		}

		byte[] decoded_data = null;

		// where the source blocks will be stored


		// for each block
		for (int sblock = 0; sblock < no_blocks; sblock++) {
			System.out.println("\nDecoding block: " + sblock);
			try {
				// get the time before decoding

				long before = System.currentTimeMillis();

				// decode
				if (sblock < ZL)
					blocks[sblock] = Encoder.decode(new EncodingPacket(0,
							aux[sblock], KL, Encoder.MAX_PAYLOAD_SIZE));
				else
					blocks[sblock] = Encoder.decode(new EncodingPacket(0,
							aux[sblock], KS, Encoder.MAX_PAYLOAD_SIZE));

				// get time after decoding
				long after = System.currentTimeMillis();

				long diff = (long) (after - before);
				System.out.println("\nSuccessfuint lly decoded block: "
						+ sblock + " (in " + diff + " milliseconds)");

			} catch (SingularMatrixException e) {
				System.out.println("\nDecoding failed!");
				successfulDecoding = false;
				return NACK;
			} catch (RuntimeException e) {
				// nb of missing packets
				int nb_packets_lost = Integer.parseInt(e.getMessage());
				this.successfulDecoding = false;
				return nb_packets_lost;
				// nb_of_utils_packet = (int) Math.round((float) nb_packets_lost
				// * redondance);
			}
			// ///////////////////////////////////////////////////////////
			if (successfulDecoding) {
				continue;

			}

		}
		return -2;
	}

	public void reconstruction() throws IOException{
			decoded_data = this.encoder.unPartition(blocks);
			File file = new File(this.fileName);

			try {
				if (file.exists())
					file.createNewFile();
				}
			catch (IOException e) {
				System.err.println("Could not open file.");
				e.printStackTrace();
				System.exit(1);
			}
				Files.write(file.toPath(), decoded_data);
	}

}
