package io.mattphillips.calculator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.mattphillips.models.Bet;
import io.mattphillips.models.Outcome;
import io.mattphillips.models.Result;
import io.mattphillips.models.microtypes.Handicap;

public abstract class AsianHandicapCalculator {

    private static final String UNRECOGNISED_BET_TYPE_EXCEPTION = "Unrecognised bet type of: %s";

    protected final Bet bet;
    protected final BigDecimal goalSupremacy;

    public AsianHandicapCalculator(final Bet bet) {
        this.bet = bet;
        goalSupremacy = bet.getTeam().isHome()
                ? calculateGoalSupremacy(bet.getScoreline().homeScore(), bet.getScoreline().awayScore())
                : calculateGoalSupremacy(bet.getScoreline().awayScore(), bet.getScoreline().homeScore());
    }

    // The three types are:
    // Full Goal +-(1.00, 2.00, 3.00, 4.00)
    // Half Goal +-(1.50, 2.50, 3.50, 4.50)
    // Quarter Goal +-(1.25, 1.75, 2.25, 2.75, 3.25, 3.75, 4.25, 4.75)
    public static AsianHandicapCalculator determineFinalScoreBetType(Bet bet) throws Exception {
        if (bet.isFullGoalBet())
            return new FullGoalCalculator(bet);

        else if (bet.isHalfGoalBet())
            return new HalfGoalCalculator(bet);

        else if (bet.isQuarterGoalBet())
            return new QuarterGoalCalculator(bet);

        else
            throw new Exception(String.format(
                    UNRECOGNISED_BET_TYPE_EXCEPTION,
                    bet.getHandicap().getRemainder().abs().toString())
            );
    }

    public static List<Outcome> calculateAllScenarios(Bet bet) {

        List<Bet> allScenarios = buildAllScenarioBets(bet);
        List<Outcome> outcomes = new ArrayList<>();

        for (Bet b : allScenarios) {
            try {
                Outcome outcome = AsianHandicapCalculator.determineFinalScoreBetType(b).calculate();
                outcomes.add(outcome);
            } catch (Exception e) {}
        }
        return outcomes;
    }

    private static List<Bet> buildAllScenarioBets(Bet bet) {
        if (bet.isFullGoalBet())
            return FullGoalCalculator.buildFullGoalAllScenariosBets(bet);
        else if (bet.isHalfGoalBet())
            return HalfGoalCalculator.buildHalfGoalAllScenariosBets(bet);
        else
            return QuarterGoalCalculator.buildQuarterGoalAllScenariosBets(bet);
    }

    protected static boolean isHomeBackedBet(Bet bet) {
        return bet.getTeam().isHome() && bet.getHandicap().isBackedHandicap();
    }

    protected static boolean isAwayLaidBet(Bet bet) {
        return bet.getTeam().isAway() && bet.getHandicap().isLaidHandicap();
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
