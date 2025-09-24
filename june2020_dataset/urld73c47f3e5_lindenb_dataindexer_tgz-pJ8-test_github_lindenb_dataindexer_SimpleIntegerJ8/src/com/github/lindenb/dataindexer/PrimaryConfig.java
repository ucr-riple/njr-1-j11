package com.github.lindenb.dataindexer;


public class PrimaryConfig<T> extends AbstractConfig<T>
	{
	private TupleBinding<T> dataBinding;
	
	
	public void setDataBinding(TupleBinding<T> dataBinding)
		{
		this.dataBinding = dataBinding;
		}
	@Override
	public  TupleBinding<T> getDataBinding() {
		return dataBinding;
		}

	

	
	}
