package il.ac.pddailycogresearch.pddailycog.data;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;
import javax.inject.Singleton;

import il.ac.pddailycogresearch.pddailycog.data.model.User;
import il.ac.pddailycogresearch.pddailycog.di.ApplicationContext;

/**
 * Created by שני on 08/11/2017.
 */

@Singleton
public class FirebaseDbHelper implements DbHelper {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //TODO find something that realy need injection. or change in module to "return new fire..."
    @Inject
    public FirebaseDbHelper(@ApplicationContext Context context) {
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public User getUser(String id) {
        return null;
    }

    @Override
    public String insertUser(User user) throws Exception {
        return null;
    }

    public String getCurrentUserDisplayName(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return currentUser.getDisplayName();
    }
}
