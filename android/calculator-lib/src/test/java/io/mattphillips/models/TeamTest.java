package io.mattphillips.models;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class TeamTest {

    @Test
    public void shouldReturnTrueWhenTeamIsHome() {
        Team t = Team.HOME;
        assertThat(t.isHome()).isTrue();
        assertThat(t.isAway()).isFalse();
    }

    @Test
    public void shouldReturnTrueWhenTeamIsAway() {
        Team t = Team.AWAY;
        assertThat(t.isAway()).isTrue();
        assertThat(t.isHome()).isFalse();
    }
}
