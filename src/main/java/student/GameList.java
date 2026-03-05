package student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Stores a list of board games the user wants to play.
 */
public class GameList implements IGameList {

    private List<BoardGame> games;

    /**
     * Creates an empty game list.
     */
    public GameList() {
        games = new ArrayList<>();
    }

    /**
     * Returns the game names sorted alphabetically, ignoring case.
     *
     * @return sorted list of game names
     */
    @Override
    public List<String> getGameNames() {
        List<String> names = new ArrayList<>();
        for (BoardGame g : games) {
            names.add(g.getName());
        }
        Collections.sort(names, (a, b) -> a.compareToIgnoreCase(b));
        return names;
    }

    @Override
    public void clear() {
        games.clear();
    }

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
     * Adds a game or games to the list based on the selector string.
     * Supports "all", a name, a number, or a range like "1-3".
     *
     * @param str the selector string
     * @param filtered the filtered stream to select from
     * @throws IllegalArgumentException if the selector is invalid or out of range
     */
    @Override
    public void addToList(String str, Stream<BoardGame> filtered) throws IllegalArgumentException {
        List<BoardGame> filteredList = filtered.collect(Collectors.toList());
        str = str.trim();

        if (str.isEmpty()) {
            throw new IllegalArgumentException("selector cannot be empty");
        }

        if (str.equalsIgnoreCase(ADD_ALL)) {
            for (BoardGame g : filteredList) {
                if (!games.contains(g)) games.add(g);
            }
        } else if (str.matches("\\d+-\\d+")) {
            String[] range = str.split("-");
            int start = Integer.parseInt(range[0]) - 1;
            int end = Math.min(Integer.parseInt(range[1]), filteredList.size());
            if (start < 0 || start >= filteredList.size()) {
                throw new IllegalArgumentException("range out of bounds");
            }
            for (int i = start; i < end; i++) {
                if (!games.contains(filteredList.get(i))) games.add(filteredList.get(i));
            }
        } else if (str.matches("\\d+")) {
            int i = Integer.parseInt(str) - 1;
            if (i < 0 || i >= filteredList.size()) {
                throw new IllegalArgumentException("index out of range");
            }
            if (!games.contains(filteredList.get(i))) {
                games.add(filteredList.get(i));
            }
        } else {
            boolean found = false;
            for (BoardGame g : filteredList) {
                if (g.getName().equalsIgnoreCase(str)) {
                    found = true;
                    if (!games.contains(g)) games.add(g);
                }
            }
            if (!found) {
                throw new IllegalArgumentException("game not found: " + str);
            }
        }
    }

    @Override
    public void removeFromList(String str) throws IllegalArgumentException {
        str = str.trim();

        if (str.isEmpty()) {
            throw new IllegalArgumentException("selector cannot be empty");
        }

        if (str.equalsIgnoreCase(ADD_ALL)) {
            games.clear();
        } else if (str.matches("\\d+-\\d+")) {
            List<String> names = getGameNames();
            String[] range = str.split("-");
            int start = Integer.parseInt(range[0]) - 1;
            int end = Math.min(Integer.parseInt(range[1]), names.size());
            if (start < 0 || start >= names.size()) {
                throw new IllegalArgumentException("range out of bounds");
            }
            List<String> toRemove = new ArrayList<>();
            for (int i = start; i < end; i++) {
                toRemove.add(names.get(i));
            }
            for (String name : toRemove) {
                for (int j = 0; j < games.size(); j++) {
                    if (games.get(j).getName().equalsIgnoreCase(name)) {
                        games.remove(j);
                        break;
                    }
                }
            }
        } else if (str.matches("\\d+")) {
            List<String> names = getGameNames();
            int i = Integer.parseInt(str) - 1;
            if (i < 0 || i >= names.size()) {
                throw new IllegalArgumentException("index out of range");
            }
            String nameToRemove = names.get(i);
            for (int j = 0; j < games.size(); j++) {
                if (games.get(j).getName().equalsIgnoreCase(nameToRemove)) {
                    games.remove(j);
                    break;
                }
            }
        } else {
            boolean found = false;
            for (int i = 0; i < games.size(); i++) {
                if (games.get(i).getName().equalsIgnoreCase(str)) {
                    games.remove(i);
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new IllegalArgumentException("game not found: " + str);
            }
        }
    }
}
