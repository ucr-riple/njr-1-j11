package jswang.reversePolishNotation;

/**
 * Created by Jiashuo Wang on 14-6-9.
 * 1.使用栈来实现
 * 2.正则表达式要用matches方法
 */
public class Solution {
    private class RPNstack {
        private int length;
        private int curpos;
        private String[] stack;

        RPNstack() {
            length = 20;
            curpos = 0;
            stack = new String[length];
        }
        void push(String s) {
            stack[curpos++] = s;
            if(curpos == length) {
                length *= 2;
                String[] tmp = new String[length];
                for(int i=0; i<curpos; i++) {
                    tmp[i] = stack[i];
                }
                stack = tmp;
            }
        }
        String pop() {
            return stack[--curpos];
        }
        Boolean isEmp() {
            return curpos == 0;
        }
    }

    public int evalRPN(String[] tokens) {
        RPNstack rs = new RPNstack();
        for(String s : tokens) {
            if(s.matches("-?[0-9]+"))
                rs.push(s);
            else {
                int x1 = Integer.parseInt(rs.pop());
                int x2 = Integer.parseInt(rs.pop());
                if(s.equals("+")) rs.push(Integer.toString(x2+x1));
                else if(s.equals("-")) rs.push(Integer.toString(x2-x1));
                else if(s.equals("*")) rs.push(Integer.toString(x2*x1));
                else if(s.equals("/")) rs.push(Integer.toString(x2/x1));
            }
        }
        return Integer.parseInt(rs.pop());
    }
}