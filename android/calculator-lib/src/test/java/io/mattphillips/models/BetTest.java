package io.mattphillips.models;

import org.junit.Test;

import io.mattphillips.models.microtypes.Handicap;

import static org.assertj.core.api.Assertions.*;

public class BetTest {

    @Test
    public void shouldReturnTrueWhenBetIsAFullGoalBet() {
        Bet b = new Bet(null, null, new Handicap("1.00"), null, null);
        assertThat(b.isFullGoalBet()).isTrue();
        assertThat(b.isHalfGoalBet()).isFalse();
        assertThat(b.isQuarterGoalBet()).isFalse();
    }

    @Test
    public void shouldReturnTrueWhenBetIsAHalfGoalBet() {
        Bet b = new Bet(null, null, new Handicap("1.50"), null, null);
        assertThat(b.isHalfGoalBet()).isTrue();
        assertThat(b.isFullGoalBet()).isFalse();
        assertThat(b.isQuarterGoalBet()).isFalse();
    }

    @Test
    public void shouldReturnTrueWhenBetIsAQuarterGoalBet() {
        Bet b = new Bet(null, null, new Handicap("1.25"), null, null);
        assertThat(b.isQuarterGoalBet()).isTrue();
        assertThat(b.isFullGoalBet()).isFalse();
        assertThat(b.isHalfGoalBet()).isFalse();
    }
}