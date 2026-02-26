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
        return applySort(filtered.stream(), sortOn, ascending);
    }


    /**
     * Resets the filter back to the full game collection.
     */
    @Override
    public void reset() {
        filtered = new ArrayList<>(allGames);
    }

}
