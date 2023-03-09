package com.example.midterm;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.midterm.databinding.FragmentReviewsBinding;
import com.example.midterm.models.Product;
import com.example.midterm.models.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ReviewsFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PRODUCT = "product";

    private Product mProduct;

    public ReviewsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ReviewsFragment newInstance(Product product) {
        ReviewsFragment fragment = new ReviewsFragment();
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

    FragmentReviewsBinding binding;
    ArrayList<Review> mReviews = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReviewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Product Reviews");

        binding.textViewProductName.setText(mProduct.getName());
        binding.textViewProductPrice.setText(mProduct.getPrice());
        new MainActivity.DownloadImageTask(binding.imageViewProductIcon).execute(mProduct.getImg_url());

        getReviews();

        binding.buttonCreateReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.sendCreateReview(mProduct);
            }
        });
    }

    private final OkHttpClient client = new OkHttpClient();

    void getReviews () {
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/product/reviews/" + mProduct.getPid())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.w("API", "API onFailure");
                Log.w("API", e.toString());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.w("API", "API onResponse");
                if(response.isSuccessful()) {
                    // Get response body
                    String body = response.body().string();

                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        JSONArray jsonArray = jsonObject.optJSONArray("reviews");

                        // Parse JSON to list
                        for(int i = 0; i < Objects.requireNonNull(jsonArray).length(); i++) {
                            JSONObject jsonData = jsonArray.optJSONObject(i);

                            Review newReview = new Review(jsonData);

                            newReview.setReview(jsonData.getString("review"));
                            newReview.setCreated_at(jsonData.getString("created_at"));
                            newReview.setRating(jsonData.getString("rating"));

                            mReviews.add(newReview);
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    // Checking products were imported correctly
//                    Log.w("API", mReviews.toString());
//                    for (int i = 0; i < mReviews.size(); i++) {
//                        // mProducts list to list view
//                        Log.w("API", mReviews.get(i).getReview());
//                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Adapter for list of products to list view
                            ReviewAdapter adapter = new ReviewAdapter(getActivity(), mReviews);
                            binding.listView.setAdapter(adapter);
                        }
                    });

                }
            }
        });

    }

    class ReviewAdapter extends ArrayAdapter<Review> {
        public ReviewAdapter(@NonNull Context context, @NonNull List<Review> objects) {
            super(context, R.layout.row_item_review, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.row_item_review, parent, false);
            }

            TextView textViewReview = convertView.findViewById(R.id.textViewReview);
            TextView textViewReviewDate = convertView.findViewById(R.id.textViewReviewDate);
            ImageView imageViewReviewRating = convertView.findViewById(R.id.imageViewReviewRating);

            Review review = getItem(position);

            textViewReview.setText(review.getReview());
            textViewReviewDate.setText(review.getCreated_at());

            Drawable stars = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.stars_1, getContext().getTheme());

            switch (Integer.parseInt(review.getRating())) {
                case 1:
                    stars = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.stars_1, getContext().getTheme());
                    break;
                case 2:
                    stars = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.stars_2, getContext().getTheme());
                    break;
                case 3:
                    stars = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.stars_3, getContext().getTheme());
                    break;
                case 4:
                    stars = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.stars_4, getContext().getTheme());
                    break;
                case 5:
                    stars = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.stars_5, getContext().getTheme());
                    break;
            }

            imageViewReviewRating.setImageDrawable(stars);

            return convertView;
        }
    }

    interface ReviewListener {
        void sendCreateReview(Product product);
    }

    ReviewListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (ReviewListener) context;
    }
}