package il.ac.pddailycogresearch.pddailycog.di.module;

import android.app.Activity;
import android.content.Context;

import javax.inject.Singleton;

import il.ac.pddailycogresearch.pddailycog.di.ActivityContext;

import dagger.Module;
import dagger.Provides;
import il.ac.pddailycogresearch.pddailycog.di.PerActivity;
import il.ac.pddailycogresearch.pddailycog.ui.main.MainContract;
import il.ac.pddailycogresearch.pddailycog.ui.main.MainPresenter;

/**
 * Created by janisharali on 08/12/16.
 */

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

/*
    private Context mContext;

    public ActivityModule(Context context) {
        mContext = context;
    }*/


    @Provides
    @PerActivity
    MainContract.Presenter providesMainPresenter() {
        return new MainPresenter();
    }
}
