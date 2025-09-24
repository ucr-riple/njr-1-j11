package shyview;

public interface IPicInfo {
	void update(IPicList list);
	void clear();
	
	void pushProcess(String info);
	void finishProcess();
}
