package util;

import expressions.Identifier;
import expressions.Numbers;
import interfaces.Handler;
import java.util.HashMap;
import statements.VarDecl;


public class Robot implements Handler {
    /** All variable declarations made in the ROBOL-code */
    private HashMap<Identifier, VarDecl> varDecls;
    
    /** This is the current position of this robot */
    private Position curPos;
    
    /** This boolean controls if the pen (it will make marks
     * on the grid if it moves with the pen down (true value))
     * */
    public static boolean penDown = false;
    
    @Override
        public void interpret() {
        // write the interpret logic here if there is any
    }

    public Robot() {
        varDecls = new HashMap<Identifier, VarDecl>();
        curPos = new Position();
    }
    
    public void addVarDecl(VarDecl vd) {
        varDecls.put(vd.getIdentifier(), vd);
    }
    
    /**
     * Prints all variable declarations
     */
    public void printVarDecls() {
        for (VarDecl vd : varDecls.values()) {
            System.out.println(vd);
        }
    }
    
    public void addStmt(Handler s) {
        s.interpret();
    }

    /**
     * Update current position for this robot. The methods in curPos
     * will check that this is an appropriate coordinate.
     * @param dir the new direction
     * @param xCord new x-coordinate
     * @param yCord new y-coordinate
     */
    public void setPos(Direction dir, Numbers xCord, Numbers yCord) {
        curPos.setDirection(dir);
        curPos.setXCord(xCord);
        curPos.setYCord(yCord);
    }
    
    public Position getPos() {
        return new Position(curPos);
    }
    
    /** 
     * @return the variable declaration corresponding to 
     * given identifier 
     */
    public VarDecl getVarDecl(Identifier ident) {
        return varDecls.get(ident);
    }
    
    /** 
     * @see setPos(Direction, Numbers, Numbers) 
     */
    public void setPos(Position p) {
        setPos(p.getDirection(), new Numbers(p.getXCord()), new Numbers(p.getYCord()));
    }
    
    public Direction getDirection() { return curPos.getDirection(); }

}