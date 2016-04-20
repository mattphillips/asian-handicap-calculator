package io.mattphillips.builder;

import java.util.List;

import io.mattphillips.models.Bet;

public abstract class AllScenariosBetBuilder {

    protected final Bet bet;

    public AllScenariosBetBuilder(Bet bet) {
        this.bet = bet;
    }

    public static AllScenariosBetBuilder determineBuilderType(Bet bet) {
        if (bet.isFullGoalBet())
            return new FullGoalAllScenariosBetBuilder(bet);
        else if (bet.isHalfGoalBet())
            return new HalfGoalAllScenariosBetBuilder(bet);
        else
            return new QuarterGoalAllScenariosBetBuilder(bet);
    }

    public abstract List<Bet> build();

    protected static boolean isHomeBackedBet(Bet bet) {
        return bet.getTeam().isHome() && bet.getHandicap().isBackedHandicap();
    }

    protected static boolean isAwayLaidBet(Bet bet) {
        return bet.getTeam().isAway() && bet.getHandicap().isLaidHandicap();
    }
}
