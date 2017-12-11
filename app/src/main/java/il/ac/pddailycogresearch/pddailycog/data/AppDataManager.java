package il.ac.pddailycogresearch.pddailycog.data;

import android.content.Context;

import il.ac.pddailycogresearch.pddailycog.data.model.Chore;
import il.ac.pddailycogresearch.pddailycog.di.ApplicationContext;

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

        //TODO delete, find place to retrieve from db for init
        currentChore = new Chore();
        currentChore.setChoreNum(1);
        currentChore.setCurrentPartNum(Chore.ChoreParts.INSTRUCTION);
    }

    public String getCurrentUserDisplayName()
    {
        return mDbHelper.getCurrentUserDisplayName();
    }

    public void login(String email, String password, DbHelper.DbLoginListener dbLoginListener){
        mDbHelper.login(email,password,dbLoginListener);
    }

    @Override
    public Chore getCurrentChore() {
        return currentChore;
    }

    @Override
    public void saveCurrentChore() {
        mDbHelper.saveChore(currentChore);
    }
}
