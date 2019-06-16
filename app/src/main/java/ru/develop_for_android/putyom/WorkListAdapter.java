package ru.develop_for_android.putyom;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.develop_for_android.putyom.model.RepairEvent;

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

    class WorkItem extends RecyclerView.ViewHolder {
        RepairEvent work;

        final TextView workNumber;
        final TextView startDate;

        WorkItem(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onSelectWork(work.contractNumber);
                }
            });
            workNumber = itemView.findViewById(R.id.contract_number);
            startDate = itemView.findViewById(R.id.date_start);
        }

        void initWork(RepairEvent repairEvent) {
            work = repairEvent;
            workNumber.setText(String.valueOf(repairEvent.contractNumber));
            startDate.setText(DateFormat.format("yyyy.MM.dd", repairEvent.start));
        }
    }
}
