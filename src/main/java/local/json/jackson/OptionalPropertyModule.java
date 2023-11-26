package local.json.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.databind.Module;

public class OptionalPropertyModule extends Module {

    private final String NAME = "OptionalPropertyModule";

    @Override
    public void setupModule(SetupContext context) {
        context.addSerializers(new OptionalPropertySerializers());
        context.addDeserializers(new OptionalPropertyDeserializers());
        // Modify type info for OptionalProperty
        context.addTypeModifier(new OptionalPropertyTypeModifier());
        context.addBeanSerializerModifier(new OptionalPropertyBeanSerializerModifier());
    }

    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }

    @Override
    public int hashCode() {
        return NAME.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public String getModuleName() {
        return NAME;
    }

}
