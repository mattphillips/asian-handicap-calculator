package io.mattphillips.models.microtypes;

import java.math.BigDecimal;

public class Stake extends Decimal {

    public Stake(final String stake) {
        super(stake);
    }
    public Stake(final BigDecimal stake) {
        super(stake);
    }
}
