package test.github.lindenb.dataindexer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.github.lindenb.dataindexer.*;

public class SimpleInteger
	{
	private void test() throws IOException
		{
		File homeDir=new File("jeter.tmp.test.simpleInteger");
		homeDir.mkdirs();
		PrimaryConfig<Integer> cfg1=new PrimaryConfig<Integer>();
		cfg1.setHomeDirectory(homeDir);
		cfg1.setName("primary");
		cfg1.setDataBinding(new TupleBinding<Integer>()
			{
				@Override
				public void writeObject(Integer o, DataOutputStream out)
						throws IOException
					{
					out.writeInt(o);
					}
				
				@Override
				public Integer readObject(DataInputStream in) throws IOException
					{
					return in.readInt();
					}
			});
		SecondaryConfig<Integer, Integer> cfg2=new SecondaryConfig<Integer, Integer>();
		cfg2.setComparator(new NaturalComparator<Integer>());
		cfg2.setKeyBinding(new TupleBinding<Integer>()
			{
			@Override
			public void writeObject(Integer o, DataOutputStream out)
					throws IOException
				{
				out.writeInt(o);
				}
			
			@Override
			public Integer readObject(DataInputStream in) throws IOException
				{
				return in.readInt();
				}
		});
		cfg2.setName("idx2");
		cfg2.setKeyCreator(new SecondaryKeyCreator<Integer, Integer>()
			{
			@Override
			public Set<Integer> getSecondaryKeys(Integer t)
				{
				Set<Integer> set=new HashSet<Integer>();
				set.add(t);
				return set;
				}
			});
		Random rand=new Random(System.currentTimeMillis());
		PrimaryDataIndexWriter<Integer> primary=new PrimaryDataIndexWriter<Integer>(cfg1);
		SecondaryDataWriter<Integer, Integer> secondary=new  SecondaryDataWriter<Integer, Integer>(cfg2,primary);
		
		for(int i=0;i< 10;i+=2)
			{
			int n=1+rand.nextInt(10);
			for(int j=0;j<1;++j)
				{
				primary.insert(i);
				}
			}
		secondary.close();
		primary.close();
		
		
		PrimaryDatabaseReader<Integer> r=new PrimaryDatabaseReader<Integer>(cfg1);
		SecondaryDatabaseReader<Integer, Integer> r2=new SecondaryDatabaseReader<Integer, Integer>(r, cfg2);
		r.open();
		r2.open();
		for(Integer i: r.asList())
			{
			System.out.println(i);
			}
		for(long i=0;i< r2.size();++i)
			{
			System.out.println("r2"+r2.get(i));
			}
		r.close();
		
		System.out.println("done");
		}
	/**
	 * @param args
	 */
	public static void main(String[] args)
		{
		try
		{
		SimpleInteger app=new SimpleInteger();
		app.test();
		}
		catch(IOException err)
			{
			err.printStackTrace();
			}
		}

	}
