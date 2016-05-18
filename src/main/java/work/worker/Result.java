package work.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Result {
    private static final Logger logger = LoggerFactory.getLogger(ParserMatch.class);
    public double acc;
    public String rest;

    public Result(double v, String r) {
        this.acc = v;
        this.rest = r;
        logger.info("New instance Result " + acc + rest);
    }

}
