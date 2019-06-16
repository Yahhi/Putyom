package ru.develop_for_android.putyom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.develop_for_android.putyom.model.RepairEvent;
import ru.develop_for_android.putyom.model.SmartDevice;

public class WorkListAdapter extends RecyclerView.Adapter<WorkListAdapter.WorkItem> {

    private List<RepairEvent> works;
    private WorkSelectListener listener;

    WorkListAdapter(WorkSelectListener listener) {
        this.listener = listener;
    }

    void initialize(List<RepairEvent> works) {
        this.works = works;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WorkItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_work,
                parent, false);
        return new WorkItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkItem holder, int position) {
        holder.initWork(works.get(position));
    }

    @Override
    public int getItemCount() {
        if (works == null) {
            return 0;
        } else {
            return works.size();
        }
    }

    void addSign(int workId, int deviceId) {
        for (int i = 0; i < works.size(); i++) {
            if (works.get(i).contractNumber == workId) {
                SmartDevice[] devices = new SmartDevice[works.get(i).devices.length + 1];
                int j = 0;
                for (SmartDevice device : works.get(i).devices) {
                    devices[j++] = device;
                }
                devices[j] = new SmartDevice(deviceId, 0, 0, workId);
                works.get(i).devices = devices;
            }
        }
    }

    class WorkItem extends RecyclerView.ViewHolder {
        RepairEvent work;

        final TextView workNumber, workAddress;
        final TextView planDate, factDate;
        final ImageView firstSign, secondSign;

        WorkItem(@NonNull View itemView) {
            super(itemView);
            CardView mainCard = itemView.findViewById(R.id.main_card);
            mainCard.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onSelectWork(work.contractNumber);
                }
            });
            workNumber = itemView.findViewById(R.id.contract_number);
            workAddress = itemView.findViewById(R.id.address);
            planDate = itemView.findViewById(R.id.plan_date);
            factDate = itemView.findViewById(R.id.fact_date);
            firstSign = itemView.findViewById(R.id.first_sign);
            secondSign = itemView.findViewById(R.id.second_sign);
        }

        void initWork(RepairEvent repairEvent) {
            work = repairEvent;
            workNumber.setText(String.valueOf(repairEvent.contractNumber));
            workAddress.setText(repairEvent.address);
            planDate.setText(repairEvent.getPlannedDates());
            factDate.setText(repairEvent.getFactDates());
            firstSign.setVisibility((repairEvent.devices != null && repairEvent.devices.length > 0)? View.VISIBLE : View.GONE);
            secondSign.setVisibility((repairEvent.devices != null && repairEvent.devices.length > 1)? View.VISIBLE : View.GONE);
        }
    }
}
