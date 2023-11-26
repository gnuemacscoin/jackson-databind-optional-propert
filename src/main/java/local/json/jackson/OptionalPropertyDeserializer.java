package local.json.jackson;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.std.ReferenceTypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;

import local.json.OptionalProperty;

public class OptionalPropertyDeserializer extends ReferenceTypeDeserializer<OptionalProperty<Object>> {

    private static final long serialVersionUID = 1L;

    /*
     * /**********************************************************
     * /* Life-cycle
     * /**********************************************************
     */
    public OptionalPropertyDeserializer(JavaType fullType, ValueInstantiator inst,
            TypeDeserializer typeDeser, JsonDeserializer<?> deser) {
        super(fullType, inst, typeDeser, deser);
    }

    /*
     * /**********************************************************
     * /* Abstract method implementations
     * /**********************************************************
     */

    @Override
    public OptionalPropertyDeserializer withResolved(TypeDeserializer typeDeser, JsonDeserializer<?> valueDeser) {
        return new OptionalPropertyDeserializer(_fullType, _valueInstantiator,
                typeDeser, valueDeser);
    }

    @Override
    public Object getAbsentValue(DeserializationContext ctxt) {
        return null;
    }

    @Override
    public OptionalProperty<Object> getNullValue(DeserializationContext ctxt) {
        return OptionalProperty.of(null);
    }

    @Override
    public OptionalProperty<Object> referenceValue(Object contents) {
        return OptionalProperty.of(contents);
    }

    @Override
    public Object getReferenced(OptionalProperty<Object> reference) {
        return null == reference ? null : reference.get();
    }

    @Override
    public OptionalProperty<Object> updateReference(OptionalProperty<Object> reference, Object contents) {
        return OptionalProperty.of(contents);
    }

    @Override
    public Boolean supportsUpdate(DeserializationConfig config) {
        // yes; regardless of value deserializer reference itself may be updated
        return Boolean.TRUE;
    }

}
