package com.example.android.e7gzlykora.views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.e7gzlykora.R;
import com.example.android.e7gzlykora.Utils.FragmentUtils;
import com.example.android.e7gzlykora.Utils.PrefUtil;
import com.example.android.e7gzlykora.databinding.ActivityLoginOwnerBinding;
import com.example.android.e7gzlykora.viewmodels.LoginOwnerFragmentViewModel;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class Loginowner extends Fragment {

    private ActivityLoginOwnerBinding binding;
    private static final String EMAIL = "email";
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "Loginowner";
    private LoginOwnerFragmentViewModel viewModel;
    private PrefUtil prefUtil;
    private GoogleApiClient mGoogleApiClient;
    private CallbackManager callbackManager;
    public Loginowner() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.setContentView(Objects.requireNonNull(getActivity()), R.layout.activity_login_owner);
        viewModel = ViewModelProviders.of(getActivity()).get(LoginOwnerFragmentViewModel.class);
        callbackManager = CallbackManager.Factory.create();
        prefUtil = new PrefUtil(this.getActivity());
        binding.setLifecycleOwner(this);
        binding.setViewModel(new LoginOwnerFragmentViewModel());
        binding.executePendingBindings();
        binding.loginButton.setFragment(this);
        binding.loginButton.setPermissions(Arrays.asList(
                "email",
                "groups_access_member_info",
                "user_age_range",
                "user_birthday",
                "user_friends",
                "user_gender",
                "user_link",
                "public_profile"));
        binding.loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess: " + loginResult);
                String accessToken = loginResult.getAccessToken().getToken();

                // save accessToken to SharedPreference
                prefUtil.saveAccessToken(accessToken);

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        (jsonObject, response) -> {

                            // Getting FB User Data
                            Bundle facebookData = getFacebookData(jsonObject);
                            Profile profile = Profile.getCurrentProfile();
                            FragmentUtils.addFragment(getActivity(), new OwnerPendingBookings(), R.id.loginOwner_layout, false);

                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,first_name,last_name,email,gender");
                request.setParameters(parameters);
                request.executeAsync();
            }


            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        viewModel.getUser().observe(getViewLifecycleOwner(), auth -> {
            if (auth != null) {
                FragmentUtils.addFragment(getActivity(), new OwnerPendingBookings(), R.id.loginOwner_layout, false);
            } else if (auth.getMobile().equals("")) {
                Toast.makeText(getActivity(), "No User found with this credentials", Toast.LENGTH_SHORT).show();
            }
        });
        return inflater.inflate(R.layout.activity_login_owner, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        onClick();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void init() {
        String userName = Objects.requireNonNull(getActivity()).getIntent().getStringExtra("UserName");
        binding.UserName.setText(userName);
    }

    private void onClick() {
        binding.googleSignInButton.setOnClickListener(view -> signInWithGoogle());


        binding.buttonLoginUser.setOnClickListener(v -> {
            if (binding.Password.getText().toString().isEmpty() || binding.Password.getText().toString().length() < 6) {
                binding.Password.setError("Enter a valid Password");
                binding.Password.requestFocus();

            } else {

                com.example.android.e7gzlykora.model.Auth.getInstance().setUserName(binding.UserName.getText().toString());
                com.example.android.e7gzlykora.model.Auth.getInstance().setPassword(binding.Password.getText().toString());
                com.example.android.e7gzlykora.model.Auth.getInstance().setUserType(102);
                viewModel.getUserApi();
            }


        });
        binding.buttonBackLogin.setOnClickListener(v -> FragmentUtils.addFragment(getActivity(), new Identity(), R.id.loginOwner_layout, false));
        binding.linkToSignUp.setOnClickListener(v -> FragmentUtils.addFragment(getActivity(), new RegisterOwner(), R.id.loginOwner_layout, false));
    }

    private Bundle getFacebookData(JSONObject object) {
        Bundle bundle = new Bundle();

        try {
            String id = object.getString("id");
            URL profile_pic;
            try {
                profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?type=large");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));


            prefUtil.saveFacebookUserInfo(object.getString("first_name"),
                    object.getString("last_name"), object.getString("email"),
                    object.getString("gender"), profile_pic.toString());

        } catch (Exception e) {
            Log.d(TAG, "BUNDLE Exception : " + e.toString());
        }

        return bundle;
    }

    private Bundle getGoogleData(GoogleSignInAccount account) {
        Bundle bundle = new Bundle();
        try {
            prefUtil.saveFacebookUserInfo(account.getGivenName(),
                    "", account.getEmail(),
                    "", Objects.requireNonNull(account.getPhotoUrl()).getPath());

        } catch (Exception e) {
            Log.d(TAG, "BUNDLE Exception : " + e.toString());
        }

        return bundle;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()) {
                final GoogleApiClient client = mGoogleApiClient;

                GoogleSignInAccount account = result.getSignInAccount();
                assert account != null;
                getGoogleData(account);
                FragmentUtils.addFragment(getActivity(), new OwnerPendingBookings(), R.id.loginOwner_layout, false);


            } else {
                Toast.makeText(getActivity(), "Try Again Later", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Try Again Later", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void signInWithGoogle() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(Objects.requireNonNull(getActivity()))
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        final Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

}

