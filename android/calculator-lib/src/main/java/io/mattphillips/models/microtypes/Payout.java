package io.mattphillips.models.microtypes;

import java.math.BigDecimal;

import io.mattphillips.models.Result;

public class Payout extends Decimal {

    public Payout(final String payout) {
        super(payout);
    }

    public Payout(final BigDecimal payout) {
        super(payout);
    }

    public static Payout determinePayout(Result result, Stake stake, Odds odds) {
        if (result == Result.WIN)
            return new Payout(stake.getValue().multiply(odds.getValue()));

        else if (result == Result.DRAW)
            return new Payout(stake.getValue());

        else
            return new Payout(BigDecimal.ZERO);
    }
}
