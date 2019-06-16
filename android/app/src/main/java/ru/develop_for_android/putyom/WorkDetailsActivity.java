package ru.develop_for_android.putyom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.develop_for_android.putyom.model.RepairEvent;
import ru.develop_for_android.putyom.model.SmartDevice;
import ru.develop_for_android.putyom.model.WorkDeviceAttachment;
import ru.develop_for_android.putyom.networking.RetrofitSingle;
import ru.develop_for_android.putyom.networking.SimpleService;

public class WorkDetailsActivity extends AppCompatActivity {
    public static final String KEY_EVENT = "repairEvent";

    RepairEvent repairEvent;

    TextView workNumber, workAddress;
    TextView planDate, factDate;
    ImageView firstSign, secondSign;
    CardView firstSignCard, secondSignCard;
    Button activate1, activate2;
    TextView coordinates1, coordinates2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent() != null) {
            repairEvent = getIntent().getParcelableExtra(KEY_EVENT);
        } else if (savedInstanceState != null) {
            repairEvent = savedInstanceState.getParcelable(KEY_EVENT);
        }

        workNumber = findViewById(R.id.contract_number);
        workAddress = findViewById(R.id.address);
        planDate = findViewById(R.id.plan_date);
        factDate = findViewById(R.id.fact_date);
        firstSign = findViewById(R.id.first_sign);
        secondSign = findViewById(R.id.second_sign);
        firstSignCard = findViewById(R.id.first_sign_card);
        secondSignCard = findViewById(R.id.second_sign_card);

        coordinates1 = findViewById(R.id.first_sign_coordinates);
        coordinates2 = findViewById(R.id.second_sign_coordinates);

        activate1 = findViewById(R.id.activate_first);
        activate1.setOnClickListener(v -> new IntentIntegrator(WorkDetailsActivity.this).initiateScan());
        activate2 = findViewById(R.id.activate_second);
        activate2.setOnClickListener(v -> new IntentIntegrator(WorkDetailsActivity.this).initiateScan());

        workNumber.setText(String.valueOf(repairEvent.contractNumber));
        workAddress.setText(repairEvent.address);
        planDate.setText(repairEvent.getPlannedDates());
        factDate.setText(repairEvent.getFactDates());
        showSigns();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void showSigns() {
        if (repairEvent.devices != null && repairEvent.devices.length > 0) {
            firstSign.setVisibility(View.VISIBLE);
            firstSignCard.setVisibility(View.VISIBLE);
            activate1.setVisibility(View.GONE);
            coordinates1.setText(repairEvent.devices[0].getCoordinates());
        } else {
            firstSign.setVisibility(View.GONE);
            firstSignCard.setVisibility(View.VISIBLE);
            activate1.setVisibility(View.VISIBLE);
        }

        if (repairEvent.devices != null && repairEvent.devices.length > 1) {
            secondSign.setVisibility(View.VISIBLE);
            secondSignCard.setVisibility(View.VISIBLE);
            activate2.setVisibility(View.GONE);
            coordinates2.setText(repairEvent.devices[1].getCoordinates());
        } else {
            secondSign.setVisibility(View.GONE);
            secondSignCard.setVisibility(View.VISIBLE);
            activate2.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(KEY_EVENT, repairEvent);
        super.onSaveInstanceState(outState);
    }

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

    void addSign(int deviceId) {
        SmartDevice[] devices = new SmartDevice[repairEvent.devices.length + 1];
        int j = 0;
        for (SmartDevice device : repairEvent.devices) {
            devices[j++] = device;
        }
        devices[j] = new SmartDevice(deviceId, 0, 0, repairEvent.contractNumber);
        repairEvent.devices = devices;
    }

    private void sendAddRequest(int deviceId) {
        SimpleService service = RetrofitSingle.getRetrofit(getBaseContext()).create(SimpleService.class);
        Call<Void> call = service.sendActivationRequest(new WorkDeviceAttachment(repairEvent.contractNumber, deviceId));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                addSign(deviceId);
                showSigns();
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
}
