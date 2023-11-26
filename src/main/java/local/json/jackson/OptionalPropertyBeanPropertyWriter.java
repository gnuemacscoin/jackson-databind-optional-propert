package local.json.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.util.NameTransformer;

public class OptionalPropertyBeanPropertyWriter extends BeanPropertyWriter {

    private static final long serialVersionUID = 1L;

    protected OptionalPropertyBeanPropertyWriter(BeanPropertyWriter base) {
        super(base);
    }

    protected OptionalPropertyBeanPropertyWriter(BeanPropertyWriter base, PropertyName newName) {
        super(base, newName);
    }

    @Override
    protected BeanPropertyWriter _new(PropertyName newName) {
        return new OptionalPropertyBeanPropertyWriter(this, newName);
    }

    @Override
    public BeanPropertyWriter unwrappingWriter(NameTransformer unwrapper) {
        return new UnwrappingOptionalPropertyBeanPropertyWriter(this, unwrapper);
    }

    @Override
    public void serializeAsField(Object bean, JsonGenerator jgen, SerializerProvider prov) throws Exception {
        Object value = get(bean);
        if (null == value) {
            return;
        }
        super.serializeAsField(bean, jgen, prov);
    }

}
