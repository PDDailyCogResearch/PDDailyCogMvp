package il.ac.pddailycogresearch.pddailycog.data;

import android.content.Context;

import il.ac.pddailycogresearch.pddailycog.data.model.Chore;
import il.ac.pddailycogresearch.pddailycog.di.ApplicationContext;
import io.reactivex.Maybe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by janisharali on 25/12/16.
 */

@Singleton
public class AppDataManager implements DataManager {

    private Context mContext;
    private DbHelper mDbHelper;
    /**
     * in-memory chore. if adding more than one type of chore, move to new memory manager
     */
    private Chore currentChore;

    @Inject
    public AppDataManager(@ApplicationContext Context context,
                       DbHelper dbHelper) {
        mContext = context;
        mDbHelper = dbHelper;
        if (mDbHelper.isUserLogged())
            userLoggedInitialization();
    }

    public void userLoggedInitialization() {
        mDbHelper.initializeDatabase();
       // initializeChore();
    }

    @Override
    public Maybe<Chore> retrieveChore() {
        return mDbHelper.retrieveLastChore().map(
                new Function<Chore, Chore>() {
                    @Override
                    public Chore apply(@NonNull Chore chore) throws Exception {
                        return  setCurrentChore(chore);
                    }
                }
                );
    }

    @Override
    public void logout() {
        mDbHelper.logout();
    }

    private Chore setCurrentChore(Chore lastChore) {
        if(lastChore.isCompleted())
            currentChore=new Chore();//TODO decide what do hear
        else
            currentChore=lastChore;
        return currentChore;
    }


    public String getCurrentUserDisplayName()
    {
        return mDbHelper.getCurrentUserDisplayName();
    }

    public Maybe<Boolean> login(String email, String password, DbHelper.DbLoginListener dbLoginListener){
        return mDbHelper.login(email,password,dbLoginListener);
    }

    @Override
    public Chore getCurrentChore() {
        return currentChore; //TODO what if null?
    }

    @Override
    public void saveCurrentChore() {
        mDbHelper.saveChore(currentChore);
    }

    @Override
    public boolean isUserLogged() {
        return mDbHelper.isUserLogged();
    }
}
