package il.ac.pddailycogresearch.pddailycog.data;

import android.content.Context;
import android.net.Uri;

import il.ac.pddailycogresearch.pddailycog.data.model.Chore;
import il.ac.pddailycogresearch.pddailycog.di.ApplicationContext;
import io.reactivex.Maybe;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *
 * Created by janisharali on 25/12/16.
 */

@Singleton
public class AppDataManager implements DataManager {

    private Context mContext;
    private DbHelper mDbHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context,
                       DbHelper dbHelper) {
        mContext = context;
        mDbHelper = dbHelper;
    }

    @Override
    public Maybe<Chore> retrieveChore() {
        return mDbHelper.retrieveLastChore();
    }

    @Override
    public void logout() {
        mDbHelper.logout();
    }

    @Override
    public Maybe<Uri> saveImage(Uri image) {
       return mDbHelper.saveImage(image);
    }

    @Override
    public boolean isUserCollisionException(Throwable throwable) {
        return mDbHelper.isUserCollisionException(throwable);
    }

    public String getCurrentUserDisplayName()
    {
        return mDbHelper.getCurrentUserDisplayName();
    }

    public Maybe<Boolean> login(String username, String password){
        return mDbHelper.login(username,password);
    }

    @Override
    public Maybe<Boolean> signup(String username, String password) {
        return mDbHelper.signup(username,password);
    }

    @Override
    public void saveChore(Chore chore) {
        mDbHelper.saveChore(chore);
    }

    @Override
    public boolean isUserLogged() {
        return mDbHelper.isUserLogged();
    }
}
