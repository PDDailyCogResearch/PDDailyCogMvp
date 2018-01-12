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

    Maybe<Boolean> login(String username, String password);

    Maybe<Boolean> signup(String username, String password);

    void saveChore(Chore chore);

    boolean isUserLogged();

    Maybe<Chore> retrieveChore();

    void logout();

    Maybe<Uri> saveImage(Uri image);

    boolean isUserCollisionException(Throwable throwable);

    Maybe<String> updateChoreImageUrl();

    //TODO retrieve chore, see how its needed...

}

