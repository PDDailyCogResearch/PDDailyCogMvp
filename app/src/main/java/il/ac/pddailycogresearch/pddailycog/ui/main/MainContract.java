package il.ac.pddailycogresearch.pddailycog.ui.main;

import il.ac.pddailycogresearch.pddailycog.ui.base.BaseMvpPresenter;
import il.ac.pddailycogresearch.pddailycog.ui.base.BaseMvpView;

/**
 * Created by faruktoptas on 28/01/17.
 */

public interface MainContract {

    // User actions. Presenter will implement
    interface Presenter extends BaseMvpPresenter<MainContract.View>{
        void loadRssFragments();
    }

    // Action callbacks. Activity/Fragment will implement
    interface View extends BaseMvpView {
        void onLoadRssFragments();
    }

}
