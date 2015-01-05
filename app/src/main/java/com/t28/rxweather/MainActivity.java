package com.t28.rxweather;

import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.t28.rxweather.data.model.Coordinate;
import com.t28.rxweather.data.service.LocationService;
import com.t28.rxweather.fragment.ForecastFragment;
import com.t28.rxweather.fragment.WeatherFragment;
import com.t28.rxweather.rx.CoordinateEventBus;
import com.t28.rxweather.rx.EventBus;

import butterknife.ButterKnife;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends ActionBarActivity {
    private EventBus<Coordinate> mCoordinateEventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mCoordinateEventBus = CoordinateEventBus.Retriever.retrieve();
        setupActionBar();

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.main_weather, new WeatherFragment())
                    .add(R.id.main_forecast, new ForecastFragment())
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        final LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        final LocationService location = new LocationService(manager);

        location.find()
                .subscribeOn(Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR))
                .subscribe(new Action1<Coordinate>() {
                    @Override
                    public void call(Coordinate coordinate) {
                        mCoordinateEventBus.publish(coordinate);
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int itemId = item.getItemId();
        if (itemId == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupActionBar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_navigation);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "click", Toast.LENGTH_SHORT).show();
            }
        });
        toolbar.setTitle("Tokyo");
        toolbar.setSubtitle("Japan");
        toolbar.inflateMenu(R.menu.menu_main);
    }
}
