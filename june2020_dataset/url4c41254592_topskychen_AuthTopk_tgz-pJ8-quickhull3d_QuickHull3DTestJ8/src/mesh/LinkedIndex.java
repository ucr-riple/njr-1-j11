package mesh;

public class LinkedIndex {

	LinkedArray array;
	int index;
	int[] links;
	int linkCount;

	public LinkedIndex(LinkedArray a, int i){
		array = a;
		index = i;
		links = new int[1];
		linkCount = 0;
	}

	public void linkTo(int v){
		if( links.length == linkCount ){
//			links = PApplet.expand(links);
			if(links == null || links.length == 0){
				links = new int[1];
			}else{
				int[] tmp = new int[links.length * 2];
				for(int i = 0; i < links.length; i++){
					tmp[i] = links[i];
				}
				links = tmp;
			}
		}
		links[linkCount++] = v;
	}

	public boolean linked(int i){
		for(int j=0; j<linkCount; j++)
			if(links[j]==i)
				return true;
		return false;
	}

	public int[] getLinks(){
		return links;
	}

}