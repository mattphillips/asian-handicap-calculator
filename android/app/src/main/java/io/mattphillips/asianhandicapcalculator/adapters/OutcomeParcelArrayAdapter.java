package io.mattphillips.asianhandicapcalculator.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.mattphillips.asianhandicapcalculator.R;
import io.mattphillips.asianhandicapcalculator.models.OutcomeParcel;

public class OutcomeParcelArrayAdapter extends ArrayAdapter<OutcomeParcel> {

    private final Context context;
    private final OutcomeParcel[] values;

    @Bind(R.id.result) TextView result;
    @Bind(R.id.payout) TextView payout;
    @Bind(R.id.profit) TextView profit;

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
        result.setText(outcome.getResult());
        payout.setText(outcome.getPayout());
        profit.setText(outcome.getProfit());

        return rowView;
    }
}
