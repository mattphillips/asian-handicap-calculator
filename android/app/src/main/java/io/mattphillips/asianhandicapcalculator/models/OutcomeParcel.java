package io.mattphillips.asianhandicapcalculator.models;

import android.os.Parcel;
import android.os.Parcelable;

import io.mattphillips.models.Outcome;

public class OutcomeParcel implements Parcelable {

    private final String result;
    private final String payout;
    private final String profit;

    public OutcomeParcel(final Outcome outcome) {
        this.result = outcome.getResult().toString();
        this.payout = outcome.getPayout().getValue().toString();
        this.profit = outcome.getProfit().getValue().toString();
    }

    public OutcomeParcel(Parcel in){
        String[] data = new String[3];

        in.readStringArray(data);
        this.result = data[0];
        this.payout = data[1];
        this.profit = data[2];
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] { this.getResult(), this.getPayout(), this.getProfit()});
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
