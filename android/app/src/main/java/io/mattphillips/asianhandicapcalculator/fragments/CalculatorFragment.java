package io.mattphillips.asianhandicapcalculator.fragments;

import android.app.Fragment;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Select;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.ButterKnife;

import io.mattphillips.asianhandicapcalculator.R;
import io.mattphillips.calculator.AsianHandicapCalculator;
import io.mattphillips.models.Bet;
import io.mattphillips.models.Outcome;
import io.mattphillips.models.Score;
import io.mattphillips.models.Team;
import io.mattphillips.models.microtypes.Handicap;
import io.mattphillips.models.microtypes.Odds;
import io.mattphillips.models.microtypes.Stake;

public class CalculatorFragment extends Fragment implements Validator.ValidationListener {

    private static final String OUTCOME_KEY = "outcome";
    private static final String STATE_NAME = "calculator";

    @Checked
    @Bind(R.id.teamSelection)
    RadioGroup teamSelection;

    @NotEmpty
    @Bind(R.id.odds)
    EditText teamOdds;

    @NotEmpty
    @Bind(R.id.stake)
    EditText stakeInput;

    @Select
    @Bind(R.id.homeScore)
    Spinner homeScore;

    @Select
    @Bind(R.id.awayScore)
    Spinner awayScore;

    @Select
    @Bind(R.id.handicaps)
    Spinner handicaps;

    @Bind(R.id.calculate)
    Button calculate;

    @Bind(R.id.reset)
    Button reset;

    private Validator validator;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_calculator, container, false);
        ButterKnife.bind(this, v);

        validator = new Validator(this);
        validator.setValidationListener(this);

        bindSpinnerToData(handicaps, R.array.array_handicaps);
        bindSpinnerToData(homeScore, R.array.array_scores);
        bindSpinnerToData(awayScore, R.array.array_scores);

        return v;
    }

    @OnClick(R.id.reset)
    public void reset(View view) {
        getActivity().recreate();
    }

    @OnClick(R.id.calculate)
    public void calculate(View view) {
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        int selectedTeam = teamSelection.getCheckedRadioButtonId();
        Team team = selectedTeam == R.id.homeTeam ? Team.HOME : Team.AWAY;
        Odds odds = new Odds(teamOdds.getText().toString());
        Handicap handicap = new Handicap(handicaps.getSelectedItem().toString());
        Stake stake = new Stake(stakeInput.getText().toString());

        Score score = new Score(
                Integer.parseInt(homeScore.getSelectedItem().toString()),
                Integer.parseInt(awayScore.getSelectedItem().toString())
        );

        Bet bet = new Bet(team, odds, handicap, stake, score);

        try {

            AsianHandicapCalculator calculator = AsianHandicapCalculator.determineBetType(bet);
            Outcome outcome = calculator.calculate();

            Bundle bundle = new Bundle();
            bundle.putSerializable(OUTCOME_KEY, outcome);

            Fragment outcomeFragment = new OutcomeFragment();
            outcomeFragment.setArguments(bundle);

            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, outcomeFragment)
                    .addToBackStack(STATE_NAME)
                    .commit();

        } catch (Exception e) {

        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity().getApplicationContext());

            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
//                view.setBackgroundColor(Color.RED);
//                int labelView = view.getLabelFor();
//                TextView v = (TextView) getActivity().findViewById(labelView);
//                v.setError("");
                Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void bindSpinnerToData(Spinner spinner, int arrayResource) {
        spinner.setAdapter(getAdapter(arrayResource));
        spinner.getBackground()
                .setColorFilter(
                        getResources().getColor(R.color.colorPrimaryDark),
                        PorterDuff.Mode.SRC_ATOP
                );
    }

    private ArrayAdapter<CharSequence> getAdapter(int arrayResource) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(),
                arrayResource,
                R.layout.spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }
}
