package data.provider;

import data.TrelloFaker;
import net.datafaker.Faker;

public class InvalidEmail {

    /* ------ Fields ------ */
    // Data source
    private static final String[] INVALID_EMAIL = {
            "^&*#$@gmail.com",
            "23423$$%@gmail.com",
            "@example.com",
            "email.example.com",
            "email@111.222.333.44444",
            "Abc..123@example.com"
    };

    // Faker object reference
    private final Faker faker;

    /* ------ Constructor  ------ */
    public InvalidEmail(TrelloFaker inputFaker) {
        this.faker = inputFaker;
    }

    // Returns random invalid email address
    // from the data source
    public String nextInvalidEmail() {
        int dataCount = INVALID_EMAIL.length;
        int idx = faker.random().nextInt(dataCount);
        return INVALID_EMAIL[idx];
    }
}//end::class