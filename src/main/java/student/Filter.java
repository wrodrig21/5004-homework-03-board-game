package student;

import java.util.stream.Stream;

/** Utility class for filtering board games by string or numeric criteria. */
public final class Filter {

    /** Private constructor to prevent instantiation of this utility class. */
    private Filter() {
    }

    /**
     * Filters a stream of games by a string column and operation.
     *
     * @param games the stream of games to filter
     * @param col   the column to filter on
     * @param op    the comparison operation
     * @param value the value to compare against
     * @return filtered stream of games
     */
    public static Stream<BoardGame> stringFilter(Stream<BoardGame> games, GameData col,
            Operations op, String value) {
        return games.filter(game -> {
            String actual = game.getName();
            int cmp = actual.compareToIgnoreCase(value);
            if (op == Operations.EQUALS) {
                return cmp == 0;
            }
            if (op == Operations.NOT_EQUALS) {
                return cmp != 0;
            }
            if (op == Operations.CONTAINS) {
                return actual.toLowerCase().contains(value.toLowerCase());
            }
            if (op == Operations.GREATER_THAN) {
                return cmp > 0;
            }
            if (op == Operations.LESS_THAN) {
                return cmp < 0;
            }
            if (op == Operations.GREATER_THAN_EQUALS) {
                return cmp >= 0;
            }
            if (op == Operations.LESS_THAN_EQUALS) {
                return cmp <= 0;
            }
            return true;
        });
    }

    /**
     * Filters a stream of games by a numeric column and operation.
     *
     * @param games the stream of games to filter
     * @param col   the column to filter on
     * @param op    the comparison operation
     * @param value the numeric value to compare against
     * @return filtered stream of games
     */
    public static Stream<BoardGame> numberFilter(Stream<BoardGame> games, GameData col,
            Operations op, double value) {
        return games.filter(game -> {
            double actual;
            switch (col) {
                case RATING: actual = game.getRating(); break;
                case DIFFICULTY: actual = game.getDifficulty(); break;
                case RANK: actual = game.getRank(); break;
                case MIN_PLAYERS: actual = game.getMinPlayers(); break;
                case MAX_PLAYERS: actual = game.getMaxPlayers(); break;
                case MIN_TIME: actual = game.getMinPlayTime(); break;
                case MAX_TIME: actual = game.getMaxPlayTime(); break;
                case YEAR: actual = game.getYearPublished(); break;
                default: return true;
            }
            if (op == Operations.EQUALS) {
                return actual == value;
            }
            if (op == Operations.NOT_EQUALS) {
                return actual != value;
            }
            if (op == Operations.GREATER_THAN) {
                return actual > value;
            }
            if (op == Operations.LESS_THAN) {
                return actual < value;
            }
            if (op == Operations.GREATER_THAN_EQUALS) {
                return actual >= value;
            }
            if (op == Operations.LESS_THAN_EQUALS) {
                return actual <= value;
            }
            return true;
        });
    }
}
