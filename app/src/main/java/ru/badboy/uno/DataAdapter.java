package ru.badboy.uno;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends ArrayAdapter<Integer> {

    private static final List<Integer> mHistory = new ArrayList<>();

    public void refreshAdapter()
    {
        mHistory.clear();
        List<List<Integer>> historyList = SingleGame.getInstance(getContext()).getHistory();
        for (int i=0; i<historyList.size(); i++){
            mHistory.addAll(historyList.get(i));
        }
//        List<Integer> helpList = new ArrayList<>();
//        for (int i=0;i<7;i++){
//            helpList.add((int)(Math.random()*100));
//        }
//        mHistory.addAll(new ArrayList<>(helpList));
//        mHistory.addAll(new ArrayList<>(helpList));
//        mHistory.addAll(new ArrayList<>(helpList));
    }

    Context mContext;

    // Конструктор
    public DataAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId, mHistory);
        // TODO Auto-generated constructor stub
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        TextView label = (TextView) convertView;

        if (convertView == null) {
            convertView = new TextView(mContext);
            label = (TextView) convertView;
        }
        label.setText(String.valueOf(mHistory.get(position)));
        return (convertView);
    }

    // возвращает содержимое выделенного элемента списка
    public Integer getItem(int position) {
        return mHistory.get(position);
    }

}
