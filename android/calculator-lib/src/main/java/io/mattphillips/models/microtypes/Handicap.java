package io.mattphillips.models.microtypes;

import java.math.BigDecimal;

public class Handicap extends Decimal {

    public Handicap(final String handicap) {
        super(handicap);
    }

    public Handicap(final BigDecimal handicap) {
        super(handicap);
    }

    public BigDecimal getRemainder() {
        return value.remainder(BigDecimal.ONE);
    }
}
