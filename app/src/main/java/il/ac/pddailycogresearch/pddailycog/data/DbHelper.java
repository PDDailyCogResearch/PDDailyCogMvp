package il.ac.pddailycogresearch.pddailycog.data;


import il.ac.pddailycogresearch.pddailycog.data.model.Chore;
import io.reactivex.Maybe;

/**
 * Created by שני on 08/11/2017.
 */

public interface DbHelper {


    interface DbLoginListener {
        void onLoginSuccess(String displayName);

        void onLoginFailure(Exception exception);//TODO error handling
    }

    void initializeDatabase();

    boolean isUserLogged();

    String getCurrentUserDisplayName();

    String getCurrentUserUid();

    Maybe<Boolean> login(String email, String password, final DbLoginListener dbLoginListener);

    Maybe<Chore> retrieveLastChore();

    void saveChore(Chore chore);

    void logout();
}
