package crackinginterview.chap3stackqueues;

import java.util.Stack;

/**
 * Created by Sobercheg on 12/7/13.
 * Problem 3.4
 */
public class HanoiTowersOOP {

    static class Tower {
        int index;
        Stack<Integer> disks;

        Tower(int index) {
            this.index = index;
            disks = new Stack<Integer>();
        }

        int getHeight() {
            return disks.size();
        }

        void addDisk(int num) {
            disks.push(num);
        }
    }

    private Tower from;
    private Tower to;
    private Tower by;

    public HanoiTowersOOP(Tower from, Tower to, Tower by) {
        this.from = from;
        this.to = to;
        this.by = by;
    }

    public void moveDisks(Tower from, int disksToMove, Tower to, Tower by) {
        if (disksToMove == 0) return;
        moveDisks(from, disksToMove - 1, by, to);
        moveTop(from, to);
        moveDisks(by, disksToMove - 1, to, from);
    }

    private void moveTop(Tower from, Tower to) {
        to.disks.push(from.disks.pop());
        printTower(this.from);
        printTower(this.by);
        printTower(this.to);
        System.out.println();
    }

    private void printTower(Tower tower) {
        System.out.println(tower.disks);
    }

    public static void main(String[] args) {
        Tower from = new Tower(0);
        from.addDisk(4);
        from.addDisk(3);
        from.addDisk(2);
        from.addDisk(1);
        Tower by = new Tower(1);
        Tower to = new Tower(2);
        HanoiTowersOOP towers = new HanoiTowersOOP(from, to, by);
        towers.moveDisks(from, from.getHeight(), to, by);
    }

}
