package io.mattphillips.asianhandicapcalculator.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.ButterKnife;
import io.mattphillips.asianhandicapcalculator.R;
import io.mattphillips.asianhandicapcalculator.adapters.OutcomeParcelArrayAdapter;
import io.mattphillips.asianhandicapcalculator.models.OutcomeParcel;

public class OutcomeFragment extends Fragment {

    private static final String OUTCOME_KEY = "outcome";
    private static final String OUTCOMES_KEY = "outcomes";

    @Bind(R.id.outcomes) ListView outcomesView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_outcome, container, false);
        ButterKnife.bind(this, v);

        Bundle args = getArguments();

        ArrayAdapter adapter = new OutcomeParcelArrayAdapter(this.getActivity(), extractOutcomes(args));
        outcomesView.setAdapter(adapter);

        return v;
    }

    private OutcomeParcel[] extractOutcomes(Bundle args) {

        if (args.containsKey(OUTCOME_KEY)) {
            return new OutcomeParcel[] { args.getParcelable(OUTCOME_KEY) };

        } else if (args.containsKey(OUTCOMES_KEY)) {

            ArrayList<OutcomeParcel> outcomes = args.getParcelableArrayList(OUTCOMES_KEY);

            Collections.sort(outcomes, new Comparator<OutcomeParcel>() {
                @Override
                public int compare(OutcomeParcel lhs, OutcomeParcel rhs) {
                    BigDecimal leftProfit = new BigDecimal(lhs.getProfit());
                    BigDecimal rightProfit = new BigDecimal(rhs.getProfit());
                    return rightProfit.compareTo(leftProfit);
                }
            });

            OutcomeParcel[] values = new OutcomeParcel[outcomes.size()];
            outcomes.toArray(values);
            return values;
        } else {
            return new OutcomeParcel[1];
        }
    }

    @OnClick(R.id.edit)
    public void edit() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.reset)
    public void reset() {
        getFragmentManager()
            .beginTransaction()
            .replace(R.id.content, new CalculatorFragment())
            .commit();
    }
}
