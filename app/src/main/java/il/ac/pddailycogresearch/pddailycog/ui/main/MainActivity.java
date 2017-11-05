package il.ac.pddailycogresearch.pddailycog.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import il.ac.pddailycogresearch.pddailycog.R;
import il.ac.pddailycogresearch.pddailycog.ui.base.BaseActivity;


public class MainActivity extends BaseActivity<MainContract.Presenter> implements MainContract.View {


    @Override
    protected int getContentResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(@Nullable Bundle state) {
        getPresenter().loadRssFragments();
    }

    @Override
    public void injectDependencies() {
        getActivityComponent().inject(this);
    }

    @Override
    public void onLoadRssFragments() {
        //TODO change name and functionality, also in contract...
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


}
