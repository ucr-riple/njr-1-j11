package ch2.westworldwithwoman;

class DoHouseWork implements State<MinersWife> {

    private static final DoHouseWork instance = new DoHouseWork();

    public static DoHouseWork instance() {
        return instance;
    }

    @Override
    public void enter(MinersWife obj) {
    }

    @Override
    public void execute(MinersWife obj) {
        int value = (int) (Math.random() * 2.0d);

        switch (value) {
            case 0:
                System.out.println(obj.getId() + ": Moppin' the floor");
                break;

            case 1:
                System.out.println(obj.getId() + ": Washin' the dishes");
                break;

            case 2:
                System.out.println(obj.getId() + ": Makin' the bed");
                break;
        }
    }

    @Override
    public void exit(MinersWife obj) {
    }
}
