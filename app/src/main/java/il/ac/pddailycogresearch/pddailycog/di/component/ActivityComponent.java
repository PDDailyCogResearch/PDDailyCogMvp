package il.ac.pddailycogresearch.pddailycog.di.component;

import il.ac.pddailycogresearch.pddailycog.ui.chore.ChoreActivity;
import il.ac.pddailycogresearch.pddailycog.ui.login.LoginActivity;
import il.ac.pddailycogresearch.pddailycog.ui.main.MainActivity;
import il.ac.pddailycogresearch.pddailycog.di.PerActivity;
import il.ac.pddailycogresearch.pddailycog.di.module.ActivityModule;

import dagger.Component;

/**
 * Created by janisharali on 08/12/16.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(LoginActivity activity);

    void inject(ChoreActivity activity);

}