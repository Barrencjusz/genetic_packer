package genetic.packer.fx;

import net.karneim.pojobuilder.GeneratePojoBuilder;

/**
 * @author piotr.larysz
 */
@GeneratePojoBuilder
public class Cell<T> {

    private int order;

    private int processingOrder;

    private T nucleus;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getProcessingOrder() {
        return processingOrder;
    }

    public void setProcessingOrder(int processingOrder) {
        this.processingOrder = processingOrder;
    }

    public T getNucleus() {
        return nucleus;
    }

    public void setNucleus(T nucleus) {
        this.nucleus = nucleus;
    }
}
