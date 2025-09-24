import java.util.*;

public class Tensor{
    
    private String khovanovNodeCode;
    private BasisElement[] tensorProduct;
    
    private ArrayList<Arrow> outArrows;
    private ArrayList<Arrow> inArrows;
  
    
    private boolean isIsolated;
    private boolean isDeleted;
    
    public Tensor(ArrayList<BasisElement> b, String code){
        this.tensorProduct = new BasisElement[b.size()];
        this.tensorProduct = b.toArray(this.tensorProduct);
        this.outArrows = new ArrayList<Arrow>();
        this.inArrows = new ArrayList<Arrow>();
        
        this.khovanovNodeCode = code;

    }
    
    public int getNumArrows(){
        return this.outArrows.size()+this.inArrows.size();
        
    }
    
    public boolean isDeleted(){
        return this.isDeleted;
    }
    
    public void deleteArrowFromIn(Arrow a){//deletes a specific arrow
        Iterator<Arrow> iter = this.inArrows.iterator();

        while (iter.hasNext()) {
            Arrow arr = iter.next();
            
            if(arr.equals(a))
                iter.remove();
        }
    }
    
    public void deleteArrowFromOut(Arrow a){//deletes a specific arrow
        Iterator<Arrow> iter = this.outArrows.iterator();

        while (iter.hasNext()) {
            Arrow arr = iter.next();
            
            if(arr.equals(a))
                iter.remove();
        }
    }
    
    public void addInArrowModTwo(Arrow a){
        
        Iterator<Arrow> iter = this.inArrows.iterator();

        while (iter.hasNext()) {
            Arrow arr = iter.next();
            
            if(arr.getFrom().equals(a.getFrom())){ //if an arrow already exists from ____, delete the existing arrow
                iter.remove();
                return;
            }
        }
        this.inArrows.add(a);  
    }
    
    public void addOutArrowModTwo(Arrow a){
        Iterator<Arrow> iter = this.outArrows.iterator();

        while (iter.hasNext()) {
            Arrow arr = iter.next();
            if(arr.getTo().equals(a.getTo())){ //if an arrow already exists to ____, delete the existing arrow
                iter.remove();
                return;
            }
        }
        this.outArrows.add(a);  
    }
    
    public void delete(){
        this.isDeleted = true;
        Iterator<Arrow> outIter = this.outArrows.iterator();
        while (outIter.hasNext()) {
            Arrow arr = outIter.next();
            arr.delete();
        }
        
        Iterator<Arrow> inIter = this.inArrows.iterator();
        while (inIter.hasNext()) {
            Arrow arr = inIter.next();
            arr.delete();
        }
        
        /*
        for(Arrow a: this.outArrows)
            a.delete();
        for(Arrow a: this.inArrows)
            a.delete();*/
    }
    
    public String getCode(){
        return this.khovanovNodeCode;
    }
    
    public void addOutArrow(Arrow a){
        this.outArrows.add(a);
    }
    
    public void addOutArrows(ArrayList<Arrow> a){
        this.outArrows.addAll(a);
    }
    
    public void addInArrow(Arrow a){
        this.inArrows.add(a);
    }
    
    public void addInArrows(ArrayList<Arrow> a){
        this.inArrows.addAll(a);
    }
    
    public void removeDeletedArrows(){
        for(Arrow a: this.outArrows){
            if(a.isDeleted())
                this.outArrows.remove(a);
        }
        
        for(Arrow a: this.inArrows){
            if(a.isDeleted())
                this.inArrows.remove(a);
        }
            
        
    }
    
    public ArrayList<Arrow> getOutArrows(){
        return this.outArrows;
    }
    
    public ArrayList<Arrow> getInArrows(){
        return this.inArrows;
    }
        
        
    public boolean contains(BasisElement b){
        boolean flag=false;
        for(BasisElement be:this.tensorProduct){
            if(be.equals(b)){
                flag = true;
                break;
            }   
        }
        return flag;
    }
    
    public boolean isIdentityMinusOne(Tensor other, BasisElement one){ //does "this" contain everything in "other" besides the "one" element?
        BasisElement[] toTest = other.getElements();
        for(BasisElement b:toTest){
            if(b.equals(one)){}
            else if(!contains(b))
                return false;
            }
        return true;
    }
    
    public BasisElement[] getElements(){
        return this.tensorProduct;
    }
    

    public boolean equals(Tensor other){
        BasisElement[] otherElements=other.getElements();
        if(this.tensorProduct.length==otherElements.length){
            for(int i=0; i<this.tensorProduct.length; i++){
                if(!this.tensorProduct[i].equals(otherElements[i])){
                    return false;
                }
            }
            return true;         
        }
        return false;    
    }
    
    public String basisToString(){
        String s="";
        
        for(BasisElement b:this.tensorProduct){
            s+=(b.toString()+" ");
        }       
        return s;
    }
    
    public String toString(){
        String s=basisToString();
        for(Arrow a:this.outArrows)
            s+=("   "+a.toString());
        s+=("   ")+this.outArrows.size();
        return s;
    }
    
    
    
}