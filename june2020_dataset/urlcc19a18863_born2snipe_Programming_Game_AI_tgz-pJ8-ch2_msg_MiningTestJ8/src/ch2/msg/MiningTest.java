package ch2.msg;

public class MiningTest  {

    public static void main(String args[]) throws Exception {
        Miner miner = new Miner();
        MinersWife wife = new MinersWife();

        EntityManager.instance().register(miner);
        EntityManager.instance().register(wife);

        for (int i=0;i<20;i++) {
            miner.update();
            wife.update();

            MessageDispatcher.instance().dispatchMessages();

            Thread.sleep(800);
        }
    }
}
