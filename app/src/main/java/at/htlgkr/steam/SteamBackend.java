package at.htlgkr.steam;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class SteamBackend {
    private List<Game> games = new ArrayList<>();

    public SteamBackend() {}

    public void loadGames(InputStream inputStream) {
        // Diese methode lädt alle Games in eine Variable welche sich im Steam Backend befinden muss.
        games = new BufferedReader(new InputStreamReader(inputStream)).lines()
                .skip(1)
                .map(s -> s.split(";"))
                .map(strings -> {
                    try {
                        return new Game(
                                strings[0]
                                ,new SimpleDateFormat("dd.MM.yyyy").parse(strings[1])
                                ,Double.valueOf(strings[2]));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }

    public void store(OutputStream fileOutputStream) {
        // Diese methode schreibt alle Games in den fileOutputStream.
        try(BufferedWriter output = new BufferedWriter(new OutputStreamWriter(fileOutputStream))){
            games.stream().forEach(game -> {
                try {
                    output.write(
                            game.getName()
                            + ";" + new SimpleDateFormat(Game.DATE_FORMAT).format(game.getReleaseDate())
                            + ";" + game.getPrice());
                    output.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games.clear();
        this.games.addAll(games);
    }

    public void addGame(Game newGame) {
        games.add(newGame);
    }

    public double sumGamePrices() {
        return games.stream()
                .mapToDouble(game -> game.getPrice())
                .sum();
    }

    public double averageGamePrice() {
        return games.stream()
                .mapToDouble(game -> game.getPrice())
                .average()
                .getAsDouble();
    }

    public List<Game> getUniqueGames() {
        return games.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Game> selectTopNGamesDependingOnPrice(int n) {
        return games.stream()
                .sorted(Comparator.comparingDouble(Game::getPrice).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }
}
