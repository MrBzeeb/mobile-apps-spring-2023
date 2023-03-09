package com.example.midterm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.midterm.databinding.FragmentProductsBinding;
import com.example.midterm.models.Product;

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

public class ProductsFragment extends Fragment {

    public ProductsFragment() {
        // Required empty public constructor
    }

    private final OkHttpClient client = new OkHttpClient();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentProductsBinding binding;
    ArrayList<Product> mProducts = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Products");

        getProducts();

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product selectedProduct = mProducts.get(i);
                mListener.sendSelectedProduct(selectedProduct);
            }
        });
    }

    void getProducts () {
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/products")
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
                    JSONArray jsonArray = jsonObject.optJSONArray("products");

                    // Parse JSON to list
                    for(int i = 0; i < Objects.requireNonNull(jsonArray).length(); i++) {
                        JSONObject jsonData = jsonArray.optJSONObject(i);

                        Product newProduct = new Product(jsonData);

                        newProduct.setPid(jsonData.getString("pid"));
                        newProduct.setName(jsonData.getString("name"));
                        newProduct.setImg_url(jsonData.getString("img_url"));
                        newProduct.setPrice(jsonData.getString("price"));
                        newProduct.setDescription(jsonData.getString("description"));
                        newProduct.setReview_count(jsonData.getString("review_count"));

                        mProducts.add(newProduct);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                // Checking products were imported correctly
//                Log.w("API", mProducts.toString());
//                for (int i = 0; i < mProducts.size(); i++) {
//                    // mProducts list to list view
//                    Log.w("API", mProducts.get(i).getName());
//                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Adapter for list of products to list view
                        ProductAdapter adapter = new ProductAdapter(getActivity(), mProducts);
                        binding.listView.setAdapter(adapter);
                    }
                });

            }
        }
    });

    }

    class ProductAdapter extends ArrayAdapter<Product> {
        public ProductAdapter(@NonNull Context context, @NonNull List<Product> objects) {
            super(context, R.layout.row_item_product, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.row_item_product, parent, false);
            }

            TextView textViewProductDesc = convertView.findViewById(R.id.textViewProductDesc);
            TextView textViewProductName = convertView.findViewById(R.id.textViewProductName);
            TextView textViewProductPrice = convertView.findViewById(R.id.textViewProductPrice);
            TextView textViewProductReviews = convertView.findViewById(R.id.textViewProductReviews);
            ImageView imageViewProductIcon = convertView.findViewById(R.id.imageViewProductIcon);

            Product product = getItem(position);

            textViewProductDesc.setText(product.getDescription());
            textViewProductName.setText(product.getName());
            textViewProductPrice.setText(product.getPrice());
            textViewProductReviews.setText(product.getReview_count());
            new MainActivity.DownloadImageTask(imageViewProductIcon).execute(product.getImg_url());

            return convertView;
        }
    }

    interface ProductsListener {
        void sendSelectedProduct(Product product);
    }

    ProductsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (ProductsListener) context;
    }
}