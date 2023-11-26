package local.json.jackson;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.ReferenceTypeSerializer;
import com.fasterxml.jackson.databind.type.ReferenceType;
import com.fasterxml.jackson.databind.util.NameTransformer;

import local.json.OptionalProperty;

/**
 * OptionalPropertySerializer
 */
public class OptionalPropertySerializer extends ReferenceTypeSerializer<OptionalProperty<?>>{

    private static final long serialVersionUID = 1L;

    /*
     * /**********************************************************
     * /* Constructors, factory methods
     * /**********************************************************
     */

    protected OptionalPropertySerializer(ReferenceType fullType, boolean staticTyping,
                                     TypeSerializer vts, JsonSerializer<Object> ser) {
        super(fullType, staticTyping, vts, ser);
    }

    protected OptionalPropertySerializer(OptionalPropertySerializer base, BeanProperty property,
                                     TypeSerializer vts, JsonSerializer<?> valueSer, NameTransformer unwrapper,
                                     Object suppressableValue)
    {
        // Keep suppressNulls to true so it behaves like a missing key
        super(base, property, vts, valueSer, unwrapper,
                suppressableValue, true);
    }

    @Override
    protected ReferenceTypeSerializer<OptionalProperty<?>> withResolved(BeanProperty prop,
            TypeSerializer vts, JsonSerializer<?> valueSer,
            NameTransformer unwrapper) {
        return new OptionalPropertySerializer(this, prop, vts, valueSer, unwrapper,
                _suppressableValue);
    }

    @Override
    public ReferenceTypeSerializer<OptionalProperty<?>> withContentInclusion(Object suppressableValue,
            boolean suppressNulls) {
        return new OptionalPropertySerializer(this, _property, _valueTypeSerializer,
                _valueSerializer, _unwrapper,
                suppressableValue);
    }

    /*
     * /**********************************************************
     * /* Abstract method impls
     * /**********************************************************
     */

    @Override
    protected boolean _isValuePresent(OptionalProperty<?> value) {
        return null != value;
    }

    @Override
    protected Object _getReferenced(OptionalProperty<?> value) {
        return null == value ? null : value.get();
    }

    @Override
    protected Object _getReferencedIfPresent(OptionalProperty<?> value) {
        return value.get();
    }

}
