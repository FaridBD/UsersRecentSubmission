package com.example.farid.usersrecentsubmission;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class recyclerAdapterOfRecentSubmission extends RecyclerView.Adapter<recyclerAdapterOfRecentSubmission.subRecyclerViewHolder> {
    Context mContest;
    List<submissionActivity> list;

    public recyclerAdapterOfRecentSubmission(Context mContest, List<submissionActivity> list) {
        this.mContest = mContest;
        this.list = list;
    }

    @Override
    public subRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContest);
        View view = inflater.inflate(R.layout.card_view_submission, parent, false);

        TextView tv = view.findViewById(R.id.solution_status);

        if(viewType == 1) tv.setTextColor(Color.parseColor("#00AA00"));
        if(viewType == 2) tv.setTextColor(Color.parseColor("#64DD17"));
        if(viewType == 3) tv.setTextColor(Color.parseColor("#FF0000"));
        if(viewType == 4) tv.setTextColor(Color.parseColor("#0000FF"));
        if(viewType == 5) tv.setTextColor(Color.parseColor("#00AAAA"));

        return new subRecyclerViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        String status = list.get(position).solution_status;

        if(status.startsWith("accepted")) return 1;
        if(status.startsWith("partially")) return 2;
        if(status.startsWith("wrong")) return 3;
        if(status.startsWith("time")) return 4;
        if(status.startsWith("runtime")) return 5;
        return 0;
    }

    @Override
    public void onBindViewHolder(subRecyclerViewHolder holder, int position) {
        int type = 0;
        // Type = 1 (Codeforces)
        // Type = 2 (CodeChef)
        if(list.get(position).getJudge() == "codeforces") type = 1;
        else type = 2;

        holder.image_judge.setImageResource(R.drawable.codechef_round_icon2);
        holder.problem_name.setText(list.get(position).problem_name);
        holder.solution_time.setText(list.get(position).solution_time);
        holder.solution_status.setText(list.get(position).solution_status);
        holder.execution_time.setText(list.get(position).solution_execution_time);
        holder.usage_memory.setText(list.get(position).usage_memory);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class subRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView problem_name;
        TextView solution_time;
        TextView solution_status;
        TextView execution_time;
        TextView usage_memory;
        ImageView image_judge;
        CardView sub_card_view;

        public subRecyclerViewHolder(View itemView) {
            super(itemView);

            problem_name = itemView.findViewById(R.id.problem_name);
            solution_status = itemView.findViewById(R.id.solution_status);
            solution_time = itemView.findViewById(R.id.solution_time);
            execution_time = itemView.findViewById(R.id.ex_time);
            usage_memory = itemView.findViewById(R.id.usage_memory);
            image_judge = itemView.findViewById(R.id.judge_icon);
            sub_card_view = itemView.findViewById(R.id.sub_card_view);
        }
    }
}
