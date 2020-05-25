package game.base.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author yzz
 * 2020/3/23 17:12
 */
public class JsonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper[] ARRAY = new ObjectMapper[10];

    static {
        for (int i = 0; i < ARRAY.length; ++i) {
            ARRAY[i] = new ObjectMapper();
        }
    }


    private static int currentThreadIdx() {
        long threadId = Thread.currentThread().getId();
        return (int) (threadId % ARRAY.length);
    }

    public static String toJsonString(Object obj) {
        int idx = currentThreadIdx();
        try {
            return ARRAY[idx].writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error("", e);
        }
        return "";
    }

    public static String toJsonPrettyString(Object obj) {
        int idx = currentThreadIdx();
        try {
            return ARRAY[idx].writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error("", e);
        }
        return "";
    }


    public static <T> T fromJsonString(String json, Class<T> clazz) {
        int idx = currentThreadIdx();
        try {
            return ARRAY[idx].readValue(json, clazz);
        } catch (IOException e) {
            LOGGER.error("", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public static <T> T fromJsonString(String json, TypeReference<T> clazz) {
        int idx = currentThreadIdx();
        try {
            return ARRAY[idx].readValue(json, clazz);
        } catch (IOException e) {
            LOGGER.error("", e);
            throw new RuntimeException(e.getMessage());
        }
    }

}
