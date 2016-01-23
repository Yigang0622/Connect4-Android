package it.miketech.connect4;

import android.app.AlertDialog;
import android.graphics.Color;
import android.support.annotation.DimenRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final int FP = ViewGroup.LayoutParams.WRAP_CONTENT;

    private TableLayout mTableLayout;
    private TableLayout mButtonTable;
    private TextView mTextView;
    private Button resetButton;
    private Button AIButton;

    private int [][] gameBoardArr=new int[7][7];

    private static boolean resultShown=false;

    private Board gameBoard;

    private boolean cpuCtrl=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView= (TextView) findViewById(R.id.current_player_textView);
        resetButton= (Button) findViewById(R.id.reset_button);
        AIButton= (Button) findViewById(R.id.buttonAI);

        initGameBoard();
        initButtons();

        gameBoard=new Board();

        //updateGameBoard(gameBoardArr);
    }

    private void initGameBoard(){

        mTableLayout= (TableLayout) findViewById(R.id.table_layout);
        mTableLayout.setStretchAllColumns(true);
        mTableLayout.setBackgroundResource(R.color.blue);

        for(int row=0;row<7;row++) {
            TableRow tableRow=new TableRow(this);
            for(int col=0;col<7;col++) {

                //tv.setText("("+col+","+row+")");
//                tv.setBackgroundResource(R.drawable.circle_red);
//                tv.setScaleX(1);
//                tv.setScaleY(1);
//                tableRow.addView(tv);
                ImageView view=new ImageView(this);

                view.setImageResource(R.drawable.circle_white);
                tableRow.addView(view);

                tableRow.setDividerPadding(R.dimen.padding);
            }

            mTableLayout.addView(tableRow, new TableLayout.LayoutParams(FP, WC));
        }


    }

    private void initButtons(){
        mButtonTable= (TableLayout) findViewById(R.id.button_table_layout);
        mButtonTable.setStretchAllColumns(true);
        mButtonTable.setBackgroundResource(R.color.blue);

        TableRow tableRow=new TableRow(this);
        for(int row=0;row<7;row++) {
                View button=new View(this);
                //button.setBackgroundResource(R.drawable.icon_arrow);
                button.setTag(row);
                button.setOnClickListener(this);
                tableRow.addView(button);
        }
        mButtonTable.addView(tableRow, new TableLayout.LayoutParams(FP, WC));

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        AIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performAI();
            }
        });
    }

    public void performAI(){

        cpuCtrl=true;

        int position =3;
        position=new AI().ABNegaMax(gameBoard.copy(),0,3,-11111111,1111111).position;
        //int position = new AI().miniMax(gameBoard.copy(),0,3).position;

        mButtonTable.findViewWithTag(position).callOnClick();

        Log.d("Main","AI Will place at "+position);

        cpuCtrl=false;
    }

    private void updateGameBoard(int[][] arr){

        mTableLayout.removeAllViews();
        mTableLayout= (TableLayout) findViewById(R.id.table_layout);
        mTableLayout.setStretchAllColumns(true);
        mTableLayout.setBackgroundResource(R.color.blue);

            for (int i=0;i<7;i++){

                TableRow tableRow=new TableRow(this);

                for (int j=0;j<7;j++){
                    ImageView view=new ImageView(this);
                    int flag=arr[i][j];
                    switch (flag){
                        case 0: view.setImageResource(R.drawable.circle_white);break;
                        case 1:view.setImageResource(R.drawable.circle_red);break;
                        case 5:view.setImageResource(R.drawable.circle_yellow);break;
                    }
                    tableRow.addView(view);

                    tableRow.setDividerPadding(R.dimen.padding);
                }

                mTableLayout.addView(tableRow, new TableLayout.LayoutParams(FP, WC));
            }


    }

    public void onClick(View v) {

        boolean actionVaild=true;
        int player=gameBoard.getCurrentPlayer();

        if (player==2 &&!resultShown){
            mTextView.setText("Player Two Turn!");
            mTextView.setTextColor(Color.rgb(255,255,0));
        }else if (player ==1&&!resultShown){
                mTextView.setText("Player One Turn!");
                mTextView.setTextColor(Color.rgb(255,0,0));
            }


            if (!resultShown){
                Log.d("MainAct","Placing Piece");
                try{
                    switch (Integer.valueOf(v.getTag().toString())){
                        case 0: gameBoard.placePiece(0); break;
                        case 1: gameBoard.placePiece(1); break;
                        case 2: gameBoard.placePiece(2); break;
                        case 3: gameBoard.placePiece(3); break;
                        case 4: gameBoard.placePiece(4); break;
                        case 5: gameBoard.placePiece(5); break;
                        case 6: gameBoard.placePiece(6); break;
                        default:  break;
                    }
                }catch (Exception e){
                    actionVaild=false;
                }
            }

            if (actionVaild){
                updateGameBoard(gameBoardArr);
            }else {
                updatePlayerUI();
            }

            updateResultUI();
            updateGameBoard(gameBoard.getGameBoardArr());

            if (!cpuCtrl){
                AIButton.callOnClick();
            }

    }

    public void updatePlayerUI(){
        if (gameBoard.getCurrentPlayer()==1){
            mTextView.setText("Player One Turn!");
            mTextView.setTextColor(Color.rgb(255,0,0));
        }else {
            mTextView.setText("Player Two Turn!");
            mTextView.setTextColor(Color.rgb(255,255,0));
        }
    }

    public void updateResultUI(){
        int result = gameBoard.getGameResult();
        switch (result){
            case 0: mTextView.setText("Draw");
                    resultShown=true;
                    break;

            case 1: mTextView.setText("Player One Wins");
                    mTextView.setTextColor(Color.rgb(255, 0, 0));
                    resultShown=true;
                    break;

            case 2: mTextView.setText("Player Two Wins");
                    mTextView.setTextColor(Color.rgb(255, 225, 0));
                    resultShown=true;
                    break;
            default: break;
        }
    }

    private void resetGame(){
        gameBoard.reset();
        updateGameBoard(gameBoard.getGameBoardArr());
        mTextView.setText("Player One Turn!");
        mTextView.setTextColor(Color.rgb(255, 0, 0));
        resultShown=false;
    }

    public void showHelp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        //View view = getActivity().getLayoutInflater().inflate(R.layout.about_fragment, null);
        builder.setTitle("How to play");
        builder.setMessage("Connect Four is a two-player connection game in which the players first choose a color and then take turns dropping colored discs from the top into a seven-column, six-row vertically suspended grid. The pieces fall straight down, occupying the next available space within the column. The objective of the game is to connect four of one's own discs of the same color next to each other vertically, horizontally, or diagonally before your opponent. ");
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    public void showAbout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        //View view = getActivity().getLayoutInflater().inflate(R.layout.about_fragment, null);
        builder.setTitle("About");
        builder.setMessage("Developer: Mike.Zhou\n\nWebsite: http://miketech.it\n\nEmail: zhou@zygmail.com");
        builder.setPositiveButton("Good!", null);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(getApplication())
                .inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_how_to_play: showHelp(); break;
            case R.id.action_about: showAbout(); break;
        }
        return super.onOptionsItemSelected(item);
    }
}
