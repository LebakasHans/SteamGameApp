package at.htlgkr.steamgameapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import at.htlgkr.steam.Game;

public class GameAdapter extends BaseAdapter{
    private List<Game> games;
    private int layoutId;
    private LayoutInflater inflater;

    public GameAdapter(Context context, int listViewItemLayoutId, List<Game> games) {
        this.games = games;
        this.layoutId = listViewItemLayoutId;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return games.size();
    }

    @Override
    public Object getItem(int position) {
        return games.get(position);
    }

    @Override
    public long getItemId(int position) {
        return -1;
    }

    @Override
    public View getView(int position, View givenView, ViewGroup parent) {
        Game game = games.get(position);
        View listItem = (givenView == null) ? inflater.inflate(this.layoutId, null) : givenView;
        ((TextView) listItem .findViewById(R.id.txt_gameName)).setText(game.getName());
        ((TextView) listItem .findViewById(R.id.txt_gameReleasedate)).setText(new SimpleDateFormat(Game.DATE_FORMAT).format(game.getReleaseDate()));
        ((TextView) listItem .findViewById(R.id.txt_gamePrice)).setText(String.valueOf(game.getPrice()));
        return listItem;
    }


}