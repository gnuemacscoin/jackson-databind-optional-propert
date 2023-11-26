package local.json;

import java.io.Serializable;
import java.util.Objects;

public class OptionalProperty<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final T value;

    public OptionalProperty(T value) {
        this.value = value;
    }

    public static <T> OptionalProperty<T> of(T value) {
        return new OptionalProperty<T>(value);
    }

    public T get() {
        return value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OptionalProperty<?> other = (OptionalProperty<?>) obj;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }

    public boolean deepEquals(OptionalProperty<?> o) {
        return (this == o) || (o != null && Objects.deepEquals(this.get(), o.get()));
    }

    public static boolean deepEquals(OptionalProperty<?> a, OptionalProperty<?> b) {
        return (a != null && a.deepEquals(b)) || (null == b);
    }

}
