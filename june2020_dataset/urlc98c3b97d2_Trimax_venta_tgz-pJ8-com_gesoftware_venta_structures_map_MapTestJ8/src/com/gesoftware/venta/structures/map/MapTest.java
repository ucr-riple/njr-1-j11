package com.gesoftware.venta.structures.map;

import com.gesoftware.venta.logging.LoggingUtility;

public final class MapTest {
    private static void testRemoteMap() {
        final RemoteMap<String, String> remoteMap = new RemoteMap<String, String>("localhost", 5555);

        LoggingUtility.info("Adding 100 values");
        for (int query = 0; query < 100; query++)
            remoteMap.put("test" + query, "hello #" + query * query);

        LoggingUtility.info("Remote map size: " + remoteMap.size());
    }

    private static void testRemoteMap2() {
        final RemoteMap<String, String> remoteMap = new RemoteMap<String, String>("localhost", 5555);
        remoteMap.put("test", "test");

        for (int query = 50; query < 150; query++)
            LoggingUtility.info("Query [" + query + "]: " + remoteMap.get("test" + query));
    }

    public static void main(final String args[]) {
        LoggingUtility.setLoggingLevel(LoggingUtility.LoggingLevel.LEVEL_CORE);

        final SharedMap<String, String> sharedMap = new SharedMap<String, String>(5555);
        testRemoteMap();

        sharedMap.put("Local string", "Local value");
        LoggingUtility.info("Shared map size: " + sharedMap.size());

        testRemoteMap2();
    }
}
