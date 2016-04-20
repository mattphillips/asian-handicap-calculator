package io.mattphillips.models.microtypes;

import org.junit.Test;

import io.mattphillips.models.Result;

import static org.assertj.core.api.Assertions.assertThat;

public class PayoutTest {

    @Test
    public void shouldDeterminePayoutFromWinResult() {
        Result r = Result.WIN;
        Stake stake = new Stake("100.00");
        Odds odds = new Odds("2.50");

        assertThat(Payout.determinePayout(r, stake, odds).getValue().toString()).isEqualTo("350.00");
    }

    @Test
    public void shouldDeterminePayoutFromDrawResult() {
        Result r = Result.DRAW;
        Stake stake = new Stake("100.00");
        Odds odds = new Odds("2.50");

        assertThat(Payout.determinePayout(r, stake, odds).getValue().toString()).isEqualTo("100.00");
    }

    @Test
    public void shouldDeterminePayoutFromLoseResult() {
        Result r = Result.LOSE;
        Stake stake = new Stake("100.00");
        Odds odds = new Odds("2.50");

        assertThat(Payout.determinePayout(r, stake, odds).getValue().toString()).isEqualTo("0.00");
    }
}
