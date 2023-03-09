package com.example.midterm;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.midterm.databinding.FragmentCreateReviewBinding;
import com.example.midterm.models.Product;

import java.io.IOException;
import java.time.LocalDateTime;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CreateReviewFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PRODUCT = "product";

    private Product mProduct;

    public CreateReviewFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CreateReviewFragment newInstance(Product product) {
        CreateReviewFragment fragment = new CreateReviewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCT, product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProduct = (Product) getArguments().getSerializable(ARG_PRODUCT);
        }
    }

    FragmentCreateReviewBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreateReviewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Create Review");

        binding.textViewProductName.setText(mProduct.getName());
        binding.textViewProductPrice.setText(mProduct.getPrice());
        new MainActivity.DownloadImageTask(binding.imageViewProductIcon).execute(mProduct.getImg_url());

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String review = binding.editTextReview.getText().toString();

                String rating = "1";
                int checkedButtonID = binding.radioGroup.getCheckedRadioButtonId();
                if(binding.radioButtonLevel1.getId() == checkedButtonID) {
                    rating = "1";
                } else if(binding.radioButtonLevel2.getId() == checkedButtonID) {
                    rating = "2";
                } else if(binding.radioButtonLevel3.getId() == checkedButtonID) {
                    rating = "3";
                } else if(binding.radioButtonLevel4.getId() == checkedButtonID) {
                    rating = "4";
                } else if(binding.radioButtonLevel5.getId() == checkedButtonID) {
                    rating = "5";
                }

                postReview(review, rating);
            }
        });
   }

   private final OkHttpClient client = new OkHttpClient();

   void postReview(String review, String rating) {
        RequestBody formBody = new FormBody.Builder()
                .add("pid", mProduct.getPid())
                .add("review", review)
                .add("rating", rating)
                .build();

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/product/review")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.w("API", "Create Review API onFailure");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.w("API", "Create Review API onResponse");
                if(response.isSuccessful()) {
                    mListener.sendCompletedReview();
                }
            }
        });
   }

    interface CreateReviewListener {
        void sendCompletedReview();
    }

    CreateReviewListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (CreateReviewListener) context;
    }
}