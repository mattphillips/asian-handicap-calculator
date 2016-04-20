package io.mattphillips.asianhandicapcalculator.models;

import android.os.Parcel;
import android.os.Parcelable;

import io.mattphillips.models.Outcome;

public class OutcomeParcel implements Parcelable {

    private final String result;
    private final String payout;
    private final String profit;
    private final String team;
    private final String odds;
    private final String handicap;
    private final String stake;
    private final String score;

    public OutcomeParcel(final Outcome outcome) {
        this.result = outcome.getResult().toString();
        this.payout = outcome.getPayout().getValue().toString();
        this.profit = outcome.getProfit().getValue().toString();
        this.team = outcome.getBet().getTeam().toString();
        this.odds = outcome.getBet().getOdds().toString();
        this.handicap = outcome.getBet().getHandicap().toString();
        this.stake = outcome.getBet().getStake().toString();
        this.score = outcome.getBet().getScoreline().toString();
    }

    public OutcomeParcel(Parcel in){
        String[] data = new String[3];

        in.readStringArray(data);
        this.result = data[0];
        this.payout = data[1];
        this.profit = data[2];
        this.team = data[3];
        this.odds = data[4];
        this.handicap = data[5];
        this.stake = data[6];
        this.score = data[7];
    }

    public String getResult() {
        return result;
    }

    public String getPayout() {
        return payout;
    }

    public String getProfit() {
        return profit;
    }

    public String getTeam() {
        return team;
    }

    public String getOdds() {
        return odds;
    }

    public String getHandicap() {
        return handicap;
    }

    public String getStake() {
        return stake;
    }

    public String getScore() {
        return score;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                this.getResult(),
                this.getPayout(),
                this.getProfit(),
                this.getTeam(),
                this.getOdds(),
                this.getHandicap(),
                this.getStake(),
                this.getScore()
        });
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public OutcomeParcel createFromParcel(Parcel in) {
            return new OutcomeParcel(in);
        }

        public Outcome[] newArray(int size) {
            return new Outcome[size];
        }
    };
}
