package com.disclinc.netmanager.variable.test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

import br.com.innovatium.mumps2java.datastructure.OrderDataCache;

public class OrderCacheTest {

	private OrderDataCache cache = new OrderDataCache();

	@Test
	public void testInsertInCache() {

		Object[] www001 = new Object[] { "^ww001", "1", "2", "3" };
		Object[] www002 = new Object[] { "^ww002" };
		cache.add(www001);
		cache.add(www002);
		assertTrue(cache.isCached(www001));
		assertFalse("No table name must not be present into the cache, ",
				cache.isCached(www002));
	}

	@Test
	public void testIsSomeChildInCache() {
		Object[] firstLike = new Object[] { "^www001", "1" };
		cache.add(firstLike);

		Object[] secondLike = new Object[] { "^www001", "1", "2", "3" };
		assertTrue(cache.isCached(secondLike));
	}

	@Test
	public void testIsAllInCache() {
		Object[] firstLike = new Object[] { "^www001", "1" };
		cache.add(firstLike);

		Object[] secondLike = new Object[] { "^www001", "1", "2" };
		assertTrue(cache.isCached(secondLike));

		Object[] thridLike = new Object[] { "^www001", "1", "2", "3" };
		assertTrue(cache.isCached(thridLike));
	}

	@Test
	public void testIsSomeInCache() {
		Object[] firstLike = new Object[] { "^www001", "1", "2", "3" };
		cache.add(firstLike);

		Object[] secondLike = new Object[] { "^www001", "1" };
		assertFalse(cache.isCached(secondLike));

		Object[] thridLike = new Object[] { "^www001" };
		assertFalse(cache.isCached(thridLike));
	}

	@Test
	public void testFlowOfCasheIncrease() {
		boolean isCached = false;
		Object[] firstLike = new Object[] { "^www001", "1", "2", "3", "4" };
		if (!cache.isCached(firstLike)) {
			cache.add(firstLike);
		}
		assertTrue(cache.isCached(firstLike));

		Object[] secondLike = new Object[] { "^www001", "1" };
		if (isCached = cache.isCached(secondLike)) {
			assertTrue("This content does not e^www001ist into the cache yet",
					isCached);
		} else {
			cache.add(secondLike);
		}
		assertTrue(cache.isCached(secondLike));

		Object[] thridLike = new Object[] { "^www001" };
		assertFalse("This variable should not be into the cache",
				cache.isCached(thridLike));
	}
}
