package io.mattphillips.models;

import java.math.BigDecimal;

public class Outcome {

    private final Result result;
    private final BigDecimal payout;
    private final BigDecimal profit;

    public Outcome(final Result result, final BigDecimal payout, final BigDecimal profit) {
        this.result = result;
        this.payout = payout;
        this.profit = profit;
    }

    public Result getResult() {
        return result;
    }

    public BigDecimal getPayout() {
        return payout;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    @Override
    public String toString() {
        return "{Result = " +  result + ", Payout = " + payout + ", Profit = " + profit + "}";
    }
}
