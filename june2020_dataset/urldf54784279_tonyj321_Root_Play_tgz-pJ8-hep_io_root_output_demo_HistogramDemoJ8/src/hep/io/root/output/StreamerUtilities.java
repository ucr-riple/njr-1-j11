package hep.io.root.output;

/**
 *
 * @author tonyj
 */
class StreamerUtilities {

    static StreamerClassInfo getClassInfo(Class c) {
        return new StreamerClassInfo(c);
    }

    static TStreamerInfo getStreamerInfo(Class c) throws StreamerInfoException {
        final StreamerClassInfo classInfo = getClassInfo(c);
        classInfo.resolveDependencies();
        return new TStreamerInfo(classInfo);
    }
}