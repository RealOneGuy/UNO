package ru.badboy.uno;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;


public class HistoryFragment extends Fragment {

    private DataAdapter mAdapter;
    private NamesAdapter mAdapterPlayers;

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_history, container, false);

        final GridView gridNames = (GridView) view.findViewById(R.id.grid_names);
        gridNames.setNumColumns(SingleGame.getInstance(getContext()).getPlayers().size());
        mAdapterPlayers = new NamesAdapter(getContext(),
                android.R.layout.simple_list_item_1);
        gridNames.setAdapter(mAdapterPlayers);

        final GridView g = (GridView) view.findViewById(R.id.gready);
        g.setNumColumns(SingleGame.getInstance(getContext()).getPlayers().size());
        mAdapter = new DataAdapter(getContext(),
                android.R.layout.simple_list_item_1);
        g.setAdapter(mAdapter);
        return view;
    }

    public void notifyAdapter(){
        mAdapter.refreshAdapter();
        mAdapter.notifyDataSetChanged();
    }

}
