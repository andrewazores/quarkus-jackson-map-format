package org.acme;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.type.MapType;
import io.quarkus.jackson.ObjectMapperCustomizer;
import jakarta.inject.Singleton;

@Singleton
public class ObjectMapperCustomization implements ObjectMapperCustomizer {

    @Override
    public void customize(ObjectMapper objectMapper) {
        SimpleModule mapModule =
                new SimpleModule(
                        "MapSerialization", new Version(1, 0, 0, null, "org.acme", "quarkus-jackson-map-format"));

        mapModule.setSerializerModifier(new MapSerializerModifier());

        objectMapper.registerModule(mapModule);
    }

    static class MapSerializerModifier extends BeanSerializerModifier {
        @Override
        public JsonSerializer<?> modifyMapSerializer(
                SerializationConfig config,
                MapType valueType,
                BeanDescription beanDesc,
                JsonSerializer serializer) {
            if (valueType.getKeyType().getRawClass().equals(String.class)
                    && valueType.getContentType().getRawClass().equals(String.class)) {
                return new MapSerializer();
            }
            return serializer;
        }
    }

    static class MapSerializer extends JsonSerializer<Map<?, ?>> {

        @Override
        public void serialize(Map<?, ?> map, JsonGenerator gen, SerializerProvider serializers)
                throws IOException {
            gen.writeStartArray();

            for (var entry : map.entrySet()) {
                gen.writeStartObject();

                gen.writePOJOField("key", entry.getKey());
                gen.writePOJOField("value", entry.getValue());

                gen.writeEndObject();
            }

            gen.writeEndArray();
        }
    }
}
