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

package il.ac.pddailycogresearch.pddailycog.ui.login;

/*import il.ac.pddailycogresearch.pddailycog.data.network.model.LoginRequest;
import il.ac.pddailycogresearch.pddailycog.data.network.model.LoginResponse;
import com.androidnetworking.error.ANError;*/

import il.ac.pddailycogresearch.pddailycog.R;
import il.ac.pddailycogresearch.pddailycog.data.DataManager;
import il.ac.pddailycogresearch.pddailycog.ui.base.BasePresenter;
import il.ac.pddailycogresearch.pddailycog.utils.CommonUtils;
import il.ac.pddailycogresearch.pddailycog.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by janisharali on 27/01/17.
 */

public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V>
        implements LoginMvpPresenter<V> {

    private static final String TAG = "LoginPresenter";

    @Inject
    public LoginPresenter(DataManager dataManager,
                          SchedulerProvider schedulerProvider,
                          CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onSignUpClick(final String username, final String password, String confirmPassword) {
        //validate email and password
        if (username == null || username.isEmpty()) {
            getMvpView().onError(R.string.empty_username);
            return;
        }
        if (password == null || password.isEmpty()||confirmPassword==null||confirmPassword.isEmpty()) {
            getMvpView().onError(R.string.empty_password);
            return;
        }
        if(!password.equals(confirmPassword)){
            getMvpView().onError(R.string.unmatch_passwords);
            return;
        }

        getDataManager().signup(username,password).doOnSubscribe(
                new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        getMvpView().showLoading();
                    }
                }
        )
                .subscribe(
                        new Consumer<Boolean>() {
                            @Override
                            public void accept(@NonNull Boolean isSigned) throws Exception {
                                getMvpView().hideLoading();
                                if (isSigned)
                                    getMvpView().openMainActivity();
                                else
                                    getMvpView().onError(R.string.login_failed);

                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                doLoginWhenSignUpFail(username, password);
                            }
                        }
                );

    }

    private void doLoginWhenSignUpFail(String username, String password) {
        getDataManager().login(username,password).subscribe(
                new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean isLogged) throws Exception {
                        getMvpView().hideLoading();
                        if (isLogged) {
                            getMvpView().showMessage(R.string.logged_in);
                            getMvpView().openMainActivity();
                        }
                        else
                            getMvpView().onError(R.string.login_failed);

                    }
                },
                new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        getMvpView().hideLoading();
                        getMvpView().onError(throwable.getMessage());
                    }
                }
        );
    }

}
