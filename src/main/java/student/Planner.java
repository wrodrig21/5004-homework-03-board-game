package student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Implementation of IPlanner that filters and sorts board games.
 * Filters add on, each call narrows results more unless reset() is called first.
 */
public class Planner implements IPlanner {

    /** The full collection of games. */
    private final Set<BoardGame> allGames;

    /** The current filtered list of games. */
    private List<BoardGame> filtered;

    /**
     * Constructs a Planner with the given collection of games.
     *
     * @param games the full set of board games to plan from.
     */
    public Planner(Set<BoardGame> games) {
        this.allGames = games;
        this.filtered = new ArrayList<>(games);
    }

    /**
     * Filters games using filter string, sorted by name ascending.
     *
     * @param filter the filter string to apply.
     * @return a stream of matching games sorted by name.
     */
    @Override
    public Stream<BoardGame> filter(String filter) {
        return filter(filter, GameData.NAME, true);
    }

    /**
     * Filters games using filter string, sorted by the specified column ascending.
     *
     * @param filter the filter string to apply.
     * @param sortOn the column to sort on.
     * @return a stream of matching games.
     */
    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn) {
        return filter(filter, sortOn, true);
    }

    /**
     * Filters games using filter string, sorted by the specified column in the specified order.
     *
     * @param filter    the filter string to apply.
     * @param sortOn    the column to sort on.
     * @param ascending true for ascending order, false for descending.
     * @return a stream of matching games.
     */
    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn, boolean ascending) {
        if (filter != null && !filter.trim().isEmpty()) {
            for (String part : filter.split(",")) {
                filtered = filterSingle(part.trim(), filtered.stream()).toList();
            }
        }
        Comparator<BoardGame> comp;
        switch (sortOn) {
            case RATING: comp = Comparator.comparingDouble(BoardGame::getRating); break;
            case DIFFICULTY: comp = Comparator.comparingDouble(BoardGame::getDifficulty); break;
            case RANK: comp = Comparator.comparingInt(BoardGame::getRank); break;
            case MIN_PLAYERS: comp = Comparator.comparingInt(BoardGame::getMinPlayers); break;
            case MAX_PLAYERS: comp = Comparator.comparingInt(BoardGame::getMaxPlayers); break;
            case MIN_TIME: comp = Comparator.comparingInt(BoardGame::getMinPlayTime); break;
            case MAX_TIME: comp = Comparator.comparingInt(BoardGame::getMaxPlayTime); break;
            case YEAR: comp = Comparator.comparingInt(BoardGame::getYearPublished); break;
            default: comp = Comparator.comparing(BoardGame::getName, String.CASE_INSENSITIVE_ORDER); break;
        }
        if (!ascending) comp = comp.reversed();
        return filtered.stream().sorted(comp);
    }

    private Stream<BoardGame> filterSingle(String filter, Stream<BoardGame> filteredGames) {
        Operations operator = Operations.getOperatorFromStr(filter);
        if (operator == null) return filteredGames;
        filter = filter.replaceAll(" ", "");
        String[] parts = filter.split(operator.getOperator());
        if (parts.length != 2) return filteredGames;
        GameData column;
        try {
            column = GameData.fromString(parts[0]);
        } catch (IllegalArgumentException e) {
            return filteredGames;
        }
        String value = parts[1];
        if (column == GameData.NAME) {
            return Filter.stringFilter(filteredGames, column, operator, value);
        }
        try {
            double num = Double.parseDouble(value);
            return Filter.numberFilter(filteredGames, column, operator, num);
        } catch (NumberFormatException e) {
            return filteredGames;
        }
    }


    /**
     * Resets the filter back to the full game collection.
     */
    @Override
    public void reset() {
        filtered = new ArrayList<>(allGames);
    }

}
