package at.htlgkr.steamgameapp;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.htlgkr.steam.Game;
import at.htlgkr.steam.ReportType;
import at.htlgkr.steam.SteamBackend;

public class MainActivity extends AppCompatActivity {
    private static final String GAMES_CSV = "games.csv";
    private SteamBackend steamBackend;
    private Spinner spinner;
    private ListView gameListView;
    private GameAdapter gameAdapter;
    private ArrayAdapter<ReportTypeSpinnerItem> mAdapter;
    private List<ReportTypeSpinnerItem> spinnerItems = new ArrayList<>();
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        steamBackend = new SteamBackend();
        loadGamesIntoListView();
        setUpReportSelection();
        setUpSearchButton();
        setUpAddGameButton();
        setUpSaveButton();
    }

    private void loadGamesIntoListView() {
        gameListView = findViewById(R.id.gamesList);
        try {
            steamBackend.loadGames(getAssets().open(GAMES_CSV));
        } catch (IOException e) {
            e.printStackTrace();
        }
        gameAdapter = new GameAdapter(this, R.layout.game_item_layout, steamBackend.getGames());
        gameListView.setAdapter(gameAdapter);
    }

    private void setUpReportSelection() {
        spinner = findViewById(R.id.chooseReport);
        spinnerItems.add(new ReportTypeSpinnerItem(ReportType.NONE ,SteamGameAppConstants.SELECT_ONE_SPINNER_TEXT));
        spinnerItems.add(new ReportTypeSpinnerItem(ReportType.SUM_GAME_PRICES ,SteamGameAppConstants.SUM_GAME_PRICES_SPINNER_TEXT));
        spinnerItems.add(new ReportTypeSpinnerItem(ReportType.AVERAGE_GAME_PRICES ,SteamGameAppConstants.AVERAGE_GAME_PRICES_SPINNER_TEXT));
        spinnerItems.add(new ReportTypeSpinnerItem(ReportType.UNIQUE_GAMES ,SteamGameAppConstants.UNIQUE_GAMES_SPINNER_TEXT));
        spinnerItems.add(new ReportTypeSpinnerItem(ReportType.MOST_EXPENSIVE_GAMES ,SteamGameAppConstants.MOST_EXPENSIVE_GAMES_SPINNER_TEXT));
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, spinnerItems);
        spinner.setAdapter(mAdapter);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ReportTypeSpinnerItem item = spinnerItems.get(position);
                switch (item.getType()){
                    case NONE:
                        return;
                    case SUM_GAME_PRICES:
                        alert.setMessage(SteamGameAppConstants.ALL_PRICES_SUM + steamBackend.sumGamePrices());
                        break;
                    case AVERAGE_GAME_PRICES:
                        alert.setMessage(SteamGameAppConstants.ALL_PRICES_AVERAGE + steamBackend.averageGamePrice());
                        break;
                    case UNIQUE_GAMES:
                        alert.setMessage(SteamGameAppConstants.UNIQUE_GAMES_COUNT + steamBackend.getUniqueGames().size());
                        break;
                    case MOST_EXPENSIVE_GAMES:
                        List<Game> mostExpensiveGames = steamBackend.selectTopNGamesDependingOnPrice(3);
                        alert.setMessage(SteamGameAppConstants.MOST_EXPENSIVE_GAMES
                        + mostExpensiveGames.get(0).toString() + "\n"
                        + mostExpensiveGames.get(1).toString() + "\n"
                        + mostExpensiveGames.get(2).toString());
                        break;
                }
                alert.setNeutralButton("ok", null);
                alert.show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setUpSearchButton() {
        searchButton = findViewById(R.id.search);
        searchButton.setOnClickListener(v -> {

        });
        // Implementieren Sie diese Methode.
    }

    private void setUpAddGameButton() {
        // Implementieren Sie diese Methode.
    }

    private void setUpSaveButton() {
        // Implementieren Sie diese Methode.
    }
}
