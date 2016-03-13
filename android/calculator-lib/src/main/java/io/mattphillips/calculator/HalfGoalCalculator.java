package io.mattphillips.calculator;

import java.math.BigDecimal;

import io.mattphillips.models.Bet;
import io.mattphillips.models.Outcome;
import io.mattphillips.models.Result;
import io.mattphillips.models.Team;

public class HalfGoalCalculator extends AsianHandicapCalculator {

    HalfGoalCalculator(final Bet bet) {
        super(bet);
    }

    public Outcome calculate() {
        Result result = determineResult(goalSupremacy, bet.getHandicap());
        BigDecimal payout = determinePayout(result);
        BigDecimal profit = payout.subtract(bet.getStake()).setScale(2, 0);;

        return new Outcome(result, payout, profit);
    }

    private Result determineResult(BigDecimal goalSupremacy, BigDecimal handicap) {
        if (goalSupremacy.add(handicap).compareTo(BigDecimal.ZERO) > 0)
            return Result.WIN;
        else
            return Result.LOSE;
    }

    private BigDecimal determinePayout(Result result) {
        if (result == Result.WIN)
            return bet.getStake().multiply(bet.getOdds()).setScale(2, 0);
        else
            return BigDecimal.ZERO.setScale(2, 0);
    }
}
