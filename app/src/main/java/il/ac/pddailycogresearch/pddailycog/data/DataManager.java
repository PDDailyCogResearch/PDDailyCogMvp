package il.ac.pddailycogresearch.pddailycog.data;

import android.net.Uri;

import javax.inject.Singleton;

import il.ac.pddailycogresearch.pddailycog.data.model.Chore;
import io.reactivex.Maybe;

/**
 * Created by janisharali on 25/12/16.
 */

@Singleton
public interface DataManager {


    String getCurrentUserDisplayName();

    Maybe<Boolean> login(String email, String password, DbHelper.DbLoginListener dbLoginListener);

    void saveChore(Chore chore);

    boolean isUserLogged();

    Maybe<Chore> retrieveChore();

    void logout();

    Maybe<Uri> saveImage(Uri image);

    //TODO retrieve chore, see how its needed...

}

