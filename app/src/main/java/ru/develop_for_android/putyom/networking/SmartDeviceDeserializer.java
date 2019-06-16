package ru.develop_for_android.putyom.networking;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import ru.develop_for_android.putyom.model.SmartDevice;

public class SmartDeviceDeserializer implements JsonDeserializer<SmartDevice> {
        @Override
        public SmartDevice deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
                throws JsonParseException
        {
            final JsonObject jsonObject = je.getAsJsonObject();
            SmartDevice smartDevice = new Gson().fromJson(je, type);

            // Deserialize it. You use a new instance of Gson to avoid infinite recursion
            // to this deserializer
            return smartDevice;

        }
}
