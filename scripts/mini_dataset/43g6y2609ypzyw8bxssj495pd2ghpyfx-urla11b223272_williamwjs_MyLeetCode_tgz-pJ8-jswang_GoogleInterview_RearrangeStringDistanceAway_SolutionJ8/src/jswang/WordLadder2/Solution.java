package jswang.WordLadder2;

import java.util.*;

/**
 * Created by willwjs on 2/23/15.
 */
public class Solution {

    class StringWithLevel {
        String str;
        int level;
        public StringWithLevel(String str, int level) {
            this.str = str;
            this.level = level;
        }
    }

    public ArrayList<ArrayList<String>> findLadders(String start, String end, HashSet<String> dict) {
        ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
        HashSet<String> unvisitedSet = new HashSet<String>();
        unvisitedSet.addAll(dict);
        unvisitedSet.add(start);
        unvisitedSet.remove(end);
        Map<String, List<String>> nextMap = new HashMap<String, List<String>>();
        for (String e : unvisitedSet) {
            nextMap.put(e, new ArrayList<String>());
        }
        LinkedList<StringWithLevel> queue = new LinkedList<StringWithLevel>();
        queue.add(new StringWithLevel(end, 0));
        boolean found = false;
        int finalLevel = Integer.MAX_VALUE;
        int curLevel = 0;
        int preLevel = 0;
        HashSet<String> visitedCurLevel = new HashSet<String>();
        while (!queue.isEmpty()) {
            StringWithLevel cur = queue.poll();
            String curStr = cur.str;
            curLevel = cur.level;
            if(found && curLevel > finalLevel) {
                break;
            }
            if (curLevel > preLevel) {
                unvisitedSet.removeAll(visitedCurLevel);
            }
            preLevel = curLevel;
            char[] curStrCharArray = curStr.toCharArray();
            for (int i = 0; i < curStr.length(); ++i) {
                char originalChar = curStrCharArray[i];
                boolean foundCurCycle = false;
                for (char c = 'a'; c <= 'z'; ++c) {
                    curStrCharArray[i] = c;
                    String newStr = new String(curStrCharArray);
                    if(c != originalChar && unvisitedSet.contains(newStr)) {
                        nextMap.get(newStr).add(curStr);
                        if(newStr.equals(start)) {
                            found = true;
                            finalLevel = curLevel;
                            foundCurCycle = true;
                            break;
                        }
                        if(visitedCurLevel.add(newStr)) {
                            queue.add(new StringWithLevel(newStr, curLevel + 1));
                        }
                    }
                }
                if(foundCurCycle) {
                    break;
                }
                curStrCharArray[i] = originalChar;
            }
        }
        if(found) {
            ArrayList<String> list = new ArrayList<String>();
            list.add(start);
            getPaths(start, end, list, finalLevel + 1, nextMap, res);
        }
        return res;
    }

    private void getPaths(String cur, String end, ArrayList<String> list, int level, Map<String, List<String>> nextMap, ArrayList<ArrayList<String>> res) {
        if(cur.equals(end)){
            res.add(new ArrayList<String>(list));
        }
        else if(level > 0){
            List<String> parentsSet = nextMap.get(cur);
            for (String parent : parentsSet) {
                list.add(parent);
                getPaths(parent, end, list, level - 1, nextMap, res);
                list.remove(list.size() - 1);
            }
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        HashSet<String> dict = new HashSet<String>();
        dict.add("hot");
        dict.add("dot");
        dict.add("dog");
        dict.add("lot");
        dict.add("log");
        System.out.println(sol.findLadders("hit", "cog", dict));
    }
}
