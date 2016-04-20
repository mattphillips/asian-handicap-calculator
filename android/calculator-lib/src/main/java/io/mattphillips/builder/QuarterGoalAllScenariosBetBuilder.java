package io.mattphillips.builder;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

import io.mattphillips.models.Bet;
import io.mattphillips.models.microtypes.Handicap;

public class QuarterGoalAllScenariosBetBuilder extends AllScenariosBetBuilder {

    private static final String QUARTER = "0.25";

    QuarterGoalAllScenariosBetBuilder(Bet bet) {
        super(bet);
    }

    @Override
    public List<Bet> build() {
        Handicap handicap = bet.getHandicap();

        if (isZeroQuarterBet(bet.getHandicap())) {
            return Arrays.asList(
                    bet.adjustScore(0, roundHandicapDown(handicap)),
                    bet.adjustScore(0, roundHandicapUp(handicap)),
                    bet.adjustScore(roundHandicapUp(handicap), 0)
            );

        } else if (isHomeBackedBet(bet) || isAwayLaidBet(bet)) {
            return Arrays.asList(
                    bet.adjustScore(0, roundHandicapDown(handicap)),
                    bet.adjustScore(0, roundHandicapUp(handicap)),
                    bet.adjustScore(0, roundAboveBelow(handicap))
            );
        } else {
            return Arrays.asList(
                    bet.adjustScore(roundAboveBelow(handicap), 0),
                    bet.adjustScore(roundHandicapUp(handicap), 0),
                    bet.adjustScore(roundHandicapDown(handicap), 0)
            );
        }
    }

    private boolean isZeroQuarterBet(Handicap handicap) {
        return handicap.getValue().abs().equals(new BigDecimal(QUARTER));
    }

    private int roundAboveBelow(Handicap handicap) {
        if (handicap.getRemainder().abs().equals(new BigDecimal(QUARTER))) {
            return roundHandicapDown(handicap) - 1;
        } else {
            return roundHandicapUp(handicap) + 1;
        }
    }

    private int roundHandicapUp(Handicap handicap) {
        BigDecimal scaledHandicap = scaleHandicapUp(handicap);
        return scaledHandicap.abs().round(new MathContext(1, RoundingMode.UP)).intValueExact();
    }

    private int roundHandicapDown(Handicap handicap) {
        BigDecimal scaledHandicap = scaleHandicapDown(handicap);
        return scaledHandicap.abs().round(new MathContext(1, RoundingMode.DOWN)).intValueExact();
    }
}
