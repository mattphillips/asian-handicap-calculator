package io.mattphillips.asianhandicapcalculator.fragments;

import android.app.Fragment;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
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
    private static final String NUMBER_INPUT_REGEX = "\\d{0,10}(\\.\\d{0,2})?";

    private static final int MIN_SCORE = 0;
    private static final int MAX_SCORE = 9;

    @Checked
    @Bind(R.id.teamSelection)
    RadioGroup teamSelection;

    @NotEmpty
    @Bind(R.id.odds)
    EditText teamOdds;

    @NotEmpty
    @Bind(R.id.stake)
    EditText stakeInput;

    @Bind(R.id.homeScore)
    NumberPicker homeScore;

    @Bind(R.id.awayScore)
    NumberPicker awayScore;

    @Select
    @Bind(R.id.handicaps)
    Spinner handicaps;

    @Checked
    @Bind(R.id.calculationType)
    RadioGroup calculationType;

    @Bind(R.id.allScenarios)
    RadioButton allScenarios;

    @Bind(R.id.finalScore)
    RadioButton finalScore;

    @Bind(R.id.scorelineHeader)
    TextView scorelineHeader;

    @Bind(R.id.scoreline)
    LinearLayout scoreline;

    @Bind(R.id.topContent)
    ScrollView scrollView;

    private Validator validator;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_calculator, container, false);
        ButterKnife.bind(this, v);

        validator = new Validator(this);
        validator.setValidationListener(this);

        bindSpinnerToData(handicaps, R.array.array_handicaps);

        addInputRestriction(stakeInput);
        addInputRestriction(teamOdds);

        setScorePickerBounds(homeScore);
        setScorePickerBounds(awayScore);

        return v;
    }

    @Override
    public void onResume() {
        determineScorelineVisibility();
        super.onResume();
    }

    private void bindSpinnerToData(Spinner spinner, int arrayResource) {
        spinner.setAdapter(getAdapter(arrayResource));
        spinner.getBackground()
                .setColorFilter(
                        getResources().getColor(R.color.colorSecondary),
                        PorterDuff.Mode.SRC_ATOP
                );
    }

    private ArrayAdapter<CharSequence> getAdapter(int arrayResource) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(),
                arrayResource,
                R.layout.spinner_item
        );
        adapter.setDropDownViewResource(R.layout.spinner_item_list);
        return adapter;
    }

    private void setScorePickerBounds(NumberPicker picker) {
        picker.setMinValue(MIN_SCORE);
        picker.setMaxValue(MAX_SCORE);
    }

    private void addInputRestriction(EditText numberInput) {
        numberInput.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned destination, int destinationStart, int destinationEnd) {
                        if (end > start) {
                            String destinationString = destination.toString();
                            String resultingTxt = destinationString.substring(0, destinationStart)
                                    + source.subSequence(start, end)
                                    + destinationString.substring(destinationEnd);
                            return resultingTxt.matches(NUMBER_INPUT_REGEX) ? null : "";
                        }
                        return null;
                    }
                }
        });
    }

    @OnClick({R.id.finalScore, R.id.allScenarios})
    public void radioGroupUpdate() {
        determineScorelineVisibility();
    }

    private void determineScorelineVisibility() {
        if (allScenarios.isChecked())
            changeScorelineState(View.GONE);

        if(finalScore.isChecked()) {
            changeScorelineState(View.VISIBLE);
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.fullScroll(View.FOCUS_DOWN);
                }
            });
        }
    }

    private void changeScorelineState(int visibility) {
        scoreline.setVisibility(visibility);
        scorelineHeader.setVisibility(visibility);
    }

    @OnClick(R.id.reset)
    public void reset(View view) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new CalculatorFragment())
                .commit();
    }

    @OnClick(R.id.calculate)
    public void calculate(View view) {
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {

        try {
            AsianHandicapCalculator calculator = AsianHandicapCalculator.determineBetType(extractBet());
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

    private Bet extractBet() {
        int selectedTeam = teamSelection.getCheckedRadioButtonId();
        Team team = selectedTeam == R.id.homeTeam ? Team.HOME : Team.AWAY;
        Odds odds = new Odds(teamOdds.getText().toString());
        Handicap handicap = new Handicap(handicaps.getSelectedItem().toString());
        Stake stake = new Stake(stakeInput.getText().toString());

        Score score = new Score(homeScore.getValue(), awayScore.getValue());

        return new Bet(team, odds, handicap, stake, score);
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
}
