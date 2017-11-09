package il.ac.pddailycogresearch.pddailycog.data;

import il.ac.pddailycogresearch.pddailycog.data.model.User;

/**
 * Created by שני on 08/11/2017.
 */

public interface DbHelper {
    public interface DbLoginListener {
        public void onLoginSuccess(String displayName);
        public void onLoginFailure(Exception exception);
    }

    public User getUser(String id);

    public String insertUser(User user) throws Exception;

    public String getCurrentUserDisplayName();

    public void login(String email, String password, final DbLoginListener dbLoginListener);
}
