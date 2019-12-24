package com.baayso.commons.sequence;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * 重复性测试
 *
 * @author lry (https://gitee.com/yu120/sequence)
 */
public class RepeatedTest {

    @Test
    public void testRepeated() {
        Set<Long> set = new HashSet<Long>();
        int maxTimes = 10000 * 10;
        Sequence sequence = new Sequence(0);
        for (int i = 0; i < maxTimes; i++) {
            set.add(sequence.nextId());
        }
        Assert.assertEquals(maxTimes, set.size());
    }

}
