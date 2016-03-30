package com.baayso.commons.serialize;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * https://git.oschina.net/ld/J2Cache/blob/master/src/test/java/net/oschina/j2cache/AppTest.java
 */
public class AppTest {

    private static Serializer jSer   = new JavaSerializer();
    private static Serializer fstSer = new FSTSerializer();

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {

        List<TestObject> list = new ArrayList<TestObject>();
        list.add(new TestObject("aaaa", "xxxx"));
        list.add(new TestObject("bbbb", "yyyy"));
        list.add(new TestObject("cccc", "zzzz"));

        System.out.println("序列化测试：");

        long time1 = System.currentTimeMillis();
        long length1 = 0;
        for (int i = 1; i <= 100000; i++) {
            List<TestObject> ob = (List<TestObject>) jSer.deserialize(jSer.serialize(list));
            if (i == 100000) {
                System.out.println(ob);
            }
            length1 += SerializationUtils.serialize(list).length;
        }
        System.out.println("100000次原生序列化测试：" + (System.currentTimeMillis() - time1));
        System.out.println("100000次原生序列化测试体积：" + length1);

        long time2 = System.currentTimeMillis();
        long length2 = 0;
        for (int i = 1; i <= 100000; i++) {
            List<TestObject> ob = (List<TestObject>) fstSer.deserialize(fstSer.serialize(list));
            if (i == 100000) {
                System.out.println(ob);
            }
            length2 += SerializationUtils.serialize(list).length;
        }
        System.out.println("100000次FST序列化测试：" + (System.currentTimeMillis() - time2));
        System.out.println("100000次FST序列化测试体积：" + length2);

    }
}

class TestObject implements Serializable {

    private static final long serialVersionUID = 1L;

    private String region;
    private String key;

    public TestObject(String region, String key) {
        super();
        this.region = region;
        this.key = key;
    }

    @Override
    public String toString() {
        return "TestObject [region=" + region + ", key=" + key + "]";
    }

}