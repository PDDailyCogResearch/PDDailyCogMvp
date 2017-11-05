package il.ac.pddailycogresearch.pddailycog.ui.base;

/**
 * Created by שני on 31/10/2017.
 */

public class BasePresenter<V extends BaseMvpView> implements BaseMvpPresenter<V> {

    /**
     * Attached view
     */
    private V mView;


    @Override
    public void attach(V view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }

    @Override
    public boolean isAttached() {
        return mView != null;
    }

    public V getView() {
        return mView;
    }
}