package com.example.android.e7gzlykora.viewmodels;

import com.example.android.e7gzlykora.model.AuthRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

public class RegisterOwnerViewModel extends ViewModel {


    private AuthRepository authRepository;
    private MediatorLiveData<String> registerResponse = new MediatorLiveData<>();

    public RegisterOwnerViewModel() {
        authRepository = AuthRepository.getAuthRepository();
    }


    public void createUser() {
        registerUser();
    }

    public LiveData<String> getRegisterResponse() {
        return registerResponse;
    }

    private void registerUser() {
        final LiveData<String> registerApiResponse = authRepository.registerUser();
        registerResponse.addSource(registerApiResponse, response -> {
            if (response != null) {
                registerResponse.setValue(response);
                registerResponse.removeSource(registerApiResponse);
            } else {
                registerResponse.removeSource(registerApiResponse);
            }
        });

    }
}
