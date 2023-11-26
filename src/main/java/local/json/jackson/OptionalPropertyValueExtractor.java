package local.json.jackson;

import jakarta.validation.valueextraction.ExtractedValue;
import jakarta.validation.valueextraction.UnwrapByDefault;
import jakarta.validation.valueextraction.ValueExtractor;
import local.json.OptionalProperty;

/**
 * Extractor for OptionalProperty
 */
@UnwrapByDefault
public class OptionalPropertyValueExtractor implements ValueExtractor<OptionalProperty<@ExtractedValue ?>> {

    @Override
    public void extractValues(OptionalProperty<?> originalValue, ValueReceiver receiver) {
        if (null != originalValue) {
            receiver.value(null, originalValue.get());
        }
    }

}
