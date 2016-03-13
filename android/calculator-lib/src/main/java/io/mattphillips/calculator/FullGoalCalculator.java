package io.mattphillips.calculator;

import android.util.Log;

import java.math.BigDecimal;

import io.mattphillips.models.Bet;
import io.mattphillips.models.Outcome;
import io.mattphillips.models.Result;
import io.mattphillips.models.Team;

public class FullGoalCalculator extends AsianHandicapCalculator {

    private final Bet bet;

    public FullGoalCalculator(final Bet bet) {
        this.bet = bet;
    }

    public Outcome calculate() {
        BigDecimal goalSupremacy = bet.getTeam() == Team.HOME ?
                calculateGoalSupremacy(bet.getScoreline().homeScore(), bet.getScoreline().awayScore()) :
                calculateGoalSupremacy(bet.getScoreline().awayScore(), bet.getScoreline().homeScore());

        Result result = determineResult(goalSupremacy, bet.getHandicap());
        BigDecimal payout = determinePayout(result, bet);
        BigDecimal profit = payout.subtract(bet.getStake());

        return new Outcome(result, payout, profit);
    }

    private BigDecimal calculateGoalSupremacy(int backedTeam, int opposingTeam) {
        return new BigDecimal(backedTeam).subtract(new BigDecimal(opposingTeam));
    }

    private Result determineResult(BigDecimal goalSupremacy, BigDecimal handicap) {
        if (goalSupremacy.add(handicap).compareTo(BigDecimal.ZERO) > 0)
            return Result.WIN;
        else if (goalSupremacy.add(handicap).compareTo(BigDecimal.ZERO) < 0)
            return Result.LOSE;
        else
            return Result.DRAW;
    }

    private BigDecimal determinePayout(Result result, Bet bet) {
        if (result == Result.WIN)
            return bet.getStake().multiply(bet.getOdds()).setScale(2, 0);
        else if (result == Result.DRAW)
            return bet.getStake().setScale(2, 0);
        else
            return BigDecimal.ZERO.setScale(2, 0);
    }
}
