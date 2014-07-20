package com.ovaflow.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ovaflow.app.R;
import com.ovaflow.app.engine.mania.model.data.ScoreType;

public class SummaryActivity extends ActionBarActivity {


    private Button backButton;
    private TextView coolsView, goodsView, badsView, missesView, scoreView, newHScoreView;
    private ImageView rankView;
    //private Activity curActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        backButton = (Button) findViewById(R.id.back);
        fillSummaryData();

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //NavUtils.navigateUpFromSameTask(curActivity);
                sendMessage(v);
            }
        });
    }

    private void fillSummaryData() {
        coolsView = (TextView) findViewById(R.id.summary_cool_pts);
        goodsView = (TextView) findViewById(R.id.summary_good_pts);
        badsView = (TextView) findViewById(R.id.summary_bad_pts);
        missesView = (TextView) findViewById(R.id.summary_miss_pts);
        scoreView = (TextView) findViewById(R.id.summary_score_pts);
        newHScoreView = (TextView) findViewById(R.id.newHScore);
        ImageView rank = (ImageView) findViewById(R.id.summary_SABCDEF);

        ScoreType st = (ScoreType) getIntent().getExtras().get("ScoreType");
        if (st != null) {
            coolsView.setText("x" + st.getCool());
            goodsView.setText("x" + st.getGood());
            badsView.setText("x" + st.getBad());
            missesView.setText("x" + st.getMiss());
            scoreView.setText(st.getScore() +"");

            switch(st.getRank()) {
                case 0: rank.setImageResource(R.drawable.summary_f);break;
                case 1: rank.setImageResource(R.drawable.summary_e);break;
                case 2: rank.setImageResource(R.drawable.summary_d);break;
                case 3: rank.setImageResource(R.drawable.summary_c);break;
                case 4: rank.setImageResource(R.drawable.summary_b);break;
                case 5: rank.setImageResource(R.drawable.summary_a);break;
                case 6: rank.setImageResource(R.drawable.summary_s);break;
            }
        }

    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, SongSelectActivity.class);
        startActivity(intent);
    }

}
