package genetic.api.individual;

import genetic.api.builder.HasBuilder;

/**
 * @author piotr.larysz
 */
@HasBuilder
public class Cell<T> {

    private int order;

    private int processingOrder; //todo maybe not required

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
