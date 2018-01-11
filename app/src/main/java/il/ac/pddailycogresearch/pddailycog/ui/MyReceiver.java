package il.ac.pddailycogresearch.pddailycog.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import javax.inject.Inject;

import il.ac.pddailycogresearch.pddailycog.PDCApp;
import il.ac.pddailycogresearch.pddailycog.R;
import il.ac.pddailycogresearch.pddailycog.data.DataManager;
import il.ac.pddailycogresearch.pddailycog.data.model.Chore;
import il.ac.pddailycogresearch.pddailycog.utils.CommonUtils;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MyReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(final Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
       // CommonUtils.createAlertDialog(context, R.string.app_name,R.string.logged_in).subscribe();

        DataManager dataManager=((PDCApp)context.getApplicationContext()).getComponent().getDataManager();
        dataManager.retrieveChore().doOnSubscribe(
                new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.d("MY_RECEIVER","on subscribe");
                    }
                }
        ).subscribe(
                new Consumer<Chore>() {
                    @Override
                    public void accept(@NonNull Chore chore) throws Exception {
                        Log.d("MY_RECEIVER","img url "+chore.getResultImg());
                    }
                }
        );
        Log.d("MY_RECEIVER","after subscribe");
        Toast.makeText(context,"recieved: \n"+CommonUtils.getTimeStamp(),Toast.LENGTH_SHORT).show();
    }
}
