package io.mattphillips.builder;

import org.junit.Test;

import io.mattphillips.models.Bet;
import io.mattphillips.models.Score;
import io.mattphillips.models.Team;
import io.mattphillips.models.microtypes.Handicap;
import io.mattphillips.models.microtypes.Odds;
import io.mattphillips.models.microtypes.Stake;

import static org.assertj.core.api.Assertions.assertThat;

public class AllScenariosBetBuilderTest {

    @Test
    public void shouldCreateFullGoalAllScenariosBetBuilderFromBet() throws Exception {
        AllScenariosBetBuilder builder =
                AllScenariosBetBuilder.determineBuilderType(getBetWithHandicap("1.00"));
        assertThat(builder).isInstanceOf(FullGoalAllScenariosBetBuilder.class);

        builder = AllScenariosBetBuilder.determineBuilderType(getBetWithHandicap("-1.00"));
        assertThat(builder).isInstanceOf(FullGoalAllScenariosBetBuilder.class);

        builder = AllScenariosBetBuilder.determineBuilderType(getBetWithHandicap("0.00"));
        assertThat(builder).isInstanceOf(FullGoalAllScenariosBetBuilder.class);
    }

    @Test
    public void shouldCreateHalfGoalCalculatorFromBet() throws Exception {
        AllScenariosBetBuilder builder =
                AllScenariosBetBuilder.determineBuilderType(getBetWithHandicap("1.50"));
        assertThat(builder).isInstanceOf(HalfGoalAllScenariosBetBuilder.class);

        builder = AllScenariosBetBuilder.determineBuilderType(getBetWithHandicap("-1.50"));
        assertThat(builder).isInstanceOf(HalfGoalAllScenariosBetBuilder.class);
    }

    @Test
    public void shouldCreateQuarterGoalCalculatorFromBet() throws Exception {
        AllScenariosBetBuilder builder =
                AllScenariosBetBuilder.determineBuilderType(getBetWithHandicap("1.25"));
        assertThat(builder).isInstanceOf(QuarterGoalAllScenariosBetBuilder.class);

        builder = AllScenariosBetBuilder.determineBuilderType(getBetWithHandicap("1.75"));
        assertThat(builder).isInstanceOf(QuarterGoalAllScenariosBetBuilder.class);

        builder = AllScenariosBetBuilder.determineBuilderType(getBetWithHandicap("-1.25"));
        assertThat(builder).isInstanceOf(QuarterGoalAllScenariosBetBuilder.class);

        builder = AllScenariosBetBuilder.determineBuilderType(getBetWithHandicap("-1.75"));
        assertThat(builder).isInstanceOf(QuarterGoalAllScenariosBetBuilder.class);
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
