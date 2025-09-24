package mi.regex;

import mi.common.CharType;
import mi.common.CharacterSet;
import mi.stream.ICharStream;
import mi.stream.StringStream;

import java.util.ArrayList;

/**
 * User: goldolphin
 * Time: 2013-06-08 23:51
 */
public class Regex {
    private static final EndRegex End = new EndRegex();

    private static final CharacterSet CONTROL_CHARS;
    static {
        CONTROL_CHARS = new CharacterSet();
        for(char c: "^$*+?.()[]{}|\\".toCharArray()) {
            CONTROL_CHARS.add(c);
        }
    }

    private final String pattern;
    private final AbstractRegex regex;
    private int groupCount;
    private ArrayList<GroupRegex> groups = new ArrayList<>();

    public Regex(String pattern) {
        this.pattern = pattern;
        groupCount = 0;
        StringStream stream = new StringStream(pattern);
        GroupRegex group = parseGroup(stream);
        verify(stream.peek() == ICharStream.EOF, stream.getPos(), "Unexpected input");
        group.setNext(End);
        regex = group;
    }

    public Match startWith(String text) {
        return startWith(new StringStream(text));
    }

    public Match match(String text) {
        return match(new StringStream(text));
    }

    public Match startWith(ICharStream stream) {
        Match match = new Match(groupCount);
        match.setSucceed(match(stream, match));
        return match;
    }

    public Match match(ICharStream stream) {
        Match match = new Match(groupCount);
        match.setSucceed(match(stream, match) && stream.peek() == ICharStream.EOF);
        return match;
    }

    boolean match(ICharStream stream, Match match) {
        int len = match.length();
        if (regex.match(stream, match)) {
            return true;
        }
        AbstractRegex.rollback(stream, match, len);
        return false;
    }

    void dump() {
        System.out.println(pattern);
        regex.print(0);
    }

    AbstractRegex parseOr(StringStream stream, AbstractRegex left, AbstractRegex end) {
        if (left == null) {
            return parseOr(stream, parseSequence(stream, end), end);
        }
        switch (stream.peek()) {
            case '|':
                stream.poll();
                return parseOr(stream, new OrRegex(left, parseSequence(stream, end)), end);
            default:
                return left;
        }
    }

    AbstractRegex parseSequence(StringStream stream, AbstractRegex end) {
        AbstractRegex cls = parseClosure(stream);
        if (cls == null) {
            return end;
        }
        cls.setNext(parseSequence(stream, end));
        return cls;
    }

    AbstractRegex parseClosure(StringStream stream) {
        switch (stream.peek()) {
            case '^':
                stream.poll();
                return new HatRegex();
            case '$':
                stream.poll();
                return new DollarRegex();
        }
        AbstractRegex term = parseTerm(stream);
        if (term == null) {
            return null;
        }
        switch (stream.peek()) {
            case '*':
                stream.poll();
                return new ClosureRegex(term, 0);
            case '+':
                stream.poll();
                return new ClosureRegex(term, 1);
            case '?':
                stream.poll();
                return new ClosureRegex(term, 0, 1);
            case '{':
                stream.poll();
                int start = parseNumber(stream);
                int end = start;
                if (stream.peek() == ',') {
                    stream.poll();
                    end = parseNumber(stream);
                    verify(start <= end, stream.getPos(), "Invalid repeat range");
                }
                verify(stream.poll() == '}', stream.getPos(), "Missing '}'");
                return new ClosureRegex(term, start, end);
            default:
                return term;
        }
    }

    int parseNumber(StringStream stream) {
        StringBuilder buffer = new StringBuilder();
        while (CharType.isDigit(stream.peek())) {
            buffer.append(stream.poll());
        }
        verify(buffer.length() > 0, stream.getPos(), "Missing number");
        return Integer.parseInt(buffer.toString());
    }

    AbstractRegex parseTerm(StringStream stream) {
        switch (stream.peek()) {
            case '(':
                stream.poll();
                AbstractRegex group = parseGroup(stream);
                verify(stream.poll() == ')', stream.getPos(), "Missing ']'");
                return group;
            default:
                return parseAtom(stream);
        }
    }

    GroupRegex parseGroup(StringStream stream) {
        int groupNum = groupCount ++;
        groups.add(null);
        GroupRegex group = new GroupRegex(groupNum);
        group.setClause(parseOr(stream, null, group.groupEnd()));
        groups.set(groupNum, group);
        return group;
    }

    AtomRegex parseAtom(StringStream stream) {
        char c = stream.peek();
        switch (c) {
            case '.':
                stream.poll();
                return new DotRegex();
            case '\\':
                stream.poll();
                char follow = stream.poll();
                verify(follow != ICharStream.EOF, stream.getPos(), "Unexpected end");
                if(CharType.isDigit(follow)) {
                    int ref = Integer.parseInt(Character.toString(follow));
                    verify(groups.get(ref) != null, stream.getPos(), "No such group");
                    return new RefRegex(ref);
                }
                return new CharRegex(follow);
            case '[':
                stream.poll();
                SetRegex set = parseSet(stream);
                verify(stream.poll() == ']', stream.getPos(), "Missing ']'");
                return set;
            default:
                if (isRegular(c)) {
                    stream.poll();
                    return new CharRegex(c);
                }
                return null;
        }
    }

    SetRegex parseSet(StringStream stream) {
        boolean inclusive = true;
        if (stream.peek() == '^') {
            inclusive = false;
            stream.poll();
        }
        SetRegex regex = new SetRegex(inclusive);
        while (true) {
            char c = stream.peek();
            if (c == '\\') {
                stream.poll();
                char follow = stream.poll();
                verify(follow != ICharStream.EOF, stream.getPos(), "Unexpected end");
                regex.add(follow);
            } else if (c != ICharStream.EOF && c!= ']') {
                regex.add(stream.poll());
                parseRange(stream, regex, c);
            } else {
                break;
            }
        }
        verify(regex.count() > 0, stream.getPos(), "Empty set");
        return regex;
    }

    void parseRange(StringStream stream, SetRegex set, char start) {
        CharType type = CharType.getType(start);
        if (!(type == CharType.DIGIT || type == CharType.UPPERCASE || type == CharType.LOWERCASE) || stream.peek() != '-') {
            return;
        }
        stream.poll();
        char follow = stream.poll();
        verify((CharType.getType(follow) == type) && start < follow, stream.getPos(), "Invalid character range");
        for (char c = (char) (start + 1); c <= follow; c ++) {
            set.add(c);
        }
    }

    static boolean isRegular(char c) {
        return c != ICharStream.EOF && !CONTROL_CHARS.contains(c);
    }

    void verify(boolean cond, int pos, String msg) {
        String s = String.format("%s before '_###_' marker:\n %s_###_%s", msg, pattern.substring(0, pos), pattern.substring(pos));
        if (!cond) throw new RegexException(s);
    }

}
