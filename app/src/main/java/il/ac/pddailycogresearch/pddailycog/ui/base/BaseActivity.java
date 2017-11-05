package il.ac.pddailycogresearch.pddailycog.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import  il.ac.pddailycogresearch.pddailycog.di.component.ActivityComponent;
import  il.ac.pddailycogresearch.pddailycog.di.component.DaggerActivityComponent;
import  il.ac.pddailycogresearch.pddailycog.di.module.ActivityModule;

/**
 * Created by שני on 31/10/2017.
 */

public abstract class BaseActivity<T extends BaseMvpPresenter> extends AppCompatActivity implements BaseMvpView {

    /**
     * Injected presenter
     */
    @Inject
    T mPresenter;

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentResource());
        ButterKnife.bind(this);
        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .build();
        injectDependencies();
        mPresenter.attach(this);
        init(savedInstanceState);
    }

    /**
     * Detach presenter
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }


    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    /**
     * Getter for the presenter
     *
     * @return the present for the activity
     */
    public T getPresenter() {
        return mPresenter;
    }

    /**
     * Layout resource to be inflated
     *
     * @return layout resource
     */
    @LayoutRes
    protected abstract int getContentResource();

    /**
     * Initializations
     */
    protected abstract void init(@Nullable Bundle state);

    /**
     * Injecting dependencies
     */
    protected abstract void injectDependencies();

}