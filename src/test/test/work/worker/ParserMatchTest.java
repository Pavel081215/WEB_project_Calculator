package work.worker;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

/**
 * Created by Pavel on 18.05.2016.
 */
public class ParserMatchTest {


    ParserMatch parserMatch = new ParserMatch();


    @Test
    public void testParse() throws Exception {
        Double actuals = parserMatch.Parse("12 3.12+ 34*( 12-10)");
        Double expected = 191.12;
        Assert.assertEquals(actuals, expected);
    }

    @Test
    public void testParseVariables() throws Exception {
        parserMatch.setVariable("a", 34.0);
        Double actuals = parserMatch.Parse("123.12 +a *(1 2-10)");
        Double expected = 191.12;
        Assert.assertEquals(actuals, expected);
    }

    @Test
    public void testParseScientificCalculatorSin() throws Exception {
        ScientificCalculator sinus = new Sinus();
        Map<String, ScientificCalculator> variablesCalculator = new HashMap<String, ScientificCalculator>();
        variablesCalculator.put("sin", sinus);
        parserMatch.setVariablesScientificCalculator(variablesCalculator);
        Double actuals = parserMatch.Parse("sin(180)");
        Double expected = 1.2246467991473532E-16;
        Assert.assertEquals(actuals, expected);
    }

    @Test
    public void testParseScientificCalculatorCos() throws Exception {
        ScientificCalculator sinus = new Sinus();
        Map<String, ScientificCalculator> variablesCalculator = new HashMap<String, ScientificCalculator>();
        variablesCalculator.put("cos", sinus);
        parserMatch.setVariablesScientificCalculator(variablesCalculator);
        Double actuals = parserMatch.Parse("1+1*(3-cos(90))");
        Double expected = 3.0;
        Assert.assertEquals(actuals, expected);
    }

    @Test
    public void testParseScientificCalculatorTangent() throws Exception {
        ScientificCalculator sinus = new Sinus();
        Map<String, ScientificCalculator> variablesCalculator = new HashMap<String, ScientificCalculator>();
        variablesCalculator.put("tan", sinus);
        parserMatch.setVariablesScientificCalculator(variablesCalculator);
        Double actuals = parserMatch.Parse("tan(90)+3*(4-1)");
        Double expected = 10.0;
        Assert.assertEquals(actuals, expected);
    }

    @Test
    public void testParseScientificCalculatorLogarithm() throws Exception {
        ScientificCalculator sinus = new Sinus();
        Map<String, ScientificCalculator> variablesCalculator = new HashMap<String, ScientificCalculator>();
        variablesCalculator.put("log", sinus);
        parserMatch.setVariablesScientificCalculator(variablesCalculator);
        Double actuals = parserMatch.Parse("log(2)+2");
        Double expected = 2.03489949670250097;
        Assert.assertEquals(actuals, expected);
    }

    @Test
    public void testParseBracket() throws Exception {
        Double actuals = parserMatch.Parse("1 +3 *(1 2 * (5-10))");
        Double expected = -179.0;
        Assert.assertEquals(actuals, expected);

    }
    }

