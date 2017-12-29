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

import javax.inject.Inject;

import il.ac.pddailycogresearch.pddailycog.data.DataManager;
import il.ac.pddailycogresearch.pddailycog.data.model.Chore;
import il.ac.pddailycogresearch.pddailycog.ui.base.BasePresenter;
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

    @Inject
    public ChorePresenter(DataManager dataManager,
                          SchedulerProvider schedulerProvider,
                          CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }


    @Override
    public void onViewInitialized() {
        retrieveChore();
    //    Chore currentChore = getDataManager().getCurrentChore();
    //    getMvpView().replaceBodyViews(currentChore.getCurrentPartNum().ordinal());
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
                        getMvpView().hideLoading();
                        getMvpView().showMessage("get! chore num: "+chore.getChoreNum());
                    }
                }
        );
    }

    @Override
    public void onNextClick() {
       /* Chore.ChoreParts nextPart =  getDataManager().getCurrentChore().getCurrentPartNum().next();
        if (nextPart != null) {
            getDataManager().getCurrentChore().setCurrentPartNum(nextPart);
            getMvpView().replaceBodyViews(nextPart.ordinal());
        }
        else
            finishChore();*/
        int nextPart =  getDataManager().getCurrentChore().getCurrentPartNum()+1;
        if (nextPart <=3) {
            getDataManager().getCurrentChore().setCurrentPartNum(nextPart);
            getMvpView().replaceBodyViews(nextPart);
        }
        else
            finishChore();

    }

    @Override
    public void foo() {
        getDataManager().logout();
    }

    private void finishChore() {
        //TODO UI
        getDataManager().saveCurrentChore();
    }
}
