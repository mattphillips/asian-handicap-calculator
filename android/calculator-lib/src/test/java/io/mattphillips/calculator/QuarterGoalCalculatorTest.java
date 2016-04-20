package io.mattphillips.calculator;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import io.mattphillips.models.Bet;
import io.mattphillips.models.Outcome;
import io.mattphillips.models.Score;
import io.mattphillips.models.Team;
import io.mattphillips.models.microtypes.Handicap;
import io.mattphillips.models.microtypes.Odds;
import io.mattphillips.models.microtypes.Stake;

import static io.mattphillips.models.Result.*;
import static io.mattphillips.models.Team.*;
import static org.assertj.core.api.Assertions.assertThat;

public class QuarterGoalCalculatorTest {

    @Test
    public void shouldDetermineOutcomeAsWinWhenBothFullGoalAndHalfGoalBetsAreWinners() throws Exception {
        Outcome outcome = calculateBetWithTeamAndScore("0.25", HOME, new Score(1, 0));
        assertThat(outcome.getResult()).isEqualTo(WIN);
        assertThat(outcome.getPayout().getValue().toString()).isEqualTo("300.00");
        assertThat(outcome.getProfit().getValue().toString()).isEqualTo("200.00");

        assertThat(calculateBetWithTeamAndScore("0.75", HOME, new Score(0, 0)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("-0.25", HOME, new Score(1, 0)).getResult()).isEqualTo(WIN);
        assertThat(calculateBetWithTeamAndScore("-0.75", HOME, new Score(2, 0)).getResult()).isEqualTo(WIN);
    }

    @Test
    public void shouldDetermineOutcomeAsLoseWhenFullGoalBetDrawsAndHalfGoalBetLoses() throws Exception {
        Outcome outcome = calculateBetWithTeamAndScore("-0.25", HOME, new Score(0, 0));
        assertThat(outcome.getResult()).isEqualTo(LOSE);
        assertThat(outcome.getPayout().getValue().toString()).isEqualTo("50.00");
        assertThat(outcome.getProfit().getValue().toString()).isEqualTo("-50.00");

        assertThat(calculateBetWithTeamAndScore("0.75", HOME, new Score(0, 1)).getResult()).isEqualTo(LOSE);
    }

    @Test
    public void shouldDetermineOutcomeAsLoseWhenFullGoalBetLosesAndHalfGoalBetLoses() throws Exception {
        Outcome outcome = calculateBetWithTeamAndScore("0.25", HOME, new Score(0, 5));
        assertThat(outcome.getResult()).isEqualTo(LOSE);
        assertThat(outcome.getPayout().getValue().toString()).isEqualTo("0.00");
        assertThat(outcome.getProfit().getValue().toString()).isEqualTo("-100.00");

        assertThat(calculateBetWithTeamAndScore("0.75", HOME, new Score(0, 5)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("-0.25", HOME, new Score(0, 5)).getResult()).isEqualTo(LOSE);
        assertThat(calculateBetWithTeamAndScore("-0.75", HOME, new Score(0, 5)).getResult()).isEqualTo(LOSE);
    }

    @Test
    public void shouldDetermineOutcomeAsWinWhenFullGoalBetDrawsAndHalfGoalBetWins() throws Exception {
        Outcome outcome = calculateBetWithTeamAndScore("0.25", HOME, new Score(0, 0));
        assertThat(outcome.getResult()).isEqualTo(WIN);
        assertThat(outcome.getPayout().getValue().toString()).isEqualTo("200.00");
        assertThat(outcome.getProfit().getValue().toString()).isEqualTo("100.00");

        assertThat(calculateBetWithTeamAndScore("-0.75", HOME, new Score(1, 0)).getResult()).isEqualTo(WIN);
    }

    @Test
    public void shouldCorrectlyRoundFullGoalHandicap() throws Exception {
        Bet bet = getBet("-0.75");
        QuarterGoalCalculator calculator = new QuarterGoalCalculator(bet);
        assertThat(calculator.roundFullGoalHandicap(bet.getHandicap().getRemainder()).getValue().toString()).isEqualTo("-1.00");

        bet = getBet("-0.25");
        calculator = new QuarterGoalCalculator(bet);
        assertThat(calculator.roundFullGoalHandicap(bet.getHandicap().getRemainder()).getValue().toString()).isEqualTo("0.00");

        bet = getBet("0.75");
        calculator = new QuarterGoalCalculator(bet);
        assertThat(calculator.roundFullGoalHandicap(bet.getHandicap().getRemainder()).getValue().toString()).isEqualTo("1.00");

        bet = getBet("0.25");
        calculator = new QuarterGoalCalculator(bet);
        assertThat(calculator.roundFullGoalHandicap(bet.getHandicap().getRemainder()).getValue().toString()).isEqualTo("0.00");
    }

    @Test
    public void shouldCorrectlyRoundHalfGoalHandicap() throws Exception {
        Bet bet = getBet("-0.75");
        QuarterGoalCalculator calculator = new QuarterGoalCalculator(bet);
        assertThat(calculator.roundHalfGoalHandicap(bet.getHandicap().getRemainder()).getValue().toString()).isEqualTo("-0.50");

        bet = getBet("-0.25");
        calculator = new QuarterGoalCalculator(bet);
        assertThat(calculator.roundHalfGoalHandicap(bet.getHandicap().getRemainder()).getValue().toString()).isEqualTo("-0.50");

        bet = getBet("0.75");
        calculator = new QuarterGoalCalculator(bet);
        assertThat(calculator.roundHalfGoalHandicap(bet.getHandicap().getRemainder()).getValue().toString()).isEqualTo("0.50");

        bet = getBet("0.25");
        calculator = new QuarterGoalCalculator(bet);
        assertThat(calculator.roundHalfGoalHandicap(bet.getHandicap().getRemainder()).getValue().toString()).isEqualTo("0.50");
    }

    private Outcome calculateBetWithTeamAndScore(String handicap, Team backedTeam, Score score) throws Exception {
        return new QuarterGoalCalculator(
                new Bet(backedTeam,
                        new Odds("2.00"),
                        new Handicap(handicap),
                        new Stake("100.00"),
                        score)).calculate();
    }

    private Bet getBet(String handicap) {
        return new Bet(
                HOME,
                new Odds("1.00"),
                new Handicap(handicap),
                new Stake("100.00"),
                new Score(1, 0)
        );
    }
}
