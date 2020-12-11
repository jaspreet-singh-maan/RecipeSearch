package com.project.recipesearch.ui.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.project.recipesearch.ContentFiles;
import com.project.recipesearch.CostumAdapter;
import com.project.recipesearch.MainActivity2;
import com.project.recipesearch.R;
import com.project.recipesearch.SearchActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Button btnSearch;
    EditText txtSearch;
    ListView listView;
    ProgressDialog pDialog;
    ArrayList<String> title;
    ArrayList<String> link;
    ArrayList<String> ing;
    ArrayList<String> thum;
    String status,searchText;
    SearchActivity searchActivity;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        btnSearch = root.findViewById(R.id.btnSearch);
        txtSearch = root.findViewById(R.id.txtSearch);
        listView = root.findViewById(R.id.list_item);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("app", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        status = sharedPreferences.getString("status", "");
        if (status.equals("")) {
        }else{
            searchText=status;
            new Details().execute();
        }


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = new ArrayList<>();
                link=new ArrayList<>();
                ing=new ArrayList<>();
                thum=new ArrayList<>();
                searchText=txtSearch.getText().toString().trim();
                if(searchText.length()>0) {
                    new Details().execute();
                    editor.putString("status", txtSearch.getText().toString().trim());
                    editor.apply();
                }else{
                    Toast.makeText(getActivity().getApplicationContext(),"Please Enter Text For Search",Toast.LENGTH_LONG).show();
                }
            }
        });
        return root;
    }
    public class Details extends AsyncTask<Object, Void, ArrayList<String>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.show();
        }

        @Override
        protected ArrayList<String> doInBackground(Object... objects) {
            AndroidNetworking.get(ContentFiles.mainPath + searchText + "&p=6")
                    .setPriority(Priority.MEDIUM)
                    .build().getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        JSONArray obj = response.getJSONArray("results");
                        for (int i = 0; i < obj.length(); i++) {
                            JSONObject ob = obj.getJSONObject(i);
                            title.add(ob.getString("title"));
                            link.add(ob.getString("href"));
                            ing.add(ob.getString("ingredients"));
                            thum.add(ob.getString("thumbnail"));
                        }
                    } catch (Exception e) {
                        Log.e("Error", e + "");
                    }
                    listView.setAdapter(new CostumAdapter(getActivity().getApplicationContext(), title, link, ing, thum));
                }

                @Override
                public void onError(ANError anError) {
                    pDialog.dismiss();
                    Log.e("Error", anError + "");
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
        }
    }
}