package il.ac.pddailycogresearch.pddailycog.data;

import javax.inject.Singleton;

import il.ac.pddailycogresearch.pddailycog.data.model.Chore;

/**
 * Created by janisharali on 25/12/16.
 */

@Singleton
public interface DataManager {


    String getCurrentUserDisplayName();

    void login(String email, String password, DbHelper.DbLoginListener dbLoginListener);

    Chore getCurrentChore();

    void saveCurrentChore();

    //TODO retrieve chore, see how its needed...

}

