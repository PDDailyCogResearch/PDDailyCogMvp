package il.ac.pddailycogresearch.pddailycog.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

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
    // private FirebaseAuth.AuthStateListener mAuthListener;

    //TODO find something that really need injection. or change in module to "return new fire..."
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

    public String getCurrentUserDisplayName() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return currentUser.getDisplayName();
    }

    public void login(String email, String password, final DbLoginListener dbLoginListener) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            dbLoginListener.onLoginSuccess(user.getDisplayName());
                        } else {
                            dbLoginListener.onLoginFailure(task.getException());
                        }

                        // ...
                    }
                });
    }

   //TODO delete this and write a script instead
    private void updateUser(final DbLoginListener dbLoginListener) {
         FirebaseUser user = mAuth.getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName("Bibi")
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            dbLoginListener.onLoginSuccess(user.getDisplayName());
                        }
                    }
                });
    }
}
