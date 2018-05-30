public class Rule {

    Symbol left;
    Symbol right1;
    Symbol right2;
    double probability = Math.random();

    public Rule() {
    }

    public Rule(Symbol left, Symbol right1, Symbol right2) {
        this.left = left;
        this.right1 = right1;
        this.right2 = right2;
    }

    @Override
    public String toString() {
        return left.toString() + "->" + right1.toString() + right2.toString();
    }
}
