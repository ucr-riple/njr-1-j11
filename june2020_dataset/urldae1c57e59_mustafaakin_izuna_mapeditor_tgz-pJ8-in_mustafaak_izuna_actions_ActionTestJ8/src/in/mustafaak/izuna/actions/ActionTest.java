package in.mustafaak.izuna.actions;

import in.mustafaak.izuna.meta.LevelInfo;
import in.mustafaak.izuna.meta.LevelList;
import in.mustafaak.izuna.meta.WaveEnemy;
import in.mustafaak.izuna.meta.WaveInfo;
import in.mustafaak.izuna.meta.WavePath;
import in.mustafaak.izuna.waves.*;
import in.mustafaak.izuna.waves.set.*;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import org.simpleframework.xml.core.Persister;

public class ActionTest {
	public static void main(String[] args) {
		WaveInfo waveInfo = new WaveInfo();

		/*
		 * RowColEnter rce1 = new RowColEnter(RowColEnter.FROM_LEFT, 100, 150,
		 * 3, 1, 100, 100, "a1", 1000); RowColEnter rce2 = new
		 * RowColEnter(RowColEnter.FROM_UP, 150, 100, 1, 3, 150, 100, "c1",
		 * 1000); RowColEnter rce3 = new RowColEnter(RowColEnter.FROM_RIGHT,500,
		 * 150, 3, 1, 100, 100, "a3", 1000);
		 * 
		 * CircularInvade ci1 = new CircularInvade(4, -200, -200, 400, 400, 200,
		 * "b1", 3000) ;
		 */
		// Invade i1 = new Invade(4, 800,-200, -250, 550, 150, 300, 200, 100,
		// "a1", 2000);

		LevelList list = new LevelList();
		ArrayList<LevelInfo> levels = new ArrayList<LevelInfo>();

		LevelInfo l1 = constructLevel(0, "A", A.w1(), A.w2(), A.w3(), A.w4(), A.w5(), A.w6(), A.w7(), A.w8(), A.w9(),
				A.w10());
		LevelInfo l2 = constructLevel(1, "B", B.w1(), B.w2(), B.w3(), B.w4(), B.w5(), B.w6(), B.w7(), B.w8(), B.w9(),
				B.w10());
		LevelInfo l3 = constructLevel(2, "C", C.w1(), C.w2(), C.w3(), C.w4(), C.w5(), C.w6(), C.w7(), C.w8(), C.w9(),
				C.w10());
		LevelInfo l4 = constructLevel(3, "D", D.w1(), D.w2(), D.w3(), D.w4(), D.w5(), D.w6(), D.w7(), D.w8(), D.w9(),
				D.w10());
		LevelInfo l5 = constructLevel(4, "E", E.w1(), E.w2(), E.w3(), E.w4(), E.w5(), E.w6(), E.w7(), E.w8(), E.w9(),
				E.w10());

		Persister serializer = new Persister();
		try {
			serializer.write(l1, new File("level_" + 0 + ".xml"));
			serializer.write(l2, new File("level_" + 1 + ".xml"));
			serializer.write(l3, new File("level_" + 2 + ".xml"));
			serializer.write(l4, new File("level_" + 3 + ".xml"));
			serializer.write(l5, new File("level_" + 4 + ".xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void streamWave(WaveInfo waveInfo) {
		try {
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress("192.168.1.104", 5000), 50000);
			Persister serializer = new Persister();
			OutputStream os = socket.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(os);

			serializer.write(waveInfo, bos);
			serializer.write(waveInfo, System.out);
			os.flush();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static LevelInfo constructLevel(int no, String name, WaveInfo... waves) {
		LevelInfo l = new LevelInfo();
		ArrayList<WaveInfo> w = new ArrayList<WaveInfo>();
		for (WaveInfo wave : waves) {
			w.add(wave);
		}
		l.setName(name);
		l.setNo(no);
		l.setPassword("***");
		l.setWaves(w);
		return l;
	}

}
