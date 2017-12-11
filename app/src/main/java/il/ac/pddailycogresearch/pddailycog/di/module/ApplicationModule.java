package il.ac.pddailycogresearch.pddailycog.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import il.ac.pddailycogresearch.pddailycog.data.AppDataManager;
import il.ac.pddailycogresearch.pddailycog.data.DataManager;
import il.ac.pddailycogresearch.pddailycog.data.DbHelper;
import il.ac.pddailycogresearch.pddailycog.data.FirebaseDbHelper;
import il.ac.pddailycogresearch.pddailycog.di.ApplicationContext;
import il.ac.pddailycogresearch.pddailycog.di.DatabaseInfo;

import dagger.Module;
import dagger.Provides;

/**
 * Created by janisharali on 25/12/16.
 */

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application app) {
        mApplication = app;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return "demo-dagger.db";
    }

    @Provides
    @DatabaseInfo
    Integer provideDatabaseVersion() {
        return 2;
    }

    @Provides
    SharedPreferences provideSharedPrefs() {
        return mApplication.getSharedPreferences("demo-prefs", Context.MODE_PRIVATE);
    }
    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(FirebaseDbHelper firebaseDbHelper) {
        return firebaseDbHelper;
    }


}
