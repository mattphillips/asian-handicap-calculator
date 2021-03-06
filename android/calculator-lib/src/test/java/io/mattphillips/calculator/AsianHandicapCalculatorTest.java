package io.mattphillips.calculator;

import org.junit.Test;

import java.util.List;

import io.mattphillips.models.Bet;
import io.mattphillips.models.Outcome;
import io.mattphillips.models.Result;
import io.mattphillips.models.Score;
import io.mattphillips.models.Team;
import io.mattphillips.models.microtypes.Handicap;
import io.mattphillips.models.microtypes.Odds;
import io.mattphillips.models.microtypes.Payout;
import io.mattphillips.models.microtypes.Profit;
import io.mattphillips.models.microtypes.Stake;

import static org.assertj.core.api.Assertions.*;

public class AsianHandicapCalculatorTest {

    @Test
    public void shouldCreateFullGoalAsianHandicapCalculatorFromBet() throws Exception {
        AsianHandicapCalculator calc =
                AsianHandicapCalculator.determineFinalScoreBetType(getBetWithHandicap("1.00"));
        assertThat(calc).isInstanceOf(FullGoalCalculator.class);

        calc = AsianHandicapCalculator.determineFinalScoreBetType(getBetWithHandicap("-1.00"));
        assertThat(calc).isInstanceOf(FullGoalCalculator.class);

        calc = AsianHandicapCalculator.determineFinalScoreBetType(getBetWithHandicap("0.00"));
        assertThat(calc).isInstanceOf(FullGoalCalculator.class);
    }

    @Test
    public void shouldCreateHalfGoalCalculatorFromBet() throws Exception {
        AsianHandicapCalculator calc =
                AsianHandicapCalculator.determineFinalScoreBetType(getBetWithHandicap("1.50"));
        assertThat(calc).isInstanceOf(HalfGoalCalculator.class);

        calc = AsianHandicapCalculator.determineFinalScoreBetType(getBetWithHandicap("-1.50"));
        assertThat(calc).isInstanceOf(HalfGoalCalculator.class);
    }

    @Test
    public void shouldCreateQuarterGoalCalculatorFromBet() throws Exception {
        AsianHandicapCalculator calc =
                AsianHandicapCalculator.determineFinalScoreBetType(getBetWithHandicap("1.25"));
        assertThat(calc).isInstanceOf(QuarterGoalCalculator.class);

        calc = AsianHandicapCalculator.determineFinalScoreBetType(getBetWithHandicap("1.75"));
        assertThat(calc).isInstanceOf(QuarterGoalCalculator.class);

        calc = AsianHandicapCalculator.determineFinalScoreBetType(getBetWithHandicap("-1.25"));
        assertThat(calc).isInstanceOf(QuarterGoalCalculator.class);

        calc = AsianHandicapCalculator.determineFinalScoreBetType(getBetWithHandicap("-1.75"));
        assertThat(calc).isInstanceOf(QuarterGoalCalculator.class);
    }

    @Test (expected = Exception.class)
    public void shouldThrowExceptionWhenHandicapTypeIsNotRecognised() throws Exception {
        AsianHandicapCalculator.determineFinalScoreBetType(getBetWithHandicap("1.33"));
    }

    @Test
    public void shouldCorrectlyCalculateOutcomeForFullGoalHomeBackedHandicap() throws Exception {
        Bet bet = new Bet(Team.HOME, new Odds("2"), new Handicap("+2.00"), new Stake("10"), null);
        List<Outcome> outcomes = AsianHandicapCalculator.calculateAllScenarios(bet);
        assertThat(outcomes.size()).isEqualTo(3);
        assertThat(outcomes).contains(new Outcome(Result.WIN, new Payout("30"), new Profit("20"), bet));
        assertThat(outcomes).contains(new Outcome(Result.DRAW, new Payout("10"), new Profit("0"), bet));
        assertThat(outcomes).contains(new Outcome(Result.LOSE, new Payout("0"), new Profit("-10"), bet));
    }

    @Test
    public void shouldCorrectlyCalculateOutcomeForFullGoalHomeLaidHandicap() throws Exception {
        Bet bet = new Bet(Team.HOME, new Odds("2"), new Handicap("-2.00"), new Stake("10"), null);
        List<Outcome> outcomes = AsianHandicapCalculator.calculateAllScenarios(bet);
        assertThat(outcomes.size()).isEqualTo(3);
        assertThat(outcomes).contains(new Outcome(Result.WIN, new Payout("30"), new Profit("20"), bet));
        assertThat(outcomes).contains(new Outcome(Result.DRAW, new Payout("10"), new Profit("0"), bet));
        assertThat(outcomes).contains(new Outcome(Result.LOSE, new Payout("0"), new Profit("-10"), bet));
    }

