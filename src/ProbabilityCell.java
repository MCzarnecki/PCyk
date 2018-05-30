public class ProbabilityCell {

    static final ProbabilityCell EMPTY_CELL = new ProbabilityCell(-1.0, -1.0);

    double item1;
    double item2;

    ProbabilityCell() {

    }

    ProbabilityCell(double item1, double item2) {
        this.item1 = item1;
        this.item2 = item2;
    }
}
