package il.ac.pddailycogresearch.pddailycog.di.component;

import android.app.Application;
import android.content.Context;

import il.ac.pddailycogresearch.pddailycog.PDCApp;
import il.ac.pddailycogresearch.pddailycog.data.DataManager;
import il.ac.pddailycogresearch.pddailycog.data.DbHelper;
import il.ac.pddailycogresearch.pddailycog.data.SharedPrefsHelper;
import il.ac.pddailycogresearch.pddailycog.di.ApplicationContext;
import il.ac.pddailycogresearch.pddailycog.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;


/**
 * Created by janisharali on 08/12/16.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(PDCApp PDCApp);

    @ApplicationContext
    Context getContext();

    Application getApplication();

    DataManager getDataManager();

    SharedPrefsHelper getPreferenceHelper();

    DbHelper getDbHelper();

}
