package io.mattphillips.asianhandicapcalculator.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.mattphillips.asianhandicapcalculator.R;
import io.mattphillips.asianhandicapcalculator.models.OutcomeParcel;

public class OutcomeParcelArrayAdapter extends ArrayAdapter<OutcomeParcel> {

    private final Context context;
    private final OutcomeParcel[] values;

    @Bind(R.id.team) TextView team;
    @Bind(R.id.odds) TextView odds;
    @Bind(R.id.handicap) TextView handicap;
    @Bind(R.id.stake) TextView stake;
    @Bind(R.id.score) TextView score;

    @Bind(R.id.result) TextView result;
    @Bind(R.id.payout) TextView payout;
    @Bind(R.id.profit) TextView profit;

    @Bind(R.id.outcomeHeader) TextView outcomeHeader;
    @Bind(R.id.outcomeContainer) LinearLayout outcomeContainer;

    public OutcomeParcelArrayAdapter(Context context, OutcomeParcel[] values) {
        super(context, R.layout.list_item_outcome, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item_outcome, parent, false);
        ButterKnife.bind(this, rowView);

        OutcomeParcel outcome = values[position];

        team.setText(outcome.getTeam());
        odds.setText(outcome.getOdds());
        handicap.setText(outcome.getHandicap());
        stake.setText(outcome.getStake());
        score.setText(outcome.getScore());

        result.setText(outcome.getResult());
        payout.setText(outcome.getPayout());
        profit.setText(outcome.getProfit());

        if (outcome.getResult().equalsIgnoreCase("WIN")) {
            updateOutcomeColour(R.color.colorWin);
        } else if (outcome.getResult().equalsIgnoreCase("LOSE")) {
            updateOutcomeColour(R.color.colorLose);
        } else {
            updateOutcomeColour(R.color.colorDraw);
        }

        return rowView;
    }

    private void updateOutcomeColour(int colorRes) {
        outcomeHeader.setBackgroundColor(getContext().getResources().getColor(colorRes));
        outcomeContainer.setBackgroundColor(getContext().getResources().getColor(colorRes));
    }
}
