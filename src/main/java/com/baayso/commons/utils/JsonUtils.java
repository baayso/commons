package com.baayso.commons.utils;

import java.text.SimpleDateFormat;

import org.springside.modules.utils.mapper.JsonMapper;
import org.springside.modules.utils.time.DateFormatUtil;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;

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
        // JsonUtils.INSTANCE.getMapper().setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // JsonUtils.INSTANCE.getMapper().enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        JsonUtils.INSTANCE.getMapper().setDateFormat(new SimpleDateFormat(DateFormatUtil.PATTERN_DEFAULT_ON_SECOND));

        JsonUtils.INSTANCE.getMapper().configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
        // JsonUtils.INSTANCE.getMapper().configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        // JsonUtils.INSTANCE.getMapper().configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
    }

    public JsonUtils() {
        super(null);
    }

    public JsonUtils(JsonInclude.Include include) {
        super(include);
    }

}
