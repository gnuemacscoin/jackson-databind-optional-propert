package local.json.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.UnwrappingBeanPropertyWriter;
import com.fasterxml.jackson.databind.util.NameTransformer;

public class UnwrappingOptionalPropertyBeanPropertyWriter extends UnwrappingBeanPropertyWriter {

    private static final long serialVersionUID = 1L;

    public UnwrappingOptionalPropertyBeanPropertyWriter(BeanPropertyWriter base, NameTransformer transformer) {
        super(base, transformer);
    }

    protected UnwrappingOptionalPropertyBeanPropertyWriter(UnwrappingBeanPropertyWriter base,
            NameTransformer transformer, SerializedString name) {
        super(base, transformer, name);
    }

    @Override
    protected UnwrappingBeanPropertyWriter _new(NameTransformer transformer, SerializedString newName) {
        return new UnwrappingOptionalPropertyBeanPropertyWriter(this, transformer, newName);
    }

    @Override
    public void serializeAsField(Object bean, JsonGenerator gen, SerializerProvider prov) throws Exception {
        Object value = get(bean);
        if (null == value) {
            return;
        }
        super.serializeAsField(bean, gen, prov);
    }

}
