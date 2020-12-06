package com.teamnine.protest;

import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddBottomSheet extends BottomSheetDialogFragment {

    public String passedEventId = "";

    public void setPassedEventId(String passedEventId) {
        this.passedEventId = passedEventId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.add_bottom_sheet,
                container, false);

        Button finishButton = (Button) v.findViewById(R.id.finish_button);
        Button cancelButton = (Button) v.findViewById(R.id.cancel_add_update);
        final ProgressBar progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        final EditText descriptionEditText = (EditText) v.findViewById(R.id.update_edit_text);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                ProtestFeed newFeed = new ProtestFeed("", "", "", "", "");

                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                String key = mDatabase.child("updates").push().getKey();

                newFeed.setId(key);
                if (descriptionEditText.getText().toString() != "") {
                    newFeed.setDescription(descriptionEditText.getText().toString());
                }

                if (currentUID != null && currentUID != "") {
                    newFeed.setOwner(currentUID);
                }

                newFeed.setEvent_id(passedEventId);

                SimpleDateFormat formatter= new SimpleDateFormat("MM.dd 'at' HH:mm");
                Date date = new Date(System.currentTimeMillis());
                newFeed.setTime(formatter.format(date));

                mDatabase.child("updates").child(newFeed.getId()).setValue(newFeed);

                if (passedEventId != "") {
                    mDatabase.child("events").child(newFeed.getEvent_id()).child("latest_update").setValue(newFeed.getId());
                }

                progressBar.setVisibility(View.VISIBLE);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // Actions to do after 10 seconds
                        dismiss();
                    }
                }, 1500);
            }
        });

        return v;
    }
}
