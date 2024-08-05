
package com.example.fitplan.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fitplan.Adapter.ExerciseAdapter;
import com.example.fitplan.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectWorkoutWeekFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectWorkoutWeekFragment extends Fragment {

    private static final String API_HOST = "exercisedb.p.rapidapi.com";
    private static final String API_KEY = "ccd5887d03msh23f8ae122bdc395p1169eajsn81eb9e23195b";
    private RecyclerView recyclerView;
    private Spinner weekNoSpinner;
    private ExerciseAdapter exerciseAdapter;
    private String[] monthNames = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_create_workout_plan, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.fragment_year_month, null);


        monthNames  = new String[] {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };
        weekNoSpinner = dialogView.findViewById(R.id.extra_selection_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.extra_selection_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weekNoSpinner.setAdapter(adapter);
        showYearMonthPicker(dialogView);
        return view;
    }

    private void showYearMonthPicker(View view) {
        // Inflate the dialog layout
       NumberPicker yearPicker = view.findViewById(R.id.year_picker);
        NumberPicker monthPicker = view.findViewById(R.id.month_picker);


        if (weekNoSpinner == null) {
            Toast.makeText(view.getContext(), "Week Spinner is null", Toast.LENGTH_SHORT).show();
            return;
        }

        // Setup year picker
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        yearPicker.setMinValue(1970); // Set minimum year
        yearPicker.setMaxValue(currentYear + 10); // Set maximum year
        yearPicker.setValue(currentYear); // Set current year as default

        // Setup month picker
        monthPicker.setMinValue(0); // January
        monthPicker.setMaxValue(11); // December
        monthPicker.setValue(1); // January as default
        monthPicker.setDisplayedValues(monthNames);
        // Build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setView(view)
                .setTitle("Select Year, Month, Week For Your WorkoutPlan")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int selectedYear = yearPicker.getValue();
                        int selectedMonth = monthPicker.getValue();
                        String selectedDate = selectedYear + "-" + (selectedMonth + 1); // Example format
                        Toast.makeText(view.getContext(), "Selected date: " + selectedDate, Toast.LENGTH_LONG).show();
                        String weekNo = weekNoSpinner.getSelectedItem().toString();
                       navigateToCreateWorkout(selectedYear, selectedMonth,weekNo);
                        // Handle the selected year and month
                        // For example, you can pass the selected values to another method or perform further actions
                    }
                })
                .setCancelable(false) // Prevent dialog from being dismissed by tapping outside
                .show();
    }

    private void navigateToCreateWorkout(int selectedYear, int selectedMonth, String weekNo) {
        // Replace current fragment with a new fragment
        Fragment createWorkoutPlanFragmentCopy = new CreateWorkoutPlanFragment();

        Bundle result = new Bundle();
        result.putInt("selectedYear", selectedYear);
        result.putInt("selectedMonth", selectedMonth);
        result.putString("weekNo", weekNo);

        createWorkoutPlanFragmentCopy.setArguments(result);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager() ;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, createWorkoutPlanFragmentCopy);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
