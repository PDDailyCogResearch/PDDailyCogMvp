package il.ac.pddailycogresearch.pddailycog.data;

import android.content.Context;
import android.net.Uri;

import il.ac.pddailycogresearch.pddailycog.data.model.Chore;
import il.ac.pddailycogresearch.pddailycog.di.ApplicationContext;
import io.reactivex.Maybe;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
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

    public String getCurrentUserDisplayName()
    {
        return mDbHelper.getCurrentUserDisplayName();
    }

    public Maybe<Boolean> login(String email, String password, DbHelper.DbLoginListener dbLoginListener){
        return mDbHelper.login(email,password,dbLoginListener);
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
