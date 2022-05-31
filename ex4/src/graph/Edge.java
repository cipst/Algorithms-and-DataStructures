package graph;

public class Edge<S> {

    private S label = null;
    private Number value = 0;

    public Edge(S label) {
        this.label = label;
    }

    public void setValue(Number value) {
        this.value = value;
    }

    public Number getValue() {
        return this.value;
    }

    public S getLabel() {
        return this.label;
    }
}
