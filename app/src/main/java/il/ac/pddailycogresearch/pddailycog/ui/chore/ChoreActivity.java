package il.ac.pddailycogresearch.pddailycog.ui.chore;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import il.ac.pddailycogresearch.pddailycog.R;
import il.ac.pddailycogresearch.pddailycog.data.model.Chore;
import il.ac.pddailycogresearch.pddailycog.ui.base.BaseActivity;
import il.ac.pddailycogresearch.pddailycog.utils.ImageUtils;
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
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1080));
        bodyViews.add(imageView);
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
                mPresenter.onInstructionBtnClick();
                break;
            case R.id.chore_help_btn:
                dispatchTakePictureIntent();
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


    @Override
    public String getInputText() {
        //TODO refactor somehow, the usage in presenter too
        EditText editText = (EditText)bodyViews.get(Chore.PartsConstants.TEXT_INPUT-1);
        return String.valueOf(editText.getText());
    }
    //region take pictures, should be refactored
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String imgAbsolutePath;
    private Uri imgUri;

    @Override
    public Uri getImgUri() {
        return imgUri;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = ImageUtils.createTakePictureIntent(this);
        imgAbsolutePath = takePictureIntent.getStringExtra(ImageUtils.IMAGE_ABSOLUTE_PATH);
        Bundle extras = takePictureIntent.getExtras();
        imgUri=(Uri) extras.get(MediaStore.EXTRA_OUTPUT);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
              //  && data != null && data.getData() != null )
        {
            ImageUtils.setPic(((ImageView)bodyViews.get(1)),imgAbsolutePath);
          //  mPresenter.foo(imgUri);

        }
    }
    //endregion
}
