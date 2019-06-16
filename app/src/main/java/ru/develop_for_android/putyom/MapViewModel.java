package ru.develop_for_android.putyom;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.develop_for_android.putyom.model.RepairEvent;
import ru.develop_for_android.putyom.model.SmartDevice;
import ru.develop_for_android.putyom.networking.RetrofitSingle;
import ru.develop_for_android.putyom.networking.SimpleService;

public class MapViewModel extends AndroidViewModel {
    MutableLiveData<Location> myPosition = new MutableLiveData<>();
    MutableLiveData<List<SmartDevice>> devices = new MutableLiveData<>();
    MutableLiveData<String> error = new MutableLiveData<>();
    MutableLiveData<List<RepairEvent>> works = new MutableLiveData<>();
    MutableLiveData<List<double[]>> routesSource = new MutableLiveData<>();

    @SuppressLint("MissingPermission")
    public MapViewModel(@NonNull Application application) {
        super(application);
        loadWork();
        loadSigns();
        LocationManager locationManager = (LocationManager)
                getApplication().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        myPosition.setValue(location);
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });
    }

    public void loadWork() {
        SimpleService service = RetrofitSingle.getRetrofit(getApplication()).create(SimpleService.class);
        Call<List<RepairEvent>> call = service.getWorkList();
        call.enqueue(new Callback<List<RepairEvent>>() {
            @Override
            public void onResponse(@NotNull Call<List<RepairEvent>> call, @NotNull Response<List<RepairEvent>> response) {
                works.setValue(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<List<RepairEvent>> call, @NotNull Throwable t) {
                error.setValue(t.getMessage());
            }
        });
    }

    public void loadSigns() {
        SimpleService service = RetrofitSingle.getRetrofit(getApplication()).create(SimpleService.class);
        Call<List<SmartDevice>> call = service.getDevicesList();
        call.enqueue(new Callback<List<SmartDevice>>() {
            @Override
            public void onResponse(@NotNull Call<List<SmartDevice>> call, @NotNull Response<List<SmartDevice>> response) {
                if (response.body() == null) return;
                devices.setValue(response.body());
                calculateLineDots(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<List<SmartDevice>> call, @NotNull Throwable t) {
                error.setValue(t.getMessage());
            }
        });
    }

    private void calculateLineDots(List<SmartDevice> devices) {
        List<double[]> routes = new ArrayList<>();
        for (int i = 0; i < devices.size(); i++) {
            for (int j = i+1; j < devices.size(); j++) {
                if (devices.get(i).workId == devices.get(j).workId) {
                    double[] latLongs = new double[4];
                    latLongs[0] = devices.get(i).latitude;
                    latLongs[1] = devices.get(i).longitude;
                    latLongs[2] = devices.get(j).latitude;
                    latLongs[3] = devices.get(j).longitude;
                    routes.add(latLongs);
                    devices.remove(j);
                    break;
                }
            }
        }
        routesSource.setValue(routes);
    }
}
