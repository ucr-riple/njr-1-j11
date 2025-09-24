package ch2.msg;

public class EatStew implements State<Miner> {
    private static final EatStew instance = new EatStew();

    public static EatStew instance() {
        return instance;
    }

    @Override
    public void enter(Miner obj) {

    }

    @Override
    public void execute(Miner obj) {

    }

    @Override
    public void exit(Miner obj) {

    }

    @Override
    public void onMessage(Miner obj, Telegram telegram) {
        
    }

}
