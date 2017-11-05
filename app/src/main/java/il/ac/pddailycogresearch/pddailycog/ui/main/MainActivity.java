package il.ac.pddailycogresearch.pddailycog.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import il.ac.pddailycogresearch.pddailycog.R;
import il.ac.pddailycogresearch.pddailycog.ui.base.BaseActivity;


public class MainActivity extends BaseActivity<MainContract.Presenter> implements MainContract.View {
    @BindView(R.id.tv_user_info)
    TextView mTextView;


    @Override
    protected int getContentResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(@Nullable Bundle state) {

       // getPresenter().loadRssFragments();
    }

    @Override
    public void injectDependencies() {
        getActivityComponent().inject(this);
    }

    @OnClick(R.id.test_button)
    public void test(View view){
        getPresenter().loadRssFragments();
    }

    @Override
    public void onLoadRssFragments() {
        //TODO change name and functionality, also in contract...
        mTextView.setText("test!");
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
