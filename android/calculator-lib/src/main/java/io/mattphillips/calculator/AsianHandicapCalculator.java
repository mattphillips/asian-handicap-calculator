package io.mattphillips.calculator;

import java.math.BigDecimal;

import io.mattphillips.models.Bet;
import io.mattphillips.models.Outcome;

public abstract class AsianHandicapCalculator {

    // The three types are:
    // Full Goal +-(1.00, 2.00, 3.00, 4.00)
    // Half Goal +-(1.50, 2.50, 3.50, 4.50)
    // Quarter Goal +-(1.25, 1.75, 2.25, 2.75, 3.25, 3.75, 4.25, 4.75)
    public static AsianHandicapCalculator determineBetType(Bet bet) throws Exception {

        String rem = bet.getHandicap().remainder(BigDecimal.ONE).toString();

        if (rem.equals("0.00"))
            return new FullGoalCalculator(bet);

        else if (rem.equals("0.50"))
            return new HalfGoalCalculator(bet);

        else if (rem.equals("0.25") || rem.equals(("0.75")))
            return new QuarterGoalCalculator();

        else
            throw new Exception("Unrecognised bet type of: " + rem);
    }

    public abstract Outcome calculate();
}