    @Test
    public void shouldCorrectlyCalculateOutcomeForFullGoalAwayBackedHandicap() throws Exception {
        Bet bet = new Bet(Team.AWAY, new Odds("2"), new Handicap("+2.00"), new Stake("10"), null);
        List<Outcome> outcomes = AsianHandicapCalculator.calculateAllScenarios(bet);
        assertThat(outcomes.size()).isEqualTo(3);
        assertThat(outcomes).contains(new Outcome(Result.WIN, new Payout("30"), new Profit("20"), bet));
        assertThat(outcomes).contains(new Outcome(Result.DRAW, new Payout("10"), new Profit("0"), bet));
        assertThat(outcomes).contains(new Outcome(Result.LOSE, new Payout("0"), new Profit("-10"), bet));
    }

    @Test
    public void shouldCorrectlyCalculateOutcomeForFullGoalAwayLaidHandicap()  throws Exception {
        Bet bet = new Bet(Team.AWAY, new Odds("2"), new Handicap("-2.00"), new Stake("10"), null);
        List<Outcome> outcomes = AsianHandicapCalculator.calculateAllScenarios(bet);
        assertThat(outcomes.size()).isEqualTo(3);
        assertThat(outcomes).contains(new Outcome(Result.WIN, new Payout("30"), new Profit("20"), bet));
        assertThat(outcomes).contains(new Outcome(Result.DRAW, new Payout("10"), new Profit("0"), bet));
        assertThat(outcomes).contains(new Outcome(Result.LOSE, new Payout("0"), new Profit("-10"), bet));
    }

    @Test
    public void shouldCorrectlyCalculateOutcomeForHalfGoalHomeBackedHandicap()  throws Exception {
        Bet bet = new Bet(Team.HOME, new Odds("2"), new Handicap("+2.50"), new Stake("10"), null);
        List<Outcome> outcomes = AsianHandicapCalculator.calculateAllScenarios(bet);
        assertThat(outcomes.size()).isEqualTo(2);
        assertThat(outcomes).contains(new Outcome(Result.WIN, new Payout("30"), new Profit("20"), bet));
        assertThat(outcomes).contains(new Outcome(Result.LOSE, new Payout("0"), new Profit("-10"), bet));
    }

    @Test
    public void shouldCorrectlyCalculateOutcomeForHalfGoalHomeLaidHandicap() throws Exception {
        Bet bet = new Bet(Team.HOME, new Odds("2"), new Handicap("-2.50"), new Stake("10"), null);
        List<Outcome> outcomes = AsianHandicapCalculator.calculateAllScenarios(bet);
        assertThat(outcomes.size()).isEqualTo(2);
        assertThat(outcomes).contains(new Outcome(Result.WIN, new Payout("30"), new Profit("20"), bet));
        assertThat(outcomes).contains(new Outcome(Result.LOSE, new Payout("0"), new Profit("-10"), bet));
    }

    @Test
    public void shouldCorrectlyCalculateOutcomeForHalfGoalAwayBackedHandicap() throws Exception {
        Bet bet = new Bet(Team.AWAY, new Odds("2"), new Handicap("+2.50"), new Stake("10"), null);
        List<Outcome> outcomes = AsianHandicapCalculator.calculateAllScenarios(bet);
        assertThat(outcomes.size()).isEqualTo(2);
        assertThat(outcomes).contains(new Outcome(Result.WIN, new Payout("30"), new Profit("20"), bet));
        assertThat(outcomes).contains(new Outcome(Result.LOSE, new Payout("0"), new Profit("-10"), bet));
    }

    @Test
    public void shouldCorrectlyCalculateOutcomeForHalfGoalAwayLaidHandicap() throws Exception {
        Bet bet = new Bet(Team.AWAY, new Odds("2"), new Handicap("-2.50"), new Stake("10"), null);
        List<Outcome> outcomes = AsianHandicapCalculator.calculateAllScenarios(bet);
        assertThat(outcomes.size()).isEqualTo(2);
        assertThat(outcomes).contains(new Outcome(Result.WIN, new Payout("30"), new Profit("20"), bet));
        assertThat(outcomes).contains(new Outcome(Result.LOSE, new Payout("0"), new Profit("-10"), bet));
    }

    @Test
    public void shouldCorrectlyCalculateOutcomeForQuarterGoalHomeBackedHandicap() throws Exception {
        // half lose
        Bet bet = new Bet(Team.HOME, new Odds("2"), new Handicap("+2.75"), new Stake("10"), null);
        List<Outcome> outcomes = AsianHandicapCalculator.calculateAllScenarios(bet);
        assertThat(outcomes.size()).isEqualTo(3);
        assertThat(outcomes).contains(new Outcome(Result.WIN, new Payout("30"), new Profit("20"), bet));
        assertThat(outcomes).contains(new Outcome(Result.LOSE, new Payout("5"), new Profit("-5"), bet));
        assertThat(outcomes).contains(new Outcome(Result.LOSE, new Payout("0"), new Profit("-10"), bet));
        // half win
        bet = new Bet(Team.HOME, new Odds("2"), new Handicap("+2.25"), new Stake("10"), null);
        outcomes = AsianHandicapCalculator.calculateAllScenarios(bet);
        assertThat(outcomes.size()).isEqualTo(3);
        assertThat(outcomes).contains(new Outcome(Result.WIN, new Payout("30"), new Profit("20"), bet));
        assertThat(outcomes).contains(new Outcome(Result.WIN, new Payout("20"), new Profit("10"), bet));
        assertThat(outcomes).contains(new Outcome(Result.LOSE, new Payout("0"), new Profit("-10"), bet));
    }

