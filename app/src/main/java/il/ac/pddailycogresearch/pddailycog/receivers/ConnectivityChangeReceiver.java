package il.ac.pddailycogresearch.pddailycog.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import javax.inject.Inject;

import il.ac.pddailycogresearch.pddailycog.PDCApp;
import il.ac.pddailycogresearch.pddailycog.R;
import il.ac.pddailycogresearch.pddailycog.data.DataManager;
import il.ac.pddailycogresearch.pddailycog.data.model.Chore;
import il.ac.pddailycogresearch.pddailycog.utils.AppConstants;
import il.ac.pddailycogresearch.pddailycog.utils.CommonUtils;
import io.reactivex.Maybe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class ConnectivityChangeReceiver extends BroadcastReceiver {

    private static final String TAG = "MY_RECEIVER";
    /***
     * allow checking wether this is the first receiver
     */
    private static int race=0;

    private CompositeDisposable compositeDisposable =
            new CompositeDisposable();
    @Override
    public void onReceive(final Context context, Intent intent) {
        //act only if there is internet connection and if it is the first instance to react
        //needed because in airplane toggle happens few connectivity changes
        if(isNetworkAvailable(context)&&++race==1)
            saveChoreImageInDB(context);
        else
            writeLog("false receiver");
    }

    private void saveChoreImageInDB(Context context) {
        final DataManager dataManager = ((PDCApp) context.getApplicationContext()).getComponent().getDataManager();
        writeLog("1");
        compositeDisposable.add(dataManager.retrieveChore().subscribe(
                new Consumer<Chore>() {
                    @Override
                    public void accept(@NonNull final Chore chore) throws Exception {
                        writeLog("2");
                        //if the uri in the chore is local, save the file to db and update the img url to the db
                        if (chore.getResultImg() != null&&chore.getResultImg().split(":")[0].equals(AppConstants.LOCAL_URI_PREFIX))
                            compositeDisposable.add(dataManager.saveImage(Uri.parse(chore.getResultImg())).subscribe(
                                    new Consumer<Uri>() {
                                        @Override
                                        public void accept(@NonNull Uri uri) throws Exception {
                                            writeLog("3");
                                            writeLog("uri: "+uri.toString());
                                            chore.setResultImg(uri.toString());
                                            writeLog("4");
                                            dataManager.saveChore(chore);
                                            writeLog("5");
                                            compositeDisposable.clear();

                                        }
                                    }
                            )
                            );
                    }
                }
        )
        );
    }
    private boolean isNetworkAvailable(Context context) {
        writeLog("check intenet");
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void writeLog(String msg){
        Log.d(TAG,msg);
    }
}
