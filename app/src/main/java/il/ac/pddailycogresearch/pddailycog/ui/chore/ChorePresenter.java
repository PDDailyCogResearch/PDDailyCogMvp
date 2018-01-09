/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package il.ac.pddailycogresearch.pddailycog.ui.chore;

import android.net.Uri;

import javax.inject.Inject;

import il.ac.pddailycogresearch.pddailycog.R;
import il.ac.pddailycogresearch.pddailycog.data.DataManager;
import il.ac.pddailycogresearch.pddailycog.data.model.Chore;
import il.ac.pddailycogresearch.pddailycog.ui.base.BasePresenter;
import il.ac.pddailycogresearch.pddailycog.utils.AppConstants;
import il.ac.pddailycogresearch.pddailycog.utils.rx.SchedulerProvider;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * Created by janisharali on 27/01/17.
 */

public class ChorePresenter<V extends ChoreMvpView> extends BasePresenter<V>
        implements ChoreMvpPresenter<V> {

    private static final String TAG = "ChorePresenter";
    private Chore currentChore;
    private Boolean isInstrcClicked=false;

    @Inject
    public ChorePresenter(DataManager dataManager,
                          SchedulerProvider schedulerProvider,
                          CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }


    @Override
    public void onViewInitialized() {
        retrieveChore();
    }

    private void retrieveChore() {
        getDataManager().retrieveChore().doOnSubscribe(
                new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        getMvpView().showLoading();
                    }
                }
        ).subscribe(
                new Consumer<Chore>() {
                    @Override
                    public void accept(@NonNull Chore chore) throws Exception {
                        updateCurrentChore(chore);
                        getMvpView().hideLoading();
                        getMvpView().showMessage("get! chore num: "+chore.getChoreNum());
                    }
                }
        );
    }

    private void updateCurrentChore(Chore chore) {
        if(chore.isCompleted()){
            int nextChore = chore.getChoreNum()+1;
            if(nextChore<= AppConstants.CHORES_AMOUNT)
                currentChore = new Chore(nextChore);
            else
                getMvpView().onError(R.string.no_more_chores);
        }
        else
            this.currentChore = chore;
        getMvpView().setChoreInstruction(currentChore.getChoreNum());
        viewCurrentPart();
    }

    private void viewCurrentPart() {
        int currentViewIdx = currentChore.getCurrentPartNum()-1;
        getMvpView().replaceBodyViews(currentViewIdx);
    }

    @Override
    public void onNextClick() {
        if(isInstrcClicked) {
            isInstrcClicked=false;
            viewCurrentPart();
        }
        else {
            completePart();
            moveToNextPart();
        }
    }

    private void completePart() {
        switch (currentChore.getCurrentPartNum()) {
            case Chore.PartsConstants.TAKE_PICTURE:
                saveImage(getMvpView().getImgUri());
                break;
            case Chore.PartsConstants.TEXT_INPUT:
                currentChore.setResultText(getMvpView().getInputText());
                break;
        }

    }

    private void moveToNextPart() {
        int nextPart =  currentChore.getCurrentPartNum()+1;
        if (nextPart <= Chore.PartsConstants.PARTS_AMOUNT) {
            currentChore.setCurrentPartNum(nextPart);
            viewCurrentPart();
        }
        else
            finishChore();
    }

    @Override
    public void foo() {
        retrieveChore();
    }

    private void saveImage(Uri imageUri) {
        if(imageUri!=null)
        getDataManager().saveImage(imageUri).subscribe(
                new Consumer<Uri>() {
                    @Override
                    public void accept(@NonNull Uri uri) throws Exception {
                        currentChore.setResultImg(uri.toString());
                    }
                }
        );
    }

    @Override
    public void onInstructionBtnClick() {
        currentChore.increaseInstrcClicksNum();
        isInstrcClicked=true;
        getMvpView().replaceBodyViews(Chore.PartsConstants.INSTRUCTION-1);
    }

    @Override
    public void onTakePictureClick() {
        currentChore.increaseTakePicClickNum();
        getMvpView().dispatchTakePictureIntent();
    }

    private void finishChore() {
        //TODO UI
        getDataManager().saveChore(currentChore);
    }

}
