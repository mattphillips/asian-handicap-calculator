package io.mattphillips.calculator;

import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

import io.mattphillips.models.Bet;
import io.mattphillips.models.Outcome;
import io.mattphillips.models.Result;
import io.mattphillips.models.microtypes.Handicap;
import io.mattphillips.models.microtypes.Payout;
import io.mattphillips.models.microtypes.Profit;

public class HalfGoalCalculator extends AsianHandicapCalculator {

    HalfGoalCalculator(final Bet bet) {
        super(bet);
    }

    public Outcome calculate() {
        Result result = determineResult(goalSupremacy, bet.getHandicap());
        Payout payout = Payout.determinePayout(result, bet.getStake(), bet.getOdds());
        Profit profit = Profit.calculateProfit(payout, bet.getStake());

        return new Outcome(result, payout, profit);
    }

    public static List<Bet> buildHalfGoalAllScenariosBets(Bet bet) {
        Handicap handicap = bet.getHandicap();

        if (isHomeBackedBet(bet) || isAwayLaidBet(bet)) {
            return Arrays.asList(
                bet.adjustScore(0, getBelowHandicapScore(handicap)),
                bet.adjustScore(0, getAboveHandicapScore(handicap))
            );
        } else {
            return Arrays.asList(
                bet.adjustScore(getAboveHandicapScore(handicap), 0),
                bet.adjustScore(getBelowHandicapScore(handicap), 0)
            );
        }
    }

    private static int getAboveHandicapScore(Handicap handicap) {
        return handicap.getValue().abs().round(new MathContext(1, RoundingMode.UP)).intValueExact();
    }

    private static int getBelowHandicapScore(Handicap handicap) {
        return handicap.getValue().abs().round(new MathContext(1, RoundingMode.DOWN)).intValueExact();
    }
}
