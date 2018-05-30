import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Cyk {

    private static final int NUM_OF_THREADS = 4;

    static ConcurrentLinkedQueue<CellRule> jobs = new ConcurrentLinkedQueue<>();

    static RulesTable rulesTable;
    static ProbabilityArray probabilityArray;
    static Grammar grammar = new Grammar();

    static ExecutorService executors = Executors.newFixedThreadPool(NUM_OF_THREADS);

    static void prepareJobs(int sentenceLength) {
        for (int i = 1; i < sentenceLength; i++) {
            for (int j = 0; j < sentenceLength - i; j++) {
                jobs.add(rulesTable.get(i, j));
            }
        }
    }

    static boolean parseSentence() {

        while (true) {
            CellRule cellToAnalyse = jobs.poll();

            if (cellToAnalyse == null) {
                return true;
            }

            int i = cellToAnalyse.xCor;
            int j = cellToAnalyse.yCor;

            for (int k = 0; k < i; k++) {

                int parentOneI = k;
                int parentOneJ = j;

                int parentTwoI = i - k - 1;
                int parentTwoJ = j + k + 1;

                while (!rulesTable.table[parentOneI][parentOneJ].evaluated.get()
                        && !rulesTable.table[parentTwoI][parentTwoJ].evaluated.get()) {
                }

                for (Rule rule : grammar.rules) {
                    if (rule.right2 != null) {
                        int rule1index = rule.right1.index;
                        int rule2index = rule.right2.index;

                        if (probabilityArray.table[parentOneI][parentOneJ][rule1index] != ProbabilityCell.EMPTY_CELL
                                && probabilityArray.table[parentTwoI][parentTwoJ][rule2index] != ProbabilityCell.EMPTY_CELL) {

                            ProbabilityCell currentCell = probabilityArray.table[i][j][rule.left.index];
                            ProbabilityCell parentCellProbability = probabilityArray.table[parentOneI][parentOneJ][rule1index];
                            ProbabilityCell parent2CellProbability = probabilityArray.table[parentTwoI][parentTwoJ][rule2index];
                            probabilityArray.table[i][j][rule.left.index] = calculateProbability(currentCell, parentCellProbability, parent2CellProbability, rule);
                            rulesTable.table[i][j].rules.add(rule);
                        }
                    }
                }
            }
            rulesTable.table[i][j].evaluated.compareAndSet(false, true);
        }
    }

    private static void initFirstRow(String sentence) {
        char[] chars = sentence.toCharArray();
        for (int i = 0; i < sentence.length(); i++) {
            for (Rule rule: grammar.rules) {
                if (rule.right1.value == chars[i]) {
                    rulesTable.table[0][i].rules.add(rule);
                    probabilityArray.table[0][i][rule.left.index] = new ProbabilityCell(rule.probability, rule.probability);
                }
            }
            rulesTable.table[0][i].evaluated.compareAndSet(false, true);
        }

    }

    private static ProbabilityCell calculateProbability(ProbabilityCell currentCell, ProbabilityCell parent1, ProbabilityCell parent2, Rule rule) {
        ProbabilityCell newProbabilityCell = new ProbabilityCell();
        if (currentCell.equals(ProbabilityCell.EMPTY_CELL)) {
            newProbabilityCell.item1 = rule.probability * parent1.item1 * parent2.item1;
            newProbabilityCell.item2 = rule.probability * parent1.item2 * parent2.item2;
        } else {
            newProbabilityCell.item1 = currentCell.item1 + (rule.probability * parent1.item1 * parent2.item2);
            newProbabilityCell.item2 = currentCell.item2 + (rule.probability * parent2.item2 * parent2.item2);
        }
        return newProbabilityCell;
    }

    public static void main(String ...args) throws InterruptedException, ExecutionException {
        String testSentence = "abababababababababababababababababababababababababababababababababababababababababababababababababababababababab";
        // String testSentence = "fdfadbfaeeccfbeafdbaabecaeabecaeabdebfaeffcffaebeeafbeaabefaeafdbdeaebabefcaefc";


        // Grammar values generation
        Symbol[] terminals = GrammarGenerator.generateTerminalSymbols();
        Symbol[] nonTerminals = GrammarGenerator.generateNonTerminalSymbols();
        List<Rule> nonTerminalRules = GrammarGenerator.generateRulesFromSymbols(nonTerminals, 100);
        List<Rule> terminalRules = GrammarGenerator.generateTerminalRules(terminals, nonTerminals);
        List<Rule> rules = new ArrayList<>(nonTerminalRules);
        rules.addAll(terminalRules);
        grammar.rules = rules;
        grammar.nonTerminalSymbols = nonTerminals;
        grammar.terminalSymbols = terminals;

        // CYK parsing
        long startTime = System.currentTimeMillis();
        rulesTable = new RulesTable(testSentence.length());
        probabilityArray = new ProbabilityArray(testSentence.length(), nonTerminals.length);
        initFirstRow(testSentence);
        prepareJobs(testSentence.length());

        List<Callable<Object>> todo = new ArrayList<>();
        for (int i = 0; i < NUM_OF_THREADS; i++) {
            todo.add(Cyk::parseSentence);
        }
        List<Future<Object>> status = executors.invokeAll(todo);

        for (Future<Object> s: status) {
            s.get();
        }
        executors.shutdown();

        long endTime = System.currentTimeMillis();
        long milis = endTime - startTime;
        System.out.println(milis);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milis);
        milis = milis - seconds * 1000;

        System.out.println("s: " + seconds + " + ms:" + milis);
    }

}
