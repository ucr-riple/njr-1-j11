import org.apache.commons.lang3.StringUtils;
import java.util.*;

public class KhovanovMap{
    
    private Knot theKnot;  //root knot
    private Knot[] resolvedKnots;
    private KhovanovRow[] rows; 
    private Stack<Map> maps;

    
    public KhovanovMap(Knot kn){
        this.theKnot = kn;
        this.rows = new KhovanovRow[this.theKnot.numCrossings()+1];
        
        int n = this.rows.length-1; //COMBINATION FINDER since length of each row is based on combinations....it's nCk in this case
        int nCk = 1;
        for (int k = 0; k <= n; k++) {
            this.rows[k] = new KhovanovRow(nCk);
            nCk = nCk * (n-k) / (k+1);
        }
            
        //this.resolvedKnots = new Knot[(int) Math.pow(2,this.theKnot.numCrossings())];
        
        fillResolvedKnots(this.theKnot);
        setMaps(); 
            
    }
    
    public void fillResolvedKnots(Knot k){
        if(k.getLeftKnot()==null){
            int numOnes = StringUtils.countMatches(k.getBinCode(), "1");
            this.rows[numOnes].add(new KhovanovMapNode(k.getBinCode(),k.getCircles()));
            //this.resolvedKnots[Integer.parseInt(k.getBinCode(), 2)] = k; //puts the knot in its right place in the array based on its binary code
        }
            

        else{
            fillResolvedKnots(k.getLeftKnot());
            fillResolvedKnots(k.getRightKnot());
        }
    }
    
    public void setMaps(){       
        KhovanovMapNode current;
        MergeMap merge;
        SplitMap split;        
        
        for(int i=0; i<this.rows.length; i++){
            for(int k=0; k<this.rows[i].size(); k++){
                current = this.rows[i].nthNode(k);
                String[] outMaps = current.outMaps();
                for(String s:outMaps){
                    KhovanovMapNode to = findNode(s);

                    ArrayList<SeifertCircle> fromCircles = new ArrayList<SeifertCircle>();
                    ArrayList<SeifertCircle> toCircles = new ArrayList<SeifertCircle>();
                    ArrayList<SeifertCircle> sameCircles = new ArrayList<SeifertCircle>();
                        
                    SeifertCircle theCircle; // to be passed into the map
                    if(current.numCircles() < to.numCircles()){ //split map
                        theCircle = new SeifertCircle("");
                        fromCircles = current.getCircles();
                        toCircles = to.findDifferentCircles(current);
                        for(SeifertCircle sei:fromCircles){
                            sameCircles = to.findCircleMatch(sei);
                            if(sameCircles.size()==0){
                                theCircle = sei;
                                break;
                            }
                        }
                        
                        System.out.print("\n"+"From {");
                        for(SeifertCircle ss:fromCircles)
                            System.out.print(ss.getCode()+" ");
                        System.out.print("} to {");
                        for(SeifertCircle ss:toCircles)
                            System.out.print(ss.getCode()+" ");
                        System.out.print("} \n");
                        
                        split = new SplitMap(current,to,theCircle,toCircles);
                        current.addOutMap(split);
                        to.addInMap(split);
                    }
                    else{ //merge
                        theCircle = new SeifertCircle("");
                        toCircles = to.getCircles();
                        fromCircles = current.findDifferentCircles(to);
                        for(SeifertCircle sei:toCircles){
                            sameCircles = current.findCircleMatch(sei);
                            if(sameCircles.size()==0){
                                theCircle = sei;
                                break;
                            }
                        }
                        merge = new MergeMap(current,to,fromCircles,theCircle);
                        current.addOutMap(merge);
                        to.addInMap(merge);    
                    }
                }            
            }
        }
    }
    
    public KhovanovMapNode findNode(String code){//returns node based on binary code, throws exception otherwise
        int numOnes = StringUtils.countMatches(code, "1");
        return this.rows[numOnes].findNode(code);
    }
    
    public KhovanovRow[] getKhovanovRows(){
        return this.rows;
    }
    
    public int getNumRows(){
        return this.rows.length;
    }
    
    
    
    public void debug(){
        System.out.println("\n\n");
        for(KhovanovRow kr : this.rows){
            System.out.println(kr.toString());     
        }
        
    }
    
    
  
    
    public static void main(String[] args){
        
          
        //TREFOIL
        System.out.println("TREFOIL");
        Crossing x1 = new Crossing(1,2,4,5,true);
        Crossing x2 = new Crossing(5,6,2,3,true);
        Crossing x3 = new Crossing(3,4,6,1,true);
        
        Crossing[] crossarray = new Crossing[]{x1,x2,x3};
        Knot k = new Knot(crossarray);
        
        k.resolveknot();
        //k.output();
        
        /*//FIGURE 8
        System.out.println("\n");
        System.out.println("FIGURE 8");
        Crossing y1 = new Crossing(1,2,2,1,false);
        
        Crossing[] crossarray2 = new Crossing[]{y1};
        Knot f = new Knot(crossarray2);
        
        f.resolveknot();
        f.output();*/
        
        /*System.out.println("Apple");
        Crossing p1 = new Crossing(1,2,8,1,true);
        Crossing p2 = new Crossing(2,3,3,4,false);
        Crossing p3 = new Crossing(5,6,4,5,true);
        Crossing p4 = new Crossing(6,7,7,8,false);
        
        Crossing[] crossarray4 = new Crossing[]{p1,p2,p4,p3};
        Knot a = new Knot(crossarray4);
        
        a.resolveknot();
        a.output();*/
        
        KhovanovMap mm = new KhovanovMap(k);
        mm.debug();
        
       
        
  
        
    }
    
    
    
    
    
    
}