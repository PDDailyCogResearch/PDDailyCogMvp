package il.ac.pddailycogresearch.pddailycog.data;

import android.content.Context;
import android.net.Uri;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import javax.inject.Inject;
import javax.inject.Singleton;

import durdinapps.rxfirebase2.RxFirebaseAuth;
import durdinapps.rxfirebase2.RxFirebaseDatabase;
import durdinapps.rxfirebase2.RxFirebaseStorage;
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
    private StorageReference mStorageReference;

    //TODO find something that really need injection. or change in module to "return new fire..."
    @Inject
    public FirebaseDbHelper(@ApplicationContext Context context) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        initializeUserRef();
    }

    private void initializeUserRef() {
        if( mAuth.getCurrentUser() != null) {
            mUserReference = FirebaseDatabase.getInstance().getReference(AppConstants.USERS_KEY).child(getCurrentUserUid());
            mUserReference.keepSynced(true);//because persistence is enable, need to make sure the data is synced with database
            mStorageReference=FirebaseStorage.getInstance().getReference().child(getCurrentUserUid());
        }
    }

    @Override
    public boolean isUserCollisionException(Throwable throwable) {
        return throwable.getClass()==FirebaseAuthUserCollisionException.class;
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

    @Override
    public Maybe<Boolean> login(String username, String password) {
        username = username+AppConstants.MAIL_SUFFIX;
        return RxFirebaseAuth.signInWithEmailAndPassword(mAuth, username, password).map(
                 new Function<AuthResult, Boolean>() {
                     @Override
                     public Boolean apply(@io.reactivex.annotations.NonNull AuthResult authResult) throws Exception {
                         initializeUserRef();
                         return authResult.getUser()!=null;
                     }
                 }
         );

    }
    @Override
    public Maybe<Boolean> signup(String username, String password){
        username = username+AppConstants.MAIL_SUFFIX;
        return RxFirebaseAuth.createUserWithEmailAndPassword(mAuth,username,password).map(
                new Function<AuthResult, Boolean>() {
                    @Override
                    public Boolean apply(@io.reactivex.annotations.NonNull AuthResult authResult) throws Exception {
                        initializeUserRef();
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
               if (dataSnapshot.getChildren().iterator().hasNext()) {
                   DataSnapshot ds = dataSnapshot.getChildren().iterator().next();
                   Chore chore= ds.getValue(Chore.class);
                   return chore;
               }
               throw new Exception(AppConstants.HAS_NO_CHORES_MSG);
           }
       });

    }

    @Override
    public void saveChore(Chore chore) {
        mUserReference.child(AppConstants.CHORES_KEY)
                .child(String.valueOf(chore.getChoreNum())).setValue(chore);

    }

    public Maybe<Uri> saveImage(Uri image) {
       return RxFirebaseStorage.putFile(mStorageReference.child(image.getLastPathSegment()),image).map(
                new Function<UploadTask.TaskSnapshot, Uri>() {
                    @Override
                    public Uri apply(@io.reactivex.annotations.NonNull UploadTask.TaskSnapshot taskSnapshot) throws Exception {
                        return taskSnapshot.getDownloadUrl();
                    }
                }
        );
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
