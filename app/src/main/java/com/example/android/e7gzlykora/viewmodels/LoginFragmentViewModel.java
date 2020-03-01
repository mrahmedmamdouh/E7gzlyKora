package com.example.android.e7gzlykora.viewmodels;

import com.example.android.e7gzlykora.model.Auth;
import com.example.android.e7gzlykora.model.AuthRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

public class LoginFragmentViewModel extends ViewModel {

    private MediatorLiveData<Auth> authMediatorLiveData = new MediatorLiveData<>();
    private AuthRepository authRepository;

    public LoginFragmentViewModel() {
        authRepository = AuthRepository.getAuthRepository();
    }

    public LiveData<Auth> getUser() {
        return authMediatorLiveData;
    }

    public void getUserApi() {
        verifyUser();
    }

    private void verifyUser() {
        final LiveData<Auth> repositorySource = authRepository.verifyUser();
        authMediatorLiveData.addSource(repositorySource, auth -> {
            if (auth != null) {
                authMediatorLiveData.setValue(auth);
                authMediatorLiveData.removeSource(repositorySource);
            } else {
                authMediatorLiveData.removeSource(repositorySource);
            }

        });

    }

}