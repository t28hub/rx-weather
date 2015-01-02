package com.t28.rxweather;

import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.t28.rxweather.model.Coordinate;
import com.t28.rxweather.request.WeatherRequest;
import com.t28.rxweather.volley.RequestQueueRetriever;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final RequestQueue queue = RequestQueueRetriever.retrieve(this);
        queue.add(new WeatherRequest.Builder("")
                .setCityId("2172797")
                .build());

        final LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Coordinate.find(manager)
                .subscribeOn(Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Coordinate>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable cause) {
                        Log.d("TAG", "Thread:" + Thread.currentThread().getName());
                        Log.d("TAG", "onError:" + cause);
                    }

                    @Override
                    public void onNext(Coordinate result) {
                        Log.d("TAG", "Thread:" + Thread.currentThread().getName());
                        Log.d("TAG", "onNext:" + result);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
