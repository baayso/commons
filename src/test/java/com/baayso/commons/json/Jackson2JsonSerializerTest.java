package com.baayso.commons.json;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baayso.commons.tool.BasicResponseStatus;
import com.baayso.commons.utils.DateTimeUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Test class for {@link com.baayso.commons.json.Jackson2JsonSerializer}
 *
 * @author ChenFangjie (2023/2/26 16:06)
 * @since 1.0.0
 */
public class Jackson2JsonSerializerTest {

    private JsonSerializer jsonSerializer;

    @BeforeEach
    public void setUp() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        mapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
        mapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        mapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
        // default-property-inclusion
        // mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 序列化为JSON时将 Long 转为 String（全局配置）
        // mapper.registerModule(new SimpleModule().addSerializer(Long.class, ToStringSerializer.instance));

        LocalDateTimeSerializer dateTimeSerializer = new LocalDateTimeSerializer(DateTimeUtils.DATE_TIME_FORMATTER_SEPARATOR);
        LocalDateSerializer dateSerializer = new LocalDateSerializer(DateTimeUtils.DATE_FORMATTER_SEPARATOR);
        LocalTimeSerializer timeSerializer = new LocalTimeSerializer(DateTimeUtils.TIME_FORMATTER_SEPARATOR);
        LocalDateTimeDeserializer dateTimeDeserializer = new LocalDateTimeDeserializer(DateTimeUtils.DATE_TIME_FORMATTER_SEPARATOR);
        LocalDateDeserializer dateDeserializer = new LocalDateDeserializer(DateTimeUtils.DATE_FORMATTER_SEPARATOR);
        LocalTimeDeserializer timeDeserializer = new LocalTimeDeserializer(DateTimeUtils.TIME_FORMATTER_SEPARATOR);

        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(LocalDateTime.class, dateTimeSerializer);
        timeModule.addSerializer(LocalDate.class, dateSerializer);
        timeModule.addSerializer(LocalTime.class, timeSerializer);
        timeModule.addDeserializer(LocalDateTime.class, dateTimeDeserializer);
        timeModule.addDeserializer(LocalDate.class, dateDeserializer);
        timeModule.addDeserializer(LocalTime.class, timeDeserializer);

        mapper.registerModule(timeModule);
        // mapper.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mapper.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

