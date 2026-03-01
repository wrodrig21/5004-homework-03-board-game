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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clear'");
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveGame'");
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
            for (BoardGame game : filteredList) {
                if (!games.contains(game)) {
                    games.add(game);
                }
            }
            return;
        }

        throw new IllegalArgumentException("Invalid selector: " + str);
    }

    @Override
    public void removeFromList(String str) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeFromList'");
    }


}
