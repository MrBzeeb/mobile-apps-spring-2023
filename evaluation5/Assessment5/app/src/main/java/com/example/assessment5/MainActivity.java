package com.example.assessment5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.assessment5.models.Forum;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener, RegisterFragment.RegisterListener, CreateForumFragment.CreateForumListener, ForumsFragment.ForumsFragmentListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: Check if the user is authenticated or no..
        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new LoginFragment())
                .commit();
    }

    @Override
    public void authSuccessful() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new ForumsFragment())
                .commit();
    }

    @Override
    public void gotoLogin() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new LoginFragment())
                .commit();
    }

    @Override
    public void gotoRegister() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new RegisterFragment())
                .commit();
    }

    @Override
    public void cancelForumCreate() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void completedForumCreate() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void logout() {

    }

    @Override
    public void gotoCreateForum() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new CreateForumFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoForumMessages(Forum forum) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new CreateForumFragment())
                .addToBackStack(null)
                .commit();
    }
}