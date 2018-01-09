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
    @BindView(R.id.take_picture_btn)
    Button takePictureBtn;

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

    @OnClick({R.id.chore_exit_btn, R.id.chore_instruction_btn, R.id.chore_help_btn, R.id.chore_next_btn,R.id.take_picture_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.chore_exit_btn:
                break;
            case R.id.chore_instruction_btn:
                mPresenter.onInstructionBtnClick();
                break;
            case R.id.chore_help_btn:
                mPresenter.foo();
                break;
            case R.id.chore_next_btn:
                mPresenter.onNextClick();
                break;
            case R.id.take_picture_btn:
                mPresenter.onTakePictureClick();
                break;
        }
    }

    @Override
    public void replaceBodyViews(int viewIdx) {
        choreHeadlineTextview.setText(getResources().getStringArray(R.array.chore_headers)[viewIdx]);
        ViewGroupUtils.replaceViewInLinearLayout(currentBodyView, bodyViews.get(viewIdx));
        if (viewIdx == Chore.PartsConstants.TAKE_PICTURE - 1) {
            takePictureBtn.setVisibility(View.VISIBLE);
            if(imgUri==null)
               choreNextBtn.setEnabled(false);
        }
        else
            takePictureBtn.setVisibility(View.GONE);
        currentBodyView = bodyViews.get(viewIdx);
    }

    @Override
    public void setChoreInstruction(Integer choreNum) {
       choreInstructionTextview.setText(getResources().getStringArray(R.array.chore_instructions)[choreNum-1]);
    }


    @Override
    public String getInputText() {
        //TODO refactor somehow, the usage in presenter too
        EditText editText = (EditText) bodyViews.get(Chore.PartsConstants.TEXT_INPUT - 1);
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

    @Override
    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = ImageUtils.createTakePictureIntent(this);
        imgAbsolutePath = takePictureIntent.getStringExtra(ImageUtils.IMAGE_ABSOLUTE_PATH);
        Bundle extras = takePictureIntent.getExtras();
        imgUri = (Uri) extras.get(MediaStore.EXTRA_OUTPUT);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            ImageView imageView = (ImageView) bodyViews.get(Chore.PartsConstants.TAKE_PICTURE-1);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1080)); //TODO change hard-coded
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            ImageView imageView = (ImageView) bodyViews.get(Chore.PartsConstants.TAKE_PICTURE-1);
            ImageUtils.setPic(imageView, imgAbsolutePath);

            choreNextBtn.setEnabled(true);
        }
    }
    //endregion
}
