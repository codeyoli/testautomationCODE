package data;

import data.provider.InvalidEmail;
import data.provider.Template;
import net.datafaker.Faker;

/**
 * This class is extensions of the Faker class
 * it lists out the custom random data provider methods.
 */
public class TrelloFaker extends Faker {

    /**
     * Use this method to get randomized Trello
     * theme template titles
     *
     * @return Template data provider
     */
    public Template templates() {
        return getProvider(Template.class, ()-> new Template(this) );
    }

    /**
     * Use this method to get randomized email formats
     * that Trello site won't accept.
     *
     * @return InvalidEmail data provider
     */
    public InvalidEmail badEmail() {
        return getProvider(InvalidEmail.class, ()->new InvalidEmail(this));
    }
}//end::class