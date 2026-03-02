import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import student.BoardGame;
import student.GameList;
import student.IGameList;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * JUnit tests for the GameList class.
 */
public class TestGameList {

    private IGameList list;
    private Set<BoardGame> sampleGames;

    @BeforeEach
    public void setup() {
        list = new GameList();
        sampleGames = new HashSet<>();
        sampleGames.add(new BoardGame("Catan", 1, 3, 4, 60, 120, 2.5, 10, 7.2, 1995));
        sampleGames.add(new BoardGame("Chess", 2, 2, 2, 10, 60, 3.5, 1, 8.5, 1475));
        sampleGames.add(new BoardGame("Go", 3, 2, 2, 30, 180, 4.0, 2, 8.2, 2000));
    }

    @Test
    public void testInitialCountIsZero() {
        assertEquals(0, list.count());
    }

    @Test
    public void testInitialNamesIsEmpty() {
        assertTrue(list.getGameNames().isEmpty());
    }

    @Test
    public void testAddAllAddsEveryGame() {
        list.addToList("all", sampleGames.stream());
        assertEquals(3, list.count());
    }

    @Test
    public void testClearEmptiesList() {
        list.addToList("all", sampleGames.stream());
        list.clear();
        assertEquals(0, list.count());
    }

    @Test
    public void testAddByName() {
        list.addToList("Chess", sampleGames.stream());
        assertEquals(1, list.count());
        assertEquals("Chess", list.getGameNames().get(0));
    }

    @Test
    public void testAddByNumber() {
        Stream<BoardGame> stream = sampleGames.stream()
                .sorted((a, b) -> String.CASE_INSENSITIVE_ORDER.compare(a.getName(), b.getName()));
        list.addToList("1", stream);
        assertEquals(1, list.count());
    }

}
