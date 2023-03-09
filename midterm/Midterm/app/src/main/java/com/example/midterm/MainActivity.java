package com.example.midterm;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.midterm.models.Product;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements ProductsFragment.ProductsListener, ReviewsFragment.ReviewListener, CreateReviewFragment.CreateReviewListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new ProductsFragment())
                .commit();
    }

    @Override
    public void sendSelectedProduct(Product product) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, ReviewsFragment.newInstance(product))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void sendCreateReview(Product product) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, CreateReviewFragment.newInstance(product))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void sendCompletedReview() {
        getSupportFragmentManager()
                .popBackStack();
    }

    public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {

            final OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(urls[0])
                    .build();

            Response response = null;
            Bitmap mIcon11 = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (response.isSuccessful()) {
                try {
                    mIcon11 = BitmapFactory.decodeStream(response.body().byteStream());
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }

            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}