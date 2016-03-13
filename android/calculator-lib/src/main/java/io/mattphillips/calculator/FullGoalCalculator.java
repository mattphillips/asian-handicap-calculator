package io.mattphillips.calculator;

import io.mattphillips.models.Bet;
import io.mattphillips.models.Outcome;
import io.mattphillips.models.Result;
import io.mattphillips.models.microtypes.Payout;
import io.mattphillips.models.microtypes.Profit;

public class FullGoalCalculator extends AsianHandicapCalculator {

    public FullGoalCalculator(final Bet bet) {
        super(bet);
    }

    public Outcome calculate() {
        Result result = determineResult(goalSupremacy, bet.getHandicap());
        Payout payout = Payout.determinePayout(result, bet.getStake(), bet.getOdds());
        Profit profit = Profit.calculateProfit(payout, bet.getStake());

        return new Outcome(result, payout, profit);
    }
}