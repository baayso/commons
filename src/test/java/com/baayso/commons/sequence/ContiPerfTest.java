package com.baayso.commons.sequence;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * 性能测试
 *
 * @author lry (https://gitee.com/yu120/sequence)
 */
public class ContiPerfTest {

    @Rule
    public ContiPerfRule i = new ContiPerfRule();

    private Sequence sequence = new Sequence();

    @Test
    @PerfTest(invocations = 200000000, threads = 16)
    public void test1() throws Exception {
        this.sequence.nextId();
    }

}