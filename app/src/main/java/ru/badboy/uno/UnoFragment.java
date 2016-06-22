package ru.badboy.uno;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UnoFragment extends Fragment {
    public static final String EXTRA_NAMES =
            "com.bignerdranch.android.uno.player_names";
    public static final String EXTRA_LIMIT =
            "com.bignerdranch.android.uno.limit";

    private ArrayList<Player> mPlayers = new ArrayList<>();
    private int limit;
    private UnoGame mGame;
    private ArrayList<PlayerLine> playerLines = new ArrayList<>();

    public static UnoFragment newInstance(ArrayList<String> playerNames, int limit) {
        UnoFragment fragment = new UnoFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_LIMIT, limit);
        args.putStringArrayList(EXTRA_NAMES, playerNames);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        limit = getArguments().getInt(EXTRA_LIMIT);
        ArrayList<String> mPlayersNames = getArguments().getStringArrayList(EXTRA_NAMES);
        for (String name:mPlayersNames){
            mPlayers.add(new Player(name));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.game_fragment, container, false);

        final Button mAddButton = (Button) view.findViewById(R.id.add_button);
        final Button mUndoButton = (Button) view.findViewById(R.id.undo_button);
        Button mClearButton = (Button) view.findViewById(R.id.clear_button);
        Button mNewButton = (Button) view.findViewById(R.id.new_button);
        final LinearLayout mLayout = (LinearLayout) view.findViewById(R.id.all_layout);
        final LinearLayout mBaseLayout = (LinearLayout) view.findViewById(R.id.base_layout);

        mGame = new UnoGame(mPlayers, limit);
        SingleGame.setGame(mGame);
        mUndoButton.setEnabled(false);
        for (int i = 0; i < mGame.getPlayers().size(); i++) {
            PlayerLine pLine = new PlayerLine(getActivity());
            pLine.setPlayerName(mGame.getPlayers().get(i).getName());
            pLine.setCurrentScore(String.valueOf(mGame.getPlayers().get(i).getCurrentScore()));
            pLine.setTotalScore(String.valueOf(mGame.getPlayers().get(i).getTotalScore()));
            playerLines.add(pLine);
        }
        for (PlayerLine pLine : playerLines) {
            mLayout.addView(pLine);
        }



        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> mScores = new ArrayList<>();
                ArrayList<Integer> mNumberOfBadPlayers = new ArrayList<>();
                boolean goodGame = true;
                for (int i = 0; i < playerLines.size(); i++) {
                    goodGame = !(playerLines.get(i).getCurrentScore()<0) && goodGame;
                    if (!goodGame){
                        mNumberOfBadPlayers.add(i);
                    }
                    else {
                        mScores.add(playerLines.get(i).getCurrentScore());
                    }
                }
                if (goodGame) {
                    mGame.oneStepAdd(mScores);
                    setScores();
                    clearColors();
                    setWinnersAndLosers();
                    if (mGame.isOver()){
                        List<Player> losers = mGame.getLosers();
                        Toast.makeText(getActivity(),String.format("Game over. The loser is: %s",losers.get(0).getName()),Toast.LENGTH_LONG).show();
                        mAddButton.setEnabled(false);
                    }
                    mUndoButton.setEnabled(true);
                }
                else
                {
                    for (Integer badPlayer:
                    mNumberOfBadPlayers) {
                        playerLines.get(badPlayer).setCurrentScoreColor(Color.parseColor("#FFE70300"));
                    }
                    mScores.clear();
                    Toast.makeText(getActivity(), R.string.bad_data,Toast.LENGTH_LONG).show();
                }
                mBaseLayout.requestFocus();
            }
        });

        mUndoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGame.oneStepUndo();
                setScores();
                clearColors();
                if (mGame.getRound()!=0)
                    setWinnersAndLosers();
                else
                    mUndoButton.setEnabled(false);
                if (!mAddButton.isEnabled()){
                    mAddButton.setEnabled(true);
                }
                mBaseLayout.requestFocus();
            }
        });

        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                        getActivity());
                quitDialog.setTitle("Are you sure you want to clear the results?");

                quitDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mGame.oneStepClear();
                        setScores();
                        clearColors();
                        if (!mAddButton.isEnabled()) {
                            mAddButton.setEnabled(true);
                        }
                        mUndoButton.setEnabled(false);
                        mBaseLayout.requestFocus();
                    }
                });
                quitDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                quitDialog.show();


            }
        });

        mNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                        getActivity());
                quitDialog.setTitle("Are you sure you want to quit this game?");

                quitDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(getActivity(), StartActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }
                });
                quitDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                quitDialog.show();
            }
        });
        return view;
    }




    private void setScores(){
        for (int i = 0; i < mGame.getPlayers().size(); i++) {
            playerLines.get(i).setTotalScore(String.valueOf(mGame.getCurrentScores().get(i)));
            playerLines.get(i).setCurrentScore("");
        }
    }

    private void clearColors(){
        for (int i = 0; i < playerLines.size(); i++) {
            playerLines.get(i).setTotalScoreColor(Color.parseColor("#B5A3B187"));
        }
    }

    private void setWinnersAndLosers(){
        for (Integer winnerNumber:mGame.getWinnersNumbers()) {
            playerLines.get(winnerNumber).setTotalScoreColor(Color.parseColor("#AFF919B1"));
        }
        for (Integer loserNumber:mGame.getLosersNumbers()) {
            playerLines.get(loserNumber).setTotalScoreColor(Color.parseColor("#B5B14F07"));
        }
    }
}
