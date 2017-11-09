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
public interface DataManager extends DbHelper {

    public void saveAccessToken(String accessToken);

    public String getAccessToken();

    public String createUser(User user) throws Exception;

    public User getUser(String userId) throws Resources.NotFoundException, NullPointerException;

    public String getCurrentUserDisplayName();
}
