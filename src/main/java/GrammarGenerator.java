//import java.util.*;
//import java.util.concurrent.ThreadLocalRandom;
//
//public class GrammarGenerator {
//
//    private static final String nonTerminals = "abcdefghijklmnopqrstuvwxyz".toUpperCase();
//    private static final String terminals = "abcdefghijklmnopqrstuvwxyz";
//
//    private GrammarGenerator() {
//
//    }
//
////    public static Symbol[] generateTerminalSymbols() {
////
////        Symbol[] symbols = new Symbol[terminals.length()];
////        char[] terminalValues = terminals.toCharArray();
////        for (int i = 0; i < terminals.length(); i++) {
////            symbols[i] = new Symbol(terminalValues[i], SymbolType.ST_TERMINAL, i);
////        }
////
////        return symbols;
////    }
////
////    public static Symbol[] generateNonTerminalSymbols() {
////
////        Symbol[] symbols = new Symbol[nonTerminals.length()];
////        char[] nonTerminalsSymbols = nonTerminals.toCharArray();
////        for (int i = 0; i < nonTerminals.length(); i++) {
////            symbols[i] = new Symbol(nonTerminalsSymbols[i], SymbolType.ST_NON_TERMINAL, i);
////        }
////
////        return symbols;
////    }
//
//    public static List<Rule> generateRulesFromSymbols(Symbol[] symbols, int size) {
//
//        Set<Rule> rules = new HashSet<>();
//
//        Symbol A = null;
//        Symbol B = null;
//        Symbol C = null;
//        Symbol S = null;
//
//        for (int i = 0; i < symbols.length; i++) {
//            if (symbols[i].value == 'A') {
//                A = symbols[i];
//            }
//            if (symbols[i].value == 'B') {
//                B = symbols[i];
//            }
//            if (symbols[i].value == 'C') {
//                C = symbols[i];
//            }
//            if (symbols[i].value == 'S') {
//                S = symbols[i];
//            }
//        }
//
//        // Add necessary rules
//        rules.add(new Rule(S, A, B));
//        rules.add(new Rule(S, B, C));
//        rules.add(new Rule(A, B, A));
//        rules.add(new Rule(B, C, C));
//        rules.add(new Rule(C, A, B));
//
//        // Generate random rules
//        while(rules.size() < size) {
//            int leftIndex = ThreadLocalRandom.current().nextInt(0, symbols.length);
//            int rigthIndex = 0;
//            int right2Index = 0;
//
//            while (rigthIndex == right2Index) {
//                rigthIndex = ThreadLocalRandom.current().nextInt(0, symbols.length);
//                right2Index = ThreadLocalRandom.current().nextInt(0, symbols.length);
//            }
//            rules.add(new Rule(symbols[leftIndex], symbols[rigthIndex], symbols[right2Index]));
//        }
//
//        return new ArrayList<>(rules);
//    }
//
//    public static List<Rule> generateTerminalRules(Symbol[] terminals, Symbol[] nonTerminals) {
//        List<Rule> rules = new ArrayList<>();
//
//        for (int i = 0; i < terminals.length; i++) {
//            Rule rule = new Rule();
//            rule.left = nonTerminals[ThreadLocalRandom.current().nextInt(0, nonTerminals.length)];
//            rule.right1 = terminals[i];
//            rules.add(rule);
//        }
//
//        return rules;
//    }
//
//
//
//
//}
