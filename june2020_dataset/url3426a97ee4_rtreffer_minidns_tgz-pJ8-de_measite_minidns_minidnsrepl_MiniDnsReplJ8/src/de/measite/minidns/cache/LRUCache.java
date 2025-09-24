/*
 * Copyright 2015-2016 the original author or authors
 *
 * This software is licensed under the Apache License, Version 2.0,
 * the GNU Lesser General Public License version 2 or later ("LGPL")
 * and the WTFPL.
 * You may choose either license to govern your use of this software only
 * upon the condition that you accept all of the terms of either
 * the Apache License 2.0, the LGPL 2.1+ or the WTFPL.
 */
package de.measite.minidns.cache;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import de.measite.minidns.DNSCache;
import de.measite.minidns.DNSMessage;
import de.measite.minidns.DNSName;
import de.measite.minidns.Record;
import de.measite.minidns.record.Data;

/**
 * LRU based DNSCache backed by a LinkedHashMap.
 */
public class LRUCache extends DNSCache {

    /**
     * Internal miss count.
     */
    protected long missCount = 0L;

    /**
     * Internal expire count (subset of misses that was caused by expire).
     */
    protected long expireCount = 0L;

    /**
     * Internal hit count.
     */
    protected long hitCount = 0L;

    /**
     * The internal capacity of the backend cache.
     */
    protected int capacity;

    /**
     * The upper bound of the ttl. All longer TTLs will be capped by this ttl.
     */
    protected long maxTTL;

    /**
     * The backend cache.
     */
    protected LinkedHashMap<DNSMessage, DNSMessage> backend;

    /**
     * Create a new LRUCache with given capacity and upper bound ttl.
     * @param capacity The internal capacity.
     * @param maxTTL The upper bound for any ttl.
     */
    @SuppressWarnings("serial")
    public LRUCache(final int capacity, final long maxTTL) {
        this.capacity = capacity;
        this.maxTTL = maxTTL;
        backend = new LinkedHashMap<DNSMessage, DNSMessage>(
                Math.min(capacity + (capacity + 3) / 4 + 2, 11), 0.75f, true)
            {
                @Override
                protected boolean removeEldestEntry(
                        Entry<DNSMessage, DNSMessage> eldest) {
                    return size() > capacity;
                }
            };
    }

    /**
     * Create a new LRUCache with given capacity.
     * @param capacity The capacity of this cache.
     */
    public LRUCache(final int capacity) {
        this(capacity, Long.MAX_VALUE);
    }

    @Override
    protected synchronized void putNormalized(DNSMessage q, DNSMessage message) {
        if (message.receiveTimestamp <= 0L) {
            return;
        }
        backend.put(q, message);
    }

    @Override
    protected synchronized DNSMessage getNormalized(DNSMessage q) {
        DNSMessage message = backend.get(q);
        if (message == null) {
            missCount++;
            return null;
        }

        long ttl = maxTTL;
        // RFC 2181 § 5.2 says that all TTLs in a RRSet should be equal, if this isn't the case, then we assume the
        // shortest TTL to be the effective one.
        for (Record<? extends Data> r : message.answerSection) {
            ttl = Math.min(ttl, r.ttl);
        }
        if (message.receiveTimestamp + ttl < System.currentTimeMillis()) {
            missCount++;
            expireCount++;
            backend.remove(q);
            return null;
        } else {
            hitCount++;
            return message;
        }
    }

    /**
     * Clear all entries in this cache.
     */
    public synchronized void clear() {
        backend.clear();
        missCount = 0L;
        hitCount = 0L;
        expireCount = 0L;
    }

    /**
     * Get the miss count of this cache which is the number of fruitless
     * get calls since this cache was last resetted.
     * @return The number of cache misses.
     */
    public long getMissCount() {
        return missCount;
    }

    /**
     * The number of expires (cache hits that have had a ttl to low to be
     * retrieved).
     * @return The expire count.
     */
    public long getExpireCount() {
        return expireCount;
    }

    /**
     * The cache hit count (all sucessful calls to get).
     * @return The hit count.
     */
    public long getHitCount() {
        return hitCount;
    }

    @Override
    public String toString() {
        return "LRUCache{usage=" + backend.size() + "/" + capacity + ", hits=" + hitCount + ", misses=" + missCount + ", expires=" + expireCount + "}";
    }

    @Override
    public void offer(DNSMessage query, DNSMessage reply, DNSName knownAuthoritativeZone) {
    }
}
