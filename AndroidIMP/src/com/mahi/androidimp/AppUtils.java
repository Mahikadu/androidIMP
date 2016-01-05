package com.mahi.androidimp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.widget.Toast;

public class AppUtils {

	/*
	 * Parse ApplicationId and ClientKey
	 */
	public static Context context;
	
	public static String ApplicationId = "bEsMWScddlBYLy0JwRq3cFGSDnvF8mqkoTRixiMG";
	public static String ClientKey = "d5fDVwFNkmY6VOMXiZlUFpIF6ENJriHNviScK29m";

	// For Checking the Internet
		public static boolean isNetworkAvailable(Context context) {
			// Get Connectivity Manager class object from Systems Service
			AppUtils.context=context;
			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			// Get Network Info from connectivity Manager
			NetworkInfo networkInfo = cm.getActiveNetworkInfo();

			// if no network is available networkInfo will be null
			// otherwise check if we are connected
			if (networkInfo != null && networkInfo.isConnected()) {
				return true;
			}
			return false;
		}

	public static void showToast(Context context, String message) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	public static void ShowAlertDialog()
	{
		final AlertDialog alertDialog = new AlertDialog.Builder(
				AppUtils.context).create();
alertDialog.setTitle("Keine Internetverbindung");
alertDialog.setMessage("Bitte mit dem Internet verbinden um fortzufahren");
alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
        	alertDialog.dismiss();
        }
});
alertDialog.show();
	}
	
	public static void MyShowAlertDialog(String message)
	{
		final AlertDialog alertDialog = new AlertDialog.Builder(
				AppUtils.context).create();
alertDialog.setTitle("Message");
alertDialog.setMessage(message);
alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
        	alertDialog.dismiss();
        }
});
alertDialog.show();
	}
	
}
