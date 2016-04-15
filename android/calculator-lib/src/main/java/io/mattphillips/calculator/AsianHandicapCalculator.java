package io.mattphillips.calculator;

import java.math.BigDecimal;

import io.mattphillips.models.Bet;
import io.mattphillips.models.Outcome;
import io.mattphillips.models.Result;
import io.mattphillips.models.Team;
import io.mattphillips.models.microtypes.Handicap;

public abstract class AsianHandicapCalculator {

    private static final String FULL_GOAL_REMAINDER = "0.00";
    private static final String HALF_GOAL_REMAINDER = "0.50";
    private static final String QUARTER_GOAL_LOWER_REMAINDER = "0.25";
    private static final String QUARTER_GOAL_UPPER_REMAINDER = "0.75";

    private static final String UNRECOGNISED_BET_TYPE_EXCEPTION = "Unrecognised bet type of: %s";

    protected final Bet bet;
    protected final BigDecimal goalSupremacy;

    public AsianHandicapCalculator(final Bet bet) {
        this.bet = bet;
        goalSupremacy = bet.getTeam() == Team.HOME
                ? calculateGoalSupremacy(bet.getScoreline().homeScore(), bet.getScoreline().awayScore())
                : calculateGoalSupremacy(bet.getScoreline().awayScore(), bet.getScoreline().homeScore());
    }

    // The three types are:
    // Full Goal +-(1.00, 2.00, 3.00, 4.00)
    // Half Goal +-(1.50, 2.50, 3.50, 4.50)
    // Quarter Goal +-(1.25, 1.75, 2.25, 2.75, 3.25, 3.75, 4.25, 4.75)
    public static AsianHandicapCalculator determineBetType(Bet bet) throws Exception {

        String rem = bet.getHandicap().getRemainder().abs().toString();

        if (rem.equals(FULL_GOAL_REMAINDER))
            return new FullGoalCalculator(bet);

        else if (rem.equals(HALF_GOAL_REMAINDER))
            return new HalfGoalCalculator(bet);

        else if (rem.equals(QUARTER_GOAL_LOWER_REMAINDER) || rem.equals((QUARTER_GOAL_UPPER_REMAINDER)))
            return new QuarterGoalCalculator(bet);

        else
            throw new Exception(String.format(UNRECOGNISED_BET_TYPE_EXCEPTION, rem));
    }

    public abstract Outcome calculate();

    protected BigDecimal calculateGoalSupremacy(int backedTeam, int opposingTeam) {
        return new BigDecimal(backedTeam).subtract(new BigDecimal(opposingTeam));
    }

    protected Result determineResult(BigDecimal goalSupremacy, Handicap handicap) {
        if (goalSupremacy.add(handicap.getValue()).compareTo(BigDecimal.ZERO) > 0)
            return Result.WIN;
        else if (goalSupremacy.add(handicap.getValue()).compareTo(BigDecimal.ZERO) < 0)
            return Result.LOSE;
        else
            return Result.DRAW;
    }
}
