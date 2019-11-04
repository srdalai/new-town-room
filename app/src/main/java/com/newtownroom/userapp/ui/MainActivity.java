package com.newtownroom.userapp.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.newtownroom.userapp.R;
import com.newtownroom.userapp.utils.Utilities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout parentFrame;
    BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parentFrame = findViewById(R.id.container);
        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment, R.id.bookingsFragment, R.id.accountFragment)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do You want to exit the App?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity();
            }
        });

        builder.setNegativeButton("No", null);
        builder.create().show();
    }

    public void showSnack(String message, int length) {
        Snackbar snack = Snackbar.make(parentFrame, message, length);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snack.getView().getLayoutParams();

        params.setMargins(getSnackBarMargin()[0], getSnackBarMargin()[1], getSnackBarMargin()[2], getSnackBarMargin()[3]);
        snack.getView().setLayoutParams(params);
        snack.show();
    }

    public int[] getSnackBarMargin() {
        int marginLeft = Utilities.convertDpToPixel(this, 4);
        int marginRight = Utilities.convertDpToPixel(this, 4);
        int marginTop = Utilities.convertDpToPixel(this, 4);
        int marginBottom = navView.getHeight() + Utilities.convertDpToPixel(this, 4);
        int[] marginArray = new int[4];
        marginArray[0] = marginLeft;
        marginArray[1] = marginTop;
        marginArray[2] = marginRight;
        marginArray[3] = marginBottom;
        return marginArray;
    }

    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
        showExitDialog();
    }
}
