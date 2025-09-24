import java.lang.*;

public class Global{
  
  public static boolean[][] lookUp = {
  //
  //INPUTS AB can be encoded as ints
  //
  //LOGIC     FF      FT      TF      TT
  //BINARY    00      01      10      11
  //INDEX     0       1       2       3
              {false, false,  false,  false},   // OFF          1
              {false, false,  false,  true},    // AND          2
              {false, false,  true,   false},   // A AND ¬B     3
              {false, false,  true,   true},    // A            4
  
              {false, true,   false,  false},   // ¬A AND B     5 
              {false, true,   false,  true},    // B            6
              {false, true,   true,   false},   // XOR          7
              {false, true,   true,   true},    // OR           8
  
              {true,  false,  false,  false},   // NOR          9
              {true,  false,  false,  true},    // XNOR         10
              {true,  false,  true,   false},   // ¬B           11
              {true,  false,  true,   true},    // ¬A NAND B    12
  
              {true,  true,   false,  false},   // ¬A           13
              {true,  true,   false,  true},    // A NAND ¬B    14
              {true,  true,   true,   false},   // NAND         15
              {true,  true,   true,   true},    // ON           16
  };
    
  public static boolean[][] lookUp3 = {
  
//FFF     FFT     FTF     FTT     TFF     TFT     TTF     TTT
  {false,	false,	false,	false,	false,	false,	false,	false},
  {false,	false,	false,	false,	false,	false,	false,	true},
  {false,	false,	false,	false,	false,	false,	true,	false},
  {false,	false,	false,	false,	false,	false,	true,	true},
  {false,	false,	false,	false,	false,	true,	false,	false},
  {false,	false,	false,	false,	false,	true,	false,	true},
  {false,	false,	false,	false,	false,	true,	true,	false},
  {false,	false,	false,	false,	false,	true,	true,	true},
  {false,	false,	false,	false,	true,	false,	false,	false},
  {false,	false,	false,	false,	true,	false,	false,	true},
  {false,	false,	false,	false,	true,	false,	true,	false},
  {false,	false,	false,	false,	true,	false,	true,	true},
  {false,	false,	false,	false,	true,	true,	false,	false},
  {false,	false,	false,	false,	true,	true,	false,	true},
  {false,	false,	false,	false,	true,	true,	true,	false},
  {false,	false,	false,	false,	true,	true,	true,	true},
  {false,	false,	false,	true,	false,	false,	false,	false},
  {false,	false,	false,	true,	false,	false,	false,	true},
  {false,	false,	false,	true,	false,	false,	true,	false},
  {false,	false,	false,	true,	false,	false,	true,	true},
  {false,	false,	false,	true,	false,	true,	false,	false},
  {false,	false,	false,	true,	false,	true,	false,	true},
  {false,	false,	false,	true,	false,	true,	true,	false},
  {false,	false,	false,	true,	false,	true,	true,	true},
  {false,	false,	false,	true,	true,	false,	false,	false},
  {false,	false,	false,	true,	true,	false,	false,	true},
  {false,	false,	false,	true,	true,	false,	true,	false},
  {false,	false,	false,	true,	true,	false,	true,	true},
  {false,	false,	false,	true,	true,	true,	false,	false},
  {false,	false,	false,	true,	true,	true,	false,	true},
  {false,	false,	false,	true,	true,	true,	true,	false},
  {false,	false,	false,	true,	true,	true,	true,	true},
  {false,	false,	true,	false,	false,	false,	false,	false},
  {false,	false,	true,	false,	false,	false,	false,	true},
  {false,	false,	true,	false,	false,	false,	true,	false},
  {false,	false,	true,	false,	false,	false,	true,	true},
  {false,	false,	true,	false,	false,	true,	false,	false},
  {false,	false,	true,	false,	false,	true,	false,	true},
  {false,	false,	true,	false,	false,	true,	true,	false},
  {false,	false,	true,	false,	false,	true,	true,	true},
  {false,	false,	true,	false,	true,	false,	false,	false},
  {false,	false,	true,	false,	true,	false,	false,	true},
  {false,	false,	true,	false,	true,	false,	true,	false},
  {false,	false,	true,	false,	true,	false,	true,	true},
  {false,	false,	true,	false,	true,	true,	false,	false},
  {false,	false,	true,	false,	true,	true,	false,	true},
  {false,	false,	true,	false,	true,	true,	true,	false},
  {false,	false,	true,	false,	true,	true,	true,	true},
  {false,	false,	true,	true,	false,	false,	false,	false},
  {false,	false,	true,	true,	false,	false,	false,	true},
  {false,	false,	true,	true,	false,	false,	true,	false},
  {false,	false,	true,	true,	false,	false,	true,	true},
  {false,	false,	true,	true,	false,	true,	false,	false},
  {false,	false,	true,	true,	false,	true,	false,	true},
  {false,	false,	true,	true,	false,	true,	true,	false},
  {false,	false,	true,	true,	false,	true,	true,	true},
  {false,	false,	true,	true,	true,	false,	false,	false},
  {false,	false,	true,	true,	true,	false,	false,	true},
  {false,	false,	true,	true,	true,	false,	true,	false},
  {false,	false,	true,	true,	true,	false,	true,	true},
  {false,	false,	true,	true,	true,	true,	false,	false},
  {false,	false,	true,	true,	true,	true,	false,	true},
  {false,	false,	true,	true,	true,	true,	true,	false},
  {false,	false,	true,	true,	true,	true,	true,	true},
  {false,	true,	false,	false,	false,	false,	false,	false},
  {false,	true,	false,	false,	false,	false,	false,	true},
  {false,	true,	false,	false,	false,	false,	true,	false},
  {false,	true,	false,	false,	false,	false,	true,	true},
  {false,	true,	false,	false,	false,	true,	false,	false},
  {false,	true,	false,	false,	false,	true,	false,	true},
  {false,	true,	false,	false,	false,	true,	true,	false},
  {false,	true,	false,	false,	false,	true,	true,	true},
  {false,	true,	false,	false,	true,	false,	false,	false},
  {false,	true,	false,	false,	true,	false,	false,	true},
  {false,	true,	false,	false,	true,	false,	true,	false},
  {false,	true,	false,	false,	true,	false,	true,	true},
  {false,	true,	false,	false,	true,	true,	false,	false},
  {false,	true,	false,	false,	true,	true,	false,	true},
  {false,	true,	false,	false,	true,	true,	true,	false},
  {false,	true,	false,	false,	true,	true,	true,	true},
  {false,	true,	false,	true,	false,	false,	false,	false},
  {false,	true,	false,	true,	false,	false,	false,	true},
  {false,	true,	false,	true,	false,	false,	true,	false},
  {false,	true,	false,	true,	false,	false,	true,	true},
  {false,	true,	false,	true,	false,	true,	false,	false},
  {false,	true,	false,	true,	false,	true,	false,	true},
  {false,	true,	false,	true,	false,	true,	true,	false},
  {false,	true,	false,	true,	false,	true,	true,	true},
  {false,	true,	false,	true,	true,	false,	false,	false},
  {false,	true,	false,	true,	true,	false,	false,	true},
  {false,	true,	false,	true,	true,	false,	true,	false},
  {false,	true,	false,	true,	true,	false,	true,	true},
  {false,	true,	false,	true,	true,	true,	false,	false},
  {false,	true,	false,	true,	true,	true,	false,	true},
  {false,	true,	false,	true,	true,	true,	true,	false},
  {false,	true,	false,	true,	true,	true,	true,	true},
  {false,	true,	true,	false,	false,	false,	false,	false},
  {false,	true,	true,	false,	false,	false,	false,	true},
  {false,	true,	true,	false,	false,	false,	true,	false},
  {false,	true,	true,	false,	false,	false,	true,	true},
  {false,	true,	true,	false,	false,	true,	false,	false},
  {false,	true,	true,	false,	false,	true,	false,	true},
  {false,	true,	true,	false,	false,	true,	true,	false},
  {false,	true,	true,	false,	false,	true,	true,	true},
  {false,	true,	true,	false,	true,	false,	false,	false},
  {false,	true,	true,	false,	true,	false,	false,	true},
  {false,	true,	true,	false,	true,	false,	true,	false},
  {false,	true,	true,	false,	true,	false,	true,	true},
  {false,	true,	true,	false,	true,	true,	false,	false},
  {false,	true,	true,	false,	true,	true,	false,	true},
  {false,	true,	true,	false,	true,	true,	true,	false},
  {false,	true,	true,	false,	true,	true,	true,	true},
  {false,	true,	true,	true,	false,	false,	false,	false},
  {false,	true,	true,	true,	false,	false,	false,	true},
  {false,	true,	true,	true,	false,	false,	true,	false},
  {false,	true,	true,	true,	false,	false,	true,	true},
  {false,	true,	true,	true,	false,	true,	false,	false},
  {false,	true,	true,	true,	false,	true,	false,	true},
  {false,	true,	true,	true,	false,	true,	true,	false},
  {false,	true,	true,	true,	false,	true,	true,	true},
  {false,	true,	true,	true,	true,	false,	false,	false},
  {false,	true,	true,	true,	true,	false,	false,	true},
  {false,	true,	true,	true,	true,	false,	true,	false},
  {false,	true,	true,	true,	true,	false,	true,	true},
  {false,	true,	true,	true,	true,	true,	false,	false},
  {false,	true,	true,	true,	true,	true,	false,	true},
  {false,	true,	true,	true,	true,	true,	true,	false},
  {false,	true,	true,	true,	true,	true,	true,	true},
  {true,	false,	false,	false,	false,	false,	false,	false},
  {true,	false,	false,	false,	false,	false,	false,	true},
  {true,	false,	false,	false,	false,	false,	true,	false},
  {true,	false,	false,	false,	false,	false,	true,	true},
  {true,	false,	false,	false,	false,	true,	false,	false},
  {true,	false,	false,	false,	false,	true,	false,	true},
  {true,	false,	false,	false,	false,	true,	true,	false},
  {true,	false,	false,	false,	false,	true,	true,	true},
  {true,	false,	false,	false,	true,	false,	false,	false},
  {true,	false,	false,	false,	true,	false,	false,	true},
  {true,	false,	false,	false,	true,	false,	true,	false},
  {true,	false,	false,	false,	true,	false,	true,	true},
  {true,	false,	false,	false,	true,	true,	false,	false},
  {true,	false,	false,	false,	true,	true,	false,	true},
  {true,	false,	false,	false,	true,	true,	true,	false},
  {true,	false,	false,	false,	true,	true,	true,	true},
  {true,	false,	false,	true,	false,	false,	false,	false},
  {true,	false,	false,	true,	false,	false,	false,	true},
  {true,	false,	false,	true,	false,	false,	true,	false},
  {true,	false,	false,	true,	false,	false,	true,	true},
  {true,	false,	false,	true,	false,	true,	false,	false},
  {true,	false,	false,	true,	false,	true,	false,	true},
  {true,	false,	false,	true,	false,	true,	true,	false},
  {true,	false,	false,	true,	false,	true,	true,	true},
  {true,	false,	false,	true,	true,	false,	false,	false},
  {true,	false,	false,	true,	true,	false,	false,	true},
  {true,	false,	false,	true,	true,	false,	true,	false},
  {true,	false,	false,	true,	true,	false,	true,	true},
  {true,	false,	false,	true,	true,	true,	false,	false},
  {true,	false,	false,	true,	true,	true,	false,	true},
  {true,	false,	false,	true,	true,	true,	true,	false},
  {true,	false,	false,	true,	true,	true,	true,	true},
  {true,	false,	true,	false,	false,	false,	false,	false},
  {true,	false,	true,	false,	false,	false,	false,	true},
  {true,	false,	true,	false,	false,	false,	true,	false},
  {true,	false,	true,	false,	false,	false,	true,	true},
  {true,	false,	true,	false,	false,	true,	false,	false},
  {true,	false,	true,	false,	false,	true,	false,	true},
  {true,	false,	true,	false,	false,	true,	true,	false},
  {true,	false,	true,	false,	false,	true,	true,	true},
  {true,	false,	true,	false,	true,	false,	false,	false},
  {true,	false,	true,	false,	true,	false,	false,	true},
  {true,	false,	true,	false,	true,	false,	true,	false},
  {true,	false,	true,	false,	true,	false,	true,	true},
  {true,	false,	true,	false,	true,	true,	false,	false},
  {true,	false,	true,	false,	true,	true,	false,	true},
  {true,	false,	true,	false,	true,	true,	true,	false},
  {true,	false,	true,	false,	true,	true,	true,	true},
  {true,	false,	true,	true,	false,	false,	false,	false},
  {true,	false,	true,	true,	false,	false,	false,	true},
  {true,	false,	true,	true,	false,	false,	true,	false},
  {true,	false,	true,	true,	false,	false,	true,	true},
  {true,	false,	true,	true,	false,	true,	false,	false},
  {true,	false,	true,	true,	false,	true,	false,	true},
  {true,	false,	true,	true,	false,	true,	true,	false},
  {true,	false,	true,	true,	false,	true,	true,	true},
  {true,	false,	true,	true,	true,	false,	false,	false},
  {true,	false,	true,	true,	true,	false,	false,	true},
  {true,	false,	true,	true,	true,	false,	true,	false},
  {true,	false,	true,	true,	true,	false,	true,	true},
  {true,	false,	true,	true,	true,	true,	false,	false},
  {true,	false,	true,	true,	true,	true,	false,	true},
  {true,	false,	true,	true,	true,	true,	true,	false},
  {true,	false,	true,	true,	true,	true,	true,	true},
  {true,	true,	false,	false,	false,	false,	false,	false},
  {true,	true,	false,	false,	false,	false,	false,	true},
  {true,	true,	false,	false,	false,	false,	true,	false},
  {true,	true,	false,	false,	false,	false,	true,	true},
  {true,	true,	false,	false,	false,	true,	false,	false},
  {true,	true,	false,	false,	false,	true,	false,	true},
  {true,	true,	false,	false,	false,	true,	true,	false},
  {true,	true,	false,	false,	false,	true,	true,	true},
  {true,	true,	false,	false,	true,	false,	false,	false},
  {true,	true,	false,	false,	true,	false,	false,	true},
  {true,	true,	false,	false,	true,	false,	true,	false},
  {true,	true,	false,	false,	true,	false,	true,	true},
  {true,	true,	false,	false,	true,	true,	false,	false},
  {true,	true,	false,	false,	true,	true,	false,	true},
  {true,	true,	false,	false,	true,	true,	true,	false},
  {true,	true,	false,	false,	true,	true,	true,	true},
  {true,	true,	false,	true,	false,	false,	false,	false},
  {true,	true,	false,	true,	false,	false,	false,	true},
  {true,	true,	false,	true,	false,	false,	true,	false},
  {true,	true,	false,	true,	false,	false,	true,	true},
  {true,	true,	false,	true,	false,	true,	false,	false},
  {true,	true,	false,	true,	false,	true,	false,	true},
  {true,	true,	false,	true,	false,	true,	true,	false},
  {true,	true,	false,	true,	false,	true,	true,	true},
  {true,	true,	false,	true,	true,	false,	false,	false},
  {true,	true,	false,	true,	true,	false,	false,	true},
  {true,	true,	false,	true,	true,	false,	true,	false},
  {true,	true,	false,	true,	true,	false,	true,	true},
  {true,	true,	false,	true,	true,	true,	false,	false},
  {true,	true,	false,	true,	true,	true,	false,	true},
  {true,	true,	false,	true,	true,	true,	true,	false},
  {true,	true,	false,	true,	true,	true,	true,	true},
  {true,	true,	true,	false,	false,	false,	false,	false},
  {true,	true,	true,	false,	false,	false,	false,	true},
  {true,	true,	true,	false,	false,	false,	true,	false},
  {true,	true,	true,	false,	false,	false,	true,	true},
  {true,	true,	true,	false,	false,	true,	false,	false},
  {true,	true,	true,	false,	false,	true,	false,	true},
  {true,	true,	true,	false,	false,	true,	true,	false},
  {true,	true,	true,	false,	false,	true,	true,	true},
  {true,	true,	true,	false,	true,	false,	false,	false},
  {true,	true,	true,	false,	true,	false,	false,	true},
  {true,	true,	true,	false,	true,	false,	true,	false},
  {true,	true,	true,	false,	true,	false,	true,	true},
  {true,	true,	true,	false,	true,	true,	false,	false},
  {true,	true,	true,	false,	true,	true,	false,	true},
  {true,	true,	true,	false,	true,	true,	true,	false},
  {true,	true,	true,	false,	true,	true,	true,	true},
  {true,	true,	true,	true,	false,	false,	false,	false},
  {true,	true,	true,	true,	false,	false,	false,	true},
  {true,	true,	true,	true,	false,	false,	true,	false},
  {true,	true,	true,	true,	false,	false,	true,	true},
  {true,	true,	true,	true,	false,	true,	false,	false},
  {true,	true,	true,	true,	false,	true,	false,	true},
  {true,	true,	true,	true,	false,	true,	true,	false},
  {true,	true,	true,	true,	false,	true,	true,	true},
  {true,	true,	true,	true,	true,	false,	false,	false},
  {true,	true,	true,	true,	true,	false,	false,	true},
  {true,	true,	true,	true,	true,	false,	true,	false},
  {true,	true,	true,	true,	true,	false,	true,	true},
  {true,	true,	true,	true,	true,	true,	false,	false},
  {true,	true,	true,	true,	true,	true,	false,	true},
  {true,	true,	true,	true,	true,	true,	true,	false},
  {true,	true,	true,	true,	true,	true,	true,	true},
  
  
  };
  
  
  public static String[] chars = {"0","A","B","C","D","E","F","G","H",
  "I","J","K","L","M","N","O","P","Q",
  "R","S","T","U","V","W","X","Y","Z",
  "a", "b", "c", "d", "e", "f", "g", "h",
  "i", "j", "k", "l", "m", "n", "p", "q",
  "r", "s", "t", "u", "v", "w", "x", "y", "z",
  "0", "1", "2", "3", "4", "5", "6", "7", "8",
  "9", 
  
  };
  
  public static void printBoolVal(String pre, char str, String suf){
  
    if(str == '0' ){
      
      System.out.printf(pre+"false"+suf);
    
    }
    
    else if(str == '1' ){
      
      System.out.printf(pre+"true"+suf);
      
    }
    
    else{
      
      System.out.printf("Error occured, printed "+str+"\n");
    
    }
    
  }
  
  public static void main(String args[]){
    
    /*for(int x=0;x<16;x++){
      for(int y=0;y<4; y++){
        System.out.printf("%b\t", lookUp[x][y]);
      }
      System.out.printf("\n");
      
    }
  }*/
    
    for( int x = 0; x < 256; x++){
      String bin = Integer.toBinaryString(x);
      
      if(bin.length()<8){
      
        String padding = "";
        for(int p=0; p<8-bin.length(); p++){
          padding += "0";
        }
        
        bin=padding+bin;
        
      }
      
      printBoolVal("{", bin.charAt(0), ",\t");
      for( int y=1; y<7; y++){
      
        printBoolVal("", bin.charAt(y), ",\t");
        
      
      }
      printBoolVal("", bin.charAt(7), "},\n");
      
    }
  
}

}