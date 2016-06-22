package ru.badboy.uno;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Евгений on 12.01.2016.
 */
public class StartGameFragment extends Fragment {
    private List<EditText> mEdits = new ArrayList<>();
    private int countOfEdits;
    private int limit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.start_game);
        countOfEdits = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.start_fragment, container, false);

        final LinearLayout playerLayout = (LinearLayout) view.findViewById(R.id.players_layout);
        final EditText limitEdit = (EditText) view.findViewById(R.id.limit_edit);
        final Button addButton = (Button) view.findViewById(R.id.add_player_button);
        final Button startButton = (Button) view.findViewById(R.id.start_game_button);
        final Button removeButton = (Button) view.findViewById(R.id.remove_player_button);

        removeButton.setEnabled(false);
        startButton.setEnabled(false);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEdit(playerLayout);
                removeButton.setEnabled(true);
                if (countOfEdits==9){
                    addButton.setEnabled(false);
                }
                if ((!startButton.isEnabled())&&countOfEdits>=3){
                    startButton.setEnabled(true);
                }
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEdit(playerLayout);
                if (countOfEdits==0){
                    removeButton.setEnabled(false);
                }
                if ((!addButton.isEnabled())&& countOfEdits<9){
                    addButton.setEnabled(true);
                }
                if (startButton.isEnabled()&&countOfEdits<3){
                    startButton.setEnabled(false);
                }
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    limit = Integer.parseInt(limitEdit.getText().toString());
                    Intent i = new Intent(getActivity(), UnoActivity.class);
                    ArrayList<String> mNames = new ArrayList<>();
                    for (EditText editText : mEdits) {
                        mNames.add(editText.getText().toString());
                    }
                    i.putStringArrayListExtra(UnoFragment.EXTRA_NAMES, mNames);
                    i.putExtra(UnoFragment.EXTRA_LIMIT, limit);
                    startActivity(i);
                }
                catch (NumberFormatException e){
                    Toast.makeText(getActivity(), R.string.wrong_limit,Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    private void createEdit(LinearLayout layout){
        EditText mEdit = new EditText(getContext());
        mEdit.setBackgroundColor(Color.parseColor("#ffffff"));
        mEdit.setHint(R.string.player_name_hint);
        mEdit.setText("Player "+(countOfEdits+1));
        mEdit.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        layout.addView(mEdit);
        mEdits.add(mEdit);
        countOfEdits++;
    }

    private void deleteEdit(LinearLayout layout){
        if (countOfEdits>0) {
            layout.removeViewAt(countOfEdits - 1);
            mEdits.remove(countOfEdits - 1);
        }
        countOfEdits--;
    }
}
