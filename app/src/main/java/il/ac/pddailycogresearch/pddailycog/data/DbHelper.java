package il.ac.pddailycogresearch.pddailycog.data;


import android.net.Uri;

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

    boolean isUserLogged();

    String getCurrentUserDisplayName();

    String getCurrentUserUid();

    Maybe<Boolean> login(String email, String password, final DbLoginListener dbLoginListener);

    Maybe<Chore> retrieveLastChore();

    void saveChore(Chore chore);

    Maybe<Uri> saveImage(Uri image);

    void logout();
}
