package io.mattphillips.asianhandicapcalculator.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.mattphillips.asianhandicapcalculator.R;

public class SplashScreenFragment extends Fragment {

    private static final int SPLASH_SCREEN_DURATION = 3500;
    private static final int TEXT_FADE_IN_DURATION = 1850;
    private static final int TEXT_FADE_IN_DELAY = 500;
    private static final float ANIMATION_FROM_ALPHA = 0.0f;
    private static final float ANIMATION_TO_ALPHA = 1.0f;

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.author)
    TextView author;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_splashscreen, container, false);
        ButterKnife.bind(this, v);

        final Animation in = new AlphaAnimation(ANIMATION_FROM_ALPHA, ANIMATION_TO_ALPHA);
        in.setDuration(TEXT_FADE_IN_DURATION);

        AnimationSet as = new AnimationSet(true);
        in.setStartOffset(TEXT_FADE_IN_DELAY);
        as.addAnimation(in);
        title.startAnimation(in);
        author.startAnimation(in);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, new CalculatorFragment())
                        .commit();
            }
        }, SPLASH_SCREEN_DURATION);

        return v;
    }
}
