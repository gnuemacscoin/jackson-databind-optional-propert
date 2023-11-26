
package local.json.jackson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.ReferenceType;

import local.json.OptionalProperty;

public class OptionalPropertyDeserializers extends Deserializers.Base {

    @Override
    public JsonDeserializer<?> findReferenceDeserializer(ReferenceType refType, DeserializationConfig config,
            BeanDescription beanDesc, TypeDeserializer contentTypeDeserializer,
            JsonDeserializer<?> contentDeserializer) {
        return (refType.hasRawClass(OptionalProperty.class))
                ? new OptionalPropertyDeserializer(refType, null, contentTypeDeserializer, contentDeserializer)
                : null;
    }

}
