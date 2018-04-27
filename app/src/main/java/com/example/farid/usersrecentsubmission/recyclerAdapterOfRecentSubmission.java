package com.example.farid.usersrecentsubmission;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.LinkAddress;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class recyclerAdapterOfRecentSubmission extends RecyclerView.Adapter<recyclerAdapterOfRecentSubmission.subRecyclerViewHolder> {
    Context mContext;
    List<submissionActivity> list;
    BottomSheetBehavior bottomSheetBehavior;
    LinearLayout problem_statement, source_code;

    public recyclerAdapterOfRecentSubmission(Context mContest, List<submissionActivity> list, BottomSheetBehavior bottomSheetBehavior, LinearLayout problem_statement, LinearLayout source_code) {
        this.mContext = mContest;
        this.list = list;
        this.bottomSheetBehavior = bottomSheetBehavior;
        this.problem_statement = problem_statement;
        this.source_code = source_code;
    }

    @Override
    public subRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
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
    public void onBindViewHolder(final subRecyclerViewHolder holder, final int position) {
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
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        holder.sub_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        problem_statement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                String problem_link = list.get(position).problem_link;
                if(problem_link != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(problem_link));
                    mContext.startActivity(intent);
                }
                else {
                    Toast.makeText(mContext, "Seems like this is private problem", Toast.LENGTH_SHORT).show();
                }
            }
        });
        source_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                String solution_link = list.get(position).solution_link;
                if(solution_link != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(solution_link));
                    mContext.startActivity(intent);
                }
                else {
                    Toast.makeText(mContext, "Seems like this is on going contest problem! Try again later", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
