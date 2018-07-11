package com.jornco.mframework.libs;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.joda.time.DateTime;

import java.lang.reflect.Type;

/**
 * Created by kkopite on 2018/7/11.
 */

public class DateTimeTypeConverter implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {
    @Override
    public DateTime deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        return new DateTime(json.getAsInt() * 1000L);
    }

    @Override
    public JsonElement serialize(DateTime src, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(src.getMillis() / 1000);
    }
}
