package data.provider;


import data.TrelloFaker;
import net.datafaker.Faker;

public class Template {
    private static final String[] TEMPLATE_NAME = {
                "Business",
                "Design",
                "Education",
                "Photography",
                "Interior",
                "Etsy"
            };

    private final Faker faker;

    public Template(TrelloFaker faker) {
        this.faker = faker;
    }

    public String nextTemplate() {
        int dataCount = TEMPLATE_NAME.length;
        int idx = faker.random().nextInt(dataCount);
        return TEMPLATE_NAME[idx];
    }
}//end::class
