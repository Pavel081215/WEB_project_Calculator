package work.worker;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ParserMatch {
    private static final Logger logger = LoggerFactory.getLogger(ParserMatch.class);
    private Map<String, Double> variables;
    private Map<String, ScientificCalculator> variablesScientificCalculator;


    public ParserMatch() {
        variables = new HashMap<String, Double>();
        variablesScientificCalculator = new HashMap<String, ScientificCalculator>();
    }

    public ParserMatch(Map<String, ScientificCalculator> variablesScientificCalculator) {
        variables = new HashMap<String, Double>();
        this.variablesScientificCalculator = variablesScientificCalculator;
    }


    public Map<String, ScientificCalculator> getVariablesScientificCalculator() {
        return variablesScientificCalculator;
    }

    public void setVariablesScientificCalculator(Map<String, ScientificCalculator> variablesScientificCalculator) {
        this.variablesScientificCalculator = variablesScientificCalculator;
    }


    public void setVariable(String variableName, Double variableValue) {
        variables.put(variableName, variableValue);
    }

    public Double getVariable(String variableName) {
        if (!variables.containsKey(variableName)) {
            logger.error("Error: Try get unexists variable '" + variableName + "'");
            return 0.0;
        }
        return variables.get(variableName);
    }


    public double Parse(String b) throws Exception {
        logger.info("Start parse");
        String s = b.replaceAll(" ", "");
        Result result = PlusMinus(s);
        if (!result.rest.isEmpty()) {
            logger.error("Error: can't full parse" + result.rest);
        }
        return result.acc;
    }

    private Result PlusMinus(String s) throws Exception {
        logger.info("Start PlusMinus");
        Result current = MulDiv(s);
        double acc = current.acc;
        while (current.rest.length() > 0) {
            if (!(current.rest.charAt(0) == '+' || current.rest.charAt(0) == '-')) break;

            char sign = current.rest.charAt(0);
            String next = current.rest.substring(1);

            current = MulDiv(next);
            if (sign == '+') {
                acc += current.acc;
            } else {
                acc -= current.acc;
            }
        }
        return new Result(acc, current.rest);
    }

    private Result Bracket(String s) throws Exception {
        logger.info("Start Bracket");
        char zeroChar = s.charAt(0);
        if (zeroChar == '(') {
            Result r = PlusMinus(s.substring(1));
            if (!r.rest.isEmpty() && r.rest.charAt(0) == ')') {
                r.rest = r.rest.substring(1);
            } else {
                logger.error("Error: not close bracket");
            }
            return r;
        }
        return FunctionVariable(s);
    }

    private Result FunctionVariable(String s) throws Exception {
        logger.info("Start FunctionVariable");
        String f = "";
        int i = 0;
        while (i < s.length() && (Character.isLetter(s.charAt(i)) || (Character.isDigit(s.charAt(i)) && i > 0))) {
            f += s.charAt(i);
            i++;
        }
        if (!f.isEmpty()) {
            if (s.length() > i && s.charAt(i) == '(') {
                Result r = Bracket(s.substring(f.length()));
                return processFunction(f, r);
            } else {
                logger.info("Otherwise - is a variable");
                return new Result(getVariable(f), s.substring(f.length()));
            }
        }
        return Num(s);
    }

    private Result MulDiv(String s) throws Exception {
        logger.info("Start MulDiv");
        Result current = Bracket(s);

        double acc = current.acc;
        while (true) {
            if (current.rest.length() == 0) {
                return current;
            }
            char sign = current.rest.charAt(0);
            if ((sign != '*' && sign != '/')) return current;
            String next = current.rest.substring(1);
            Result right = Bracket(next);
            if (sign == '*') {
                acc *= right.acc;
            } else {
                acc /= right.acc;
            }
            current = new Result(acc, right.rest);
        }
    }

    private Result Num(String s) throws Exception {
        logger.info("Start Num");
        int i = 0;
        int dot_cnt = 0;
        boolean negative = false;
        if (s.charAt(0) == '-') {
            negative = true;
            s = s.substring(1);
        }
        while (i < s.length() && (Character.isDigit(s.charAt(i)) || s.charAt(i) == '.')) {
            if (s.charAt(i) == '.' && ++dot_cnt > 1) {
                logger.info("not valid number '" + s.substring(0, i + 1) + "'");
                throw new Exception("not valid number '" + s.substring(0, i + 1) + "'");
            }
            i++;
        }
        if (i == 0) {
            logger.info("can't get valid number in '" + s + "'");
            throw new Exception("can't get valid number in '" + s + "'");
        }
        double dPart = Double.parseDouble(s.substring(0, i));
        if (negative) dPart = -dPart;
        String restPart = s.substring(i);

        return new Result(dPart, restPart);
    }

    private Result processFunction(String func, Result r) {
        logger.info("Start processFunction");
        if (variablesScientificCalculator.containsKey(func)) {
            return new Result(variablesScientificCalculator.get(func).Calculation(r.acc), r.rest);
        } else {
            logger.error("function '" + func + "' is not defined");
        }
        return r;
    }

}
