package com.tutuniao.tutuniao.util;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
public class Jackson2Helper {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static final String toJsonString(Object obj) {
        if (null == obj) {
            return null;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    public static final String toJsonStringNotNull(Object obj) {
        if (null == obj) {
            return null;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 支持泛型
     */
    public static final <T> T parsonObject(String jsonString, TypeReference<T> valueTypeRef) {
        try {

            // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(jsonString, valueTypeRef);

        } catch (Exception e) {
            log.error("parsonObject error ", e);
        }
        return null;
    }


    public static JsonNode readJson(String json) {
        try {
            return mapper.readTree(json);
        } catch (Exception e) {
            log.error("readJson error ", e);
        }
        return null;
    }

    public static <T extends Object> List<T> jsonToList(String json, Class<T> bean) {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, bean);
        try {
            return mapper.readValue(json, javaType);
        } catch (IOException e) {
            log.error("jsonToList error ", e);
        }
        return null;
    }
}
