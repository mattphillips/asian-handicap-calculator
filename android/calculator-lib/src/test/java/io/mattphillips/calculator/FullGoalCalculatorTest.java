package io.mattphillips.calculator;

import org.junit.Test;

import io.mattphillips.models.Bet;
import io.mattphillips.models.Outcome;
import io.mattphillips.models.Score;
import io.mattphillips.models.Team;
import io.mattphillips.models.microtypes.Handicap;
import io.mattphillips.models.microtypes.Odds;
import io.mattphillips.models.microtypes.Stake;

import static io.mattphillips.models.Team.*;
import static io.mattphillips.models.Result.*;
import static org.assertj.core.api.Assertions.assertThat;

public class FullGoalCalculatorTest {

    @Test
    public void shouldDetermineOutcomeAsHomeWin() throws Exception {
        assertThat(calculateBetWithTeamAndScore("0.00", HOME, new Score(1, 0)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("1.00", HOME, new Score(0, 0)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("-1.00", HOME, new Score(2, 0)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("2.00", HOME, new Score(0, 1)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("-2.00", HOME, new Score(3, 0)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("3.00", HOME, new Score(0, 2)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("-3.00", HOME, new Score(4, 0)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("4.00", HOME, new Score(0, 3)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("-4.00", HOME, new Score(5, 0)).getResult()).isEqualTo(WIN);
    }

    @Test
    public void shouldDetermineOutcomeAsHomeLose() throws Exception {
        assertThat(calculateBetWithTeamAndScore("0.00", HOME, new Score(0, 1)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("1.00", HOME, new Score(0, 2)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("-1.00", HOME, new Score(0, 0)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("2.00", HOME, new Score(0, 3)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("-2.00", HOME, new Score(1, 0)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("3.00", HOME, new Score(0, 4)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("-3.00", HOME, new Score(2, 0)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("4.00", HOME, new Score(0, 5)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("-4.00", HOME, new Score(3, 0)).getResult()).isEqualTo(LOSE);
    }

    @Test
    public void shouldDetermineOutcomeAsDrawWin() throws Exception {
        assertThat(calculateBetWithTeamAndScore("0.00", HOME, new Score(0, 0)).getResult()).isEqualTo(DRAW);
        assertThat(calculateBetWithTeamAndScore("1.00", HOME, new Score(0, 1)).getResult()).isEqualTo(DRAW);
        assertThat(calculateBetWithTeamAndScore("-1.00", HOME, new Score(1, 0)).getResult()).isEqualTo(DRAW);
        assertThat(calculateBetWithTeamAndScore("2.00", HOME, new Score(0, 2)).getResult()).isEqualTo(DRAW);
        assertThat(calculateBetWithTeamAndScore("-2.00", HOME, new Score(2, 0)).getResult()).isEqualTo(DRAW);
        assertThat(calculateBetWithTeamAndScore("3.00", HOME, new Score(0, 3)).getResult()).isEqualTo(DRAW);
        assertThat(calculateBetWithTeamAndScore("-3.00", HOME, new Score(3, 0)).getResult()).isEqualTo(DRAW);
        assertThat(calculateBetWithTeamAndScore("4.00", HOME, new Score(0, 4)).getResult()).isEqualTo(DRAW);
        assertThat(calculateBetWithTeamAndScore("-4.00", HOME, new Score(4, 0)).getResult()).isEqualTo(DRAW);

        assertThat(calculateBetWithTeamAndScore("0.00", AWAY, new Score(0, 0)).getResult()).isEqualTo(DRAW);
        assertThat(calculateBetWithTeamAndScore("1.00", AWAY, new Score(1, 0)).getResult()).isEqualTo(DRAW);
        assertThat(calculateBetWithTeamAndScore("-1.00", AWAY, new Score(0, 1)).getResult()).isEqualTo(DRAW);
        assertThat(calculateBetWithTeamAndScore("2.00", AWAY, new Score(2, 0)).getResult()).isEqualTo(DRAW);
        assertThat(calculateBetWithTeamAndScore("-2.00", AWAY, new Score(0, 2)).getResult()).isEqualTo(DRAW);
        assertThat(calculateBetWithTeamAndScore("3.00", AWAY, new Score(3, 0)).getResult()).isEqualTo(DRAW);
        assertThat(calculateBetWithTeamAndScore("-3.00", AWAY, new Score(0, 3)).getResult()).isEqualTo(DRAW);
        assertThat(calculateBetWithTeamAndScore("4.00", AWAY, new Score(4, 0)).getResult()).isEqualTo(DRAW);
        assertThat(calculateBetWithTeamAndScore("-4.00", AWAY, new Score(0, 4)).getResult()).isEqualTo(DRAW);
    }

    @Test
    public void shouldDetermineOutcomeAsAwayWin() throws Exception {
        assertThat(calculateBetWithTeamAndScore("0.00", AWAY, new Score(0, 1)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("1.00", AWAY, new Score(0, 0)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("-1.00", AWAY, new Score(0, 2)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("2.00", AWAY, new Score(1, 0)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("-2.00", AWAY, new Score(0, 3)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("3.00", AWAY, new Score(2, 0)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("-3.00", AWAY, new Score(0, 4)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("4.00", AWAY, new Score(3, 0)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("-4.00", AWAY, new Score(0, 5)).getResult()).isEqualTo(WIN);
    }

    @Test
    public void shouldDetermineOutcomeAsAwayLose() throws Exception {
        assertThat(calculateBetWithTeamAndScore("0.00", AWAY, new Score(1, 0)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("1.00", AWAY, new Score(2, 0)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("-1.00", AWAY, new Score(0, 0)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("2.00", AWAY, new Score(3, 0)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("-2.00", AWAY, new Score(0, 1)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("3.00", AWAY, new Score(4, 0)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("-3.00", AWAY, new Score(0, 2)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("4.00", AWAY, new Score(5, 0)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("-4.00", AWAY, new Score(0, 3)).getResult()).isEqualTo(LOSE);
    }

    @Test
    public void shouldCalculatePayoutAndProfitCorrectly() throws Exception {
        Outcome winOutcome = calculateBetWithTeamAndScore("0.00", HOME, new Score(1, 0));
        assertThat(winOutcome.getResult()).isEqualTo(WIN);
        assertThat(winOutcome.getPayout().getValue().toString()).isEqualTo("300.00");
        assertThat(winOutcome.getProfit().getValue().toString()).isEqualTo("200.00");

        Outcome drawOutcome = calculateBetWithTeamAndScore("0.00", HOME, new Score(0, 0));
        assertThat(drawOutcome.getResult()).isEqualTo(DRAW);
        assertThat(drawOutcome.getPayout().getValue().toString()).isEqualTo("100.00");
        assertThat(drawOutcome.getProfit().getValue().toString()).isEqualTo("0.00");

        Outcome loseOutcome = calculateBetWithTeamAndScore("0.00", HOME, new Score(0, 1));
        assertThat(loseOutcome.getResult()).isEqualTo(LOSE);
        assertThat(loseOutcome.getPayout().getValue().toString()).isEqualTo("0.00");
        assertThat(loseOutcome.getProfit().getValue().toString()).isEqualTo("-100.00");
    }

    private Outcome calculateBetWithTeamAndScore(String handicap, Team backedTeam, Score score) throws Exception {
        return new FullGoalCalculator(
                new Bet(backedTeam,
                        new Odds("2.00"),
                        new Handicap(handicap),
                        new Stake("100.00"),
                        score)).calculate();
    }
}
