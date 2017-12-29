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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;

import durdinapps.rxfirebase2.RxFirebaseAuth;
import durdinapps.rxfirebase2.RxFirebaseDatabase;
import il.ac.pddailycogresearch.pddailycog.data.model.Chore;
import il.ac.pddailycogresearch.pddailycog.di.ApplicationContext;
import il.ac.pddailycogresearch.pddailycog.utils.AppConstants;
import io.reactivex.Maybe;
import io.reactivex.functions.Function;

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

    @Override
    public void initializeDatabase() {
        mUserReference = FirebaseDatabase.getInstance().getReference("users").child(getCurrentUserUid());
    }

    @Override
    public boolean isUserLogged() {
        return mAuth.getCurrentUser()!=null;
    }

    public String getCurrentUserDisplayName() {
        return mAuth.getCurrentUser().getDisplayName();
    }

    @Override
    public String getCurrentUserUid() {
        return mAuth.getCurrentUser().getUid();
    }

    public Maybe<Boolean> login(String email, String password, final DbLoginListener dbLoginListener) {

        return RxFirebaseAuth.signInWithEmailAndPassword(mAuth, email, password).map(
                 new Function<AuthResult, Boolean>() {
                     @Override
                     public Boolean apply(@io.reactivex.annotations.NonNull AuthResult authResult) throws Exception {
                         return authResult.getUser()!=null;
                     }
                 }
         );
    }

    @Override
    public Maybe<Chore> retrieveLastChore() {

       return RxFirebaseDatabase.observeSingleValueEvent(mUserReference.child(AppConstants.CHORES_KEY).orderByKey().limitToLast(1), new Function<DataSnapshot, Chore>() {
           @Override
           public Chore apply(@io.reactivex.annotations.NonNull DataSnapshot dataSnapshot) throws Exception {
               DataSnapshot ds = dataSnapshot.getChildren().iterator().next();
               Chore chore= ds.getValue(Chore.class);
               return chore;
           }
       });

    }

    @Override
    public void saveChore(Chore chore) {
        mUserReference.child(AppConstants.CHORES_KEY)
                .child(String.valueOf(chore.getChoreNum())).setValue(chore);

    }

    @Override
    public void logout() {
       mAuth.signOut();
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