    @Test
    public void shouldCorrectlyCalculateOutcomeForQuarterGoalHomeLaidHandicap() throws Exception {
        // half win
        Bet bet = new Bet(Team.HOME, new Odds("2"), new Handicap("-2.75"), new Stake("10"), null);
        List<Outcome> outcomes = AsianHandicapCalculator.calculateAllScenarios(bet);
        assertThat(outcomes.size()).isEqualTo(3);
        assertThat(outcomes).contains(new Outcome(Result.WIN, new Payout("30"), new Profit("20"), bet));
        assertThat(outcomes).contains(new Outcome(Result.WIN, new Payout("20"), new Profit("10"), bet));
        assertThat(outcomes).contains(new Outcome(Result.LOSE, new Payout("0"), new Profit("-10"), bet));
        // half lose
        bet = new Bet(Team.HOME, new Odds("2"), new Handicap("-2.25"), new Stake("10"), null);
        outcomes = AsianHandicapCalculator.calculateAllScenarios(bet);
        assertThat(outcomes.size()).isEqualTo(3);
        assertThat(outcomes).contains(new Outcome(Result.WIN, new Payout("30"), new Profit("20"), bet));
        assertThat(outcomes).contains(new Outcome(Result.LOSE, new Payout("5"), new Profit("-5"), bet));
        assertThat(outcomes).contains(new Outcome(Result.LOSE, new Payout("0"), new Profit("-10"), bet));
    }

    @Test
    public void shouldCorrectlyCalculateOutcomeForQuarterGoalAwayBackedHandicap() throws Exception {
        // half lose
        Bet bet = new Bet(Team.AWAY, new Odds("2"), new Handicap("+2.75"), new Stake("10"), null);
        List<Outcome> outcomes = AsianHandicapCalculator.calculateAllScenarios(bet);
        assertThat(outcomes.size()).isEqualTo(3);
        assertThat(outcomes).contains(new Outcome(Result.WIN, new Payout("30"), new Profit("20"), bet));
        assertThat(outcomes).contains(new Outcome(Result.LOSE, new Payout("5"), new Profit("-5"), bet));
        assertThat(outcomes).contains(new Outcome(Result.LOSE, new Payout("0"), new Profit("-10"), bet));
        // half win
        bet = new Bet(Team.AWAY, new Odds("2"), new Handicap("+2.25"), new Stake("10"), null);
        outcomes = AsianHandicapCalculator.calculateAllScenarios(bet);
        assertThat(outcomes.size()).isEqualTo(3);
        assertThat(outcomes).contains(new Outcome(Result.WIN, new Payout("20"), new Profit("10"), bet));
        assertThat(outcomes).contains(new Outcome(Result.WIN, new Payout("20"), new Profit("10"), bet));
        assertThat(outcomes).contains(new Outcome(Result.LOSE, new Payout("0"), new Profit("-10"), bet));
    }

    @Test
    public void shouldCorrectlyCalculateOutcomeForQuarterGoalAwayLaidHandicap() throws Exception {
        // half win
        Bet bet = new Bet(Team.AWAY, new Odds("2"), new Handicap("-2.75"), new Stake("10"), null);
        List<Outcome> outcomes = AsianHandicapCalculator.calculateAllScenarios(bet);
        assertThat(outcomes.size()).isEqualTo(3);
        assertThat(outcomes).contains(new Outcome(Result.WIN, new Payout("30"), new Profit("20"), bet));
        assertThat(outcomes).contains(new Outcome(Result.WIN, new Payout("20"), new Profit("10"), bet));
        assertThat(outcomes).contains(new Outcome(Result.LOSE, new Payout("0"), new Profit("-10"), bet));
        // half lose
        bet = new Bet(Team.AWAY, new Odds("2"), new Handicap("-2.25"), new Stake("10"), null);
        outcomes = AsianHandicapCalculator.calculateAllScenarios(bet);
        assertThat(outcomes.size()).isEqualTo(3);
        assertThat(outcomes).contains(new Outcome(Result.WIN, new Payout("30"), new Profit("20"), bet));
        assertThat(outcomes).contains(new Outcome(Result.LOSE, new Payout("5"), new Profit("-5"), bet));
        assertThat(outcomes).contains(new Outcome(Result.LOSE, new Payout("0"), new Profit("-10"), bet));
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
