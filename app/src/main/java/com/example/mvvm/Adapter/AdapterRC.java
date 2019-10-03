package com.example.mvvm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvm.Node;
import com.example.mvvm.OnItemClickListener;
import com.example.mvvm.R;

import java.util.List;

public class AdapterRC extends ListAdapter<Node,AdapterRC.ViewHolder> {

//    private List<Node> nodeList;
    private OnItemClickListener listener;

    public AdapterRC() {
        super(DIFF_CALLBACK);
    }
    private static final DiffUtil.ItemCallback<Node> DIFF_CALLBACK = new DiffUtil.ItemCallback<Node>() {
        @Override
        public boolean areItemsTheSame(@NonNull Node oldItem, @NonNull Node newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Node oldItem, @NonNull Node newItem) {
            return (oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getTitle().equals(newItem.getTitle()));
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitle.setText(getItem(position).getTitle());
        holder.tvDescription.setText(getItem(position).getDescription());
        holder.Priority.setText(getItem(position).getPriority() + "");
    }


    public Node getNodeAt(int position) {
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription, Priority;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
            Priority = itemView.findViewById(R.id.tv_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        listener.ItemClickListener(getItem(getAdapterPosition()));
                    }

                }
            });
        }
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
