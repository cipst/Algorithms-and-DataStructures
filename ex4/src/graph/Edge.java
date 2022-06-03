package graph;

public class Edge<S> {

    private S label = null;
    private double weight = 0;

    public Edge(S label) {
        this.label = label;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return this.weight;
    }

    public S getLabel() {
        return this.label;
    }

    @Override
    public String toString() {
        return "" + this.weight;
    }
}
