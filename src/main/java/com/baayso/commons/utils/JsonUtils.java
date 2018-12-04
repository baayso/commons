package com.baayso.commons.utils;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.vip.vjtools.vjkit.mapper.JsonMapper;
import com.vip.vjtools.vjkit.time.DateFormatUtil;

/**
 * Json工具类。
 *
 * @author ChenFangjie (2015/12/9 20:22)
 * @since 1.0.0
 */
public class JsonUtils extends JsonMapper {

    public static final JsonUtils INSTANCE;

    static {
        INSTANCE = new JsonUtils();

        ObjectMapper mapper = INSTANCE.getMapper();

        // mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        mapper.setDateFormat(new SimpleDateFormat(DateFormatUtil.PATTERN_DEFAULT_ON_SECOND));

        mapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
        mapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        mapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);

        // default-property-inclusion
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        // 序列化为JSON时将 Long 转为 String（全局配置）
        // mapper.registerModule(new SimpleModule().addSerializer(Long.class, ToStringSerializer.instance));
    }

    public JsonUtils() {
        super(null);
    }

    public JsonUtils(JsonInclude.Include include) {
        super(include);
    }

}
