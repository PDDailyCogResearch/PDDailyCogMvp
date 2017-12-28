package il.ac.pddailycogresearch.pddailycog.data;


import il.ac.pddailycogresearch.pddailycog.data.model.Chore;

/**
 * Created by שני on 08/11/2017.
 */

public interface DbHelper {


    interface DbLoginListener {
        void onLoginSuccess(String displayName);

        void onLoginFailure(Exception exception);//TODO error handling
    }

    interface RetrieveChoreCallback {
        void onRetrieved(Chore retrievedChore);
        void onError(Exception exception); //TODO error handling
    }

    void initializeDatabase();

    boolean isUserLogged();

    String getCurrentUserDisplayName();

    String getCurrentUserUid();

    void login(String email, String password, final DbLoginListener dbLoginListener);

    void retrieveChore(final RetrieveChoreCallback retrieveChoreCallback);

    void saveChore(Chore chore);
}
