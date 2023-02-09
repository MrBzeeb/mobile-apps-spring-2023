package com.example.assessment3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements WelcomeFragment.WelcomeFragmentListener, RegistrationFragment.RegistrationFragmentListener, ProfileFragment.ProfileFragmentListener, SetGenderFragment.SetGenderFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new WelcomeFragment())
                .commit();
    }

    @Override
    public void goToRegistration() {
        Log.d("beren", "goToRegistration Start");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new RegistrationFragment(), "registration-fragment")
                .addToBackStack(null)
                .commit();
        Log.d("beren", "goToRegistration End");
    }

    @Override
    public void goToSetGender() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SetGenderFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToProfile(Profile profile) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, ProfileFragment.newInstance(profile))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void closeProfile() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelSetGender() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sendGender(String gender) {
        RegistrationFragment fragment = (RegistrationFragment) getSupportFragmentManager().findFragmentByTag("registration-fragment");
        fragment.setGender(gender);
        getSupportFragmentManager().popBackStack();
    }
}