package com.tombrus.vthundred.test.terminal;

import com.tombrus.vthundred.terminal.*;
import com.tombrus.vthundred.terminal.input.*;
import com.tombrus.vthundred.terminal.input.Key.*;
import com.tombrus.vthundred.terminal.types.*;
import com.tombrus.vthundred.test.util.*;
import com.tombrus.vthundred.util.*;

import java.util.*;

public class Matching {
    static       InputPattern p1         = new AnsiCursorPositionPattern();
    static       InputPattern p2         = new SimpleInputPattern(Kind.F4, AnsiType.ESC, 'O', 'S');
    static       UnixTerminal t;
    static       int          line       = 2;
    static final boolean      PRINT_CODE = false;

    public static void main (String[] args) throws InterruptedException {
        try {
            t = new UnixTerminal();

            t.write(0,1,"Matching");
            t.flush();

            test(p1, "", "null"                                    );
            test(p1, "A", "null"                                   );
            test(p1, "AB", "null"                                  );
            test(p1, "EA", "null"                                  );
            test(p1, "EAB", "null"                                 );
            test(p1, "E", "<Partial:0>"                            );
            test(p1, "E[", "<Partial:0>"                           );
            test(p1, "E[A", "null"                                 );
            test(p1, "E[1", "<Partial:0>"                          );
            test(p1, "E[12A", "null"                               );
            test(p1, "E[12", "<Partial:0>"                         );
            test(p1, "E[123;", "<Partial:0>"                       );
            test(p1, "E[123;A", "null"                             );
            test(p1, "E[123;4", "<Partial:0>"                      );
            test(p1, "E[123;4A", "null"                            );
            test(p1, "E[123;45", "<Partial:0>"                     );
            test(p1, "E[123;456R", "<{x=456,y=123}:10>"            );
            test(p1, "E[123;456RE", "<{x=456,y=123}:10>"           );
            test(p1, "E[123;456RXXXXX", "<{x=456,y=123}:10>"       );
            test(p1, "E[123456;654321R", "<{x=654321,y=123456}:16>");
            test(p1, "E[;R", "<{x=1,y=1}:4>"                       );
            test(p1, "E[;3R", "<{x=3,y=1}:5>"                      );
            test(p1, "E[3;R", "<{x=1,y=3}:5>"                      );

            test(p2, "", "<Partial:0>"                             );
            test(p2, "A", "null"                                   );
            test(p2, "AB", "null"                                  );
            test(p2, "EA", "null"                                  );
            test(p2, "EAB", "null"                                 );
            test(p2, "E", "<Partial:0>"                            );
            test(p2, "EO", "<Partial:0>"                           );
            test(p2, "EOA", "null"                          );
            test(p2, "EOS", "<F4:3>"                               );
            test(p2, "EOSE", "<F4:3>"                              );
            test(p2, "EOSA", "<F4:3>"                              );

            U.waitForQ(t);
        } catch (Throwable e) {
            DB.error(e);
        }
    }

    private static void test (final InputPattern p, final String s, final String expect) {
        t.run(new Runnable() {
            @Override
            public void run () {
                List<Character> lc = new ArrayList<Character>(s.length());
                for (char c : s.replace('E', (char) 0x1b).toCharArray()) {
                    lc.add(c);
                }
                final Key result = p.match(lc);
                if (PRINT_CODE) {
                    t.write(10, line, "test("+(p==p1 ? "p1" : "p2")+",\""+s +"\",\"" +result +"\");");
                }else {
                    final boolean ok = expect.equals("" +result);
                    t.write(10, line, "\"", s, "\""                                                                     );
                    t.write(30, line, ok ? CharProps.FG_GREEN : CharProps.FG_RED, "" +result, CharProps.FG_DEFAULT, 0, 0);
                }
                line++;
            }
        });
    }
}
