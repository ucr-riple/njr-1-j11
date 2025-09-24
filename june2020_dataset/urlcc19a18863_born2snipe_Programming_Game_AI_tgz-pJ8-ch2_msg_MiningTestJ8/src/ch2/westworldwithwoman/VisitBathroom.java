package ch2.westworldwithwoman;

public class VisitBathroom implements State<MinersWife> {
    private static final VisitBathroom instance = new VisitBathroom();
    
    public static VisitBathroom instance() {
        return instance;
    }

    @Override
    public void enter(MinersWife obj) {
        System.out.println(obj.getId() + ": Walkin' to the can. Need to powda mah pretty li'lle nose");
    }

    @Override
    public void execute(MinersWife obj) {
        System.out.println(obj.getId() + ": Ahhhhhh! Sweet relief!");
        obj.machine.revertToPreviousState();
    }

    @Override
    public void exit(MinersWife obj) {
        System.out.println(obj.getId() + ": Leavin' the Jon");
    }
}
