package com.example.farid.usersrecentsubmission;

import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class submission_list extends AppCompatActivity {

    private TextView username;
    List<submissionActivity> codechef_list = new ArrayList<>();
    private BottomSheetBehavior bottomSheetBehavior;
    LinearLayout problem_statement, source_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission_list);

        recent_submission_for_codechef(getIntent().getStringExtra("username").toString());
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.problem_code_bottom_sheet));
        problem_statement = findViewById(R.id.problem_view);
        source_code = findViewById(R.id.code_view);

        try {
            Thread.sleep(5000);
        }catch (Exception e) {}

        RecyclerView recyclerView = findViewById(R.id.sub_recycler_view);
        recyclerAdapterOfRecentSubmission myAdapter = new recyclerAdapterOfRecentSubmission(submission_list.this, codechef_list, bottomSheetBehavior, problem_statement, source_code);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.problem_code_bottom_sheet));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

    }

    void recent_submission_for_codechef(final String username) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for(int y=2018; ; y--) {

                        String year = Integer.toString(y);
                        System.out.println("thread e hamaise *************************************");
                        String url = "https://www.codechef.com/submissions?sort_by=All&sorting_order=asc&language=All&status=All&year="+year+"&handle="+username+"&pcode=&ccode=&Submit=GO";
                        System.out.println(url);
                        Document doc = (Document) Jsoup.connect(url).get();

                        System.out.println("conntect paise *************************************");

                        Elements el = doc.getElementsByClass("dataTable").select("tbody").select("tr");

                        for(int i=0; i<el.size(); i++) {
                            System.out.println("loop e soler *************************************");
                            String solution_id = el.get(i).select("td").get(0).text();
                            String solution_time = el.get(i).select("td").get(1).text();
                            String problem_link = el.get(i).select("td").get(4).select("a").attr("abs:href").toString();
                            String problem_code = el.get(i).select("td").get(3).select("a").text();
                            String problem_difficulty = el.get(i).select("td").get(4).select("a").text();
                            String solution_status = el.get(i).select("td").get(5).select("div").select("span").first().attr("title").toString();
                            String solution_execution_time = el.get(i).select("td").get(6).text();
                            String usage_memory = el.get(i).select("td").get(7).text();
                            String solution_language = el.get(i).select("td").get(8).text();
                            String solution_link = el.get(i).select("td").get(9).select("a").attr("abs:href").toString();

                            //System.out.println(solution_status + " " + solution_execution_time + " " + usage_memory);
                            if(solution_status.length() == 0) {
                                String point =  el.get(i).select("td").get(5).select("div").select("span").first().text();
                                StringTokenizer st = new StringTokenizer(point);
                                point = st.nextToken();
                                if(Integer.valueOf(point) == 100) {
                                    solution_status = "accepted";
                                }
                                else solution_status = "partially accepted("+point+"pts)";
                                //System.out.println(point);

                            }
                            if(!solution_status.equals("accepted")) {
                                System.out.println(solution_status + "*************************");
                                solution_execution_time = "-";
                                usage_memory = "-";
                            }

                            //System.out.println(solution_link);
                            //System.out.println("loop e soler 2*************************************");


                            String tmp = solution_link;
                            int len = solution_link.length();

                            tmp = tmp.replaceFirst("viewsolution", "viewplaintext");

                            //Document doc2 = Jsoup.connect(tmp).get();

                            codechef_list.add( new submissionActivity(solution_id, solution_time, problem_code, solution_status, problem_link, solution_link, "codechef", solution_language, solution_execution_time, usage_memory, problem_difficulty)) ;

                        }
                    }
                } catch(IndexOutOfBoundsException e) {
                    System.out.println("Catch e hamaise");
                } catch (IOException e) {
                    System.out.println("Catch e hamaise IO");
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
