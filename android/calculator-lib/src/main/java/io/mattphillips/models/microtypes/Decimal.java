package io.mattphillips.models.microtypes;

import java.math.BigDecimal;

public abstract class Decimal {

    private static final int SCALE = 2;
    private static final int ROUNDING_MODE_UP = 0;

    protected BigDecimal value;

    public Decimal(final String value) {
        this.value = addScale(new BigDecimal(value));
    }

    public Decimal(final BigDecimal value) {
        this.value = addScale(value);
    }

    private BigDecimal addScale(BigDecimal value) {
        return value.setScale(SCALE, ROUNDING_MODE_UP);
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Decimal decimal = (Decimal) o;

        return !(value != null ? !value.equals(decimal.value) : decimal.value != null);

    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
