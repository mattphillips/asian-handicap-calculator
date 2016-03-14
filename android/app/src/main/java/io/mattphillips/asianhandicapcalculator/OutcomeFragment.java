package io.mattphillips.asianhandicapcalculator;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.ButterKnife;
import io.mattphillips.models.Outcome;

public class OutcomeFragment extends Fragment {

    private static final String OUTCOME_KEY = "outcome";

    @Bind(R.id.result) TextView result;
    @Bind(R.id.payout) TextView payout;
    @Bind(R.id.profit) TextView profit;

    @Bind(R.id.edit) Button edit;
    @Bind(R.id.reset) Button reset;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_outcome, container, false);
        ButterKnife.bind(this, v);

        Bundle args = getArguments();
        Outcome outcome = (Outcome) args.getSerializable(OUTCOME_KEY);

        result.setText(outcome.getResult().toString());
        payout.setText(outcome.getPayout().getValue().toString());
        profit.setText(outcome.getProfit().getValue().toString());

        return v;
    }

    @OnClick(R.id.edit)
    public void edit() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.reset)
    public void reset() {
        getActivity().recreate();
    }
}
