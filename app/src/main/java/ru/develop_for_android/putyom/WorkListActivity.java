package ru.develop_for_android.putyom;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.develop_for_android.putyom.model.RepairEvent;
import ru.develop_for_android.putyom.model.WorkDeviceAttachment;
import ru.develop_for_android.putyom.networking.RetrofitSingle;
import ru.develop_for_android.putyom.networking.SimpleService;

public class WorkListActivity extends AppCompatActivity implements WorkSelectListener {
    private final static String KEY_SELECTED_WORK = "workId";
    private final static int REQUEST_MODIFY_WORK = 1742;

    WorkListAdapter adapter;
    private int workId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_device);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView worksList = findViewById(R.id.works_list);
        worksList.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        adapter = new WorkListAdapter(this);
        worksList.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null) {
            workId = savedInstanceState.getInt(KEY_SELECTED_WORK);
        }

        getWorkList();
    }

    private void getWorkList() {
        SimpleService service = RetrofitSingle.getRetrofit(getBaseContext()).create(SimpleService.class);
        Call<List<RepairEvent>> call = service.getWorkList();
        call.enqueue(new Callback<List<RepairEvent>>() {
            @Override
            public void onResponse(@NotNull Call<List<RepairEvent>> call, @NotNull Response<List<RepairEvent>> response) {
                adapter.initialize(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<List<RepairEvent>> call, @NotNull Throwable t) {
                Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_remove_sign, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_remove) {
            new IntentIntegrator(this).initiateScan();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_MODIFY_WORK) {
            getWorkList();
        } else {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {
                    Toast.makeText(this, getString(R.string.cancelled), Toast.LENGTH_LONG).show();
                } else {
                    sendRemoveRequest(Integer.parseInt(result.getContents()));
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    private void sendRemoveRequest(int deviceId) {
        SimpleService service = RetrofitSingle.getRetrofit(getBaseContext()).create(SimpleService.class);
        Call<Void> call = service.sendDeactivationRequest(new WorkDeviceAttachment(0, deviceId));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                adapter.removeSign(deviceId);
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_SELECTED_WORK, workId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onSelectWork(RepairEvent work) {
        Intent workDetails = new Intent(getBaseContext(), WorkDetailsActivity.class);
        workDetails.putExtra(WorkDetailsActivity.KEY_EVENT, work);
        startActivityForResult(workDetails, REQUEST_MODIFY_WORK);
    }
}
