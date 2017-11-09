package il.ac.pddailycogresearch.pddailycog.data;

import android.content.Context;
import android.content.res.Resources;

import il.ac.pddailycogresearch.pddailycog.data.model.User;
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
    private SharedPrefsHelper mSharedPrefsHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context,
                       DbHelper dbHelper,
                       SharedPrefsHelper sharedPrefsHelper) {
        mContext = context;
        mDbHelper = dbHelper;
        mSharedPrefsHelper = sharedPrefsHelper;
    }

    public void saveAccessToken(String accessToken) {
        mSharedPrefsHelper.put(SharedPrefsHelper.PREF_KEY_ACCESS_TOKEN, accessToken);
    }

    public String getAccessToken(){
        return mSharedPrefsHelper.get(SharedPrefsHelper.PREF_KEY_ACCESS_TOKEN, null);
    }

    public String createUser(User user) throws Exception {
        return mDbHelper.insertUser(user);
    }

    public User getUser(String userId) throws Resources.NotFoundException, NullPointerException {
        return mDbHelper.getUser(userId);
    }

    public String getCurrentUserDisplayName()
    {
        return mDbHelper.getCurrentUserDisplayName();
    }

    public void login(String email, String password, DbHelper.DbLoginListener dbLoginListener){
        mDbHelper.login(email,password,dbLoginListener);
    }
}
