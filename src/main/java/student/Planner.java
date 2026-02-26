package student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
     * Filters games using the given filter string, sorted by name ascending.
     *
     * @param filter the filter string to apply.
     * @return a stream of matching games sorted by name.
     */
    @Override
    public Stream<BoardGame> filter(String filter) {
        if (filter != null && !filter.trim().isEmpty()) {
            Stream<BoardGame> stream = filtered.stream();
            filtered = filterSingle(filter.trim(), stream).collect(Collectors.toList());
        }

        Comparator<BoardGame> comp = Comparator.comparing(
                BoardGame::getName, String.CASE_INSENSITIVE_ORDER);
        return filtered.stream().sorted(comp);
    }

    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'filter'");
    }

    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn, boolean ascending) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'filter'");
    }

    /**
     * Resets the filter back to the full game collection.
     */
    @Override
    public void reset() {
        filtered = new ArrayList<>(allGames);
    }

    /**
     * Applies a single filter expression to the stream.
     * Currently a stub -- returns the stream unchanged.
     *
     * @param filter        the single filter string.
     * @param currentStream the stream to filter.
     * @return the filtered stream.
     */
    private Stream<BoardGame> filterSingle(String filter, Stream<BoardGame> currentStream) {
        return currentStream;
    }

}
