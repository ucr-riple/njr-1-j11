package com.tombrus.vthundred.screen;

public enum TabBehaviour {
    ONE_SPACE,
    FOUR_SPACES,
    EIGHT_SPACES,
    ALIGN_4,
    ALIGN_8;

    public String replaceTabs (String text, final int initialLinePos) {
        if (text.indexOf('\t') !=-1) {
            final char[]        input   = text.toCharArray();
            final StringBuilder sb      = new StringBuilder();

                  int           linepos = initialLinePos;
            for (final char ch : input) {
                if (ch =='\t') {
                    switch (this) {
                    case ONE_SPACE:
                        sb.append(' ');
                        linepos++;
                        break;
                    case FOUR_SPACES:
                        sb.append("    ");
                        linepos += 4;
                        break;
                    case EIGHT_SPACES:
                        sb.append("        ");
                        linepos += 8;
                        break;
                    case ALIGN_4:
                        do {
                            sb.append(' ');
                            linepos++;
                        } while (0!=linepos%4);
                        break;
                    case ALIGN_8:
                        do {
                            sb.append(' ');
                            linepos++;
                        } while (0!=linepos%8);
                        break;
                    }
                }else {
                    sb.append(ch);
                    linepos++;
                }
            }
            text = sb.toString();
        }
        return text;
    }
}
