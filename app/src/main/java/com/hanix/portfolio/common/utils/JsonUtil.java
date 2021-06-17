package com.hanix.portfolio.common.utils;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class JsonUtil {
    public static final String[] EXCLUDE_WORD_IN_JSON = {"password", "pwd", "userPwd", "currentPwd", "versionNum", "newPwd", "oldPwd"};
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.setSerializationInclusion(Include.NON_EMPTY);
        mapper.addMixIn(Object.class, MyMixIn.class);
        mapper.setFilterProvider(getFilterProvider(EXCLUDE_WORD_IN_JSON));
    }

    @JsonFilter("myMixIn")
    public static class MyMixIn {
        //
    }

    private static FilterProvider getFilterProvider(String... excludeWords) {
        SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider();
        return simpleFilterProvider
                .addFilter("myMixIn", SimpleBeanPropertyFilter.serializeAllExcept(excludeWords));
    }

    @SuppressWarnings("unchecked")
    public static <T> T toObject(String string, TypeReference<?> typeReference) throws JsonProcessingException {
        if (string == null) {
            return null;
        }
        return (T) mapper.readValue(string, typeReference);
    }

}
