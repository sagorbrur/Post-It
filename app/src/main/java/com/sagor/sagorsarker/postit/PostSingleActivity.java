package com.sagor.sagorsarker.postit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PostSingleActivity extends AppCompatActivity {

    private ImageView postImage;
    private TextView postTitle;
    private TextView postDesc;

    private Button deletePostBtn;


    private String mPostKey = null;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_single);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");

        mAuth = FirebaseAuth.getInstance();

        mPostKey = getIntent().getExtras().getString("post_id");

        postImage = (ImageView) findViewById(R.id.postimage_show);
        postTitle = (TextView) findViewById(R.id.posttitle_show);
        postDesc = (TextView) findViewById(R.id.postdesc_show);

        deletePostBtn = (Button) findViewById(R.id.post_delete);


        mDatabase.child(mPostKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String post_title = (String) dataSnapshot.child("title").getValue();
                String post_desc = (String) dataSnapshot.child("desc").getValue();
                String post_img = (String) dataSnapshot.child("image").getValue();
                String uid = (String) dataSnapshot.child("uid").getValue();

                postTitle.setText(post_title);
                postDesc.setText(post_desc);
                Picasso.with(PostSingleActivity.this).load(post_img).into(postImage);

                if(mAuth.getCurrentUser().getUid().equals(uid) ){
                    deletePostBtn.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        deletePostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child(mPostKey).removeValue();

                Intent mainIntent = new Intent(PostSingleActivity.this,MainActivity.class);
                startActivity(mainIntent);
            }
        });


    }



}
