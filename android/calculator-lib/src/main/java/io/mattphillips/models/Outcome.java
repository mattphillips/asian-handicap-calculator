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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Outcome outcome = (Outcome) o;

        if (result != outcome.result) return false;
        if (payout != null ? !payout.equals(outcome.payout) : outcome.payout != null) return false;
        return !(profit != null ? !profit.equals(outcome.profit) : outcome.profit != null);

    }

    @Override
    public int hashCode() {
        int result1 = result != null ? result.hashCode() : 0;
        result1 = 31 * result1 + (payout != null ? payout.hashCode() : 0);
        result1 = 31 * result1 + (profit != null ? profit.hashCode() : 0);
        return result1;
    }

    @Override
    public String toString() {
        return "{Result = " +  result + ", Payout = " + payout + ", Profit = " + profit + "}";
    }
}
