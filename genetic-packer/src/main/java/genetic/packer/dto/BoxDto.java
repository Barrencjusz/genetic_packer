package genetic.packer.dto;

import genetic.api.builder.HasBuilder;

/**
 * @author piotr.larysz
 */
@HasBuilder
public class BoxDto {

    private Integer width;

    private Integer height;

    private Integer depth;

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }
}
