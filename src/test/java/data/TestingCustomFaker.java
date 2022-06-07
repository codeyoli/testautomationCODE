package data;

import org.testng.annotations.Test;

public class TestingCustomFaker {

    @Test
    public void demo() {
        TrelloFaker myFaker = new TrelloFaker();
        String insect = myFaker.templates().nextTemplate();
        System.out.println(insect);

        String badEmail = myFaker.badEmail().nextInvalidEmail();
        System.out.println(badEmail);
    }
}

// see for reference:
//   https://www.datafaker.net/documentation/custom-providers/
