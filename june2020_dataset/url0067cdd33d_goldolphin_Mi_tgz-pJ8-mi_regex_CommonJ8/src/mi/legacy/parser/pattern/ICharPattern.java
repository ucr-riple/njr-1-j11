package mi.legacy.parser.pattern;

import mi.stream.ICharStream;

/**
 * User: goldolphin
 * Time: 2013-06-01 14:15
 */
public interface ICharPattern {
    boolean match(ICharStream stream, IContext context);
}
