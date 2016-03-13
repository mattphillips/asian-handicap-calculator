package io.mattphillips.calculator;

import java.math.BigDecimal;

import io.mattphillips.models.Bet;
import io.mattphillips.models.Outcome;
import io.mattphillips.models.Result;
import io.mattphillips.models.microtypes.Handicap;
import io.mattphillips.models.microtypes.Payout;
import io.mattphillips.models.microtypes.Profit;
import io.mattphillips.models.microtypes.Stake;

public class QuarterGoalCalculator extends AsianHandicapCalculator {

    private static final BigDecimal QUARTER_HANDICAP_OFFSET = new BigDecimal(0.25);

    public QuarterGoalCalculator(final Bet bet) {
        super(bet);
    }

    public Outcome calculate() {
        Outcome fullGoal = new FullGoalCalculator(buildFullGoalBet()).calculate();
        Outcome halfGoal = new HalfGoalCalculator(buildHalfGoalBet()).calculate();

        return combineOutcomes(fullGoal, halfGoal);
    }

    private Bet buildFullGoalBet() {
        return new Bet(
                bet.getTeam(),
                bet.getOdds(),
                new Handicap(roundFullGoalHandicap(bet.getHandicap().getRemainder())),
                splitStake(bet.getStake()),
                bet.getScoreline()
        );
    }

    private Bet buildHalfGoalBet() {
        return new Bet(
                bet.getTeam(),
                bet.getOdds(),
                new Handicap(roundHalfGoalHandicap(bet.getHandicap().getRemainder())),
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
        BigDecimal handicap = bet.getHandicap().getValue();
        return rem.equals(QUARTER_HANDICAP_OFFSET)
                ? handicap.subtract(QUARTER_HANDICAP_OFFSET)
                : handicap.add(QUARTER_HANDICAP_OFFSET);
    }

    private BigDecimal roundFullGoalNegativeAndHalfGoalPositiveHandicaps(BigDecimal rem) {
        BigDecimal handicap = bet.getHandicap().getValue();
        return rem.equals(QUARTER_HANDICAP_OFFSET)
                ? handicap.add(QUARTER_HANDICAP_OFFSET)
                : handicap.subtract(QUARTER_HANDICAP_OFFSET);
    }

    private Stake splitStake(Stake stake) {
        return new Stake(stake.getValue().divide(new BigDecimal(2.00)).setScale(2, 0));
    }

    private Outcome combineOutcomes(Outcome fullGoal, Outcome halfGoal) {
        Profit profit = sumProfit(fullGoal.getProfit(), halfGoal.getProfit());
        return new Outcome(
                determineResultFromOutcomeProfits(profit),
                sumPayout(fullGoal.getPayout(), halfGoal.getPayout()),
                profit
        );
    }

    private Profit sumProfit(Profit fullGoal, Profit halfGoal) {
        return new Profit(fullGoal.getValue().add(halfGoal.getValue()));
    }

    private Payout sumPayout(Payout fullGoal, Payout halfGoal) {
        return new Payout(fullGoal.getValue().add(halfGoal.getValue()));
    }

    private Result determineResultFromOutcomeProfits(Profit profit) {
        if (profit.getValue().compareTo(BigDecimal.ZERO) > 0)
            return Result.WIN;
        else if(profit.getValue().compareTo(BigDecimal.ZERO) < 0)
            return Result.LOSE;
        else
            return Result.DRAW;
    }
}
