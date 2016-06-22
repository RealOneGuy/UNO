package ru.badboy.uno;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Евгений on 04.01.2016.
 */
public class PlayerLine extends LinearLayout {
    private TextView mPlayerName;
    private EditText mCurrentScore;
    private TextView mTotalScore;

    public PlayerLine(Context context) {
        super(context);
        init();
    }

    public PlayerLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlayerLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.player_line, this);
        mPlayerName = (TextView) findViewById(R.id.player_name);
        mCurrentScore = (EditText) findViewById(R.id.player_current_score);
        mTotalScore = (TextView) findViewById(R.id.player_total_score);
        mCurrentScore.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                                   @Override
                                                   public void onFocusChange(View v, boolean t) {
                                                       mCurrentScore.setBackgroundColor(Color.parseColor("#afd0d8f9"));
                                                       mCurrentScore.selectAll();
                                                   }
                                               }
        );
    }

    public void setPlayerName(String text) {
        mPlayerName.setText(text);
    }

    public void setCurrentScore(String text) {
        mCurrentScore.setText(text);
    }

    public void setTotalScore(String text) {
        mTotalScore.setText(text);
    }

    public int getCurrentScore() {
        try {
            return Integer.parseInt(mCurrentScore.getText().toString());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void setCurrentScoreColor(int color) {
        mCurrentScore.setBackgroundColor(color);
    }

    public void setTotalScoreColor(int color) {
        mTotalScore.setBackgroundColor(color);
    }

}
