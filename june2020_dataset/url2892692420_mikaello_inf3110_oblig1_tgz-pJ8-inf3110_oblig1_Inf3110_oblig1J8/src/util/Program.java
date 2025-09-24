package util;

import interfaces.Handler;
import log.Log;
import statements.Move;
import statements.VarDecl;

public class Program implements Handler {
    public static Grid gridGlobal = new Grid();
    public static Robot robotGlobal = new Robot();
    private static StringBuilder prettyPrint = new StringBuilder();
    public static final String STARS = "**********************************************";
    public static boolean printGrid;
    public static boolean printPrettyPrint;

    @Override
        public void interpret() {
        // write the interpret logic here if there is any
    }

    public static void addPrettyPrint(String... stringList) {
        for (String s : stringList) {
            prettyPrint.append("\n");
            prettyPrint.append(s);
        }
    }

    /**
     * Call the printLog method in Log and prints all the 
     * PrettyPrinting to terminal.
     */
    public static void printPrettyPrint() {
        Log.printLog();
    }
    
    public Program() {
        this("PrettyPrint", false, false);
    }
    
    public Program(boolean printGrid, boolean printPrettyPrint) {
        this("PrettyPrint", printGrid, printPrettyPrint);
    }
    
    public Program(String name, boolean printGrid, boolean printPrettyPrint) {
        this.printGrid = printGrid;
        this.printPrettyPrint = printPrettyPrint;
        
        // Clear pen positions from previous programs
        Move.clearPenPositions();
        
        // Welcome message from compiler
        printWelcome();
        
        // Make first entry in PrettyPrint
        makePrettyPrint(name);
    }

    public void addGrid(Grid gridIns) {
        setGridGlobal(gridIns);
    }
    public void addRobot(Robot robotIns) {
        setRobotGlobal(robotIns);
    }

    //....
    
    public void addStmt(Handler s) {
        Log.writeLogLine(s.toString());
        robotGlobal.addStmt(s);
    }
    
    public void addVarDecl(VarDecl vd) {
        Log.writeLogLine(vd.toString());
        robotGlobal.addVarDecl(vd);
    }

    private void setGridGlobal(Grid gridIns) {
        gridGlobal = gridIns;
        gridGlobal.interpret();
    }

    private void setRobotGlobal(Robot robotIns) {
        robotGlobal = robotIns;
        robotGlobal.interpret();
    }
    
    private void printWelcome() {
        System.out.println(" _______  _______  ______   _______  _       ");
        System.out.println("(  ____ )(  ___  )(  ___ \\ (  ___  )( \\      ");
        System.out.println("| (    )|| (   ) || (   ) )| (   ) || (      ");
        System.out.println("| (____)|| |   | || (__/ / | |   | || |      ");
        System.out.println("|     __)| |   | ||  __ (  | |   | || |      ");
        System.out.println("| (\\ (   | |   | || (  \\ \\ | |   | || |      ");
        System.out.println("| ) \\ \\__| (___) || )___) )| (___) || (____/\\");
        System.out.println("|/   \\__/(_______)|/ \\___/ (_______)(_______/");

        System.out.println("\n ... language interpreter!");
    }

    /**
     * Makes an intro line in the PrettyPrint
     * @param name name of the program (will appear in the intro line)
     */
    private void makePrettyPrint(String name) {
        // - 2 because of two whitespaces
        int loopLength = STARS.length() - name.length() - 2;
        
        // This makes the name be centralized with stars around
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < loopLength; i++) {
            sb.append("*");
            if (i == loopLength/2-1) {
                sb.append(" ");
                sb.append(name);
                sb.append(" ");
            }
        }
        Log.writeLogLine(sb.toString());
    }

}