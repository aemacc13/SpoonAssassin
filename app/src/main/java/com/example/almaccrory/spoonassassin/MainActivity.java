package com.example.almaccrory.spoonassassin;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    /* Button containers */
    private LinearLayout column_one= null;
    private LinearLayout column_two= null;
    private LinearLayout column_three= null;

    /* Button */
    private Button generate= null;

    /* Lower layout */
    private LinearLayout lower_layout= null;
    private TextView prompt= null;
    private TextView target= null;
    private Button back= null;
    private Button reveal= null;
    private Button next= null;


    /* Participants */
    private ArrayList<String> activePlayers;
    private ArrayList<String> assignmentList;
    private ArrayList<String> nameList;
    private ArrayList<Boolean> buttonclicked;
    private String[] staffMembers= {"Dipper", "Seabass", "Captain", "Mclaren", "Peridot", "BFG", "SRK", "Henry",
                                    "TRS", "Noodles", "Shogun", "Dash", "Robocop", "Cowboy", "AgentP", "Ace",
                                    "C4tbus", "Rhino", "KIZR", "KingPapaya", "Nami",  "Zion", "SilverFox", "Thor",
                                    "Patriot"};

    /* Colors */
    private int lightGreen= Color.rgb(126, 229, 146);
    private int lightRed= Color.rgb(249, 145, 145);

    private int count= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Button containers */
        column_one= (LinearLayout)findViewById(R.id.column_one);
        column_two= (LinearLayout)findViewById(R.id.column_two);
        column_three= (LinearLayout)findViewById(R.id.column_three);

        /* Button */
        generate= (Button)findViewById(R.id.generate);

        /* Lower layout */
        lower_layout= (LinearLayout)findViewById(R.id.lower_layout);

        /* Initialize lower layout fields */
        prompt= (TextView)findViewById(R.id.prompt);
        target= (TextView)findViewById(R.id.target);
        back= (Button)findViewById(R.id.back);
        reveal= (Button)findViewById(R.id.reveal);
        next= (Button)findViewById(R.id.next);

        /* Generate list of who is participating */
        activePlayers= new ArrayList();
        assignmentList= new ArrayList();
        nameList= new ArrayList();
        buttonclicked= new ArrayList();

        /* Button listeners */
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* Shuffle players */
                Collections.shuffle(activePlayers);
                /* Assign targets */
                targetAssignment();
                /* Set Layout to visible */
                lower_layout.setVisibility(View.VISIBLE);
                /* First assignment */
                count= 0;
                prompt.setText(nameList.get(count).toString() + " press 'REVEAL' to see your target");
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count > 0 && count < nameList.size()){
                    count--;
                    prompt.setText(nameList.get(count).toString() + " press 'REVEAL' to see your target");
                    target.setText("");
                } else if (count == 0){
                    prompt.setText(nameList.get(count).toString() + " press 'REVEAL' to see your target");
                    target.setText("");
                } else {
                    count--;
                }
            }
        });

        reveal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count >= 0 && count < assignmentList.size()){
                    prompt.setText("");
                    target.setText(assignmentList.get(count).toString());
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                if (count < nameList.size()){
                    prompt.setText(nameList.get(count).toString() + " press 'REVEAL' to see your target");
                    target.setText("");
                } else {
                    prompt.setText("Let the games begin");
                    target.setText("");
                    count--;
                }
            }
        });

        /* button appearance */
        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(15, 15, 15, 15);

        /* Create buttons for each possible players */
        for (int i= 0; i < staffMembers.length; i++){
            final Button button= new Button(this);

            /* Button appearance */
            button.setText(staffMembers[i]);
            button.setLayoutParams(params);

            /* Has button been clicked? */
            buttonclicked.add(false);
            final int iFinal= i;

            /* Divide buttons into columns */
            if (i % 3 == 0) {
                column_one.addView(button);
            } else if (i % 3 == 1) {
                column_two.addView(button);

            } else if (i % 3 == 2) {
                column_three.addView(button);
            }

            /* Button listeners */
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (buttonclicked.get(iFinal)){
                        activePlayers.remove(staffMembers[iFinal]);
                        button.setBackgroundColor(lightRed);
                        buttonclicked.set(iFinal, false);

                    } else {
                        activePlayers.add(staffMembers[iFinal]);
                        button.setBackgroundColor(lightGreen);
                        buttonclicked.set(iFinal, true);
                    }
                }
            });
        }
    }

    public void targetAssignment(){

        assignmentList.clear();
        nameList.clear();

        String assignment= "";

        //assign targets
        for (int i= 0; i < activePlayers.size(); i++){
            if (i == activePlayers.size() - 1)
                assignment= (activePlayers.get(i).toString() + " your target is: " + activePlayers.get(0).toString());
            else
                assignment= (activePlayers.get(i).toString() + " your target is: " + activePlayers.get(i + 1).toString());

            assignmentList.add(assignment);
            Collections.shuffle(assignmentList);
        }



        //who is up next
        StringBuilder nextName= new StringBuilder("");

        for (int i= 0; i < assignmentList.size(); i++) {
            for (int j= 0; assignmentList.get(i).toString().charAt(j) != ' '; j++){
                nextName.append(assignmentList.get(i).charAt(j));
            }

            nameList.add(nextName.toString());
            nextName.delete(0, nextName.length());
        }

        //reveal targets
//        Scanner scanner= new Scanner(System.in);
//
//        for (int i= 0; i < assignmentList.size(); i++) {
//            /* */
//            if (i == 0)
//                System.out.println(nameList.get(i).toString() + " is the first discover their fate\n");
//            else
//                System.out.println("Please send " + nameList.get(i).toString() + " in next to discover their fate\n\n\n\n");
//
//            System.out.println("Press 'enter' to reveal a target:");
//            scanner.nextLine();
//
//            System.out.println("****** " + assignmentList.get(i).toString() + " ******\n");
//            System.out.println("Now press 'enter' three times to hide your target");
//            scanner.nextLine();scanner.nextLine();scanner.nextLine();
//
//        }
    }

}
