package ie.app.main;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ie.app.database.DBManager;
import ie.app.models.Donation;

public class DonationApp extends Application {
    public final int target = 10000;
    public int totalDonated = 0;
    public List<Donation> donations = new ArrayList<>();
//     public DBManager dbManager;

    public boolean newDonation(Donation donation) {
        boolean targetAchieved = totalDonated + donation.amount > target;

        if (!targetAchieved) {
            donations.add(donation);
            totalDonated += donation.amount;
        } else {
            Toast toast = Toast.makeText(this, "Target Exceeded", Toast.LENGTH_SHORT);
            toast.show();
        }

        return targetAchieved;
    }

//    public boolean newDonation(Donation donation)
//    {
//        boolean targetAchieved = totalDonated > target;
//        if (!targetAchieved)
//        {
//            dbManager.add(donation);
//            totalDonated += donation.amount;
//        }
//        else
//        {
//            Toast.makeText(this, "Target Exceeded!", Toast.LENGTH_SHORT).show();
//        }
//        return targetAchieved;
//    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("Donate", "Donate Application Started!");
        // dbManager = new DBManager(this);
    }
}
