package io.mattphillips.models.microtypes;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProfitTest {

    @Test
    public void shouldCalculateProfitCorrectlyFromPayoutAndStake() {
        Payout payout = new Payout("100.00");
        Stake stake = new Stake("50.00");
        assertThat(Profit.calculateProfit(payout, stake).getValue().toString()).isEqualTo("50.00");
    }
}
