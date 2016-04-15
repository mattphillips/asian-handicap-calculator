package io.mattphillips.models.microtypes;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HandicapTest {

    @Test
    public void shouldReturnRemainderOfHandicap() {
        assertThat(new Handicap("0.00").getRemainder().toString()).isEqualTo("0.00");
        assertThat(new Handicap("0.25").getRemainder().toString()).isEqualTo("0.25");
        assertThat(new Handicap("0.50").getRemainder().toString()).isEqualTo("0.50");
        assertThat(new Handicap("0.75").getRemainder().toString()).isEqualTo("0.75");
        assertThat(new Handicap("1.00").getRemainder().toString()).isEqualTo("0.00");
        assertThat(new Handicap("1.25").getRemainder().toString()).isEqualTo("0.25");
        assertThat(new Handicap("1.50").getRemainder().toString()).isEqualTo("0.50");
        assertThat(new Handicap("1.75").getRemainder().toString()).isEqualTo("0.75");

        assertThat(new Handicap("-0.25").getRemainder().toString()).isEqualTo("-0.25");
        assertThat(new Handicap("-0.50").getRemainder().toString()).isEqualTo("-0.50");
        assertThat(new Handicap("-0.75").getRemainder().toString()).isEqualTo("-0.75");
        assertThat(new Handicap("-1.00").getRemainder().toString()).isEqualTo("0.00");
        assertThat(new Handicap("-1.25").getRemainder().toString()).isEqualTo("-0.25");
        assertThat(new Handicap("-1.50").getRemainder().toString()).isEqualTo("-0.50");
        assertThat(new Handicap("-1.75").getRemainder().toString()).isEqualTo("-0.75");
    }
}
