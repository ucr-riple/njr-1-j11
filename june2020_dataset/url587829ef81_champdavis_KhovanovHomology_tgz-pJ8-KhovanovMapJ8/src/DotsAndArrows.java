import java.util.*;

public class DotsAndArrows{
    
    private KhovanovMap khmap;
    private DotRow[] rows;
    private ArrayList<Arrow> arrows;
    
    public DotsAndArrows(KhovanovMap k){
        this.khmap = k;
        this.rows = new DotRow[this.khmap.getNumRows()];
        this.arrows = new ArrayList<Arrow>();
        fillRows();
        fillArrows();
        resolve();
    }
    
    public void fillRows(){
        //fill the rows with all the dots (tensors)
        KhovanovRow[] krows = this.khmap.getKhovanovRows(); 
        KhovanovMapNode[] nodes;
        Tensor[] basis;
        
        for(int i=0; i<krows.length; i++){
            this.rows[i] = new DotRow(krows[i].howManyDots());
            nodes = krows[i].getNodes();
            for(KhovanovMapNode k: nodes){
                basis = k.getBasis();
                for(Tensor t: basis){
                    this.rows[i].add(t);                    
                }
            }
        }  
    }
    
    public void fillArrows(){
        Tensor[] basis;
        
        for(DotRow d: this.rows){
            basis = d.getDots();
            for(Tensor t: basis){
                this.arrows.addAll(t.getOutArrows());
            }
        }
    }
    
    public void resolve(){
        Arrow current;
        ArrayList<Arrow> originators;
        ArrayList<Arrow> targets;
        Tensor origin;
        Tensor target;
        Arrow newArrow;
        
        while(!this.arrows.isEmpty()){
            current = this.arrows.get(0);
            //Step 1: choose arrow & delete the arrow and the dots it's connected to
            current.getFrom().delete();
            current.getTo().delete();
            current.delete();
            
            //Step 2: go to each dot that had an arrow going into the target of the original arrow
            originators = current.getTo().getInArrows();
            targets = current.getTo().getOutArrows();
            
            //Step 3: link the originators to the targets, mod 2
            for(Arrow o: originators){
                origin=o.getFrom();
                for(Arrow t: targets){
                    target=t.getTo();
                    newArrow = new Arrow(origin,target);
                    origin.addOutArrowModTwo(newArrow);
                    target.addInArrowModTwo(newArrow);
                }
            }
            //remove all the "deleted" arrows
            Iterator<Arrow> iter = this.arrows.iterator();
            
            while (iter.hasNext()) {
                Arrow arr = iter.next();
                
                if(arr.isDeleted())
                    iter.remove();
            }   
        }    
    }
    
    public void output(){
        for(int i=0; i<this.rows.length; i++){
            System.out.println("Row "+i+" has "+this.rows[i].numDotsRemaining()+" dots remaining");
        }
    }
    
      
    public static void main(String[] args){
        
        //FIGURE 8
        System.out.println("\n");
        System.out.println("FIGURE 8");
        Crossing y1 = new Crossing(1,2,2,1,false);
        
        Crossing[] crossarray2 = new Crossing[]{y1};
        
        /*/APPLE
        System.out.println("Apple");
        Crossing p1 = new Crossing(1,2,8,1,true);
        Crossing p2 = new Crossing(2,3,3,4,false);
        Crossing p3 = new Crossing(5,6,4,5,true);
        Crossing p4 = new Crossing(6,7,7,8,false);*/
        
        //TREFOIL
        System.out.println("TREFOIL");
        Crossing x1 = new Crossing(1,2,4,5,true);
        Crossing x2 = new Crossing(5,6,2,3,true);
        Crossing x3 = new Crossing(3,4,6,1,true);
        
        Crossing[] crossarray = new Crossing[]{x1,x2,x3};
        
        Knot f = new Knot(crossarray2);
        
        f.resolveknot();
      
       
        
        KhovanovMap ff = new KhovanovMap(f);
        //DotsAndArrows fff = new DotsAndArrows(ff);
        //fff.output();
        
        
        
    }
    
    
    
    
    
    
}