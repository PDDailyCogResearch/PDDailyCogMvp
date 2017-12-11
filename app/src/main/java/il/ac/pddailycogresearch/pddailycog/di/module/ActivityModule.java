package il.ac.pddailycogresearch.pddailycog.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import il.ac.pddailycogresearch.pddailycog.di.ActivityContext;

import dagger.Module;
import dagger.Provides;
import il.ac.pddailycogresearch.pddailycog.di.PerActivity;
import il.ac.pddailycogresearch.pddailycog.ui.chore.ChoreMvpPresenter;
import il.ac.pddailycogresearch.pddailycog.ui.chore.ChoreMvpView;
import il.ac.pddailycogresearch.pddailycog.ui.chore.ChorePresenter;
import il.ac.pddailycogresearch.pddailycog.ui.login.LoginMvpPresenter;
import il.ac.pddailycogresearch.pddailycog.ui.login.LoginMvpView;
import il.ac.pddailycogresearch.pddailycog.ui.login.LoginPresenter;
import il.ac.pddailycogresearch.pddailycog.ui.main.MainMvpPresenter;
import il.ac.pddailycogresearch.pddailycog.ui.main.MainMvpView;
import il.ac.pddailycogresearch.pddailycog.ui.main.MainPresenter;
import il.ac.pddailycogresearch.pddailycog.utils.rx.AppSchedulerProvider;
import il.ac.pddailycogresearch.pddailycog.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by janisharali on 08/12/16.
 */

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }



    @Provides
    @PerActivity
    LoginMvpPresenter<LoginMvpView> provideLoginPresenter(
            LoginPresenter<LoginMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> provideMainPresenter(
            MainPresenter<MainMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ChoreMvpPresenter<ChoreMvpView> provideChorePresenter(
            ChorePresenter<ChoreMvpView> presenter) {
        return presenter;
    }


    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
        return new LinearLayoutManager(activity);
    }
}

