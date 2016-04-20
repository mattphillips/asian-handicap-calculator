package io.mattphillips.models;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class ScoreTest {

    @Test
    public void shouldCorrectlyOverrideEquals() {
        assertThat(new Score(0, 0)).isEqualTo(new Score(0, 0));
        assertThat(new Score(0, 0)).isNotEqualTo(new Score(1, 0));
        assertThat(new Score(0, 0)).isNotEqualTo(null);
    }
}
