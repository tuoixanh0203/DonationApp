package ie.app.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import ie.app.activities.databinding.ActivityDonateBinding;
import ie.app.activities.databinding.ContentDonateBinding;
import ie.app.api.DonationApi;
import ie.app.models.Donation;

import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class Donate extends Base {
    private ContentDonateBinding contentDonateBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ie.app.activities.databinding.ActivityDonateBinding donateBinding = ActivityDonateBinding.inflate(getLayoutInflater());
        contentDonateBinding = donateBinding.contentDonate;
        setContentView(donateBinding.getRoot());

        setSupportActionBar(donateBinding.toolbar);

        donateBinding.fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        contentDonateBinding.amountPicker.setMinValue(0);
        contentDonateBinding.amountPicker.setMaxValue(1000);
        contentDonateBinding.progressBar.setMax(10000);
        contentDonateBinding.progressBar.setProgress(app.totalDonated);
        contentDonateBinding.totalDonated.setText(getString(R.string.totalDonated, app.totalDonated));

        contentDonateBinding.donateButton.setOnClickListener(view -> {
            int amount = contentDonateBinding.amountPicker.getValue();
            int radioId = contentDonateBinding.paymentMethod.getCheckedRadioButtonId();
            String method = radioId == R.id.PayPal ? "PayPal" : "Direct";

            if (amount == 0) {
                Toast toast = Toast.makeText(this, "Donate must be greater than 0", Toast.LENGTH_SHORT);
                toast.show();
            }

            if (amount > 0) {
                new InsertTask(this).execute("/add.php", new Donation(amount, method,0));
                new GetAllTask(this).execute("/getAll.php");

                contentDonateBinding.progressBar.setProgress(app.totalDonated);
                contentDonateBinding.totalDonated.setText(getString(R.string.totalDonated, app.totalDonated));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        new GetAllTask(this).execute("/getAll.php");
    }

    @Override
    public void reset(MenuItem item) {
        // app.dbManager.reset();
        onReset();
    }

    private class GetAllTask extends AsyncTask<String, Void, List<Donation>> {

        protected ProgressDialog dialog;
        protected Context context;

        public GetAllTask(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Retrieving Donations List");
            this.dialog.show();
        }

        @Override
        protected List<Donation> doInBackground(String... params) {
            try {
                Log.v("Donate", "Donation App Getting All Donations" + params[0]);
                return DonationApi.getAll(params[0]);
            }
            catch (Exception e) {
                Log.v("Donate", "ERROR : " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Donation> result) {
            super.onPostExecute(result);

            app.donations = result;
            app.totalDonated = 0;

            for (Donation donation: app.donations) {
                app.totalDonated += donation.amount;
            }

            contentDonateBinding.progressBar.setProgress(app.totalDonated);
            contentDonateBinding.totalDonated.setText("$" + app.totalDonated);

            if (dialog.isShowing())
                dialog.dismiss();
        }
    }

    private class InsertTask extends AsyncTask<Object, Void, String> {
        protected ProgressDialog dialog;
        protected Context context;

        public InsertTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Saving Donation...");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(Object... params) {
            String res = null;
            try {
                Log.v("Donate", "Donation App Inserting");
                res = DonationApi.insert((String) params[0], (Donation) params[1]);
            } catch(Exception e) {
                Log.v("Donate","ERROR : " + e);
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (dialog.isShowing())
                dialog.dismiss();
        }
    }

    public void onReset() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete ALL Donations?");
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setMessage("Are you sure you want to delete ALL the donations ?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", (dialog, id) -> {

            new ResetTask(Donate.this).execute("/reset.php");
            app.donations.clear();
            app.totalDonated = 0;
            contentDonateBinding.totalDonated.setText("$" + app.totalDonated);
        }).setNegativeButton("No", (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }

    private class ResetTask extends AsyncTask<Object, Void, String> {
        protected ProgressDialog dialog;
        protected Context context;

        public ResetTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Deleting Donations...");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(Object... params) {
            String res = null;
            try {
                res = DonationApi.deleteAll((String)params[0]);
            } catch(Exception e) {
                Log.v("Donate"," RESET ERROR : " + e);
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            app.totalDonated = 0;
            contentDonateBinding.progressBar.setProgress(app.totalDonated);
            contentDonateBinding.totalDonated.setText("$" + app.totalDonated);

            if (dialog.isShowing())
                dialog.dismiss();
        }
    }
}