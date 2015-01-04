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
import com.t28.rxweather.model.Forecast;
import com.t28.rxweather.model.Weather;
import com.t28.rxweather.volley.RequestQueueRetriever;
import com.t28.rxweather.volley.RxSupport;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
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
        final RxSupport support = new RxSupport(queue);

        final LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        final Observable<Coordinate> coordinateObservable = Coordinate.find(manager)
                .subscribeOn(Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR));

        coordinateObservable
                .flatMap(new Func1<Coordinate, Observable<Weather>>() {
                    @Override
                    public Observable<Weather> call(Coordinate coordinate) {
                        return Weather.findByCoordinate(support, coordinate);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Weather>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable cause) {
                        Log.d("TAG", "Thread:" + Thread.currentThread().getName());
                        Log.d("TAG", "onError:" + cause);
                    }

                    @Override
                    public void onNext(Weather result) {
                        Log.d("TAG", "Thread:" + Thread.currentThread().getName());
                        Log.d("TAG", "onNext:" + result);
                    }
                });

        coordinateObservable
                .flatMap(new Func1<Coordinate, Observable<Forecast>>() {
                    @Override
                    public Observable<Forecast> call(Coordinate coordinate) {
                        return Forecast.findByCoordinate(support, coordinate);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Forecast>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable cause) {
                        Log.d("TAG", "Thread:" + Thread.currentThread().getName());
                        Log.d("TAG", "onError:" + cause);
                    }

                    @Override
                    public void onNext(Forecast result) {
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
