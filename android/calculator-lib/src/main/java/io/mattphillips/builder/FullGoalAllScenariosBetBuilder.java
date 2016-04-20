package io.mattphillips.builder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import io.mattphillips.models.Bet;
import io.mattphillips.models.microtypes.Handicap;

public class FullGoalAllScenariosBetBuilder extends AllScenariosBetBuilder {

    FullGoalAllScenariosBetBuilder(Bet bet) {
        super(bet);
    }

    @Override
    public List<Bet> build() {
        Handicap handicap = bet.getHandicap();

        if (isHomeBackedBet(bet) || isAwayLaidBet(bet)) {
            return Arrays.asList(
                    bet.adjustScore(0, getBelowHandicapScore(handicap)),
                    bet.adjustScore(0, getExactHandicapScore(handicap)),
                    bet.adjustScore(0, getAboveHandicapScore(handicap))
            );
        } else {
            return Arrays.asList(
                    bet.adjustScore(getAboveHandicapScore(handicap), 0),
                    bet.adjustScore(getExactHandicapScore(handicap), 0),
                    bet.adjustScore(getBelowHandicapScore(handicap), 0)
            );
        }
    }

    private int getExactHandicapScore(Handicap handicap) {
        return handicap.getValue().abs().intValueExact();
    }

    private int getAboveHandicapScore(Handicap handicap) {
        return handicap.getValue().abs().add(BigDecimal.ONE).intValueExact();
    }

    private int getBelowHandicapScore(Handicap handicap) {
        return handicap.getValue().abs().subtract(BigDecimal.ONE).intValueExact();
    }
}
