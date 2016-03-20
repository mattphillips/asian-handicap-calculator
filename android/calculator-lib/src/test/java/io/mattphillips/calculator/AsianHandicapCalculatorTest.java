package io.mattphillips.calculator;

import org.junit.Test;

import io.mattphillips.models.Bet;
import io.mattphillips.models.Score;
import io.mattphillips.models.Team;
import io.mattphillips.models.microtypes.Handicap;
import io.mattphillips.models.microtypes.Odds;
import io.mattphillips.models.microtypes.Stake;

import static org.assertj.core.api.Assertions.*;

public class AsianHandicapCalculatorTest {

    @Test
    public void shouldCreateFullGoalAsianHandicapCalculatorFromBet() throws Exception {
        AsianHandicapCalculator calc =
                AsianHandicapCalculator.determineBetType(getBetWithHandicap("1.00"));
        assertThat(calc).isInstanceOf(FullGoalCalculator.class);

        calc = AsianHandicapCalculator.determineBetType(getBetWithHandicap("-1.00"));
        assertThat(calc).isInstanceOf(FullGoalCalculator.class);

        calc = AsianHandicapCalculator.determineBetType(getBetWithHandicap("0.00"));
        assertThat(calc).isInstanceOf(FullGoalCalculator.class);
    }

    @Test
    public void shouldCreateHalfGoalCalculatorFromBet() throws Exception {
        AsianHandicapCalculator calc =
                AsianHandicapCalculator.determineBetType(getBetWithHandicap("1.50"));
        assertThat(calc).isInstanceOf(HalfGoalCalculator.class);

        calc = AsianHandicapCalculator.determineBetType(getBetWithHandicap("-1.50"));
        assertThat(calc).isInstanceOf(HalfGoalCalculator.class);
    }

    @Test
    public void shouldCreateQuarterGoalCalculatorFromBet() throws Exception {
        AsianHandicapCalculator calc =
                AsianHandicapCalculator.determineBetType(getBetWithHandicap("1.25"));
        assertThat(calc).isInstanceOf(QuarterGoalCalculator.class);

        calc = AsianHandicapCalculator.determineBetType(getBetWithHandicap("1.75"));
        assertThat(calc).isInstanceOf(QuarterGoalCalculator.class);

        calc = AsianHandicapCalculator.determineBetType(getBetWithHandicap("-1.25"));
        assertThat(calc).isInstanceOf(QuarterGoalCalculator.class);

        calc = AsianHandicapCalculator.determineBetType(getBetWithHandicap("-1.75"));
        assertThat(calc).isInstanceOf(QuarterGoalCalculator.class);
    }

    @Test (expected = Exception.class)
    public void shouldThrowExceptionWhenHandicapTypeIsNotRecognised() throws Exception {
        AsianHandicapCalculator.determineBetType(getBetWithHandicap("1.33"));
    }

    private Bet getBetWithHandicap(String handicap) {
        return new Bet(
                Team.HOME,
                new Odds("1.8"),
                new Handicap(handicap),
                new Stake("100.00"),
                new Score(1,1));
    }
}
