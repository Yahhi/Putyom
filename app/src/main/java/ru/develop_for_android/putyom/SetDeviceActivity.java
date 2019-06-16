package ru.develop_for_android.putyom;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.develop_for_android.putyom.model.Contractor;
import ru.develop_for_android.putyom.model.RepairEvent;
import ru.develop_for_android.putyom.model.SmartDevice;
import ru.develop_for_android.putyom.model.WorkDeviceAttachment;
import ru.develop_for_android.putyom.networking.RetrofitSingle;
import ru.develop_for_android.putyom.networking.SimpleService;

public class SetDeviceActivity extends AppCompatActivity implements WorkSelectListener {
    private final static String KEY_SELECTED_WORK = "workId";

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

        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(v -> {
            workId = 2;
            sendAddRequest(3);
        });
        getWorkList();
    }

    private void getWorkList() {
        SimpleService service = RetrofitSingle.getRetrofit(getBaseContext()).create(SimpleService.class);
        Call<List<RepairEvent>> call = service.getWorkList();
        call.enqueue(new Callback<List<RepairEvent>>() {
            @Override
            public void onResponse(@NotNull Call<List<RepairEvent>> call, @NotNull Response<List<RepairEvent>> response) {
                //adapter.initialize(response.body());

                List<RepairEvent> events = new ArrayList<>();
                SmartDevice[] devArr = new SmartDevice[0];
                events.add(new RepairEvent(1, new Date(), new Date(2019, 7, 10),
                        null, devArr, new Contractor(115, "Дядя Вася"), 5, new String[0]));
                events.add(new RepairEvent(2, new Date(), new Date(2019, 7, 10),
                        null, devArr, new Contractor(120, "ООО Рога"), 5, new String[0]));
                adapter.initialize(events);
            }

            @Override
            public void onFailure(@NotNull Call<List<RepairEvent>> call, @NotNull Throwable t) {
                Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                List<RepairEvent> events = new ArrayList<>();
                SmartDevice[] devArr = new SmartDevice[0];
                events.add(new RepairEvent(1, new Date(), new Date(2019, 7, 10),
                        null, devArr, new Contractor(115, "Дядя Вася"), 5, new String[0]));
                events.add(new RepairEvent(2, new Date(), new Date(2019, 7, 10),
                        null, devArr, new Contractor(120, "ООО Рога"), 5, new String[0]));
                adapter.initialize(events);
            }
        });
    }

    // Get the results:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, getString(R.string.cancelled), Toast.LENGTH_LONG).show();
            } else {
                sendAddRequest(Integer.parseInt(result.getContents()));
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void sendAddRequest(int deviceId) {
        SimpleService service = RetrofitSingle.getRetrofit(getBaseContext()).create(SimpleService.class);
        Call<Void> call = service.sendActivationRequest(new WorkDeviceAttachment(workId, deviceId));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                finish();
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
    public void onSelectWork(int workId) {
        this.workId = workId;
        new IntentIntegrator(SetDeviceActivity.this).initiateScan();
    }
}
