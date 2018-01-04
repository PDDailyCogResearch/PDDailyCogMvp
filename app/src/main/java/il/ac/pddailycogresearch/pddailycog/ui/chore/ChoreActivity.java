package il.ac.pddailycogresearch.pddailycog.ui.chore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import il.ac.pddailycogresearch.pddailycog.R;
import il.ac.pddailycogresearch.pddailycog.ui.base.BaseActivity;
import il.ac.pddailycogresearch.pddailycog.ui.login.LoginActivity;
import il.ac.pddailycogresearch.pddailycog.utils.ViewGroupUtils;

public class ChoreActivity extends BaseActivity implements ChoreMvpView {


    @Inject
    ChoreMvpPresenter<ChoreMvpView> mPresenter;

    @BindView(R.id.chore_headline_textview)
    TextView choreHeadlineTextview;
    @BindView(R.id.chore_instruction_textview)
    TextView choreInstructionTextview;

    @BindView(R.id.chore_help_btn)
    Button choreHelpBtn;
    @BindView(R.id.chore_next_btn)
    Button choreNextBtn;
    @BindView(R.id.chore_exit_btn)
    Button choreExitBtn;
    @BindView(R.id.chore_instruction_btn)
    Button choreInstructionBtn;

    View currentBodyView;
    ArrayList<View> bodyViews = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chore);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        initilizeBodyViews();

        mPresenter.onAttach(this);

        setUp();
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ChoreActivity.class);
        return intent;
    }

    private void initilizeBodyViews() {
        currentBodyView = choreInstructionTextview;
        bodyViews.add(choreInstructionTextview);
        bodyViews.add(new ImageView(this));
        bodyViews.add(new ImageView(this));
        bodyViews.add(new EditText(this));
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp() {
        mPresenter.onViewInitialized();
    }

    @OnClick({R.id.chore_exit_btn, R.id.chore_instruction_btn, R.id.chore_help_btn, R.id.chore_next_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.chore_exit_btn:
                break;
            case R.id.chore_instruction_btn:
                mPresenter.fa();
                break;
            case R.id.chore_help_btn:
                mPresenter.foo();
                break;
            case R.id.chore_next_btn:
                mPresenter.onNextClick();
                break;
        }
    }

    @Override
    public void replaceBodyViews(int viewIdx) {
        ViewGroupUtils.replaceViewInLinearLayout(currentBodyView,bodyViews.get(viewIdx));
        currentBodyView = bodyViews.get(viewIdx);
    }
}
