package student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementation of IGameList that stores a unique collection of board games.
 * Names are returned in case-insensitive ascending order.
 */
public class GameList implements IGameList {

    /** The internal list of games. */
    private List<BoardGame> games;

    /**
     * Constructor for the GameList.
     */
    public GameList() {
        games = new ArrayList<>();
    }

    /**
     * Returns game names sorted alphabetically, ignoring case.
     *
     * @return sorted list of game names.
     */
    @Override
    public List<String> getGameNames() {
        return games.stream()
                .map(BoardGame::getName)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .collect(Collectors.toList());
    }

    @Override
    public void clear() {
        games.clear();
    }

    /**
     * Returns the number of games in the list.
     *
     * @return game count.
     */
    @Override
    public int count() {
        return games.size();
    }

    @Override
    public void saveGame(String filename) {
        try {
            java.io.PrintWriter pw = new java.io.PrintWriter(filename);
            for (String name : getGameNames()) {
                pw.println(name);
            }
            pw.close();
        } catch (java.io.IOException e) {
            // nothing
        }
    }

    /**
     * Adds games from the filtered stream based on the selector string.
     * Currently supports "all" to add every game.
     *
     * @param str      the selector string.
     * @param filtered the stream of games to select from.
     * @throws IllegalArgumentException if the selector is not valid.
     */
    @Override
    public void addToList(String str, Stream<BoardGame> filtered) throws IllegalArgumentException {
        List<BoardGame> filteredList = filtered.collect(Collectors.toList());
        str = str.trim();

        if (str.equalsIgnoreCase(ADD_ALL)) {
            for (BoardGame g : filteredList) {
                if (!games.contains(g)) games.add(g);
            }
        } else if (str.matches("\\d+-\\d+")) {
            String[] range = str.split("-");
            int start = Integer.parseInt(range[0]) - 1;
            int end = Math.min(Integer.parseInt(range[1]), filteredList.size());
            for (int i = start; i < end; i++) {
                if (!games.contains(filteredList.get(i))) games.add(filteredList.get(i));
            }
        } else if (str.matches("\\d+")) {
            int i = Integer.parseInt(str) - 1;
            if (i >= 0 && i < filteredList.size() && !games.contains(filteredList.get(i))) {
                games.add(filteredList.get(i));
            }
        } else {
            for (BoardGame g : filteredList) {
                if (g.getName().equalsIgnoreCase(str) && !games.contains(g)) {
                    games.add(g);
                }
            }
        }
    }

    @Override
    public void removeFromList(String str) throws IllegalArgumentException {
        str = str.trim();
        if (str.equalsIgnoreCase(ADD_ALL)) {
            games.clear();
        } else {
            for (int i = 0; i < games.size(); i++) {
                if (games.get(i).getName().equalsIgnoreCase(str)) {
                    games.remove(i);
                    break;
                }
            }
        }
    }


}
