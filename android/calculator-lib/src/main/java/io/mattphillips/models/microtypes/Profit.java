package io.mattphillips.models.microtypes;

import java.math.BigDecimal;

public class Profit extends Decimal {

    public Profit(final BigDecimal profit) {
        super(profit);
    }

    public static Profit calculateProfit(Payout payout, Stake stake) {
        return new Profit(payout.getValue().subtract(stake.getValue()));
    }
}
