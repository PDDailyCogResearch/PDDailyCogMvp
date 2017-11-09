package il.ac.pddailycogresearch.pddailycog.data;

import il.ac.pddailycogresearch.pddailycog.data.model.User;

/**
 * Created by שני on 08/11/2017.
 */

public interface DbHelper {
     public User getUser(String id);
     public String insertUser(User user) throws Exception;
    public String getCurrentUserDisplayName();
}
