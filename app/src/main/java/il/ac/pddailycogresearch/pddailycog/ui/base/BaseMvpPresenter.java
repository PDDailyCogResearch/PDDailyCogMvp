package il.ac.pddailycogresearch.pddailycog.ui.base;

/**
 * Created by שני on 31/10/2017.
 */

/**
 * Each presenter must implement this interface
 *
 * @param <V> MvpView for the presenter
 */
public interface BaseMvpPresenter<V extends BaseMvpView> {

    /**
     * Called when view attached to presenter
     *
     * @param view
     */
    void attach(V view);

    /**
     * Called when view is detached from presenter
     */
    void detach();

    /**
     * @return true if view is attached to presenter
     */
    boolean isAttached();
}