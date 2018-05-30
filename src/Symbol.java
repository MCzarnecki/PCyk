public class Symbol {
    char value;
    SymbolType symbolType;
    int index;

    Symbol(char value, SymbolType symbolType, int index) {
        this.value = value;
        this.symbolType = symbolType;
        this.index = index;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
