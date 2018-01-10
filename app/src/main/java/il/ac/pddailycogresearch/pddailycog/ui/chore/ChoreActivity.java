package il.ac.pddailycogresearch.pddailycog.ui.chore;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
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

import com.jakewharton.rxbinding2.widget.RxTextView;

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
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

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
    @BindView(R.id.instr_sound_btn)
    Button instrSoundBtn;

    View currentBodyView;
    ArrayList<View> bodyViews = new ArrayList<>();

    private CompositeDisposable compositeDisposable =
            new CompositeDisposable();

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
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void setUp() {
        mPresenter.onViewInitialized();
        subscribeRxBindingListeners();
    }

    private void subscribeRxBindingListeners() {
        compositeDisposable.add(
                RxTextView.textChanges((EditText)bodyViews.get(Chore.PartsConstants.TEXT_INPUT-1))
                        .subscribe(new Consumer<CharSequence>() {
                            @Override
                            public void accept(@NonNull CharSequence text) throws Exception {
                                if(text.length()==0)
                                    choreNextBtn.setEnabled(false);
                                else
                                    choreNextBtn.setEnabled(true);

                            }
                        }
                        )
        );

    }

    @OnClick({R.id.chore_exit_btn, R.id.chore_instruction_btn, R.id.chore_help_btn, R.id.chore_next_btn,
            R.id.take_picture_btn, R.id.instr_sound_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.chore_exit_btn:
                mPresenter.onExitClick();
                break;
            case R.id.chore_instruction_btn:
                mPresenter.onInstructionBtnClick();
                break;
            case R.id.chore_help_btn:
                localFoo();
                mPresenter.foo();
                break;
            case R.id.chore_next_btn:
                mPresenter.onNextClick();
                break;
            case R.id.take_picture_btn:
                mPresenter.onTakePictureClick();
                break;
            case R.id.instr_sound_btn:
                localFoo();
                break;
        }
    }

    private void localFoo() {
        MediaPlayer mpori;

        mpori= MediaPlayer.create(getApplicationContext(), R.raw.temp_audio_instr);
        mpori.start();
    }


    @Override
    public void replaceBodyViews(int viewIdx) {
        choreHeadlineTextview.setText(getResources().getStringArray(R.array.chore_headers)[viewIdx]);
        ViewGroupUtils.replaceViewInLinearLayout(currentBodyView, bodyViews.get(viewIdx));
        handleViewsSpecificRequirements(viewIdx+1);
        currentBodyView = bodyViews.get(viewIdx);
    }

    private void handleViewsSpecificRequirements(int viewNumber) {
        if(viewNumber==Chore.PartsConstants.INSTRUCTION) {
            choreInstructionBtn.setVisibility(View.GONE);
            instrSoundBtn.setVisibility(View.VISIBLE);
        }
        else {
            choreInstructionBtn.setVisibility(View.VISIBLE);
            instrSoundBtn.setVisibility(View.GONE);
        }

        if(viewNumber==Chore.PartsConstants.TAKE_PICTURE) {
            takePictureBtn.setVisibility(View.VISIBLE);
            if (imgUri == null)
                choreNextBtn.setEnabled(false);
        }
        else {
            takePictureBtn.setVisibility(View.GONE);
            choreNextBtn.setEnabled(true);
        }

        if(viewNumber==Chore.PartsConstants.TEXT_INPUT) {
            choreNextBtn.setEnabled(false);
        }
    }

    @Override
    public void setChoreInstruction(Integer choreNum) {
        choreInstructionTextview.setText(getResources().getStringArray(R.array.chore_instructions)[choreNum - 1]);
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
            ImageView imageView = (ImageView) bodyViews.get(Chore.PartsConstants.TAKE_PICTURE - 1);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1080)); //TODO change hard-coded
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE ) {
            ImageView imageView = (ImageView) bodyViews.get(Chore.PartsConstants.TAKE_PICTURE - 1);
            if(resultCode==RESULT_OK) {
                ImageUtils.setPic(imageView, imgAbsolutePath);
                choreNextBtn.setEnabled(true);
            }
            else
                imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0)); //TODO change hard-coded

        }
    }
    //endregion
}
