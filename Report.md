# Report

Submitted report to be manually graded. We encourage you to review the report as you read through the provided
code as it is meant to help you understand some of the concepts. 

## Technical Questions

1. What is the difference between == and .equals in java? Provide a code example of each, where they would return different results for an object. Include the code snippet using the hash marks (```) to create a code block.

   == compares whether two variables point to the exact same object in memory. equals() compares the actual value of two objects. For most things they'll produce different results

   ```java
   String a = String("hello");
   String b = String("hello");

   a == b;       // false
   a.equals(b);  // true 
   ```


2. Logical sorting can be difficult when talking about case. For example, should "apple" come before "Banana" or after? How would you sort a list of strings in a case-insensitive manner?

   Java sorts strings using Unicode values where all uppercase letters come before all lowercase letters so "Banana" would sort before "apple". To sort case insensitive, you can use `String.CASE_INSENSITIVE_ORDER` as the comparator

   ```java
   list.sort(String.CASE_INSENSITIVE_ORDER);
   ```



3. In our version of the solution, we had the following code (snippet)
    ```java
    public static Operations getOperatorFromStr(String str) {
        if (str.contains(">=")) {
            return Operations.GREATER_THAN_EQUALS;
        } else if (str.contains("<=")) {
            return Operations.LESS_THAN_EQUALS;
        } else if (str.contains(">")) {
            return Operations.GREATER_THAN;
        } else if (str.contains("<")) {
            return Operations.LESS_THAN;
        } else if (str.contains("=="))...
    ```
    Why would the order in which we checked matter (if it does matter)? Provide examples either way proving your point.

    The order does matters because `>=` contains `>`, and `<=` contains `<`. If you checked the shorter operator first they would always match before the longer ones get a chance

    Wrong order (checking > before >=)
    ```java
    // Input: "minPlayers>=4"
    if (str.contains(">"))  // TRUE — matches immediately, returns GREATER_THAN 
    ```

    Correct order (checking >= before >):
    ```java
    // Input: "minPlayers>=4"
    if (str.contains(">="))  // TRUE — matches correctly, returns GREATER_THAN_EQUALS
    ```



4. What is the difference between a List and a Set in Java? When would you use one over the other?

   A List is an ordered collection that allows duplicate items where each has an index. A set is an unordered collection that does not allow duplicates and has no index access. We'd use a list when order matters, you need to access elements by position, or duplicates are acceptable, `GameList` uses a `List<BoardGame>` so that the user's saved games stay in  order and can be referenced by number. We'd use a set when uniqueness is needed.

5. In GamesLoader.java, we use a Map to help figure out the columns. What is a map? Why would we use a Map here?

   A map is a data structure that stores key value pairs, where each key maps to one value. You look up a value by its key, similar to a dictionary. In GamesLoader `processHeader()` uses one by reading the CSV header row and recording which column index to each `GameData` field ie: `GameData.NAME → 0`, `GameData.RATING → 3`. When parsing each  row, the it uses this map to find the right column by name


6. GameData.java is actually an `enum` with special properties we added to help with column name mappings. What is an `enum` in Java? Why would we use it for this application?

   An enumis a special  type that represents a set of named values. Unlike  `int` or `String` constants, enums are type safe, the compiler prevents you from passing an invalid value where a `GameData` is expected. Enums can also have fields, constructors, and methods, making them much more powerful than simple constants.

   For this application, GameData is a good fit because the set of game columns (`NAME`, `RATING`, `RANK`) are fixed. Adding `getColumnName()` and `fromString()` methods directly means each carries its own mapping logic.



7. Rewrite the following as an if else statement inside the empty code block.
    ```java
    switch (ct) {
                case CMD_QUESTION: // same as help
                case CMD_HELP:
                    processHelp();
                    break;
                case INVALID:
                default:
                    CONSOLE.printf("%s%n", ConsoleText.INVALID);
            }
    ``` 

    ```java
    if (ct == ConsoleText.CMD_QUESTION || ct == ConsoleText.CMD_HELP) {
        processHelp();
    } else {
        CONSOLE.printf("%s%n", ConsoleText.INVALID);
    }
    ```

## Deeper Thinking

ConsoleApp.java uses a .properties file that contains all the strings
that are displayed to the client. This is a common pattern in software development
as it can help localize the application for different languages. You can see this
talked about here on [Java Localization – Formatting Messages](https://www.baeldung.com/java-localization-messages-formatting).

Take time to look through the console.properties file, and change some of the messages to
another language (probably the welcome message is easier). It could even be a made up language and for this - and only this - alright to use a translator. See how the main program changes, but there are still limitations in 
the current layout. 

Post a copy of the run with the updated languages below this. Use three back ticks (```) to create a code block. 

```text
*******¡Bienvenido al Planificador de BoardGame Arena!*******

Una herramienta para ayudar a las personas a planificar
qué juegos quieren jugar en Board Game Arena.

Para comenzar, ingrese su primer comando a continuación, o escriba ? o ayuda para ver las opciones.
> Adiós, ¡que te diviertas jugando!
```

Now, thinking about localization - we have the question of why does it matter? The obvious
one is more about market share, but there may be other reasons.  I encourage
you to take time researching localization and the importance of having programs
flexible enough to be localized to different languages and cultures. Maybe pull up data on the
various spoken languages around the world? What about areas with internet access - do they match? Just some ideas to get you started. Another question you are welcome to talk about - what are the dangers of trying to localize your program and doing it wrong? Can you find any examples of that? Business marketing classes love to point out an example of a car name in Mexico that meant something very different in Spanish than it did in English - however [Snopes has shown that is a false tale](https://www.snopes.com/fact-check/chevrolet-nova-name-spanish/).  As a developer, what are some things you can do to reduce 'hick ups' when expanding your program to other languages?


As a reminder, deeper thinking questions are meant to require some research and to be answered in a paragraph for with references. The goal is to open up some of the discussion topics in CS, so you are better informed going into industry.

I honestly didn't think much about localization before this but looking into this more its obviously a very important issue to make software global. Only about 17% of the world speaks English and research shows 72% of users prefer browsing in their own language, and 90% of online shoppers will pick a product with native language support over one without, some even said it mattered more than price [1]. So its not just a nice to have for bigger markets its actually shutting people out if you don't do it especially as internet access keeps growing in places like Africa and Latin America [3]. The tricky part is that getting it wrong isn't just about bad translations. Things like text expanding way longer in German or French and breaking your layout, or placeholder variables showing up raw because of formatting errors, or strings hardcoded in backend errors that never even got sent to translators are all real problems [2]. I think the biggest takeaway for me as a developer is that localization has to be built in from the start not added on at the end.

**References:**

[1] MotionPoint. 2024. What is software localization and why is it important? https://www.motionpoint.com/blog/what-is-software-localization-and-why-is-it-important/

[2] N. Kacem. 2023. The hard parts of software localization. DEV Community. https://dev.to/nadiakacem/the-hard-parts-of-software-localization-50ad

[3] Cazoomi. 2024. Reasons why software localization is a must. https://www.cazoomi.com/blog/reasons-why-software-localization-is-a-must/
