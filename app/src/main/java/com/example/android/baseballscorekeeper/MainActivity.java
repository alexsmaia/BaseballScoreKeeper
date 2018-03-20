package com.example.android.baseballscorekeeper;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    // Global Variables
    int runsHome = 0;
    int runsVisitor = 0;
    int balls = 0;
    int strikes = 0;
    int outs = 0;
    int innings = 1;
    boolean inningBottom = false;
    boolean gameEnd = false;
    String winner = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Add Run.
     */
    public void addRun(View view) {
        balls = 0;
        strikes = 0;
        outs = 0;
        if (inningBottom) {
            runsVisitor++;
            setRunVisitor();
            setBallHome();
            setStrikeVisitor();
            setOutVisitor();
        } else {
            runsHome++;
            setRunHome();
            setBallVisitor();
            setStrikeHome();
            setOutHome();
        }
    }

    /**
     * Add Ball.
     */
    public void addBall(View view) {
        if (balls == 3) {
            balls = 0;
            strikes = 0;
            if (inningBottom) {
                setStrikeVisitor();
            } else {
                setStrikeHome();
            }
            // Toast if ball reach limit
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, R.string.toast_balls, duration);
            toast.show();
        } else {
            balls++;
        }
        if (inningBottom) {
            setBallHome();
        } else {
            setBallVisitor();
        }
    }

    /**
     * Add Strike.
     */
    public void addStrike(View view) {
        if (strikes == 2) {
            outs++;
            checkOuts();
            // Toast if Strikes reach limit
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, R.string.toast_strikes, duration);
            toast.show();
        } else {
            strikes++;
            if (inningBottom) {
                setStrikeVisitor();
            } else {
                setStrikeHome();
            }
        }
    }

    /**
     * Add Out.
     */
    public void addOut(View view) {
        outs++;
        checkOuts();
    }


    /**
     * Check Outs.
     */
    public void checkOuts() {
        strikes = 0;
        balls = 0;
        if (outs == 3) {
            outs = 0;
            if (inningBottom) {
                inningBottom = false;
                if (innings >= 9) {
                    if (runsHome == runsVisitor) {
                        innings++;
                        setInnings();
                    } else {
                        gameEnd = true;
                        if (runsHome > runsVisitor) {
                            winner = "home";
                        }
                        showHideSecBox();
                        showHideWinner();
                    }
                } else {
                    innings++;
                    setInnings();
                }
                setOutVisitor();
            } else {
                inningBottom = true;
                setOutHome();
            }
            // Toast if Outs reach limit
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, R.string.toast_outs, duration);
            toast.show();
            // Change Top / Bottom Inning
            setLayout();
        } else {
            if (inningBottom) {
                setOutVisitor();
                setBallHome();
                setStrikeVisitor();
            } else {
                setOutHome();
                setBallVisitor();
                setStrikeHome();
            }
        }
    }

    /**
     * Reset the Counter.
     */
    public void reset(View view) {
        // Reset variables to original values
        runsHome = 0;
        runsVisitor = 0;
        balls = 0;
        strikes = 0;
        outs = 0;
        innings = 1;
        inningBottom = false;
        gameEnd = false;
        winner = "";
        // Set inicial values
        setInnings();
        setRunHome();
        setRunVisitor();
        setBallHome();
        setBallVisitor();
        setStrikeHome();
        setStrikeVisitor();
        setOutHome();
        setOutVisitor();
        // Show / Hide Boxes
        setLayout();
        showHideSecBox();
        showHideWinner();
    }

    /**
     * Set the app Layout.
     */
    public void setLayout() {
        changeBallTopBottom();
        showHideRuns();
        showHideBalls();
        showHideStrikes();
        showHideOuts();
    }

    /**
     * Set Innings.
     */
    public void setInnings() {
        TextView inningsCount = (TextView) findViewById(R.id.inningsCount);
        inningsCount.setText(String.valueOf(innings));
    }

    /**
     * Set Runs Home.
     */
    public void setRunHome() {
        TextView scoreView = (TextView) findViewById(R.id.runsHome);
        scoreView.setText(String.valueOf(runsHome));
    }

    /**
     * Set Run Visitor.
     */
    public void setRunVisitor() {
        TextView scoreView = (TextView) findViewById(R.id.runsVisitor);
        scoreView.setText(String.valueOf(runsVisitor));
    }

    /**
     * Set Balls Home.
     */
    public void setBallHome() {
        TextView scoreView = (TextView) findViewById(R.id.ballHomeCount);
        scoreView.setText(String.valueOf(balls));
    }

    /**
     * Set Balls Visitor.
     */
    public void setBallVisitor() {
        TextView scoreView = (TextView) findViewById(R.id.ballVisitorCount);
        scoreView.setText(String.valueOf(balls));
    }

    /**
     * Set Strikes Home.
     */
    public void setStrikeHome() {
        TextView scoreView = (TextView) findViewById(R.id.strikeHomeCount);
        scoreView.setText(String.valueOf(strikes));
    }

    /**
     * Set Strikes Visitor.
     */
    public void setStrikeVisitor() {
        TextView scoreView = (TextView) findViewById(R.id.strikeVisitorCount);
        scoreView.setText(String.valueOf(strikes));
    }

    /**
     * Set Outs Home.
     */
    public void setOutHome() {
        TextView scoreView = (TextView) findViewById(R.id.outHomeCount);
        scoreView.setText(String.valueOf(outs));
    }

    /**
     * Set Outs Visitor.
     */
    public void setOutVisitor() {
        TextView scoreView = (TextView) findViewById(R.id.outVisitorCount);
        scoreView.setText(String.valueOf(outs));
    }

    /**
     * Show / Hide Secundary Box.
     */
    public void showHideSecBox() {
        LinearLayout secBoxHome = (LinearLayout) findViewById(R.id.secBoxHome);
        LinearLayout secBoxVisitor = (LinearLayout) findViewById(R.id.secBoxVisitor);
        if (gameEnd) {
            secBoxHome.setVisibility(View.GONE);
            secBoxVisitor.setVisibility(View.GONE);
        } else {
            secBoxHome.setVisibility(View.VISIBLE);
            secBoxVisitor.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Change Ball Top.
     */
    public void changeBallTopBottom() {
        ImageView ballTop = (ImageView) findViewById(R.id.ballTop);
        ImageView ballBottom = (ImageView) findViewById(R.id.ballBottom);
        if (gameEnd) {
            ballTop.setVisibility(View.GONE);
            ballBottom.setVisibility(View.GONE);
        } else {
            if (inningBottom) {
                ballTop.setVisibility(View.GONE);
                ballBottom.setVisibility(View.VISIBLE);
            } else {
                ballTop.setVisibility(View.VISIBLE);
                ballBottom.setVisibility(View.GONE);
            }
        }
    }

    /**
     * Show / Hide Runs.
     */
    public void showHideRuns() {
        Button runHomeBtn = (Button) findViewById(R.id.runHomeBtn);
        Button runVisitorBtn = (Button) findViewById(R.id.runVisitorBtn);
        if (inningBottom) {
            runHomeBtn.setVisibility(View.GONE);
            runVisitorBtn.setVisibility(View.VISIBLE);
        } else {
            runHomeBtn.setVisibility(View.VISIBLE);
            runVisitorBtn.setVisibility(View.GONE);
        }
    }

    /**
     * Show / Hide Balls.
     */
    public void showHideBalls() {
        LinearLayout ballHomeBox = (LinearLayout) findViewById(R.id.ballHomeBox);
        LinearLayout ballVisitorBox = (LinearLayout) findViewById(R.id.ballVisitorBox);
        if (inningBottom) {
            ballHomeBox.setVisibility(View.VISIBLE);
            ballVisitorBox.setVisibility(View.GONE);
        } else {
            ballHomeBox.setVisibility(View.GONE);
            ballVisitorBox.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Show / Hide Strikes.
     */
    public void showHideStrikes() {
        LinearLayout strikeHomeBox = (LinearLayout) findViewById(R.id.strikeHomeBox);
        LinearLayout strikeVisitorBox = (LinearLayout) findViewById(R.id.strikeVisitorBox);
        if (inningBottom) {
            strikeHomeBox.setVisibility(View.GONE);
            strikeVisitorBox.setVisibility(View.VISIBLE);
        } else {
            strikeHomeBox.setVisibility(View.VISIBLE);
            strikeVisitorBox.setVisibility(View.GONE);
        }
    }

    /**
     * Show / Hide Outs.
     */
    public void showHideOuts() {
        LinearLayout outHomeBox = (LinearLayout) findViewById(R.id.outHomeBox);
        LinearLayout outVisitorBox = (LinearLayout) findViewById(R.id.outVisitorBox);
        if (inningBottom) {
            outHomeBox.setVisibility(View.GONE);
            outVisitorBox.setVisibility(View.VISIBLE);
        } else {
            outHomeBox.setVisibility(View.VISIBLE);
            outVisitorBox.setVisibility(View.GONE);
        }
    }

    /**
     * Show / Hide Winner Home.
     */
    public void showHideWinner() {
        ImageView winnerHome = (ImageView) findViewById(R.id.winnerHome);
        ImageView winnerVisitor = (ImageView) findViewById(R.id.winnerVisitor);
        if (gameEnd) {
            if (winner == "home") {
                winnerHome.setVisibility(View.VISIBLE);
            } else {
                winnerVisitor.setVisibility(View.VISIBLE);
            }
        } else {
            winnerHome.setVisibility(View.GONE);
            winnerVisitor.setVisibility(View.GONE);
        }
    }

}
