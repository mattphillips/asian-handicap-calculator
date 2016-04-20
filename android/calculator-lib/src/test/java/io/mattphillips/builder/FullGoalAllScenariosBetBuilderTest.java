package io.mattphillips.builder;

import org.junit.Test;

import java.util.List;

import io.mattphillips.models.Bet;
import io.mattphillips.models.Team;
import io.mattphillips.models.microtypes.Handicap;
import io.mattphillips.models.microtypes.Odds;
import io.mattphillips.models.microtypes.Stake;

import static org.assertj.core.api.Assertions.assertThat;

public class FullGoalAllScenariosBetBuilderTest {

    @Test
    public void shouldBuildAllScenarioBetsCorrectlyForHomeBackedHandicap() {
        // TODO: remove this null should be optional or another constructor
        Bet bet = new Bet(Team.HOME, new Odds("1"), new Handicap("+2.00"), new Stake("10"), null);
        List<Bet> bets = new FullGoalAllScenariosBetBuilder(bet).build();
        assertThat(bets.size()).isEqualTo(3);
        assertThat(bets).contains(bet.adjustScore(0, 1));
        assertThat(bets).contains(bet.adjustScore(0, 2));
        assertThat(bets).contains(bet.adjustScore(0, 3));
    }

    @Test
    public void shouldBuildAllScenarioBetsCorrectlyForHomeLaidHandicap() {
        Bet bet = new Bet(Team.HOME, new Odds("1"), new Handicap("-2.00"), new Stake("10"), null);
        List<Bet> bets = new FullGoalAllScenariosBetBuilder(bet).build();
        assertThat(bets.size()).isEqualTo(3);
        assertThat(bets).contains(bet.adjustScore(1, 0));
        assertThat(bets).contains(bet.adjustScore(2, 0));
        assertThat(bets).contains(bet.adjustScore(3, 0));
    }

    @Test
    public void shouldBuildAllScenarioBetsCorrectlyForAwayBackedHandicap() {
        Bet bet = new Bet(Team.AWAY, new Odds("1"), new Handicap("+2.00"), new Stake("10"), null);
        List<Bet> bets = new FullGoalAllScenariosBetBuilder(bet).build();
        assertThat(bets.size()).isEqualTo(3);
        assertThat(bets).contains(bet.adjustScore(1, 0));
        assertThat(bets).contains(bet.adjustScore(2, 0));
        assertThat(bets).contains(bet.adjustScore(3, 0));
    }

    @Test
    public void shouldBuildAllScenarioBetsCorrectlyForAwayLaidHandicap() {
        Bet bet = new Bet(Team.AWAY, new Odds("1"), new Handicap("-2.00"), new Stake("10"), null);
        List<Bet> bets = new FullGoalAllScenariosBetBuilder(bet).build();
        assertThat(bets.size()).isEqualTo(3);
        assertThat(bets).contains(bet.adjustScore(0, 1));
        assertThat(bets).contains(bet.adjustScore(0, 2));
        assertThat(bets).contains(bet.adjustScore(0, 3));
    }
}
