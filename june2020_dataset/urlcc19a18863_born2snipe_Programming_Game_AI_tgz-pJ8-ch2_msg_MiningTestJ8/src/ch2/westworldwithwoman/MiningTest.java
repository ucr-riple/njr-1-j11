package ch2.westworldwithwoman;

public class MiningTest  {

    public static void main(String args[]) throws Exception {
        Miner miner = new Miner();
        MinersWife wife = new MinersWife();

        for (int i=0;i<20;i++) {
            miner.update();
            wife.update();
        }
    }
}
