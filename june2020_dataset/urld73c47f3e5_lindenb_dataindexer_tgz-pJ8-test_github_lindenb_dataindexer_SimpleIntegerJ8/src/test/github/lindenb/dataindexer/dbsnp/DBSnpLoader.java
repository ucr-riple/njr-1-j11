package test.github.lindenb.dataindexer.dbsnp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import com.github.lindenb.dataindexer.PrimaryConfig;
import com.github.lindenb.dataindexer.PrimaryDataIndexWriter;
import com.github.lindenb.dataindexer.PrimaryDatabaseReader;
import com.github.lindenb.dataindexer.SecondaryConfig;
import com.github.lindenb.dataindexer.SecondaryDataWriter;
import com.github.lindenb.dataindexer.SecondaryDatabaseReader;
import com.github.lindenb.dataindexer.SecondaryKeyCreator;
import com.github.lindenb.dataindexer.TupleBinding;

public class DBSnpLoader
	{
	protected static final Logger LOG=Logger.getLogger("com.github.lindenb.dataindexer");

	private File dbSnp137File;
	
	private static class Rs
		{
		int rs_id;
		Rs(int rs_id)
			{
			this.rs_id=rs_id;
			}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + rs_id;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Rs other = (Rs) obj;
			if (rs_id != other.rs_id)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "rs"+rs_id;
			}
		}
	
	private static class Snp
		{
		int bin;
		String chrom;
		int chromStart;
		int chromEnd;
		Rs rs_id;
		@Override
		public String toString() {
			return "rs"+rs_id+" "+chrom+":"+chromStart+"-"+chromEnd;
			}
		}
	private static class SnpBinding
		implements TupleBinding<Snp>
		{
		@Override
		public Snp readObject(DataInputStream in) throws IOException
			{
			Snp snp=new Snp();
			snp.bin=in.readInt();
			snp.chrom=in.readUTF();
			snp.chromStart=in.readInt();
			snp.chromEnd=in.readInt();
			snp.rs_id=new Rs(in.readInt());
			return snp;
			}
		@Override
		public void writeObject(final Snp o, DataOutputStream out) throws IOException {
			out.writeInt(o.bin);
			out.writeUTF(o.chrom);
			out.writeInt(o.chromStart);
			out.writeInt(o.chromEnd);
			out.writeInt(o.rs_id.rs_id);
			}
		}
	
	private BufferedReader opendbSnp137()
		throws IOException
		{
		InputStream in=null;
		if(dbSnp137File.exists())
			{
			in=new FileInputStream(this.dbSnp137File);
			}
		else
			{
			in=new URL("http://hgdownload.cse.ucsc.edu/goldenPath/hg19/database/snp137.txt.gz").openStream();
			}
		return new BufferedReader(new InputStreamReader(new GZIPInputStream(in)));
		}
	
	public void test()
		throws IOException
		{
		File homDir=new File("tmp.dbsnp");
		homDir.mkdir();
		PrimaryConfig<Snp> config=new PrimaryConfig<Snp>();
		config.setName("tmp.dbsnp");
		LOG.setLevel(Level.ALL);
		config.setHomeDirectory(homDir);
		config.setDataBinding(new SnpBinding());
		PrimaryDataIndexWriter<Snp> primaryWriter=new PrimaryDataIndexWriter<DBSnpLoader.Snp>( config );
		
		SecondaryConfig<Snp, Rs> cfg2=new SecondaryConfig<Snp, Rs>();
		cfg2.setName("rs");
		cfg2.setBufferSize(1000000);
		cfg2.setComparator(new Comparator<Rs>() {
			@Override
			public int compare(Rs arg0, Rs arg1)
				{
				return arg0.rs_id-arg1.rs_id;
				}
			});
		cfg2.setKeyBinding(new TupleBinding<Rs>() {
			@Override
			public Rs readObject(DataInputStream in) throws IOException {
				return new Rs(in.readInt());
				}
			@Override
			public void writeObject(Rs o, DataOutputStream out)
					throws IOException {
				out.writeInt(o.rs_id);
				}
			});
		cfg2.setKeyCreator(new SecondaryKeyCreator<DBSnpLoader.Snp, Rs>()
			{
			@Override
			public Set<Rs> getSecondaryKeys(Snp t) {
				Set<Rs> S= new HashSet<Rs>(1);
				S.add(t.rs_id);
				return S;
				}
			});
		SecondaryDataWriter<Snp, Rs> rs2snp=new SecondaryDataWriter<DBSnpLoader.Snp, Rs>(cfg2,primaryWriter);
		
		
		long nLine=0;
		BufferedReader in=opendbSnp137();
		String line;
		Pattern tab=Pattern.compile("[\t]");
		while((line=in.readLine())!=null)
			{
			if(++nLine>100000) break;
			if(nLine%10000==0) System.err.println("count:"+nLine);
			String tokens[]=tab.split(line);
			Snp snp=new Snp();
			snp.bin=Integer.parseInt(tokens[0]);
			snp.chrom=tokens[1];
			snp.chromStart=Integer.parseInt(tokens[2]);
			snp.chromEnd=Integer.parseInt(tokens[3]);
			snp.rs_id=new Rs(Integer.parseInt(tokens[4].substring(2)));
			primaryWriter.insert(snp);
			}
		in.close();
		primaryWriter.close();
		
		Random rand=new Random(System.currentTimeMillis());
		PrimaryDatabaseReader<Snp> primaryDatabaseReader=new PrimaryDatabaseReader<DBSnpLoader.Snp>(config);
		primaryDatabaseReader.open();
		List<Rs> L=new ArrayList<Rs>();
		for(int i=0;i< 10;++i)
			{
			int index=rand.nextInt((int)primaryDatabaseReader.size());
			Snp snp=primaryDatabaseReader.get(index);
			System.out.println(snp);
			L.add(snp.rs_id);
			}
		SecondaryDatabaseReader<Snp, Rs> r2=new SecondaryDatabaseReader<DBSnpLoader.Snp,Rs>(primaryDatabaseReader, cfg2);
		r2.open();
		for(Rs i:L)
			{
			System.out.println("rs"+i);
			for(Rs i2:r2.getList(i))
				{
				System.out.println(r2.getList(i2));
				System.out.println(r2.getPrimaryKeyList(i2));
				System.out.println(r2.getPrimaryList(i2));
				}
			
			}
		
		primaryDatabaseReader.close();
		}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
		{
		DBSnpLoader app=new DBSnpLoader();
		app.dbSnp137File=new File("/home/lindenb/src/cardioserve/snp137.txt.gz");
		app.test();
		
		}

	}
