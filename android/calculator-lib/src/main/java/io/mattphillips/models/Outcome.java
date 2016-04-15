package io.mattphillips.models;

import io.mattphillips.models.microtypes.Payout;
import io.mattphillips.models.microtypes.Profit;

public class Outcome {

    private final Result result;
    private final Payout payout;
    private final Profit profit;

    public Outcome(final Result result, final Payout payout, final Profit profit) {
        this.result = result;
        this.payout = payout;
        this.profit = profit;
    }

    public Result getResult() {
        return result;
    }

    public Payout getPayout() {
        return payout;
    }

    public Profit getProfit() {
        return profit;
    }

    @Override
    public String toString() {
        return "{Result = " +  result + ", Payout = " + payout + ", Profit = " + profit + "}";
    }
}
