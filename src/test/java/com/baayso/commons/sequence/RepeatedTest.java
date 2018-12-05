package com.baayso.commons.sequence;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class RepeatedTest {

	/**
	 * 重复性测试
	 */
	@Test
	public void testRepeated() {
		Set<Long> set = new HashSet<>();
		int maxTimes = 10000 * 10;
		Sequence sequence = new Sequence();
		for (int i = 0; i < maxTimes; i++) {
			set.add(sequence.nextId());
		}
		Assert.assertEquals(maxTimes, set.size());
	}

}
