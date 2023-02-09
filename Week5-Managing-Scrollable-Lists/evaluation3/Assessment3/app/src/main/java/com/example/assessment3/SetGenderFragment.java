package com.example.assessment3;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.assessment3.databinding.FragmentSetGenderBinding;

public class SetGenderFragment extends Fragment {

    public SetGenderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSetGenderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Set Gender");

        binding.buttonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedID = binding.radioGroup.getCheckedRadioButtonId();
                RadioButton selectedButton = (RadioButton) binding.getRoot().findViewById(selectedID);
                String gender = selectedButton.getText().toString();

                mListener.sendGender(gender);
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.cancelSetGender();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SetGenderFragmentListener) context;
    }

    FragmentSetGenderBinding binding;

    SetGenderFragmentListener mListener;

    interface SetGenderFragmentListener {
        void cancelSetGender();
        void sendGender(String gender);
    }

}