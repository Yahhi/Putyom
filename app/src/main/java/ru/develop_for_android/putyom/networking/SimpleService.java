package ru.develop_for_android.putyom.networking;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import ru.develop_for_android.putyom.model.RepairEvent;
import ru.develop_for_android.putyom.model.SmartDevice;
import ru.develop_for_android.putyom.model.WorkDeviceAttachment;

public interface SimpleService {
    @GET("sign/")
    Call<List<SmartDevice>> getDevicesList();

    @GET("work/")
    Call<List<RepairEvent>> getWorkList();

    @POST("sign/activate/")
    Call<Void> sendActivationRequest(@Body WorkDeviceAttachment attachment);

    @POST("sign/deactivate/")
    Call<Void> sendDeactivationRequest(@Body WorkDeviceAttachment attachment);

}
