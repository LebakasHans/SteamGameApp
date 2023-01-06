package at.htlgkr.steamgameapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import at.htlgkr.steam.Game;

public class GameAdapter extends BaseAdapter implements Filterable {
    private List<Game> originalGames;
    private List<Game> filteredGames;
    private int layoutId;
    private LayoutInflater inflater;
    private ItemFilter mFilter  = new ItemFilter();

    public GameAdapter(Context context, int listViewItemLayoutId, List<Game> games) {
        this.filteredGames = games;
        this.originalGames = games;
        this.layoutId = listViewItemLayoutId;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return filteredGames.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredGames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return -1;
    }

    //TODO: Add method to update originalGames list (maybe)

    @Override
    public View getView(int position, View givenView, ViewGroup parent) {
        Game game = filteredGames.get(position);
        View listItem = (givenView == null) ? inflater.inflate(this.layoutId, null) : givenView;
        ((TextView) listItem .findViewById(R.id.txt_gameName)).setText(game.getName());
        ((TextView) listItem .findViewById(R.id.txt_gameReleasedate)).setText(new SimpleDateFormat(Game.DATE_FORMAT).format(game.getReleaseDate()));
        ((TextView) listItem .findViewById(R.id.txt_gamePrice)).setText(String.valueOf(game.getPrice()));
        return listItem;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            List<Game> list = new ArrayList<>();
            list.addAll(originalGames);

            list = list.stream()
                    .filter(game -> game.getName().toLowerCase().contains(filterString))
                    .collect(Collectors.toList());

            results.values = list;
            results.count = list.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredGames = (ArrayList<Game>) results.values;
            notifyDataSetChanged();
        }
    }
}