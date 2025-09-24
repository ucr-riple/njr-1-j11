package ch2.westworld;

public class MiningTest  {

    public static void main(String args[]) {
        Miner miner = new Miner();

        for (int i=0;i<20;i++)
            miner.update();
    }
}
