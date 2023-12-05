package com.example.cscb07_project;

import android.content.Intent;
import android.os.Bundle;

import com.example.cscb07_project.events.Event;
import com.example.cscb07_project.events.EventAdapter;
import com.example.cscb07_project.events.EventList;
import com.example.cscb07_project.post.POStActivityBasic;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.View;


import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cscb07_project.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.view.MenuItem;
import android.widget.Button;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    FirebaseAuth auth;
    FirebaseUser user;
    BottomNavigationView bottomNavigationView;
    View announcement, events;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        user = auth.getCurrentUser();

        if(user == null){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

        announcement = findViewById(R.id.announcement);
        events = findViewById(R.id.events);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.action_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.action_home) {
                    return true;
                } else if (itemId == R.id.action_post) {
                    navigateToPage(POStActivityBasic.class);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.action_complaints) {
                    navigateToPage(UserComplaintActivityScreen.class);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.action_events) {
                    navigateToPage(EventList.class);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.action_profile) {
                    navigateToPage(Profile.class);
                    overridePendingTransition(0, 0);
                    return true;
                } else {
                    return false;
                }
            }
        });

        announcement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToPage(AnnouncementList.class);
            }
        });

        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToPage(EventList.class);
            }
        });

        fetchLast2Events(new Callback<ArrayList<Event>>() {
            @Override
            public void onSuccess(ArrayList<Event> events) {
                // Display the last 3 events
                displayEvents(events);
            }

            @Override
            public void onFailure(Exception e) {
                // Handle the failure, e.g., log the error
                Log.e("FirebaseData", "Error fetching events", e);
            }
        });

        fetchLast3Announcements(new Callback<ArrayList<Announcements>>() {
            @Override
            public void onSuccess(ArrayList<Announcements> announcements) {
                // Display the last 2 announcements
                displayAnnouncements(announcements);
            }

            @Override
            public void onFailure(Exception e) {
                // Handle the failure, e.g., log the error
                Log.e("FirebaseData", "Error fetching announcements", e);
            }
        });


    }

    private void fetchLast2Events(Callback<ArrayList<Event>> callback) {
        DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference("Events");

        eventsRef.orderByKey().limitToLast(4).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Event> events = new ArrayList<>();

                for (DataSnapshot eventSnapshot : snapshot.getChildren()) {
                    Log.d("RawData", "Raw Data: " + eventSnapshot.toString());
                    Event event = eventSnapshot.getValue(Event.class);
                    String eventId = eventSnapshot.getKey();
                    event.setEventId(eventId);
                    Log.d("RawData", "Raw Data event id: " + event.getEventId());
                    if (event != null) {
                        events.add(event);
                    }
                }

                // Invoke the callback with the fetched events
                callback.onSuccess(events);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
                callback.onFailure(error.toException());
            }
        });
    }

    private void fetchLast3Announcements(Callback<ArrayList<Announcements>> callback) {
        DatabaseReference announcementsRef = FirebaseDatabase.getInstance().getReference("Announcements");

        announcementsRef.orderByKey().limitToLast(4).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Announcements> announcements = new ArrayList<>();

                for (DataSnapshot announcementSnapshot : snapshot.getChildren()) {
                    Log.d("RawData", "Raw Data: " + announcementSnapshot.toString());
                    Announcements announcement = announcementSnapshot.getValue(Announcements.class);
                    String announcementId = announcementSnapshot.getKey();
                    announcement.setAnnouncementId(announcementId);
                    if (announcement != null) {
                        announcements.add(announcement);
                    }
                }

                // Invoke the callback with the fetched announcements
                callback.onSuccess(announcements);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
                callback.onFailure(error.toException());
            }
        });
    }

    private void displayEvents(ArrayList<Event> events) {
        // Update your UI to display the events
        RecyclerView recyclerView = findViewById(R.id.events_home_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        MainEventAdapter eventAdapter = new MainEventAdapter(this, events);
        recyclerView.setAdapter(eventAdapter);
    }

    private void displayAnnouncements(ArrayList<Announcements> announcementsList) {
        // Update your UI to display the announcements
        RecyclerView recyclerView = findViewById(R.id.announcements_home_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MainAnnouncementAdapter announcementAdapter = new MainAnnouncementAdapter(this, announcementsList);
        recyclerView.setAdapter(announcementAdapter);
    }


    void navigateToPage(Class<?> destinationClass) {
        finish();
        Intent intent = new Intent(getApplicationContext(), destinationClass);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


}