package il.ac.pddailycogresearch.pddailycog.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;
import javax.inject.Singleton;

import il.ac.pddailycogresearch.pddailycog.data.model.Chore;
import il.ac.pddailycogresearch.pddailycog.di.ApplicationContext;
import il.ac.pddailycogresearch.pddailycog.utils.AppConstants;

/**
 * Created by שני on 08/11/2017.
 */

@Singleton
public class FirebaseDbHelper implements DbHelper {
    private FirebaseAuth mAuth;
    private DatabaseReference mUserReference; //TODO deal with null when not logged in - just to be safe


    //TODO find something that really need injection. or change in module to "return new fire..."
    @Inject
    public FirebaseDbHelper(@ApplicationContext Context context) {
        mAuth = FirebaseAuth.getInstance();
    }

    public String getCurrentUserDisplayName() {
        return mAuth.getCurrentUser().getDisplayName();
    }

    @Override
    public String getCurrentUserUid() {
        return mAuth.getCurrentUser().getUid();
    }

    public void login(String email, String password, final DbLoginListener dbLoginListener) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            mUserReference = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());

                            dbLoginListener.onLoginSuccess(user.getDisplayName());
                        } else {
                            dbLoginListener.onLoginFailure(task.getException());
                        }

                    }
                });
    }

    @Override
    public void retrieveChore(final RetrieveChoreCallback retrieveChoreCallback) {

        ValueEventListener choreListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Chore chore = dataSnapshot.getValue(Chore.class);
                retrieveChoreCallback.onRetrieved(chore);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                retrieveChoreCallback.onError(new Exception(databaseError.getMessage()));
                //TODO error handling
            }
        };
        mUserReference.addListenerForSingleValueEvent(choreListener);
    }

    @Override
    public void saveChore(Chore chore) {
        mUserReference.child(AppConstants.CHORES_KEY)
                .child(String.valueOf(chore.getChoreNum())).setValue(chore);

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
