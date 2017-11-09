package il.ac.pddailycogresearch.pddailycog;

import android.app.Application;
import android.content.Context;

import il.ac.pddailycogresearch.pddailycog.data.DataManager;
import il.ac.pddailycogresearch.pddailycog.di.component.ApplicationComponent;
import il.ac.pddailycogresearch.pddailycog.di.component.DaggerApplicationComponent;
import il.ac.pddailycogresearch.pddailycog.di.module.ApplicationModule;

import javax.inject.Inject;

/**
 * Created by janisharali on 25/12/16.
 */

public class PDCApp extends Application {

    protected ApplicationComponent applicationComponent;

    @Inject
    DataManager dataManager;

    public static PDCApp get(Context context) {
        return (PDCApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent
                                    .builder()
                                    .applicationModule(new ApplicationModule(this))
                                    .build();
        applicationComponent.inject(this);
    }

    public ApplicationComponent getComponent(){
        return applicationComponent;
    }
}
