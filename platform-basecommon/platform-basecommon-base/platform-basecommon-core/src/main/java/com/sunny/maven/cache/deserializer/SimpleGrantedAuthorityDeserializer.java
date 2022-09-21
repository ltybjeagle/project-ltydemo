package com.sunny.maven.cache.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;

/**
 * @author SUNNY
 * @description: 自定义spring security权限集合反序列
 * @create: 2022-09-21 16:12
 */
public class SimpleGrantedAuthorityDeserializer extends StdDeserializer<SimpleGrantedAuthority> {
    public SimpleGrantedAuthorityDeserializer() {
        super(SimpleGrantedAuthority.class);
    }
    @Override
    public SimpleGrantedAuthority deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JacksonException {
        JsonNode tree = jsonParser.getCodec().readTree(jsonParser);
        return new SimpleGrantedAuthority(tree.get("authority").textValue());
    }
}
