package io.mattphillips.builder;

import android.util.Log;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

import io.mattphillips.models.Bet;
import io.mattphillips.models.microtypes.Handicap;

public class HalfGoalAllScenariosBetBuilder extends AllScenariosBetBuilder {

    HalfGoalAllScenariosBetBuilder(Bet bet) {
        super(bet);
    }

    @Override
    public List<Bet> build() {
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

    private int getAboveHandicapScore(Handicap handicap) {
        BigDecimal scaledHandicap = scaleHandicapUp(handicap);
        return scaledHandicap.abs().round(new MathContext(1, RoundingMode.CEILING)).intValueExact();
    }

    private int getBelowHandicapScore(Handicap handicap) {
        BigDecimal scaledHandicap = scaleHandicapDown(handicap);
        return scaledHandicap.abs().round(new MathContext(1, RoundingMode.DOWN)).intValueExact();
    }
}
