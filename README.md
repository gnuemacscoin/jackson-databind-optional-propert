# jackson-databind-optional-property

This module provides an `OptionalProperty` wrapper class, a Jackson module to serialize/deserialize it, and modified `openapi-generator` templates for the `spring` generator, which will use this wrapper instead of the existing `JsonNullable`.
Usage: `java -jar path/to/openapi-generator-cli.jar generate -i path/to/openapi.yaml -g spring -c path/to/openapi-spring-config.json -o /tmp/spring-api --template-dir openapi/templates/ --additional-properties library=spring-boot,openApiNullable=false`.

## Example

Given the following model
``` yaml
components:
  schemas:
    OptionalNullable:
      required:
        - requiredKnown
        - requiredNullable
      properties:
        requiredKnown:
          type: string
        requiredNullable:
          type: string
          nullable: true
        optionalKnown:
          type: string
          default: "default value"
        optionalNullable:
          type: string
          nullable: true
```
it will produce
``` java
public class OptionalNullable {

    private String requiredKnown;

    private @Nullable String requiredNullable = null;

    private @Nullable OptionalProperty<String> optionalKnown = OptionalProperty.of("default value");

    private @Nullable OptionalProperty<@Nullable String> optionalNullable = null;

    public OptionalNullable(
            @JsonProperty(value = "requiredKnown", required = true) String requiredKnown,
            @JsonProperty(value = "requiredNullable", required = true) String requiredNullable
        ) {
        this.requiredKnown = requiredKnown;
        this.requiredNullable = requiredNullable;
    }

    @NotNull
    @Schema(name = "requiredKnown", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(value = "requiredKnown", required = true)
    public String getRequiredKnown() {
        return requiredKnown;
    }

    @Schema(name = "requiredNullable", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(value = "requiredNullable", required = true)
    public @Nullable String getRequiredNullable() {
        return requiredNullable;
    }

    @NotNull
    @Schema(name = "optionalKnown", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty(value = "optionalKnown")
    public @Nullable OptionalProperty<String> getOptionalKnown() {
        return optionalKnown;
    }

    @Schema(name = "optionalNullable", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty(value = "optionalNullable")
    public @Nullable OptionalProperty<@Nullable String> getOptionalNullable() {
        return optionalNullable;
    }

    // setters, fluent methods, equals and hashCode

}
```

## Motivation

The original generator reasons like this:
* a missing json property is mapped to a java `null`,
* unless that property is defined as nullable, in which case a missing property is mapped to `JsonNullable.undefined()`, while a present but null property is mapped to `JsonNullable.of(null)`.

That design is a mess in several ways:
* The reasonable expectation, suggested even by the OpenAPI schema definition, that null should be part of a property is broken, as the null is hijacked by the containing object for its own purposes.
* This hijacking is inconsistent: while nulls in generated properties stand for missing keys, this no longer applies inside `JsonNullable`, where `null` is once again given back to the property.
* On top of all that - the `JsonNullable` is overdefined: it wraps both a null and a missing key, hence setting the `JsonNullable` itself to `null` is always illegal - there's nothing left for it to represent.
* It isn't carried out to completion. No `@JsonIgnore` is applied anywhere, so a java `null` in an optional, non-null property, which should represent a missing key by the above logic, is instead serialized as `null`.

This here is an attempt to set things straight. The null state stays with the property at all times, and an optional property is instead wrapped in an `OptionalProperty`, which is itself `@Nullable` and whose `null` state stands for a missing key.
* Jackson itself will refuse to deserialize a json with required, yet missing keys, due to the newly generated constructor argument annotations.
* `jakarta.validation.constraints` such as `@NotNull` will be applied to the value inside the `OptionalProperty`, and only if this `OptionalProperty` is present.

## MapStruct and JSON Merge Patch

A working setup for MapStruct, with RFC 7396 JSON Merge Patch semantics, mapping from a patch object to an `@Entity`, would look like:
``` java
@Mapper(componentModel = "spring")
public interface SomeMapper {

    @Condition
    default <T> boolean isPresent(@Nullable OptionalProperty<T> optionalProperty) {
        return null != optionalProperty;
    }

    default <T> T fromPresent(OptionalProperty<T> property) {
        return property.get();
    }

}
```
