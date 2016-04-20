package io.mattphillips.calculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

import io.mattphillips.models.Bet;
import io.mattphillips.models.Outcome;
import io.mattphillips.models.Result;
import io.mattphillips.models.microtypes.Handicap;
import io.mattphillips.models.microtypes.Payout;
import io.mattphillips.models.microtypes.Profit;
import io.mattphillips.models.microtypes.Stake;

public class QuarterGoalCalculator extends AsianHandicapCalculator {

    private static final BigDecimal QUARTER_HANDICAP_OFFSET = new BigDecimal(0.25);
    private static final BigDecimal HALF = new BigDecimal(2.00);

    QuarterGoalCalculator(final Bet bet) {
        super(bet);
    }

    public Outcome calculate() {
        Outcome fullGoal = new FullGoalCalculator(buildFullGoalBet()).calculate();
        Outcome halfGoal = new HalfGoalCalculator(buildHalfGoalBet()).calculate();

        return combineOutcomes(fullGoal, halfGoal);
    }

    public static List<Bet> buildQuarterGoalAllScenariosBets(Bet bet) {
        Handicap handicap = bet.getHandicap();

        if (isHomeBackedBet(bet) || isAwayLaidBet(bet)) {
            return Arrays.asList(
                bet.adjustScore(0, roundHandicapDown(handicap)),
                bet.adjustScore(0, roundHandicapUp(handicap)),
                bet.adjustScore(0, roundAboveBelow(handicap))
            );
        } else {
            return Arrays.asList(
                bet.adjustScore(roundAboveBelow(handicap), 0),
                bet.adjustScore(roundHandicapUp(handicap), 0),
                bet.adjustScore(roundHandicapDown(handicap), 0)
            );
        }
    }

    private static int roundAboveBelow(Handicap handicap) {
        if (handicap.getRemainder().abs().equals(new BigDecimal("0.25"))) {
            return roundHandicapDown(handicap) - 1;
        } else {
            return roundHandicapUp(handicap) + 1;
        }
    }

    private static int roundHandicapUp(Handicap handicap) {
        return handicap.getValue().abs().round(new MathContext(1, RoundingMode.UP)).intValueExact();
    }

    private static int roundHandicapDown(Handicap handicap) {
        return handicap.getValue().abs().round(new MathContext(1, RoundingMode.DOWN)).intValueExact();
    }

    private Bet buildFullGoalBet() {
        return buildBet(roundFullGoalHandicap(bet.getHandicap().getRemainder()));
    }

    private Bet buildHalfGoalBet() {
        return buildBet(roundHalfGoalHandicap(bet.getHandicap().getRemainder()));
    }

    private Bet buildBet(Handicap handicap) {
        return new Bet(
                bet.getTeam(),
                bet.getOdds(),
                handicap,
                splitStake(bet.getStake()),
                bet.getScoreline()
        );
    }

    protected Handicap roundFullGoalHandicap(BigDecimal remainder) {
        BigDecimal roundedHandicap = remainder.signum() == 1
                ? roundFullGoalPositiveAndHalfGoalNegativeHandicaps(remainder)
                : roundFullGoalNegativeAndHalfGoalPositiveHandicaps(remainder.negate());
        return new Handicap(roundedHandicap);
    }

    protected Handicap roundHalfGoalHandicap(BigDecimal remainder) {
        BigDecimal roundedHandicap = remainder.signum() == 1
                ? roundFullGoalNegativeAndHalfGoalPositiveHandicaps(remainder)
                : roundFullGoalPositiveAndHalfGoalNegativeHandicaps(remainder.negate());
        return new Handicap(roundedHandicap);
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
        return new Stake(stake.getValue().divide(HALF));
    }

    private Outcome combineOutcomes(Outcome fullGoal, Outcome halfGoal) {
        // TODO: this shouldn't be combined but should be two seperate slots in the array
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
