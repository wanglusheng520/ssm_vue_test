package com.beneway.common.common.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class ClassUtil {

    // jackson转换工具
    private static final ObjectMapper objectMapper = getObjMapper();

    private static ObjectMapper getObjMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    public static <T> T toClass(Object object, Class<T> tClass){
        try {
            String s = objectMapper.writeValueAsString(object);
            T t = objectMapper.readValue(s, tClass);
            return t;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> toClassList(List<? super T> objects, Class<T> tClass){
        try {
            List<T> list = new LinkedList<>();
            for (Object object : objects) {
                String s = objectMapper.writeValueAsString(object);
                T t = objectMapper.readValue(s, tClass);
                list.add(t);
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
