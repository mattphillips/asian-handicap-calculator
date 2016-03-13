package io.mattphillips.calculator;

import java.math.BigDecimal;

import io.mattphillips.models.Bet;
import io.mattphillips.models.Outcome;
import io.mattphillips.models.Result;

public class QuarterGoalCalculator extends AsianHandicapCalculator {

    private static final BigDecimal QUARTER_HANDICAP_OFFSET = new BigDecimal(0.25);

    public QuarterGoalCalculator(final Bet bet) {
        super(bet);
    }

    public Outcome calculate() {
        BigDecimal rem = bet.getHandicap().remainder(BigDecimal.ONE);
        Outcome fullGoal = new FullGoalCalculator(buildFullGoalBet(rem)).calculate();
        Outcome halfGoal = new HalfGoalCalculator(buildHalfGoalBet(rem)).calculate();

        return combineOutcomes(fullGoal, halfGoal);
    }

    private Bet buildFullGoalBet(BigDecimal remainder) {
        return new Bet(
                bet.getTeam(),
                bet.getOdds(),
                roundFullGoalHandicap(remainder),
                splitStake(bet.getStake()),
                bet.getScoreline()
        );
    }

    private Bet buildHalfGoalBet(BigDecimal remainder) {
        return new Bet(
                bet.getTeam(),
                bet.getOdds(),
                roundHalfGoalHandicap(remainder),
                splitStake(bet.getStake()),
                bet.getScoreline()
        );
    }

    protected BigDecimal roundFullGoalHandicap(BigDecimal remainder) {
        return remainder.signum() == 1
                ? roundFullGoalPositiveAndHalfGoalNegativeHandicaps(remainder)
                : roundFullGoalNegativeAndHalfGoalPositiveHandicaps(remainder.negate());
    }

    protected BigDecimal roundHalfGoalHandicap(BigDecimal remainder) {
        return remainder.signum() == 1
                ? roundFullGoalNegativeAndHalfGoalPositiveHandicaps(remainder)
                : roundFullGoalPositiveAndHalfGoalNegativeHandicaps(remainder.negate());
    }

    private BigDecimal roundFullGoalPositiveAndHalfGoalNegativeHandicaps(BigDecimal rem) {
        return rem.equals(QUARTER_HANDICAP_OFFSET)
                ? bet.getHandicap().subtract(QUARTER_HANDICAP_OFFSET)
                : bet.getHandicap().add(QUARTER_HANDICAP_OFFSET);
    }

    private BigDecimal roundFullGoalNegativeAndHalfGoalPositiveHandicaps(BigDecimal rem) {
        return rem.equals(QUARTER_HANDICAP_OFFSET)
                ? bet.getHandicap().add(QUARTER_HANDICAP_OFFSET)
                : bet.getHandicap().subtract(QUARTER_HANDICAP_OFFSET);
    }

    private BigDecimal splitStake(BigDecimal stake) {
        return stake.divide(new BigDecimal(2.00)).setScale(2, 0);
    }

    private Outcome combineOutcomes(Outcome fullGoal, Outcome halfGoal) {
        BigDecimal profit = fullGoal.getProfit().add(halfGoal.getProfit());
        return new Outcome(
                determineResultFromOutcomeProfits(profit),
                fullGoal.getPayout().add(halfGoal.getPayout()),
                profit
        );
    }

    private Result determineResultFromOutcomeProfits(BigDecimal profit) {
        if (profit.compareTo(BigDecimal.ZERO) > 0)
            return Result.WIN;
        else if(profit.compareTo(BigDecimal.ZERO) < 0)
            return Result.LOSE;
        else
            return Result.DRAW;
    }
}
