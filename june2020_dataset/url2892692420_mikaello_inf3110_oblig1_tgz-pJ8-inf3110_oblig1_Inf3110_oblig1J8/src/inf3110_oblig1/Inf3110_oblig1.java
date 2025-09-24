package inf3110_oblig1;

import expressions.BooleanExp;
import expressions.Booleans;
import expressions.Exp;
import expressions.Identifier;
import expressions.Numbers;
import expressions.Operand;
import expressions.PlusExp;
import statements.Assignment;
import statements.Backward;
import statements.Forward;
import statements.IfThenElse;
import statements.Left;
import statements.PenDown;
import statements.PenUp;
import statements.Right;
import statements.Size;
import statements.Start;
import statements.Stmt;
import statements.Stop;
import statements.VarDecl;
import statements.While;
import util.Direction;
import util.Grid;
import util.Program;
import util.Robot;

/**
 * Start program from here, example programs are given as methods. 
 * All four programs given in text are programmed as methods, in 
 * addition to the program in README.pdf.
 * 
 * @author mikaelolausson
 */
public class Inf3110_oblig1 {

    /**
     * Executes example programs. Remove the comments from the example
     * you wish to test, but remember that each example will exit the 
     * program when it reaches the "stop" command, so you have to comment
     * out methods before the one you wish to try.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //exampleFromReadme();
        //testingCode1();
        //testingCode2();
        //testingCode3();
        testingCode4();
    }  
    
    private static void testingCode1() {
        Program pro_inst = new Program("Simple example", true, true);
        pro_inst.addGrid(new Grid(new Size(new Numbers(64), new Numbers(64))));
        pro_inst.addRobot(new Robot()); 
        
        pro_inst.addStmt(new Start(new Exp(new Numbers(23)), new Exp(new Numbers(30)), Direction.minusX));
        
        pro_inst.addStmt(new PenDown());
        pro_inst.addStmt(new Forward(new Exp(new Numbers(15))));
        pro_inst.addStmt(new Left(new Exp(new Numbers(15))));
        pro_inst.addStmt(new Right(new PlusExp(new Exp(new Numbers(2)), Operand.plus, new Exp(new Numbers(3)))));
        pro_inst.addStmt(new Backward(new PlusExp(new Exp(new Numbers(10)), Operand.plus, new Exp(new Numbers(27)))));
        pro_inst.addStmt(new Stop());
    }
   
    private static void testingCode2() {
        Program pro_inst = new Program("Example with variables", true, true);
        pro_inst.addGrid(new Grid(new Size(new Numbers(64), new Numbers(64))));
        pro_inst.addRobot(new Robot()); 
        
        pro_inst.addVarDecl(new VarDecl(new Identifier("i"), new Exp(new Numbers(5))));
        pro_inst.addVarDecl(new VarDecl(new Identifier("j"), new Exp(new Numbers(3))));
        pro_inst.addStmt(new Start(new Exp(new Numbers(23)), new Exp(new Numbers(6)), Direction.minusX));
        pro_inst.addStmt(new Forward(new PlusExp(new Exp(new Numbers(3)), Operand.times, new Identifier("i"))));
        pro_inst.addStmt(new PenDown());
        pro_inst.addStmt(new Right(new Exp(new Numbers(15))));
        pro_inst.addStmt(new Left(new Exp(new Numbers(4))));
        pro_inst.addStmt(new PenUp());
        pro_inst.addStmt(new Backward(
                new PlusExp(
                new PlusExp(
                new PlusExp(new Exp(new Numbers(2)), Operand.times, new Identifier("i")),
                Operand.plus,
                new PlusExp(new Exp(new Numbers(3)), Operand.times, new Identifier("j"))),
                Operand.plus,
                new Exp(new Numbers(5)))));
        
        pro_inst.addStmt(new Stop());
    }
        private static void testingCode3() {
        Program pro_inst = new Program("Example with while", true, true);
        pro_inst.addGrid(new Grid(new Size(new Numbers(64), new Numbers(64))));
        pro_inst.addRobot(new Robot()); 
        
        pro_inst.addVarDecl(new VarDecl(new Identifier("i"), new Exp(new Numbers(5))));
        pro_inst.addVarDecl(new VarDecl(new Identifier("j"), new Exp(new Numbers(3))));
        pro_inst.addStmt(new Start(new Exp(new Numbers(23)), new Exp(new Numbers(6)), Direction.minusX));
        pro_inst.addStmt(new Forward(new PlusExp(new Exp(new Numbers(3)), Operand.times, new Identifier("i"))));
        pro_inst.addStmt(new PenDown());
        pro_inst.addStmt(new Right(new Exp(new Numbers(15))));
        pro_inst.addStmt(new Left(new Exp(new Numbers(4))));
        pro_inst.addStmt(new PenUp());
        pro_inst.addStmt(new Backward(
                new PlusExp(
                new PlusExp(
                new PlusExp(new Exp(new Numbers(2)), Operand.times, new Identifier("i")),
                Operand.plus,
                new PlusExp(new Exp(new Numbers(3)), Operand.times, new Identifier("j"))),
                Operand.plus,
                new Exp(new Numbers(5)))));
        
        pro_inst.addStmt(new While(
                            new BooleanExp(
                              new Identifier("j"), 
                              Booleans.greaterThan, 
                              new Exp(new Numbers(0))), 
                            new Right(new Identifier("j")),
                            new Assignment(
                              new Identifier("j"), 
                              new PlusExp(
                                new Identifier("j"), 
                                Operand.minus, 
                                new Exp(new Numbers(1))))));
        pro_inst.addStmt(new Stop());
    }
    
    
    private static void testingCode4() {
        Program pro_inst = new Program("Example with if-then-else", true, true);
        pro_inst.addGrid(new Grid(new Size(new Numbers(64), new Numbers(64))));
        pro_inst.addRobot(new Robot()); 
        
        pro_inst.addVarDecl(new VarDecl(new Identifier("i"), new Exp(new Numbers(5))));
        pro_inst.addVarDecl(new VarDecl(new Identifier("j"), new Exp(new Numbers(3))));
        pro_inst.addStmt(new Start(new Exp(new Numbers(23)), new Exp(new Numbers(6)), Direction.minusX));
        pro_inst.addStmt(new Forward(new PlusExp(new Exp(new Numbers(3)), Operand.times, new Identifier("i"))));
        pro_inst.addStmt(new PenDown());
        pro_inst.addStmt(new Right(new Exp(new Numbers(15))));
        pro_inst.addStmt(new Left(new Exp(new Numbers(4))));
        pro_inst.addStmt(new PenUp());
        pro_inst.addStmt(new Backward(
                new PlusExp(
                new PlusExp(
                new PlusExp(new Exp(new Numbers(2)), Operand.times, new Identifier("i")),
                Operand.plus,
                new PlusExp(new Exp(new Numbers(3)), Operand.times, new Identifier("j"))),
                Operand.plus,
                new Exp(new Numbers(5)))));
        
        pro_inst.addStmt(new While(
                            new BooleanExp(
                              new Identifier("j"), 
                              Booleans.greaterThan, 
                              new Exp(new Numbers(0))), 
                            new Right(new Identifier("j")),
                            new Assignment(
                              new Identifier("j"), 
                              new PlusExp(
                                new Identifier("j"), 
                                Operand.minus, 
                                new Exp(new Numbers(1))))));

        Stmt[] ifList = { new Forward(new Exp(new Numbers(14))) };
        Stmt[] elseList = { new Backward(new Exp(new Numbers(14))) };
        pro_inst.addStmt(new IfThenElse(
                            new BooleanExp(new Identifier("i"), 
                            Booleans.greaterThan, 
                            new Exp(new Numbers(3))),
                                         ifList,
                                         elseList));
        
        pro_inst.addStmt(new Stop());
    }

    private static void exampleFromReadme() {
        Program pro_inst = new Program("Example grid", true, true);
        pro_inst.addGrid(new Grid(new Size(new Numbers(9), 
                                           new Numbers(7))));
        pro_inst.addRobot(new Robot()); 
        pro_inst.addStmt(new PenDown());
        pro_inst.addStmt(new Start(new Exp(new Numbers(2)), 
                                   new Exp(new Numbers(3)), 
                                   Direction.x));
        pro_inst.addStmt(new Forward(new Exp(new Numbers(3))));
        pro_inst.addStmt(new Left(new Exp(new Numbers(4))));
        pro_inst.addStmt(new Backward(new Exp(new Numbers(1))));
        pro_inst.addStmt(new Right(new Exp(new Numbers(2))));
        pro_inst.addStmt(new Stop());
    }
 }
