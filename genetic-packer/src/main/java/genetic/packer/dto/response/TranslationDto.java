package genetic.packer.dto.response;

import genetic.api.builder.HasBuilder;

/**
 * @author piotr.larysz
 */
@HasBuilder
public class TranslationDto<T> {

    private T x;

    private T y;

    private T z;

    public T getX() {
        return x;
    }

    public void setX(T x) {
        this.x = x;
    }

    public T getY() {
        return y;
    }

    public void setY(T y) {
        this.y = y;
    }

    public T getZ() {
        return z;
    }

    public void setZ(T z) {
        this.z = z;
    }
}
