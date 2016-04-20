package io.mattphillips.calculator;

import org.junit.Test;

import java.util.List;

import io.mattphillips.models.Bet;
import io.mattphillips.models.Outcome;
import io.mattphillips.models.Score;
import io.mattphillips.models.Team;
import io.mattphillips.models.microtypes.Handicap;
import io.mattphillips.models.microtypes.Odds;
import io.mattphillips.models.microtypes.Stake;

import static io.mattphillips.models.Result.LOSE;
import static io.mattphillips.models.Result.WIN;
import static io.mattphillips.models.Team.AWAY;
import static io.mattphillips.models.Team.HOME;
import static org.assertj.core.api.Assertions.assertThat;

public class HalfGoalCalculatorTest {

    @Test
    public void shouldBuildAllScenarioBetsCorrectlyForHomeBackedHandicap() {
        // TODO: remove this null should be optional or another constructor
        Bet bet = new Bet(Team.HOME, new Odds("1"), new Handicap("+2.50"), new Stake("10"), null);
        List<Bet> bets = HalfGoalCalculator.buildHalfGoalAllScenariosBets(bet);
        assertThat(bets.size()).isEqualTo(2);
        assertThat(bets).contains(bet.adjustScore(0, 2));
        assertThat(bets).contains(bet.adjustScore(0, 3));
    }

    @Test
    public void shouldBuildAllScenarioBetsCorrectlyForHomeLaidHandicap() {
        Bet bet = new Bet(Team.HOME, new Odds("1"), new Handicap("-2.50"), new Stake("10"), null);
        List<Bet> bets = HalfGoalCalculator.buildHalfGoalAllScenariosBets(bet);
        assertThat(bets.size()).isEqualTo(2);
        assertThat(bets).contains(bet.adjustScore(2, 0));
        assertThat(bets).contains(bet.adjustScore(3, 0));
    }

    @Test
    public void shouldBuildAllScenarioBetsCorrectlyForAwayBackedHandicap() {
        Bet bet = new Bet(Team.AWAY, new Odds("1"), new Handicap("+2.50"), new Stake("10"), null);
        List<Bet> bets = HalfGoalCalculator.buildHalfGoalAllScenariosBets(bet);
        assertThat(bets.size()).isEqualTo(2);
        assertThat(bets).contains(bet.adjustScore(2, 0));
        assertThat(bets).contains(bet.adjustScore(3, 0));
    }

    @Test
    public void shouldBuildAllScenarioBetsCorrectlyForAwayLaidHandicap() {
        Bet bet = new Bet(Team.AWAY, new Odds("1"), new Handicap("-2.50"), new Stake("10"), null);
        List<Bet> bets = HalfGoalCalculator.buildHalfGoalAllScenariosBets(bet);
        assertThat(bets.size()).isEqualTo(2);
        assertThat(bets).contains(bet.adjustScore(0, 2));
        assertThat(bets).contains(bet.adjustScore(0, 3));
    }

    @Test
    public void shouldDetermineOutcomeAsHomeWin() throws Exception {
        assertThat(calculateBetWithTeamAndScore("0.50", HOME, new Score(0, 0)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("-0.50", HOME, new Score(1, 0)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("1.50", HOME, new Score(0, 1)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("-1.50", HOME, new Score(2, 0)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("2.50", HOME, new Score(0, 2)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("-2.50", HOME, new Score(3, 0)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("3.50", HOME, new Score(0, 3)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("-3.50", HOME, new Score(4, 0)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("4.50", HOME, new Score(0, 4)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("-4.50", HOME, new Score(5, 0)).getResult()).isEqualTo(WIN);
    }

    @Test
    public void shouldDetermineOutcomeAsHomeLose() throws Exception {
        assertThat(calculateBetWithTeamAndScore("-0.50", HOME, new Score(0, 0)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("0.50", HOME, new Score(0, 1)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("1.50", HOME, new Score(0, 2)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("-1.50", HOME, new Score(1, 0)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("2.50", HOME, new Score(0, 3)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("-2.50", HOME, new Score(2, 0)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("3.50", HOME, new Score(0, 4)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("-3.50", HOME, new Score(3, 0)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("4.50", HOME, new Score(0, 5)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("-4.50", HOME, new Score(4, 0)).getResult()).isEqualTo(LOSE);
    }

    @Test
    public void shouldDetermineOutcomeAsAwayWin() throws Exception {
        assertThat(calculateBetWithTeamAndScore("0.50", AWAY, new Score(0, 0)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("-0.50", AWAY, new Score(0, 1)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("1.50", AWAY, new Score(1, 0)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("-1.50", AWAY, new Score(0, 2)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("2.50", AWAY, new Score(2, 0)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("-2.50", AWAY, new Score(0, 3)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("3.50", AWAY, new Score(3, 0)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("-3.50", AWAY, new Score(0, 4)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("4.50", AWAY, new Score(4, 0)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("-4.50", AWAY, new Score(0, 5)).getResult()).isEqualTo(WIN);
    }

    @Test
    public void shouldDetermineOutcomeAsAwayLose() throws Exception {
        assertThat(calculateBetWithTeamAndScore("-0.50", AWAY, new Score(0, 0)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("0.50", AWAY, new Score(1, 0)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("1.50", AWAY, new Score(2, 0)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("-1.50", AWAY, new Score(0, 1)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("2.50", AWAY, new Score(3, 0)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("-2.50", AWAY, new Score(0, 2)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("3.50", AWAY, new Score(4, 0)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("-3.50", AWAY, new Score(0, 3)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("4.50", AWAY, new Score(5, 0)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("-4.50", AWAY, new Score(0, 4)).getResult()).isEqualTo(LOSE);
    }

    @Test
    public void shouldCalculatePayoutAndProfitCorrectly() throws Exception {
        Outcome winOutcome = calculateBetWithTeamAndScore("0.50", HOME, new Score(0, 0));
        assertThat(winOutcome.getResult()).isEqualTo(WIN);
        assertThat(winOutcome.getPayout().getValue().toString()).isEqualTo("200.00");
        assertThat(winOutcome.getProfit().getValue().toString()).isEqualTo("100.00");

        Outcome loseOutcome = calculateBetWithTeamAndScore("0.50", HOME, new Score(0, 1));
        assertThat(loseOutcome.getResult()).isEqualTo(LOSE);
        assertThat(loseOutcome.getPayout().getValue().toString()).isEqualTo("0.00");
        assertThat(loseOutcome.getProfit().getValue().toString()).isEqualTo("-100.00");
    }

    private Outcome calculateBetWithTeamAndScore(String handicap, Team backedTeam, Score score) throws Exception {
        return new HalfGoalCalculator(
                new Bet(backedTeam,
                        new Odds("2.00"),
                        new Handicap(handicap),
                        new Stake("100.00"),
                        score)).calculate();
    }
}
