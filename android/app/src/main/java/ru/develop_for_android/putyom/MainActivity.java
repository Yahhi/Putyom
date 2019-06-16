package ru.develop_for_android.putyom;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.develop_for_android.putyom.model.WorkDeviceAttachment;
import ru.develop_for_android.putyom.networking.RetrofitSingle;
import ru.develop_for_android.putyom.networking.SimpleService;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private MapFragmentView mapFragmentView;
    private MapViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupViewModel();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> startActivity(new Intent(getBaseContext(), WorkDetailsActivity.class)));

        showPermissionRequest();
    }

    private void setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(MapViewModel.class);
    }

    private void showPermissionRequest() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.ACCESS_NETWORK_STATE
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.isAnyPermissionPermanentlyDenied()) {
                    Snackbar.make(findViewById(R.id.main_view), getString(R.string.extract_position), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.grant), v -> showPermissionRequest()).show();
                } else {
                    setupMapFragmentView();
                }
            }
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }

    private void setupMapFragmentView() {
        // All permission requests are being handled. Create map fragment view. Please note
        // the HERE SDK requires all permissions defined above to operate properly.
        Timber.i("setting up map fragment");
        mapFragmentView = new MapFragmentView(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_add:
                startActivity(new Intent(getBaseContext(), WorkListActivity.class));
                return true;
            case R.id.action_remove:
                new IntentIntegrator(this).initiateScan();
                return true;
            case R.id.action_find_me:
                if (mapFragmentView != null) {
                    mapFragmentView.showCurrentPosition();
                }
                return true;
            case R.id.action_update:
                viewModel.loadWork();
                viewModel.loadSigns();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, getString(R.string.cancelled), Toast.LENGTH_LONG).show();
            } else {
                sendRemoveRequest(Integer.parseInt(result.getContents()));
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void sendRemoveRequest(int deviceId) {
        SimpleService service = RetrofitSingle.getRetrofit(getBaseContext()).create(SimpleService.class);
        Call<Void> call = service.sendDeactivationRequest(new WorkDeviceAttachment(0, deviceId));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                viewModel.loadSigns();
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
}