        this.jsonSerializer = new Jackson2JsonSerializer(mapper);
    }

    @AfterEach
    public void tearDown() {
        this.jsonSerializer = null;
    }

    /**
     * Test method for {@link Jackson2JsonSerializer#toJson(Object)}.
     *
     * @since 1.0.0
     */
    @Test
    public void testToJson() {
        Map<String, Object> data = new HashMap<>();
        data.put("dateTime", LocalDateTime.now());
        data.put("name", "hello");
        data.put("age", 18);
        data.put("money", new BigDecimal("654321.0000"));
        data.put("address", "");
        data.put("remark", null);
        data.put("status", BasicResponseStatus.OK);

        ResultVO<Map<String, Object>> resultVO = new ResultVO<>();
        resultVO.setSuccess(true);
        resultVO.setCode(200);
        resultVO.setMessage("ok");
        resultVO.setData(data);

        System.out.println(this.jsonSerializer.toJson(resultVO));
    }

    /**
     * Test method for {@link Jackson2JsonSerializer#toBean(String, Class)}.
     *
     * @since 1.0.0
     */
    @Test
    public void testToBean() {
        String jsonString = "{\"dateTime\":\"2023-02-26 17:21:38\",\"address\":\"\",\"money\":654321.0000,\"name\":\"hello\",\"remark\":null,\"age\":18}";

        TestUser user = this.jsonSerializer.toBean(jsonString, TestUser.class);

        Assertions.assertEquals(new BigDecimal("654321.0000"), user.getMoney());
        Assertions.assertEquals("hello", user.getName());

        System.out.println(user);
    }

    /**
     * Test method for {@link Jackson2JsonSerializer#toBean(String, Class, Class[])}.
     *
     * @since 1.0.0
     */
    @Test
    public void testToBean2() {
        String jsonString = "{\"success\":true,\"code\":200,\"message\":\"ok\",\"data\":{\"dateTime\":\"2023-02-26 16:44:07\",\"address\":\"\",\"money\":654321.0000,\"name\":\"hello\",\"remark\":null,\"age\":18}}";

        ResultVO<TestUser> resultVO = this.jsonSerializer.toBean(jsonString, ResultVO.class, TestUser.class);

        Assertions.assertTrue(resultVO.isSuccess());
        Assertions.assertEquals(Integer.valueOf(200), resultVO.getCode());
        Assertions.assertEquals("ok", resultVO.getMessage());
        Assertions.assertEquals(TestUser.class, resultVO.getData().getClass());
        Assertions.assertEquals(new BigDecimal("654321.0000"), resultVO.getData().getMoney());

        System.out.println(resultVO);
    }

    /**
     * Test method for {@link Jackson2JsonSerializer#toList(String, Class)}.
     *
     * @since 1.0.0
     */
    @Test
    public void testToList() {
        TestUser2 user = new TestUser2();
        user.setName("world");
        user.setAge(19);
        user.setMoney(new BigDecimal("123456.20"));
        user.setAddress("");
        user.setRemark(null);
        user.setDateTime(LocalDateTime.now());
        user.setStatus(BasicResponseStatus.OK);

        List<TestUser> userList = new ArrayList<>();
        userList.add(user);

        String jsonString = this.jsonSerializer.toJson(userList);

        System.out.println(jsonString);

        List<TestUser2> retList = this.jsonSerializer.toList(jsonString, TestUser2.class);

        TestUser2 testUser = retList.get(0);
        Assertions.assertEquals(TestUser2.class, testUser.getClass());
        Assertions.assertEquals(new BigDecimal("123456.20"), testUser.getMoney());

        System.out.println(retList);
    }

    /**
     * Test method for {@link Jackson2JsonSerializer#toList(String, Class)}.
     *
     * @since 1.0.0
     */
    @Test
    public void testToList2() throws ClassNotFoundException {
        // [{"name":"world","age":19,"money":123456.20,"address":"","remark":null,"dateTime":"2023-02-26 17:32:48"}]
        Map<String, Object> data = new HashMap<>();
        data.put("dateTime", LocalDateTime.now());
        data.put("name", "world");
        data.put("age", 19);
        data.put("money", new BigDecimal("123456.20"));
        data.put("address", "");
        data.put("remark", null);
        data.put("status", BasicResponseStatus.OK);

        List<Map<String, Object>> userList = new ArrayList<>();
        userList.add(data);

        String jsonString = this.jsonSerializer.toJson(userList);

        System.out.println(jsonString);

        Class<Map<String, Object>> mapClass = (Class<Map<String, Object>>) Class.forName("java.util.Map");

        List<Map<String, Object>> retList = this.jsonSerializer.toList(jsonString, mapClass);

        Map<String, Object> map = retList.get(0);
        Double money = (Double) map.get("money");
        Assertions.assertEquals(new BigDecimal("123456.2"), new BigDecimal(money.toString()));

        System.out.println(retList);
    }

    /**
     * Test method for {@link Jackson2JsonSerializer#toMap(String, Class, Class)}.
     *
     * @since 1.0.0
     */
    @Test
    public void testToMap() {
        String jsonString = "{\"name\":\"world\",\"age\":19,\"money\":123456.20,\"address\":\"\",\"remark\":null,\"dateTime\":\"2023-02-26 17:32:48\"}";

        Map<String, Object> map = this.jsonSerializer.toMap(jsonString, String.class, Object.class);

        Double money = (Double) map.get("money");
        Assertions.assertEquals(new BigDecimal("123456.2"), new BigDecimal(money.toString()));

        System.out.println(map);
    }

    @Getter
    @Setter
    private static class TestUser2 extends TestUser {
        private static final long serialVersionUID = 1L;
    }

    @Getter
    @Setter
    private static class TestUser implements Serializable {
        private static final long serialVersionUID = 1L;

        protected String              name;
        protected Integer             age;
        protected BigDecimal          money;
        protected String              address;
        protected String              remark;
        protected LocalDateTime       dateTime;
        protected BasicResponseStatus status;

        public TestUser() {
        }

        @Builder
        public TestUser(String name, Integer age, BigDecimal money, String address, String remark,
                        LocalDateTime dateTime, BasicResponseStatus status) {
            this.name = name;
            this.age = age;
            this.money = money;
            this.address = address;
            this.remark = remark;
            this.dateTime = dateTime;
            this.status = status;
        }
    }

    @Getter
    @Setter
    private static class ResultVO<T> implements Serializable {
        private static final long serialVersionUID = 1L;

        private boolean success;    // 请求状态
        private Integer code;       // 返回编码
        private String  message;    // 提示信息
        private T       data;       // 返回数据

    }

}
