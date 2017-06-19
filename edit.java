package com.example.sushanth.play2048;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;


import java.util.ArrayList;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by sushanth on 5/7/2017.
 */

public class Gamebox extends View implements View.OnTouchListener {
    int scoreCalculated;
    Boolean gameOverFlag = false;
    String prefName = "MyPref";
    int lastboxinder;
    TextView bestscore;
    SharedPreferences sharedPreferences;
    int bestscoreCalculated=0;
    Boolean tried = false;
    Box box00=null, box01=null,box02=null, box03=null,box10=null,box11=null,
            box12=null,box13=null,box20=null,box21=null,box22=null,box23=null,box30=null,
            box31=null,box32=null,box33=null;
    private GestureDetector mGestureDetector;
    public Gamebox(final Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);

        //bestscore = (TextView) ((Activity)getContext()).findViewById(R.id.best);
        //sharedPreferences = ((Activity)getContext()).getSharedPreferences(prefName, MODE_PRIVATE);
        //bestscoreCalculated = sharedPreferences.getInt("bestscore" ,0);
        //bestscore.setText("BEST\n"+Integer.toString(bestscoreCalculated));


        mGestureDetector= new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                sharedPreferences = ((Activity)getContext()).getSharedPreferences(prefName, MODE_PRIVATE);
                bestscoreCalculated = sharedPreferences.getInt("bestscore" ,0);
                bestscore = (TextView) ((Activity)getContext()).findViewById(R.id.best);
                bestscore.setText("BEST\n"+bestscoreCalculated);
                if(gameOverFlag){
                    tried=true;
                }else{
                    tried=false;
                }


