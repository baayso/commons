package com.baayso.commons.sequence;

/**
 * @author lry (https://gitee.com/yu120/sequence)
 */
public class SequenceTest2 {

    public static void main(String[] args) throws Exception {
        System.out.println("场景一：毫秒内固定起始值开始");
        testSequence();
        System.out.println("场景二：毫秒内随机起始值开始");
        testRandomSequence();
    }

    /**
     * 场景一：毫秒内固定起始值开始
     */
    private static void testSequence() throws Exception {
        Sequence sequence = new Sequence(1L, false, false);
        for (int i = 0; i < 100; i++) {
            Thread.sleep(1);
            System.out.println(sequence.nextId());
        }
    }

    /**
     * 场景二：毫秒内随机起始值开始
     */
    private static void testRandomSequence() throws Exception {
        Sequence sequence = new Sequence(1L, false, true);
        for (int i = 0; i < 100; i++) {
            Thread.sleep(1);
            System.out.println(sequence.nextId());
        }
    }

}