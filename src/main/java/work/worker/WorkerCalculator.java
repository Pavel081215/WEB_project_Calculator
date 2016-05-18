package work.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
public class WorkerCalculator {


    private String infoin = "0";
    private String infoout;
    private ParserMatch parserMatch = null;
    private static final Logger logger = LoggerFactory.getLogger(WorkerCalculator.class);

    public WorkerCalculator() throws Exception {

        Map<String, ScientificCalculator> variablesCalculator = new HashMap<String, ScientificCalculator>();
        variablesCalculator.put("cos", new Cosinus());
        variablesCalculator.put("log", new Logarithm());
        variablesCalculator.put("tan", new Tangent());
        variablesCalculator.put("sin", new Sinus());
        this.parserMatch = new ParserMatch();
        this.parserMatch.setVariablesScientificCalculator(variablesCalculator);

    }

    public void setInfoin(String infoin) {
        this.infoin = infoin;
    }

    public String getInfoout() throws Exception {
        logger.info("start getInfoout");
        String temp = Double.toString(parserMatch.Parse(infoin));
        return temp;
    }


}
