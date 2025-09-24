package com.github.users.schlabberdog.blocks.solver;

import java.util.HashMap;

class LeveledSteps {
    private HashMap<String,Integer> steps = new HashMap<String, Integer>();

    public synchronized void clear() {
        steps.clear();
    }

    /**
     * Fügt einen Step mit einem Level hinzu
     * @param hash  Der Hash
     * @param level Das Level auf dem er sich befindet
     */
    public synchronized void pushOnLevel(String hash, int level) {
        if(containsOnBetterLevel(hash,level))
            throw new RuntimeException("Hash <"+hash+"> @ "+level+" is already at level <"+steps.get(hash)+">!");
        steps.put(hash,level);
    }

    /**
     * Prüft ob es den Step bereits gegeben hat UND ob er auf einem höheren Level passiert ist
     * @param nextHash Der Hash (Step)
     * @param myLevel  Das Level, dass verglichen werden soll
     * @return True, wenn es nextHash schon gibt und sein Level besser ist als myLevel
     */
    public synchronized boolean containsOnBetterLevel(String nextHash, int myLevel) {
        Integer oldLevel = steps.get(nextHash);
        if(oldLevel != null) {
            if(oldLevel < myLevel)
                return true;
        }
        return false;
    }
}
