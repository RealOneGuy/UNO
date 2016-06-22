package ru.badboy.uno;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Евгений on 12.01.2016.
 */
public class NamesAdapter extends ArrayAdapter<String> {
    private static final ArrayList<String> mContacts = new ArrayList<>();
    Context mContext;

    public NamesAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId, mContacts);
        mContacts.clear();
        List<Player> players = SingleGame.getInstance(getContext()).getPlayers();
        for (Player player:players){
            mContacts.add(player.getName());
        }
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView label = (TextView) convertView;

        if (convertView == null) {
            convertView = new TextView(mContext);
            label = (TextView) convertView;
        }
        label.setText(mContacts.get(position));
        return (convertView);
    }

    // возвращает содержимое выделенного элемента списка
    public String getItem(int position) {
        return mContacts.get(position);
    }
}