                switch (getSlope(e1.getX(), e1.getY(), e2.getX(), e2.getY())) {
                    case 1:
                        //swipe up;
                        //Toast.makeText(getContext(),"Swipe up",Toast.LENGTH_SHORT).show();
                        moveboxestowardsup();
                        checkforbestscore();
                        for(Box each: box){
                            if(each.number == 2048){
                                //game won
                                Intent gamewon = new Intent(getContext(), GameWon.class);
                                gamewon.putExtra("clickeduser",scoreCalculated);
                                ((Activity)getContext()).startActivity(gamewon);
                                ((Activity)getContext()).finish();

                            }
                        }
                        if(gameOverFlag && tried){
                            //Gameover
                            Intent gameover = new Intent(getContext(), GameOver.class);
                            gameover.putExtra("clickeduser",scoreCalculated);
                            ((Activity)getContext()).startActivity(gameover);
                            ((Activity)getContext()).finish();
                        }
                        return true;
                    case 2:
                        //swipe left
                        //Toast.makeText(getContext(),"Swipe left",Toast.LENGTH_SHORT).show();
                        moveboxestowardsleft();
                        checkforbestscore();
                        for(Box each: box){
                            if(each.number == 2048){
                                //game won
                                Intent gamewon = new Intent(getContext(), GameWon.class);
                                gamewon.putExtra("clickeduser",scoreCalculated);
                                ((Activity)getContext()).startActivity(gamewon);
                                ((Activity)getContext()).finish();

                            }
                        }
                        if(gameOverFlag && tried){
                            //Gameover
                            Intent gameover = new Intent(getContext(), GameOver.class);
                            gameover.putExtra("clickeduser",scoreCalculated);
                            ((Activity)getContext()).startActivity(gameover);
                            ((Activity)getContext()).finish();
                        }
                        return true;
                    case 3:
                        //swipe down
                        //Toast.makeText(getContext(),"Swipe down",Toast.LENGTH_SHORT).show();
                        moveboxestowardsdown();
                        checkforbestscore();
                        for(Box each: box){
                            if(each.number == 2048){
                                //game won
                                Intent gamewon = new Intent(getContext(), GameWon.class);
                                gamewon.putExtra("clickeduser",scoreCalculated);
                                ((Activity)getContext()).startActivity(gamewon);
                                ((Activity)getContext()).finish();

                            }
                        }
                        if(gameOverFlag && tried){
                            //Gameover
                            Intent gameover = new Intent(getContext(), GameOver.class);
                            gameover.putExtra("clickeduser",scoreCalculated);
                            ((Activity)getContext()).startActivity(gameover);
                            ((Activity)getContext()).finish();
                        }
                        return true;
                    case 4:
                        //swipe right
                        //Toast.makeText(getContext(),"Swipe right",Toast.LENGTH_SHORT).show();
                        moveboxestowardsright();
                        checkforbestscore();
                        for(Box each: box){
                            if(each.number == 2048){
                                //game won
                                Intent gamewon = new Intent(getContext(), GameWon.class);
                                gamewon.putExtra("clickeduser",scoreCalculated);
                                ((Activity)getContext()).startActivity(gamewon);
                                ((Activity)getContext()).finish();

                            }
                        }
                        if(gameOverFlag && tried){
                            //Gameover
                            Intent gameover = new Intent(getContext(), GameOver.class);
                            gameover.putExtra("clickeduser",scoreCalculated);
                            ((Activity)getContext()).startActivity(gameover);
                            ((Activity)getContext()).finish();
                        }
                        return true;
                }
                return false;  

            }
        });
    }

    private void checkforbestscore() {
        if(scoreCalculated>=bestscoreCalculated){
            bestscore.setText("BEST\n"+scoreCalculated);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("bestscore", scoreCalculated);
            editor.commit();
        }
    }


    private int getSlope(float x1, float y1, float x2, float y2) {
        Double angle = Math.toDegrees(Math.atan2(y1 - y2, x2 - x1));
        if (angle > 45 && angle <= 135)
            // top
            return 1;
        if (angle >= 135 && angle < 180 || angle < -135 && angle > -180)
            // left
            return 2;
        if (angle < -45 && angle>= -135)
            // down
            return 3;
        if (angle > -45 && angle <= 45)
            // right
            return 4;
        return 0;
    }

    Boolean startOfGame=true;
    int[] numbers={2,2,2,2,4,4};

    static Paint dafault;
    static {
        dafault=new Paint();
        dafault.setColor(Color.rgb(210,198,150));
        dafault.setStyle(Paint.Style.FILL_AND_STROKE);
    }
    ArrayList<Box> box = new ArrayList<>();
    float firstRowTop, firstRowBottom, secondRowTop, secondRowBottom,thirdRowTop, thirdRowBottom, fourthRowTop,fourthRowBottom ;
    float firstCoulumnLeft, firstColumnRight, secondColumnLeft, secondColumnRight, thirdColumnLeft, thirdColumnRight, fourthColumnLeft, fourthColumnRight;
    ArrayList<Integer> boxnumber= new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15));

    public void generateRandom(){

        int randIndex= new Random().nextInt(boxnumber.size());
        int boxtoadd = boxnumber.get(randIndex);
        int row=0;
        int column=0;
        switch (boxtoadd){
            case 0:
                row = 0;
                column = 0;
                break;
            case 1:
                row = 0;
                column = 1;
                break;
            case 2:
                row = 0;
                column = 2;
                break;
            case 3:
                row = 0;
                column = 3;
                break;
            case 4:
                row = 1;
                column = 0;
                break;
            case 5:
                row = 1;
                column = 1;
                break;
            case 6: row = 1;
                column = 2;
                break;
            case 7: row = 1;
                column = 3;
                break;
            case 8: row = 2;
                column = 0;
                break;
            case 9: row = 2;
                column = 1;
                break;
            case 10: row = 2;
                column = 2;
                break;
            case 11: row = 2;
                column = 3;
                break;
            case 12:
                row = 3;
                column = 0;
                break;
            case 13: row = 3;
                column = 1;
                break;
            case 14: row = 3;
                column = 2;
                break;
            case 15:
                row = 3;
                column = 3;
                break;
        }
        int temp= new Random().nextInt(numbers.length);
        int number = numbers[temp];
        Log.i("ra","Row:"+ row+" Column"+column+" number:"+number);
        box.add(new Box(row,column,number));
        boxnumber.remove(randIndex);
        if(boxnumber.size()==0){
            gameOverFlag = true;
            Toast.makeText(getContext(),"You have to Make Correct Move!",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean didUseEvent = mGestureDetector.onTouchEvent(event);
        return true;

    }

    protected void onDraw(Canvas canvas) {

        canvas.drawColor(Color.rgb(179,167,120));
        int screenHeight =canvas.getHeight();
        int screenWidth = canvas.getWidth();
        firstRowTop = 0 + Double.valueOf(0.1 * (screenWidth / 4)).floatValue();
        firstRowBottom = screenHeight / 4 - Double.valueOf(0.1 * (screenHeight / 4)).floatValue();
        secondRowTop = screenHeight / 4 + Double.valueOf(0.1 * (screenHeight / 4)).floatValue();
        secondRowBottom = 2*screenHeight / 4 - Double.valueOf(0.1 * (screenHeight / 4)).floatValue();
        thirdRowTop= 2*screenHeight / 4 + Double.valueOf(0.1 * (screenHeight / 4)).floatValue();
        thirdRowBottom= 3*screenHeight / 4 - Double.valueOf(0.1 * (screenHeight / 4)).floatValue();
        fourthRowTop= 3*screenHeight / 4 + Double.valueOf(0.1 * (screenHeight / 4)).floatValue();
        fourthRowBottom= 4*screenHeight / 4 - Double.valueOf(0.1 * (screenHeight / 4)).floatValue();
        firstCoulumnLeft=0 + Double.valueOf(0.1 * (screenHeight / 4)).floatValue();
        firstColumnRight=screenWidth / 4 - Double.valueOf(0.1 * (screenHeight / 4)).floatValue();
        secondColumnLeft= 1 * (screenHeight / 4) + Double.valueOf(0.1 * (screenHeight / 4)).floatValue();
        secondColumnRight= 2 * (screenHeight / 4) - Double.valueOf(0.1 * (screenHeight / 4)).floatValue();
        thirdColumnLeft= 2 * (screenHeight / 4) + Double.valueOf(0.1 * (screenHeight / 4)).floatValue();
        thirdColumnRight= 3 * (screenHeight / 4) - Double.valueOf(0.1 * (screenHeight / 4)).floatValue();
        fourthColumnLeft= 3 * (screenHeight / 4) + Double.valueOf(0.1 * (screenHeight / 4)).floatValue();
        fourthColumnRight= 4* screenHeight / 4 - Double.valueOf(0.1 * (screenHeight / 4)).floatValue();

        //first row
        {
            canvas.drawRect(firstCoulumnLeft, firstRowTop, firstColumnRight, firstRowBottom, dafault);
            canvas.drawRect(secondColumnLeft, firstRowTop, secondColumnRight, firstRowBottom, dafault);
            canvas.drawRect(thirdColumnLeft, firstRowTop, thirdColumnRight, firstRowBottom, dafault);
            canvas.drawRect(fourthColumnLeft, firstRowTop, fourthColumnRight, firstRowBottom,dafault);
        }
        //second row
        {
            canvas.drawRect(firstCoulumnLeft, secondRowTop, firstColumnRight, secondRowBottom, dafault);
            canvas.drawRect(secondColumnLeft, secondRowTop, secondColumnRight, secondRowBottom, dafault);
            canvas.drawRect(thirdColumnLeft, secondRowTop, thirdColumnRight, secondRowBottom, dafault);
            canvas.drawRect(fourthColumnLeft, secondRowTop, fourthColumnRight, secondRowBottom,dafault);
        }
        //third row
        {
            canvas.drawRect(firstCoulumnLeft, thirdRowTop, firstColumnRight, thirdRowBottom, dafault);
            canvas.drawRect(secondColumnLeft, thirdRowTop, secondColumnRight, thirdRowBottom, dafault);
            canvas.drawRect(thirdColumnLeft, thirdRowTop, thirdColumnRight, thirdRowBottom, dafault);
            canvas.drawRect(fourthColumnLeft, thirdRowTop, fourthColumnRight, thirdRowBottom,dafault);
        }
        //fourth row
        {
            canvas.drawRect(firstCoulumnLeft, fourthRowTop, firstColumnRight, fourthRowBottom, dafault);
            canvas.drawRect(secondColumnLeft, fourthRowTop, secondColumnRight, fourthRowBottom, dafault);
            canvas.drawRect(thirdColumnLeft, fourthRowTop, thirdColumnRight, fourthRowBottom, dafault);
            canvas.drawRect(fourthColumnLeft, fourthRowTop, fourthColumnRight, fourthRowBottom,dafault);
        }
        if(startOfGame) {
            generateRandom();
            generateRandom();
            startOfGame=false;
        }
        for (Box each : box) {
            if(each!=null) {
                each.drawOn(canvas);
            }
        }
    }

    private void moveboxestowardsleft() {
        Log.i("rew","box size " +box.size());
        Boolean didMove = false;

        for (Box each : box) {
            if(each.row==0 && each.column==0 && each!=null){box00=each;}
            if(each.row==0 && each.column==1 && each!=null){box01=each;}
            if(each.row==0 && each.column==2 && each!=null){box02=each;}
            if(each.row==0 && each.column==3 && each!=null){box03=each;}
            if(each.row==1 && each.column==0 && each!=null){box10=each;}
            if(each.row==1 && each.column==1 && each!=null){box11=each;}
            if(each.row==1 && each.column==2 && each!=null){box12=each;}
            if(each.row==1 && each.column==3 && each!=null){box13=each;}
            if(each.row==2 && each.column==0 && each!=null){box20=each;}
            if(each.row==2 && each.column==1 && each!=null){box21=each;}
            if(each.row==2 && each.column==2 && each!=null){box22=each;}
            if(each.row==2 && each.column==3 && each!=null){box23=each;}
            if(each.row==3 && each.column==0 && each!=null){box30=each;}
            if(each.row==3 && each.column==1 && each!=null){box31=each;}
            if(each.row==3 && each.column==2 && each!=null){box32=each;}
            if(each.row==3 && each.column==3 && each!=null){box33=each;}
        }



        //first row Calculations
        for(int i=0;i <=10;i++){
            if(box00==null){
                if(box01!=null) {
                    box.remove(box01);
                    box01.column = 0;
                    box00 = box01;
                    didMove=true;
                    box01=null;
                    box.add(box00);
                    boxnumber.add(1);
                    boxnumber.remove(boxnumber.indexOf(0));
                }

            }
            if(box01==null){
                if(box02!=null) {
                    box.remove(box02);
                    box02.column = 1;
                    didMove=true;
                    box01 = box02;
                    box02=null;
                    box.add(box01);
                    boxnumber.add(2);
                    boxnumber.remove(boxnumber.indexOf(1));
                }

            }
            if(box02==null){
                if(box03!=null){
                    box.remove(box03);
                    box03.column=2;
                    box02=box03;
                    didMove=true;
                    box03=null;
                    box.add(box02);
                    boxnumber.add(3);
                    boxnumber.remove(boxnumber.indexOf(2));

                }
            }

        }

        //add row1 here

        if(box00!= null && box01!=null){
            if(box00.number==box01.number){
                didMove=true;
                //add both nuumbers and move the remaining to left
                box.remove(box00);
                box.remove(box01);
                box00.number += box01.number;

                box01=null;
                boxnumber.add(1);
                box.add(box00);
                if(box02!=null && box03==null){
                    box.remove(box02);
                    boxnumber.add(2);
                    box02.column=1;
                    box01=box02;
                    box.add(box01);
                    boxnumber.remove(boxnumber.indexOf(1));
                    box02=null;
                }
                else if(box02!=null && box03!=null){
                    box.remove(box02);
                    boxnumber.add(2);
                    box02.column=1;
                    box01=box02;
                    box.add(box01);
                    boxnumber.remove(boxnumber.indexOf(1));
                    box02=null;
                    box.remove(box03);
                    boxnumber.add(3);
                    box03.column=2;
                    box02=box03;
                    box.add(box02);
                    boxnumber.remove(boxnumber.indexOf(2));
                    box03=null;
                    if(box01!=null && box02 != null &&box01.number==box02.number ){

                        didMove=true;
                        box.remove(box01);
                        box.remove(box02);
                        box01.number += box02.number;
                        box02=null;
                        boxnumber.add(2);
                        box.add(box01);

                    }

                }
            }
            else if( box02!=null && box01!=null && box01.number ==box02.number ){
                //add both the number and shift to right
                didMove=true;
                box.remove(box01);
                box.remove(box02);
                box01.number += box02.number;
                box02=null;
                boxnumber.add(2);
                box.add(box01);
                if(box03!=null){
                    box.remove(box03);
                    boxnumber.add(3);
                    box03.column=2;
                    box02=box03;
                    box03=null;
                    box.add(box02);
                    boxnumber.remove(boxnumber.indexOf(2));

                }

            }
            else if(box02!= null && box03!=null && box02.number==box03.number ){
                //add both the number and clear left box
                didMove=true;
                box.remove(box02);
                box.remove(box03);
                box02.number += box03.number;
                box03=null;
                boxnumber.add(3);
                box.add(box02);
            }
            else if(box02!= null && box03!=null && box02.number!=box03.number ){

            }
        }




        //Second row Calculations
        for(int i=0;i <=10;i++){
            if(box10==null){
                if(box11!=null) {
                    box.remove(box11);
                    box11.column = 0;
                    box10 = box11;
                    didMove=true;
                    box11=null;
                    box.add(box10);
                    boxnumber.add(5);
                    boxnumber.remove(boxnumber.indexOf(4));
                }

            }
            if(box11==null){
                if(box12!=null) {
                    box.remove(box12);
                    box12.column = 1;
                    didMove=true;
                    box11 = box12;
                    box12=null;
                    box.add(box11);
                    boxnumber.add(6);
                    boxnumber.remove(boxnumber.indexOf(5));
                }

            }
            if(box12==null){
                if(box13!=null){
                    box.remove(box13);
                    box13.column=2;
                    box12=box13;
                    didMove=true;
                    box13=null;
                    box.add(box12);
                    boxnumber.add(7);
                    boxnumber.remove(boxnumber.indexOf(6));

                }
            }

        }

        //add row2 here

        if(box10!= null && box11!=null){
            if(box10.number==box11.number){
                didMove=true;
                //add both nuumbers and move the remaining to left
                box.remove(box10);
                box.remove(box11);
                box10.number += box11.number;

                box11=null;
                boxnumber.add(5);
                box.add(box10);
                if(box12!=null && box13==null){
                    box.remove(box12);
                    boxnumber.add(6);
                    box12.column=1;
                    box11=box12;
                    box.add(box11);
                    boxnumber.remove(boxnumber.indexOf(5));
                    box12=null;
                }
                else if(box12!=null && box13!=null){
                    box.remove(box12);
                    boxnumber.add(6);
                    box12.column=1;
                    box11=box12;
                    box.add(box11);
                    boxnumber.remove(boxnumber.indexOf(5));
                    box12=null;
                    box.remove(box13);
                    boxnumber.add(7);
                    box13.column=2;
                    box12=box13;
                    box.add(box12);
                    boxnumber.remove(boxnumber.indexOf(6));
                    box13=null;
                    if(box11!=null && box12 != null &&box11.number==box12.number ){

                        didMove=true;
                        box.remove(box11);
                        box.remove(box12);
                        box11.number += box12.number;
                        box12=null;
                        boxnumber.add(6);
                        box.add(box11);

                    }

                }
            }
            else if( box12!=null && box11!=null && box11.number ==box12.number ){
                //add both the number and shift to right
                didMove=true;
                box.remove(box11);
                box.remove(box12);
                box11.number += box12.number;
                box12=null;
                boxnumber.add(6);
                box.add(box11);
                if(box13!=null){
                    box.remove(box13);
                    boxnumber.add(7);
                    box13.column=2;
                    box12=box13;
                    box13=null;
                    box.add(box12);
                    boxnumber.remove(boxnumber.indexOf(6));

                }

            }
            else if(box12!= null && box13!=null && box12.number==box13.number ){
                //add both the number and clear left box
                didMove=true;
                box.remove(box12);
                box.remove(box13);
                box12.number += box13.number;
                box13=null;
                boxnumber.add(7);
                box.add(box12);
            }
            else if(box12!= null && box13!=null && box12.number!=box13.number ){

            }
        }





        //Third row Calculations
        for(int i=0;i <=10;i++){
            if(box20==null){
                if(box21!=null) {
                    box.remove(box21);
                    box21.column = 0;
                    box20 = box21;
                    didMove=true;
                    box21=null;
                    box.add(box20);
                    boxnumber.add(9);
                    boxnumber.remove(boxnumber.indexOf(8));
                }

            }
            if(box21==null){
                if(box22!=null) {
                    box.remove(box22);
                    box22.column = 1;
                    didMove=true;
                    box21 = box22;
                    box22=null;
                    box.add(box21);
                    boxnumber.add(10);
                    boxnumber.remove(boxnumber.indexOf(9));
                }

            }
            if(box22==null){
                if(box23!=null){
                    box.remove(box23);
                    box23.column=2;
                    box22=box23;
                    didMove=true;
                    box23=null;
                    box.add(box22);
                    boxnumber.add(11);
                    boxnumber.remove(boxnumber.indexOf(10));

                }
            }

        }

        //add row3 here

        if(box20!= null && box21!=null){
            if(box20.number==box21.number){
                didMove=true;
                //add both nuumbers and move the remaining to left
                box.remove(box20);
                box.remove(box21);
                box20.number += box21.number;

                box21=null;
                boxnumber.add(9);
                box.add(box20);
                if(box22!=null && box23==null){
                    box.remove(box22);
                    boxnumber.add(10);
                    box22.column=1;
                    box21=box22;
                    box.add(box21);
                    boxnumber.remove(boxnumber.indexOf(9));
                    box22=null;
                }
                else if(box22!=null && box23!=null){
                    box.remove(box22);
                    boxnumber.add(10);
                    box22.column=1;
                    box21=box22;
                    box.add(box21);
                    boxnumber.remove(boxnumber.indexOf(9));
                    box22=null;
                    box.remove(box23);
                    boxnumber.add(11);
                    box23.column=2;
                    box22=box23;
                    box.add(box22);
                    boxnumber.remove(boxnumber.indexOf(10));
                    box23=null;
                    if(box21!=null && box22 != null &&box21.number==box22.number ){

                        didMove=true;
                        box.remove(box21);
                        box.remove(box22);
                        box21.number += box22.number;
                        box22=null;
                        boxnumber.add(10);
                        box.add(box21);

                    }

                }
            }
            else if( box22!=null && box21!=null && box21.number ==box22.number ){
                //add both the number and shift to right
                didMove=true;
                box.remove(box21);
                box.remove(box22);
                box21.number += box22.number;
                box22=null;
                boxnumber.add(10);
                box.add(box21);
                if(box23!=null){
                    box.remove(box23);
                    boxnumber.add(11);
                    box23.column=2;
                    box22=box23;
                    box23=null;
                    box.add(box22);
                    boxnumber.remove(boxnumber.indexOf(10));

                }

            }
            else if(box22!= null && box23!=null && box22.number==box23.number ){
                //add both the number and clear left box
                didMove=true;
                box.remove(box22);
                box.remove(box23);
                box22.number += box23.number;
                box23=null;
                boxnumber.add(11);
                box.add(box22);
            }
            else if(box22!= null && box23!=null && box22.number!=box23.number ){

            }
        }


        //Fourth row Calculations
        for(int i=0;i <=10;i++){
            if(box30==null){
                if(box31!=null) {
                    box.remove(box31);
                    box31.column = 0;
                    box30 = box31;
                    didMove=true;
                    box31=null;
                    box.add(box30);
                    boxnumber.add(13);
                    boxnumber.remove(boxnumber.indexOf(12));
                }

            }
            if(box31==null){
                if(box32!=null) {
                    box.remove(box32);
                    box32.column = 1;
                    didMove=true;
                    box31 = box32;
                    box32=null;
                    box.add(box31);
                    boxnumber.add(14);
                    boxnumber.remove(boxnumber.indexOf(13));
                }

            }
            if(box32==null){
                if(box33!=null){
                    box.remove(box33);
                    box33.column=2;
                    box32=box33;
                    didMove=true;
                    box33=null;
                    box.add(box32);
                    boxnumber.add(15);
                    boxnumber.remove(boxnumber.indexOf(14));

                }
            }

        }

        //add row4 here

        if(box30!= null && box31!=null){
            if(box30.number==box31.number){
                didMove=true;
                //add both nuumbers and move the remaining to left
                box.remove(box30);
                box.remove(box31);
                box30.number += box31.number;

                box31=null;
                boxnumber.add(13);
                box.add(box30);
                if(box32!=null && box33==null){
                    box.remove(box32);
                    boxnumber.add(14);
                    box32.column=1;
                    box31=box32;
                    box.add(box31);
                    boxnumber.remove(boxnumber.indexOf(13));
                    box32=null;
                }
                else if(box32!=null && box33!=null){
                    box.remove(box32);
                    boxnumber.add(14);
                    box32.column=1;
                    box31=box32;
                    box.add(box31);
                    boxnumber.remove(boxnumber.indexOf(13));
                    box32=null;
                    box.remove(box33);
                    boxnumber.add(15);
                    box33.column=2;
                    box32=box33;
                    box.add(box32);
                    boxnumber.remove(boxnumber.indexOf(14));
                    box33=null;
                    if(box31!=null && box32 != null &&box31.number==box32.number ){

                        didMove=true;
                        box.remove(box31);
                        box.remove(box32);
                        box31.number += box32.number;
                        box32=null;
                        boxnumber.add(14);
                        box.add(box31);

                    }

                }
            }
            else if( box32!=null && box31!=null && box31.number ==box32.number ){
                //add both the number and shift to right
                didMove=true;
                box.remove(box31);
                box.remove(box32);
                box31.number += box32.number;
                box32=null;
                boxnumber.add(14);
                box.add(box31);
                if(box33!=null){
                    box.remove(box33);
                    boxnumber.add(15);
                    box33.column=2;
                    box32=box33;
                    box33=null;
                    box.add(box32);
                    boxnumber.remove(boxnumber.indexOf(14));

                }

            }
            else if(box32!= null && box33!=null && box32.number==box33.number ){
                //add both the number and clear left box
                didMove=true;
                box.remove(box32);
                box.remove(box33);
                box32.number += box33.number;
                box33=null;
                boxnumber.add(15);
                box.add(box32);
            }
            else if(box32!= null && box33!=null && box32.number!=box33.number ){

            }
        }


        if(didMove) {

            scoreCalculated=0;
            for(Box each: box){
                scoreCalculated+=each.number;
            }
            TextView score = (TextView) ((Activity)getContext()).findViewById(R.id.score);
            score.setText("SCORE\n"+scoreCalculated);

            gameOverFlag=false;
            tried=false;
            generateRandom();
        }
        else{
            if(boxnumber.size()==0){
                gameOverFlag = true;

            }
        }

        invalidate();




    }
    private void moveboxestowardsright() {
        Log.i("rew","box size " +box.size());
        Boolean didMove = false;
        for (Box each : box) {
            if(each.row==0 && each.column==0 && each!=null){box00=each;}
            if(each.row==0 && each.column==1 && each!=null){box01=each;}
            if(each.row==0 && each.column==2 && each!=null){box02=each;}
            if(each.row==0 && each.column==3 && each!=null){box03=each;}
            if(each.row==1 && each.column==0 && each!=null){box10=each;}
            if(each.row==1 && each.column==1 && each!=null){box11=each;}
            if(each.row==1 && each.column==2 && each!=null){box12=each;}
            if(each.row==1 && each.column==3 && each!=null){box13=each;}
            if(each.row==2 && each.column==0 && each!=null){box20=each;}
            if(each.row==2 && each.column==1 && each!=null){box21=each;}
            if(each.row==2 && each.column==2 && each!=null){box22=each;}
            if(each.row==2 && each.column==3 && each!=null){box23=each;}
            if(each.row==3 && each.column==0 && each!=null){box30=each;}
            if(each.row==3 && each.column==1 && each!=null){box31=each;}
            if(each.row==3 && each.column==2 && each!=null){box32=each;}
            if(each.row==3 && each.column==3 && each!=null){box33=each;}
        }
        //first row Calculations
        for(int i=0;i <=10;i++){
            if(box03==null){
                if(box02!=null) {
                    box.remove(box02);
                    box02.column = 3;
                    box03 = box02;
                    didMove=true;
                    box02=null;
                    box.add(box03);
                    boxnumber.add(2);
                    boxnumber.remove(boxnumber.indexOf(3));
                }

            }
            if(box02==null){
                if(box01!=null) {
                    box.remove(box01);
                    box01.column = 2;
                    didMove=true;
                    box02 = box01;
                    box01=null;
                    box.add(box02);
                    boxnumber.add(1);
                    boxnumber.remove(boxnumber.indexOf(2));
                }

            }
            if(box01==null){
                if(box00!=null){
                    box.remove(box00);
                    box00.column=1;
                    box01=box00;
                    didMove=true;
                    box00=null;
                    box.add(box01);
                    boxnumber.add(0);
                    boxnumber.remove(boxnumber.indexOf(1));

                }
            }

        }
        //add row1 here

        if(box03!= null && box02!=null){
            if(box03.number==box02.number){
                didMove=true;
                //add both nuumbers and move the remaining to right
                box.remove(box03);
                box.remove(box02);
                box03.number += box02.number;

                box02=null;
                boxnumber.add(2);
                box.add(box03);
                if(box01!=null && box00==null){
                    box.remove(box01);
                    boxnumber.add(1);
                    box01.column=2;
                    box02=box01;
                    box.add(box02);
                    boxnumber.remove(boxnumber.indexOf(2));
                    box01=null;
                }
                else if(box01!=null && box00!=null){
                    box.remove(box01);
                    boxnumber.add(1);
                    box01.column=2;
                    box02=box01;
                    box.add(box02);
                    boxnumber.remove(boxnumber.indexOf(2));
                    box01=null;
                    box.remove(box00);
                    boxnumber.add(0);
                    box00.column=1;
                    box01=box00;
                    box.add(box01);
                    boxnumber.remove(boxnumber.indexOf(1));
                    box00=null;
                    if(box02!=null && box01 != null &&box02.number==box01.number ){

                        didMove=true;
                        box.remove(box02);
                        box.remove(box01);
                        box02.number += box01.number;
                        box01=null;
                        boxnumber.add(1);
                        box.add(box02);

                    }

                }
            }
            else if( box01!=null && box02!=null && box02.number ==box01.number ){
                //add both the number and shift to right
                didMove=true;
                box.remove(box02);
                box.remove(box01);
                box02.number += box01.number;
                box01=null;
                boxnumber.add(1);
                box.add(box02);
                if(box00!=null){
                    box.remove(box00);
                    boxnumber.add(0);
                    box00.column=1;
                    box01=box00;
                    box00=null;
                    box.add(box01);
                    boxnumber.remove(boxnumber.indexOf(1));

                }

            }
            else if(box01!= null && box00!=null && box01.number==box00.number ){
                //add both the number and clear left box
                didMove=true;
                box.remove(box01);
                box.remove(box00);
                box01.number += box00.number;
                box00=null;
                boxnumber.add(0);
                box.add(box01);
            }
            else if(box01!= null && box00!=null && box01.number!=box00.number ){

            }
        }






        //Second row Calculations
        for(int i=0;i <=10;i++){
            if(box13==null){
                if(box12!=null) {
                    box.remove(box12);
                    box12.column = 3;
                    box13 = box12;
                    didMove=true;
                    box12=null;
                    box.add(box13);
                    boxnumber.add(6);
                    boxnumber.remove(boxnumber.indexOf(7));
                }

            }
            if(box12==null){
                if(box11!=null) {
                    box.remove(box11);
                    box11.column = 2;
                    didMove=true;
                    box12 = box11;
                    box11=null;
                    box.add(box12);
                    boxnumber.add(5);
                    boxnumber.remove(boxnumber.indexOf(6));
                }

            }
            if(box11==null){
                if(box10!=null){
                    box.remove(box10);
                    box10.column=1;
                    box11=box10;
                    didMove=true;
                    box10=null;
                    box.add(box11);
                    boxnumber.add(4);
                    boxnumber.remove(boxnumber.indexOf(5));

                }
            }

        }
        //add row2 here
        if(box13!= null && box12!=null){
            if(box13.number==box12.number){
                didMove=true;
                //add both nuumbers and move the remaining to right
                box.remove(box13);
                box.remove(box12);
                box13.number += box12.number;

                box12=null;
                boxnumber.add(6);
                box.add(box13);
                if(box11!=null && box10==null){
                    box.remove(box11);
                    boxnumber.add(5);
                    box11.column=2;
                    box12=box11;
                    box.add(box12);
                    boxnumber.remove(boxnumber.indexOf(6));
                    box11=null;
                }
                else if(box11!=null && box10!=null){
                    box.remove(box11);
                    boxnumber.add(5);
                    box11.column=2;
                    box12=box11;
                    box.add(box12);
                    boxnumber.remove(boxnumber.indexOf(6));
                    box11=null;
                    box.remove(box10);
                    boxnumber.add(4);
                    box10.column=1;
                    box11=box10;
                    box.add(box11);
                    boxnumber.remove(boxnumber.indexOf(5));
                    box10=null;
                    if( box12 != null  && box11!=null && box12.number==box11.number ){

                        didMove=true;
                        box.remove(box12);
                        box.remove(box11);
                        box12.number += box11.number;
                        box11=null;
                        boxnumber.add(5);
                        box.add(box12);

                    }

                }
            }
            else if(box12!=null && box11!=null && box12.number ==box11.number ){
                //add both the number and shift to right
                didMove=true;
                box.remove(box12);
                box.remove(box11);
                box12.number += box11.number;
                box11=null;
                boxnumber.add(5);
                box.add(box12);
                if(box10!=null){
                    box.remove(box10);
                    boxnumber.add(4);
                    box10.column=1;
                    box11=box10;
                    box10=null;
                    box.add(box11);
                    boxnumber.remove(boxnumber.indexOf(5));

                }

            }
            else if(box11!=null && box10!=null && box11.number==box10.number){
                //add both the number and clear left box
                didMove=true;
                box.remove(box11);
                box.remove(box10);
                box11.number += box10.number;
                box10=null;
                boxnumber.add(4);
                box.add(box11);
            }
            else if(box11!=null && box10!=null && box11.number!=box10.number ){

            }
        }







        //Third row Calculations
        for(int i=0;i <=10;i++){
            if(box23==null){
                if(box22!=null) {
                    box.remove(box22);
                    box22.column = 3;
                    box23 = box22;
                    didMove=true;
                    box22=null;
                    box.add(box23);
                    boxnumber.add(10);
                    boxnumber.remove(boxnumber.indexOf(11));
                }

            }
            if(box22==null){
                if(box21!=null) {
                    box.remove(box21);
                    box21.column = 2;
                    didMove=true;
                    box22 = box21;
                    box21=null;
                    box.add(box22);
                    boxnumber.add(9);
                    boxnumber.remove(boxnumber.indexOf(10));
                }

            }
            if(box21==null){
                if(box20!=null){
                    box.remove(box20);
                    box20.column=1;
                    box21=box20;
                    didMove=true;
                    box20=null;
                    box.add(box21);
                    boxnumber.add(8);
                    boxnumber.remove(boxnumber.indexOf(9));

                }
            }

        }
        //add row3 here


        if(box23!= null && box22!=null){
            if(box23.number==box22.number){
                didMove=true;
                //add both nuumbers and move the remaining to right
                box.remove(box23);
                box.remove(box22);
                box23.number += box22.number;

                box22=null;
                boxnumber.add(10);
                box.add(box23);
                if(box21!=null && box20==null){
                    box.remove(box21);
                    boxnumber.add(9);
                    box21.column=2;
                    box22=box21;
                    box.add(box22);
                    boxnumber.remove(boxnumber.indexOf(10));
                    box21=null;
                }
                else if(box21!=null && box20!=null){
                    box.remove(box21);
                    boxnumber.add(9);
                    box21.column=2;
                    box22=box21;
                    box.add(box22);
                    boxnumber.remove(boxnumber.indexOf(10));
                    box21=null;
                    box.remove(box20);
                    boxnumber.add(8);
                    box20.column=1;
                    box21=box20;
                    box.add(box21);
                    boxnumber.remove(boxnumber.indexOf(9));
                    box20=null;
                    if(box22!= null && box21!= null && box22.number==box21.number ){

                        didMove=true;
                        box.remove(box22);
                        box.remove(box21);
                        box22.number += box21.number;
                        box21=null;
                        boxnumber.add(9);
                        box.add(box22);

                    }

                }
            }
            else if(box22!= null && box21!= null && box22.number ==box21.number){
                //add both the number and shift to right
                didMove=true;
                box.remove(box22);
                box.remove(box21);
                box22.number += box21.number;
                box21=null;
                boxnumber.add(9);
                box.add(box22);
                if(box20!=null){
                    box.remove(box20);
                    boxnumber.add(8);
                    box20.column=1;
                    box21=box20;
                    box20=null;
                    box.add(box21);
                    boxnumber.remove(boxnumber.indexOf(9));

                }

            }
            else if(box20!= null && box21!= null && box21.number==box20.number){
                //add both the number and clear left box
                didMove=true;
                box.remove(box21);
                box.remove(box20);
                box21.number += box20.number;
                box20=null;
                boxnumber.add(8);
                box.add(box21);
            }
            else if(box20!= null && box21!= null && box21.number!=box20.number){

            }
        }


        //Fourth row Calculations
        for(int i=0;i <=10;i++){
            if(box33==null){
                if(box32!=null) {
                    box.remove(box32);
                    box32.column = 3;
                    box33 = box32;
                    didMove=true;
                    box32=null;
                    box.add(box33);
                    boxnumber.add(14);
                    boxnumber.remove(boxnumber.indexOf(15));
                }

            }
            if(box32==null){
                if(box31!=null) {
                    box.remove(box31);
                    box31.column = 2;
                    didMove=true;
                    box32 = box31;
                    box31=null;
                    box.add(box32);
                    boxnumber.add(13);
                    boxnumber.remove(boxnumber.indexOf(14));
                }

            }
            if(box31==null){
                if(box30!=null){
                    box.remove(box30);
                    box30.column=1;
                    box31=box30;
                    didMove=true;
                    box30=null;
                    box.add(box31);
                    boxnumber.add(12);
                    boxnumber.remove(boxnumber.indexOf(13));

                }
            }

        }
        //add row4 here
        if(box33!= null && box32!=null){
            if(box33.number==box32.number){
                didMove=true;
                //add both nuumbers and move the remaining to right
                box.remove(box33);
                box.remove(box32);
                box33.number += box32.number;

                box32=null;
                boxnumber.add(14);
                box.add(box33);
                if(box31!=null && box30==null){
                    box.remove(box31);
                    boxnumber.add(13);
                    box31.column=2;
                    box32=box31;
                    box.add(box32);
                    boxnumber.remove(boxnumber.indexOf(14));
                    box31=null;
                }
                else if(box31!=null && box30!=null){
                    box.remove(box31);
                    boxnumber.add(13);
                    box31.column=2;
                    box32=box31;
                    box.add(box32);
                    boxnumber.remove(boxnumber.indexOf(14));
                    box31=null;
                    box.remove(box30);
                    boxnumber.add(12);
                    box30.column=1;
                    box31=box30;
                    box.add(box31);
                    boxnumber.remove(boxnumber.indexOf(13));
                    box30=null;
                    if(box32!= null && box31 != null && box32.number==box31.number){

                        didMove=true;
                        box.remove(box32);
                        box.remove(box31);
                        box32.number += box31.number;
                        box31=null;
                        boxnumber.add(13);
                        box.add(box32);

                    }

                }
            }
            else if(box32!= null && box31 != null && box32.number ==box31.number){
                //add both the number and shift to right
                didMove=true;
                box.remove(box32);
                box.remove(box31);
                box32.number += box31.number;
                box31=null;
                boxnumber.add(13);
                box.add(box32);
                if(box30!=null){
                    box.remove(box30);
                    boxnumber.add(12);
                    box30.column=1;
                    box31=box30;
                    box30=null;
                    box.add(box31);
                    boxnumber.remove(boxnumber.indexOf(13));

                }

            }
            else if(box30!= null && box31 != null && box31.number==box30.number){
                //add both the number and clear left box
                didMove=true;
                box.remove(box31);
                box.remove(box30);
                box31.number += box30.number;
                box30=null;
                boxnumber.add(12);
                box.add(box31);
            }
            else if(box30!= null && box31 != null && box31.number!=box30.number){

            }
        }

        if(didMove) {

            scoreCalculated=0;
            for(Box each: box){
                scoreCalculated+=each.number;
            }
            TextView score = (TextView) ((Activity)getContext()).findViewById(R.id.score);
            score.setText("SCORE\n"+scoreCalculated);

            gameOverFlag=false;
            tried=false;
            generateRandom();
        }
        else{
            if(boxnumber.size()==0){
                gameOverFlag = true;

            }
        }
        invalidate();

    }
    private void moveboxestowardsup()  {

        Boolean didMove = false;

        for (Box each : box) {
            if(each.row==0 && each.column==0 && each!=null){box00=each;}
            if(each.row==0 && each.column==1 && each!=null){box01=each;}
            if(each.row==0 && each.column==2 && each!=null){box02=each;}
            if(each.row==0 && each.column==3 && each!=null){box03=each;}
            if(each.row==1 && each.column==0 && each!=null){box10=each;}
            if(each.row==1 && each.column==1 && each!=null){box11=each;}
            if(each.row==1 && each.column==2 && each!=null){box12=each;}
            if(each.row==1 && each.column==3 && each!=null){box13=each;}
            if(each.row==2 && each.column==0 && each!=null){box20=each;}
            if(each.row==2 && each.column==1 && each!=null){box21=each;}
            if(each.row==2 && each.column==2 && each!=null){box22=each;}
            if(each.row==2 && each.column==3 && each!=null){box23=each;}
            if(each.row==3 && each.column==0 && each!=null){box30=each;}
            if(each.row==3 && each.column==1 && each!=null){box31=each;}
            if(each.row==3 && each.column==2 && each!=null){box32=each;}
            if(each.row==3 && each.column==3 && each!=null){box33=each;}
        }

        //first row Calculations
        for(int i=0;i <=10;i++){
            if(box00==null){
                if(box10!=null) {
                    box.remove(box10);
                    box10.row = 0;
                    box00 = box10;
                    didMove=true;
                    box10=null;
                    box.add(box00);
                    boxnumber.add(4);
                    boxnumber.remove(boxnumber.indexOf(0));
                }

            }
            if(box10==null){
                if(box20!=null) {
                    box.remove(box20);
                    box20.row = 1;
                    didMove=true;
                    box10 = box20;
                    box20=null;
                    box.add(box10);
                    boxnumber.add(8);
                    boxnumber.remove(boxnumber.indexOf(4));
                }

            }
            if(box20==null){
                if(box30!=null){
                    box.remove(box30);
                    box30.row=2;
                    box20=box30;
                    didMove=true;
                    box30=null;
                    box.add(box20);
                    boxnumber.add(12);
                    boxnumber.remove(boxnumber.indexOf(8));

                }
            }

        }

        //add column1 here

        if(box00!= null && box10!=null){
            if(box00.number==box10.number){
                didMove=true;
                //add both nuumbers and move the remaining to up
                box.remove(box00);
                box.remove(box10);
                box00.number += box10.number;

                box10=null;
                boxnumber.add(4);
                box.add(box00);
                if(box20!=null && box30==null){
                    box.remove(box20);
                    boxnumber.add(8);
                    box20.row=1;
                    box10=box20;
                    box.add(box10);
                    boxnumber.remove(boxnumber.indexOf(4));
                    box20=null;
                }
                else if(box20!=null && box30!=null){
                    box.remove(box20);
                    boxnumber.add(8);
                    box20.row=1;
                    box10=box20;
                    box.add(box10);
                    boxnumber.remove(boxnumber.indexOf(4));
                    box20=null;
                    box.remove(box30);
                    boxnumber.add(12);
                    box30.row=2;
                    box20=box30;
                    box.add(box20);
                    boxnumber.remove(boxnumber.indexOf(8));
                    box30=null;
                    if(box10!=null && box20 != null &&box10.number==box20.number ){

                        didMove=true;
                        box.remove(box10);
                        box.remove(box20);
                        box10.number += box20.number;
                        box20=null;
                        boxnumber.add(8);
                        box.add(box10);

                    }

                }
            }
            else if( box20!=null && box10!=null && box10.number ==box20.number ){
                //add both the number and shift to up
                didMove=true;
                box.remove(box10);
                box.remove(box20);
                box10.number += box20.number;
                box20=null;
                boxnumber.add(8);
                box.add(box10);
                if(box30!=null){
                    box.remove(box30);
                    boxnumber.add(12);
                    box30.row=2;
                    box20=box30;
                    box30=null;
                    box.add(box20);
                    boxnumber.remove(boxnumber.indexOf(8));

                }

            }
            else if(box20!= null && box30!=null && box20.number==box30.number ){
                //add both the number and clear left box
                didMove=true;
                box.remove(box20);
                box.remove(box30);
                box20.number += box30.number;
                box30=null;
                boxnumber.add(12);
                box.add(box20);
            }
            else if(box20!= null && box30!=null && box20.number!=box30.number ){

            }
        }

        //Second column calculatitons

        for(int i=0;i <=10;i++){
            if(box01==null){
                if(box11!=null) {
                    box.remove(box11);
                    box11.row = 0;
                    box01 = box11;
                    didMove=true;
                    box11=null;
                    box.add(box01);
                    boxnumber.add(5);
                    boxnumber.remove(boxnumber.indexOf(1));
                }

            }
            if(box11==null){
                if(box21!=null) {
                    box.remove(box21);
                    box21.row = 1;
                    didMove=true;
                    box11 = box21;
                    box21=null;
                    box.add(box11);
                    boxnumber.add(9);
                    boxnumber.remove(boxnumber.indexOf(5));
                }

            }
            if(box21==null){
                if(box31!=null){
                    box.remove(box31);
                    box31.row=2;
                    box21=box31;
                    didMove=true;
                    box31=null;
                    box.add(box21);
                    boxnumber.add(13);
                    boxnumber.remove(boxnumber.indexOf(9));

                }
            }

        }

        //add column2 here

        if(box01!= null && box11!=null) {
            if (box01.number == box11.number) {
                didMove = true;
                //add both nuumbers and move the remaining to up
                box.remove(box01);
                box.remove(box11);
                box01.number += box11.number;

                box11 = null;
                boxnumber.add(5);
                box.add(box01);
                if (box21 != null && box31 == null) {
                    box.remove(box21);
                    boxnumber.add(9);
                    box21.row = 1;
                    box11 = box21;
                    box.add(box11);
                    boxnumber.remove(boxnumber.indexOf(5));
                    box21 = null;
                } else if (box21 != null && box31 != null) {
                    box.remove(box21);
                    boxnumber.add(9);
                    box21.row = 1;
                    box11 = box21;
                    box.add(box11);
                    boxnumber.remove(boxnumber.indexOf(5));
                    box21 = null;
                    box.remove(box31);
                    boxnumber.add(13);
                    box31.row = 2;
                    box21 = box31;
                    box.add(box21);
                    boxnumber.remove(boxnumber.indexOf(9));
                    box31 = null;
                    if (box11 != null && box21 != null && box11.number == box21.number) {

                        didMove = true;
                        box.remove(box11);
                        box.remove(box21);
                        box11.number += box21.number;
                        box21 = null;
                        boxnumber.add(9);
                        box.add(box11);

                    }

                }
            } else if (box21 != null && box11 != null && box11.number == box21.number) {
                //add both the number and shift to up
                didMove = true;
                box.remove(box11);
                box.remove(box21);
                box11.number += box21.number;
                box21 = null;
                boxnumber.add(9);
                box.add(box11);
                if (box31 != null) {
                    box.remove(box31);
                    boxnumber.add(13);
                    box31.row = 2;
                    box21 = box31;
                    box31 = null;
                    box.add(box21);
                    boxnumber.remove(boxnumber.indexOf(9));

                }

            } else if (box21 != null && box31 != null && box21.number == box31.number) {
                //add both the number and clear left box
                didMove = true;
                box.remove(box21);
                box.remove(box31);
                box21.number += box31.number;
                box31 = null;
                boxnumber.add(13);
                box.add(box21);
            } else if (box21 != null && box31 != null && box21.number != box31.number) {

            }
        }

            //third column calculations

            for(int i=0;i <=10;i++){
                if(box02==null){
                    if(box12!=null) {
                        box.remove(box12);
                        box12.row = 0;
                        box02 = box12;
                        didMove=true;
                        box12=null;
                        box.add(box02);
                        boxnumber.add(6);
                        boxnumber.remove(boxnumber.indexOf(2));
                    }

                }
                if(box12==null){
                    if(box22!=null) {
                        box.remove(box22);
                        box22.row = 1;
                        didMove=true;
                        box12 = box22;
                        box22=null;
                        box.add(box12);
                        boxnumber.add(10);
                        boxnumber.remove(boxnumber.indexOf(6));
                    }

                }
                if(box22==null){
                    if(box32!=null){
                        box.remove(box32);
                        box32.row=2;
                        box22=box32;
                        didMove=true;
                        box32=null;
                        box.add(box22);
                        boxnumber.add(14);
                        boxnumber.remove(boxnumber.indexOf(10));

                    }
                }

            }

            //add column3 here

            if(box02!= null && box12!=null){
                if(box02.number==box12.number){
                    didMove=true;
                    //add both nuumbers and move the remaining to up
                    box.remove(box02);
                    box.remove(box12);
                    box02.number += box12.number;

                    box12=null;
                    boxnumber.add(6);
                    box.add(box02);
                    if(box22!=null && box32==null){
                        box.remove(box22);
                        boxnumber.add(10);
                        box22.row=1;
                        box12=box22;
                        box.add(box12);
                        boxnumber.remove(boxnumber.indexOf(6));
                        box22=null;
                    }
                    else if(box22!=null && box32!=null){
                        box.remove(box22);
                        boxnumber.add(10);
                        box22.row=1;
                        box12=box22;
                        box.add(box12);
                        boxnumber.remove(boxnumber.indexOf(6));
                        box22=null;
                        box.remove(box32);
                        boxnumber.add(14);
                        box32.row=2;
                        box22=box32;
                        box.add(box22);
                        boxnumber.remove(boxnumber.indexOf(10));
                        box32=null;
                        if(box12!=null && box22 != null &&box12.number==box22.number ){

                            didMove=true;
                            box.remove(box12);
                            box.remove(box22);
                            box12.number += box22.number;
                            box22=null;
                            boxnumber.add(10);
                            box.add(box12);

                        }

                    }
                }
                else if( box22!=null && box12!=null && box12.number ==box22.number ){
                    //add both the number and shift to up
                    didMove=true;
                    box.remove(box12);
                    box.remove(box22);
                    box12.number += box22.number;
                    box22=null;
                    boxnumber.add(10);
                    box.add(box12);
                    if(box32!=null){
                        box.remove(box32);
                        boxnumber.add(14);
                        box32.row=2;
                        box22=box32;
                        box32=null;
                        box.add(box22);
                        boxnumber.remove(boxnumber.indexOf(10));

                    }

                }
                else if(box22!= null && box32!=null && box22.number==box32.number ){
                    //add both the number and clear left box
                    didMove=true;
                    box.remove(box22);
                    box.remove(box32);
                    box22.number += box32.number;
                    box32=null;
                    boxnumber.add(14);
                    box.add(box22);
                }
                else if(box22!= null && box32!=null && box22.number!=box32.number ){

                }
            }

            //fourth column calculations

        for(int i=0;i <=10;i++){
            if(box03==null){
                if(box13!=null) {
                    box.remove(box13);
                    box13.row = 0;
                    box03 = box13;
                    didMove=true;
                    box13=null;
                    box.add(box03);
                    boxnumber.add(7);
                    boxnumber.remove(boxnumber.indexOf(3));
                }

            }
            if(box13==null){
                if(box23!=null) {
                    box.remove(box23);
                    box23.row = 1;
                    didMove=true;
                    box13 = box23;
                    box23=null;
                    box.add(box13);
                    boxnumber.add(11);
                    boxnumber.remove(boxnumber.indexOf(7));
                }

            }
            if(box23==null){
                if(box33!=null){
                    box.remove(box33);
                    box33.row=2;
                    box23=box33;
                    didMove=true;
                    box33=null;
                    box.add(box23);
                    boxnumber.add(15);
                    boxnumber.remove(boxnumber.indexOf(11));

                }
            }

        }

        //add column3 here

        if(box03!= null && box13!=null){
            if(box03.number==box13.number){
                didMove=true;
                //add both nuumbers and move the remaining to up
                box.remove(box03);
                box.remove(box13);
                box03.number += box13.number;

                box13=null;
                boxnumber.add(7);
                box.add(box03);
                if(box23!=null && box33==null){
                    box.remove(box23);
                    boxnumber.add(11);
                    box23.row=1;
                    box13=box23;
                    box.add(box13);
                    boxnumber.remove(boxnumber.indexOf(7));
                    box23=null;
                }
                else if(box23!=null && box33!=null){
                    box.remove(box23);
                    boxnumber.add(11);
                    box23.row=1;
                    box13=box23;
                    box.add(box13);
                    boxnumber.remove(boxnumber.indexOf(7));
                    box23=null;
                    box.remove(box33);
                    boxnumber.add(15);
                    box33.row=2;
                    box23=box33;
                    box.add(box23);
                    boxnumber.remove(boxnumber.indexOf(11));
                    box33=null;
                    if(box13!=null && box23 != null &&box13.number==box23.number ){

                        didMove=true;
                        box.remove(box13);
                        box.remove(box23);
                        box13.number += box23.number;
                        box23=null;
                        boxnumber.add(11);
                        box.add(box13);

                    }

                }
            }
            else if( box23!=null && box13!=null && box13.number ==box23.number ){
                //add both the number and shift to up
                didMove=true;
                box.remove(box13);
                box.remove(box23);
                box13.number += box23.number;
                box23=null;
                boxnumber.add(11);
                box.add(box13);
                if(box33!=null){
                    box.remove(box33);
                    boxnumber.add(15);
                    box33.row=2;
                    box23=box33;
                    box33=null;
                    box.add(box23);
                    boxnumber.remove(boxnumber.indexOf(11));

                }

            }
            else if(box23!= null && box33!=null && box23.number==box33.number ){
                //add both the number and clear left box
                didMove=true;
                box.remove(box23);
                box.remove(box33);
                box23.number += box33.number;
                box33=null;
                boxnumber.add(15);
                box.add(box23);
            }
            else if(box23!= null && box33!=null && box23.number!=box33.number ){

            }
        }


        if(didMove) {

            scoreCalculated=0;
            for(Box each: box){
                scoreCalculated+=each.number;
            }
            TextView score = (TextView) ((Activity)getContext()).findViewById(R.id.score);
            score.setText("SCORE\n"+scoreCalculated);

            gameOverFlag=false;
            tried=false;
            generateRandom();
        }
        else{
            if(boxnumber.size()==0){
                gameOverFlag = true;

            }
        }

        invalidate();
    }
    private void moveboxestowardsdown() {
        Boolean didMove = false;

        for (Box each : box) {
            if(each.row==0 && each.column==0 && each!=null){box00=each;}
            if(each.row==0 && each.column==1 && each!=null){box01=each;}
            if(each.row==0 && each.column==2 && each!=null){box02=each;}
            if(each.row==0 && each.column==3 && each!=null){box03=each;}
            if(each.row==1 && each.column==0 && each!=null){box10=each;}
            if(each.row==1 && each.column==1 && each!=null){box11=each;}
            if(each.row==1 && each.column==2 && each!=null){box12=each;}
            if(each.row==1 && each.column==3 && each!=null){box13=each;}
            if(each.row==2 && each.column==0 && each!=null){box20=each;}
            if(each.row==2 && each.column==1 && each!=null){box21=each;}
            if(each.row==2 && each.column==2 && each!=null){box22=each;}
            if(each.row==2 && each.column==3 && each!=null){box23=each;}
            if(each.row==3 && each.column==0 && each!=null){box30=each;}
            if(each.row==3 && each.column==1 && each!=null){box31=each;}
            if(each.row==3 && each.column==2 && each!=null){box32=each;}
            if(each.row==3 && each.column==3 && each!=null){box33=each;}
        }

        //first row Calculations
        for(int i=0;i <=10;i++){
            if(box30==null){
                if(box20!=null) {
                    box.remove(box20);
                    box20.row = 3;
                    box30 = box20;
                    didMove=true;
                    box20=null;
                    box.add(box30);
                    boxnumber.add(8);
                    boxnumber.remove(boxnumber.indexOf(12));
                }

            }
            if(box20==null){
                if(box10!=null) {
                    box.remove(box10);
                    box10.row = 2;
                    didMove=true;
                    box20 = box10;
                    box10=null;
                    box.add(box20);
                    boxnumber.add(4);
                    boxnumber.remove(boxnumber.indexOf(8));
                }

            }
            if(box10==null){
                if(box00!=null){
                    box.remove(box00);
                    box00.row=1;
                    box10=box00;
                    didMove=true;
                    box00=null;
                    box.add(box10);
                    boxnumber.add(0);
                    boxnumber.remove(boxnumber.indexOf(4));

                }
            }

        }

        //add column1 here

        if(box30!= null && box20!=null){
            if(box30.number==box20.number){
                didMove=true;
                //add both nuumbers and move the remaining to up
                box.remove(box30);
                box.remove(box20);
                box30.number += box20.number;

                box20=null;
                boxnumber.add(8);
                box.add(box30);
                if(box10!=null && box00==null){
                    box.remove(box10);
                    boxnumber.add(4);
                    box10.row=2;
                    box20=box10;
                    box.add(box20);
                    boxnumber.remove(boxnumber.indexOf(8));
                    box10=null;
                }
                else if(box10!=null && box00!=null){
                    box.remove(box10);
                    boxnumber.add(4);
                    box10.row=2;
                    box20=box10;
                    box.add(box20);
                    boxnumber.remove(boxnumber.indexOf(8));
                    box10=null;
                    box.remove(box00);
                    boxnumber.add(0);
                    box00.row=1;
                    box10=box00;
                    box.add(box10);
                    boxnumber.remove(boxnumber.indexOf(4));
                    box00=null;
                    if(box20!=null && box10 != null &&box20.number==box10.number ){

                        didMove=true;
                        box.remove(box20);
                        box.remove(box10);
                        box20.number += box10.number;
                        box10=null;
                        boxnumber.add(4);
                        box.add(box20);

                    }

                }
            }
            else if( box10!=null && box20!=null && box20.number ==box10.number ){
                //add both the number and shift to up
                didMove=true;
                box.remove(box20);
                box.remove(box10);
                box20.number += box10.number;
                box10=null;
                boxnumber.add(4);
                box.add(box20);
                if(box00!=null){
                    box.remove(box00);
                    boxnumber.add(0);
                    box00.row=1;
                    box10=box00;
                    box00=null;
                    box.add(box10);
                    boxnumber.remove(boxnumber.indexOf(4));

                }

            }
            else if(box10!= null && box00!=null && box10.number==box00.number ){
                //add both the number and clear left box
                didMove=true;
                box.remove(box10);
                box.remove(box00);
                box10.number += box00.number;
                box00=null;
                boxnumber.add(0);
                box.add(box10);
            }
            else if(box10!= null && box00!=null && box10.number!=box00.number ){

            }
        }
        //Second column calculations
        for(int i=0;i <=10;i++){
            if(box31==null){
                if(box21!=null) {
                    box.remove(box21);
                    box21.row = 3;
                    box31 = box21;
                    didMove=true;
                    box21=null;
                    box.add(box31);
                    boxnumber.add(9);
                    boxnumber.remove(boxnumber.indexOf(13));
                }

            }
            if(box21==null){
                if(box11!=null) {
                    box.remove(box11);
                    box11.row = 2;
                    didMove=true;
                    box21 = box11;
                    box11=null;
                    box.add(box21);
                    boxnumber.add(5);
                    boxnumber.remove(boxnumber.indexOf(9));
                }

            }
            if(box11==null){
                if(box01!=null){
                    box.remove(box01);
                    box01.row=1;
                    box11=box01;
                    didMove=true;
                    box01=null;
                    box.add(box11);
                    boxnumber.add(1);
                    boxnumber.remove(boxnumber.indexOf(5));

                }
            }

        }

        //add column1 here

        if(box31!= null && box21!=null){
            if(box31.number==box21.number){
                didMove=true;
                //add both nuumbers and move the remaining to up
                box.remove(box31);
                box.remove(box21);
                box31.number += box21.number;

                box21=null;
                boxnumber.add(9);
                box.add(box31);
                if(box11!=null && box01==null){
                    box.remove(box11);
                    boxnumber.add(5);
                    box11.row=2;
                    box21=box11;
                    box.add(box21);
                    boxnumber.remove(boxnumber.indexOf(9));
                    box11=null;
                }
                else if(box11!=null && box01!=null){
                    box.remove(box11);
                    boxnumber.add(5);
                    box11.row=2;
                    box21=box11;
                    box.add(box21);
                    boxnumber.remove(boxnumber.indexOf(9));
                    box11=null;
                    box.remove(box01);
                    boxnumber.add(1);
                    box01.row=1;
                    box11=box01;
                    box.add(box11);
                    boxnumber.remove(boxnumber.indexOf(5));
                    box01=null;
                    if(box21!=null && box11 != null &&box21.number==box11.number ){

                        didMove=true;
                        box.remove(box21);
                        box.remove(box11);
                        box21.number += box11.number;
                        box11=null;
                        boxnumber.add(5);
                        box.add(box21);

                    }

                }
            }
            else if( box11!=null && box21!=null && box21.number ==box11.number ){
                //add both the number and shift to up
                didMove=true;
                box.remove(box21);
                box.remove(box11);
                box21.number += box11.number;
                box11=null;
                boxnumber.add(5);
                box.add(box21);
                if(box01!=null){
                    box.remove(box01);
                    boxnumber.add(1);
                    box01.row=1;
                    box11=box01;
                    box01=null;
                    box.add(box11);
                    boxnumber.remove(boxnumber.indexOf(5));

                }

            }
            else if(box11!= null && box01!=null && box11.number==box01.number ){
                //add both the number and clear left box
                didMove=true;
                box.remove(box11);
                box.remove(box01);
                box11.number += box01.number;
                box01=null;
                boxnumber.add(1);
                box.add(box11);
            }
            else if(box11!= null && box01!=null && box11.number!=box01.number ){

            }
        }
        //Third Column Calculations

        for(int i=0;i <=10;i++){
            if(box32==null){
                if(box22!=null) {
                    box.remove(box22);
                    box22.row = 3;
                    box32 = box22;
                    didMove=true;
                    box22=null;
                    box.add(box32);
                    boxnumber.add(10);
                    boxnumber.remove(boxnumber.indexOf(14));
                }

            }
            if(box22==null){
                if(box12!=null) {
                    box.remove(box12);
                    box12.row = 2;
                    didMove=true;
                    box22 = box12;
                    box12=null;
                    box.add(box22);
                    boxnumber.add(6);
                    boxnumber.remove(boxnumber.indexOf(10));
                }

            }
            if(box12==null){
                if(box02!=null){
                    box.remove(box02);
                    box02.row=1;
                    box12=box02;
                    didMove=true;
                    box02=null;
                    box.add(box12);
                    boxnumber.add(2);
                    boxnumber.remove(boxnumber.indexOf(6));

                }
            }

        }

        //add column1 here

        if(box32!= null && box22!=null){
            if(box32.number==box22.number){
                didMove=true;
                //add both nuumbers and move the remaining to up
                box.remove(box32);
                box.remove(box22);
                box32.number += box22.number;

                box22=null;
                boxnumber.add(10);
                box.add(box32);
                if(box12!=null && box02==null){
                    box.remove(box12);
                    boxnumber.add(6);
                    box12.row=2;
                    box22=box12;
                    box.add(box22);
                    boxnumber.remove(boxnumber.indexOf(10));
                    box12=null;
                }
                else if(box12!=null && box02!=null){
                    box.remove(box12);
                    boxnumber.add(6);
                    box12.row=2;
                    box22=box12;
                    box.add(box22);
                    boxnumber.remove(boxnumber.indexOf(10));
                    box12=null;
                    box.remove(box02);
                    boxnumber.add(2);
                    box02.row=1;
                    box12=box02;
                    box.add(box12);
                    boxnumber.remove(boxnumber.indexOf(6));
                    box02=null;
                    if(box22!=null && box12 != null &&box22.number==box12.number ){

                        didMove=true;
                        box.remove(box22);
                        box.remove(box12);
                        box22.number += box12.number;
                        box12=null;
                        boxnumber.add(6);
                        box.add(box22);

                    }

                }
            }
            else if( box12!=null && box22!=null && box22.number ==box12.number ){
                //add both the number and shift to up
                didMove=true;
                box.remove(box22);
                box.remove(box12);
                box22.number += box12.number;
                box12=null;
                boxnumber.add(6);
                box.add(box22);
                if(box02!=null){
                    box.remove(box02);
                    boxnumber.add(2);
                    box02.row=1;
                    box12=box02;
                    box02=null;
                    box.add(box12);
                    boxnumber.remove(boxnumber.indexOf(6));

                }

            }
            else if(box12!= null && box02!=null && box12.number==box02.number ){
                //add both the number and clear left box
                didMove=true;
                box.remove(box12);
                box.remove(box02);
                box12.number += box02.number;
                box02=null;
                boxnumber.add(2);
                box.add(box12);
            }
            else if(box12!= null && box02!=null && box12.number!=box02.number ){

            }
        }
        //Fourth Column calculations

        for(int i=0;i <=10;i++){
            if(box33==null){
                if(box23!=null) {
                    box.remove(box23);
                    box23.row = 3;
                    box33 = box23;
                    didMove=true;
                    box23=null;
                    box.add(box33);
                    boxnumber.add(11);
                    boxnumber.remove(boxnumber.indexOf(15));
                }

            }
            if(box23==null){
                if(box13!=null) {
                    box.remove(box13);
                    box13.row = 2;
                    didMove=true;
                    box23 = box13;
                    box13=null;
                    box.add(box23);
                    boxnumber.add(7);
                    boxnumber.remove(boxnumber.indexOf(11));
                }

            }
            if(box13==null){
                if(box03!=null){
                    box.remove(box03);
                    box03.row=1;
                    box13=box03;
                    didMove=true;
                    box03=null;
                    box.add(box13);
                    boxnumber.add(3);
                    boxnumber.remove(boxnumber.indexOf(7));

                }
            }

        }

        //add column1 here

        if(box33!= null && box23!=null){
            if(box33.number==box23.number){
                didMove=true;
                //add both nuumbers and move the remaining to up
                box.remove(box33);
                box.remove(box23);
                box33.number += box23.number;

                box23=null;
                boxnumber.add(11);
                box.add(box33);
                if(box13!=null && box03==null){
                    box.remove(box13);
                    boxnumber.add(7);
                    box13.row=2;
                    box23=box13;
                    box.add(box23);
                    boxnumber.remove(boxnumber.indexOf(11));
                    box13=null;
                }
                else if(box13!=null && box03!=null){
                    box.remove(box13);
                    boxnumber.add(7);
                    box13.row=2;
                    box23=box13;
                    box.add(box23);
                    boxnumber.remove(boxnumber.indexOf(11));
                    box13=null;
                    box.remove(box03);
                    boxnumber.add(3);
                    box03.row=1;
                    box13=box03;
                    box.add(box13);
                    boxnumber.remove(boxnumber.indexOf(7));
                    box03=null;
                    if(box23!=null && box13 != null &&box23.number==box13.number ){

                        didMove=true;
                        box.remove(box23);
                        box.remove(box13);
                        box23.number += box13.number;
                        box13=null;
                        boxnumber.add(7);
                        box.add(box23);

                    }

                }
            }
            else if( box13!=null && box23!=null && box23.number ==box13.number ){
                //add both the number and shift to up
                didMove=true;
                box.remove(box23);
                box.remove(box13);
                box23.number += box13.number;
                box13=null;
                boxnumber.add(7);
                box.add(box23);
                if(box03!=null){
                    box.remove(box03);
                    boxnumber.add(3);
                    box03.row=1;
                    box13=box03;
                    box03=null;
                    box.add(box13);
                    boxnumber.remove(boxnumber.indexOf(7));

                }

            }
            else if(box13!= null && box03!=null && box13.number==box03.number ){
                //add both the number and clear left box
                didMove=true;
                box.remove(box13);
                box.remove(box03);
                box13.number += box03.number;
                box03=null;
                boxnumber.add(3);
                box.add(box13);
            }
            else if(box13!= null && box03!=null && box13.number!=box03.number ){

            }
        }
        if(didMove) {

            scoreCalculated=0;
            for(Box each: box){
                scoreCalculated+=each.number;
            }
            TextView score = (TextView) ((Activity)getContext()).findViewById(R.id.score);
            score.setText("SCORE\n"+scoreCalculated);
            
            if(boxnumber.size()==1) {
                lastboxinder = boxnumber.get(0);
            }
            if(boxnumber.size()==0) {
                checkgameover(lastboxinder);
            }


            gameOverFlag=false;
            tried=false;
            generateRandom();
        }
        else{
            if(boxnumber.size()==0){
                gameOverFlag = true;

            }
        }

        invalidate();

    }

    private void checkgameover(int lastboxindex) {
        for (Box each : box) {
            if(each.row==0 && each.column==0 && each!=null){box00=each;}
            if(each.row==0 && each.column==1 && each!=null){box01=each;}
            if(each.row==0 && each.column==2 && each!=null){box02=each;}
            if(each.row==0 && each.column==3 && each!=null){box03=each;}
            if(each.row==1 && each.column==0 && each!=null){box10=each;}
            if(each.row==1 && each.column==1 && each!=null){box11=each;}
            if(each.row==1 && each.column==2 && each!=null){box12=each;}
            if(each.row==1 && each.column==3 && each!=null){box13=each;}
            if(each.row==2 && each.column==0 && each!=null){box20=each;}
            if(each.row==2 && each.column==1 && each!=null){box21=each;}
            if(each.row==2 && each.column==2 && each!=null){box22=each;}
            if(each.row==2 && each.column==3 && each!=null){box23=each;}
            if(each.row==3 && each.column==0 && each!=null){box30=each;}
            if(each.row==3 && each.column==1 && each!=null){box31=each;}
            if(each.row==3 && each.column==2 && each!=null){box32=each;}
            if(each.row==3 && each.column==3 && each!=null){box33=each;}
        }
        
        switch (lastboxindex){
            case 0:
                if (box00.number==box01.number|| box00.number == box10.number){
                    //not game over
                }
                else{
                    Intent gameover = new Intent(getContext(), GameOver.class);
                    gameover.putExtra("clickeduser",scoreCalculated);
                    ((Activity)getContext()).startActivity(gameover);
                    ((Activity)getContext()).finish();
                }
                break;
            case 1:
                if(box01.number==box00.number||box01.number==box11.number|| box01.number==box02.number){}
                else{
                    Intent gameover = new Intent(getContext(), GameOver.class);
                    gameover.putExtra("clickeduser",scoreCalculated);
                    ((Activity)getContext()).startActivity(gameover);
                    ((Activity)getContext()).finish();
                    
                }break;
            case 2:
                if(box02.number==box01.number||box02.number==box12.number|| box02.number==box03.number){}
                else{
                    Intent gameover = new Intent(getContext(), GameOver.class);
                    gameover.putExtra("clickeduser",scoreCalculated);
                    ((Activity)getContext()).startActivity(gameover);
                    ((Activity)getContext()).finish();

                }break;
            case 3:
                if(box03.number==box02.number||box03.number==box13.number){}
                else{
                    Intent gameover = new Intent(getContext(), GameOver.class);
                    gameover.putExtra("clickeduser",scoreCalculated);
                    ((Activity)getContext()).startActivity(gameover);
                    ((Activity)getContext()).finish();

                }break;
            case 4:
                if(box10.number==box00.number||box10.number==box11.number|| box10.number==box20.number){}
                else{
                    Intent gameover = new Intent(getContext(), GameOver.class);
                    gameover.putExtra("clickeduser",scoreCalculated);
                    ((Activity)getContext()).startActivity(gameover);
                    ((Activity)getContext()).finish();

                }break;
            case 7:
                if(box13.number==box03.number||box13.number==box12.number|| box13.number==box23.number){}
                else{
                    Intent gameover = new Intent(getContext(), GameOver.class);
                    gameover.putExtra("clickeduser",scoreCalculated);
                    ((Activity)getContext()).startActivity(gameover);
                    ((Activity)getContext()).finish();

                }break;
            
            case 8:
                if(box20.number==box10.number||box20.number==box21.number|| box20.number==box30.number){}
            else{
                Intent gameover = new Intent(getContext(), GameOver.class);
                gameover.putExtra("clickeduser",scoreCalculated);
                ((Activity)getContext()).startActivity(gameover);
                ((Activity)getContext()).finish();

            }break;
            case 11:
                if(box23.number==box13.number||box23.number==box22.number|| box23.number==box33.number){}
            else{
                Intent gameover = new Intent(getContext(), GameOver.class);
                gameover.putExtra("clickeduser",scoreCalculated);
                ((Activity)getContext()).startActivity(gameover);
                ((Activity)getContext()).finish();

            }break;
            case 12:
                if(box30.number==box20.number||box30.number==box31.number){}
                else{
                    Intent gameover = new Intent(getContext(), GameOver.class);
                    gameover.putExtra("clickeduser",scoreCalculated);
                    ((Activity)getContext()).startActivity(gameover);
                    ((Activity)getContext()).finish();

                }break;
            case 13:
                if(box31.number==box30.number||box31.number==box21.number|| box31.number==box32.number){}
                else{
                    Intent gameover = new Intent(getContext(), GameOver.class);
                    gameover.putExtra("clickeduser",scoreCalculated);
                    ((Activity)getContext()).startActivity(gameover);
                    ((Activity)getContext()).finish();

                }break;
            case 14:
                if(box32.number==box31.number||box32.number==box22.number|| box32.number==box33.number){}
                else{
                    Intent gameover = new Intent(getContext(), GameOver.class);
                    gameover.putExtra("clickeduser",scoreCalculated);
                    ((Activity)getContext()).startActivity(gameover);
                    ((Activity)getContext()).finish();

                }break;
            case 15:
                if(box33.number==box23.number||box33.number==box32.number){}
                else{
                    Intent gameover = new Intent(getContext(), GameOver.class);
                    gameover.putExtra("clickeduser",scoreCalculated);
                    ((Activity)getContext()).startActivity(gameover);
                    ((Activity)getContext()).finish();

                }break;
            
        }
        
        
        
        
    }

    protected void onMeasure(int wid, int heigh){
        int width = MeasureSpec.getSize(wid);
        setMeasuredDimension(width,width);
    }
}