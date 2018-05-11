//package com.example.marie.moodyfoodie;
//
//import android.content.Context;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//
///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link FoodFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link FoodFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class FoodFragment extends Fragment {
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    private OnFragmentInteractionListener mListener;
//
//    public FoodFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment FoodFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static FoodFragment newInstance(String param1, String param2) {
//        FoodFragment fragment = new FoodFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.food_fragment, container, false);
//    }
//
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
//}

package com.example.marie.moodyfoodie;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class FoodFragment extends Fragment{
    
    View view;

    private static final int SINGLE_ITEM_REQUEST = 100;

    public static final String RETURN_QUANTITY = "RETURN_QUANTITY";

    // out arguments (return)
    public static final String RETURN_FOOD_NAME = "RETURN_FOOD_NAME";
    public static final String RETURN_FOOD_CATEGORY = "RETURN_FOOD_CATEGORY";
    public static final String RETURN_FOOD_NDB = "RETURN_FOOD_NDB";
    public static final String RETURN_FOOD_MEASURE = "RETURN_FOOD_MEASURE";
    public static final String RETURN_FOOD_QUANTITY = "RETURN_FOOD_QUANTITY";

    private final String TAG_SEARCH_MEASURE = "USDAQuery-SearchMeasure";
    private final String TAG_SEARCH_NAME = "USDAQuery-SearchName";

    private List<FoodItem> foodItems;
    private RequestQueue queue;
    private int chosenItem;

    private ListView foodItemsListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.food_fragment, container, false);

        Button searchButton = (Button) view.findViewById(R.id.searchFoodButton);
        final EditText foodEditText = (EditText) view.findViewById(R.id.foodSearchEditBox);

        foodItems = new ArrayList<>();
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        foodItemsListView = (ListView) view.findViewById(R.id.foodResultsListView);
        chosenItem = 0;

        searchButton.setOnClickListener(
            new View.OnClickListener()
            {
                public void onClick(View view)
                {
                    foodEditText.clearFocus();
                    System.out.println("Searching...");
                    String foodString = foodEditText.getText().toString();
                    System.out.println(foodString);
                    //RequestQueue queue = Volley.newRequestQueue();

                    System.out.println("Clearing queue...");
                    foodItems.clear();
                    foodItemsListView.setAdapter(null);
                    queue.cancelAll(TAG_SEARCH_NAME);

                    System.out.println("Creating stringRequest...");
                    StringRequest stringRequest = searchNameStringRequest(foodString);
                    stringRequest.setTag(TAG_SEARCH_NAME);
                    System.out.println(stringRequest.getUrl());

                    // executing the request (adding to queue)
                    System.out.println("Executing request...");
                    queue.add(stringRequest);
                    System.out.println("Done");
                }
            });
        
        return view;
    }

    private StringRequest searchNameStringRequest(String nameSearch) {
        final String API = "&api_key=7GwSvb4KnOJvAdwYq39rFX7zsld8Df4sWTDIKlH1";
        final String NAME_SEARCH = "&q=";
        final String FOOD_GROUP = "&fg=";
        final String SORT = "&sort=n";
        final String MAX_ROWS = "&max=25";
        final String BEGINNING_ROW = "&offset=0";
        final String URL_PREFIX = "https://api.nal.usda.gov/ndb/search/?format=json";

        String url = URL_PREFIX + API + NAME_SEARCH + nameSearch + FOOD_GROUP + SORT + MAX_ROWS + BEGINNING_ROW;

        return new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("!!!!!!!!!!!!!RESPONSE!!!!!!!!!");
                        try {
                            JSONObject result = new JSONObject(response).getJSONObject("list");
                            int maxItems = result.getInt("end");
                            JSONArray resultList = result.getJSONArray("item");

                            for (int i = 0; i < maxItems; i++) {
                                FoodItem fi = new FoodItem(
                                        resultList.getJSONObject(i).getString("name").trim(),
                                        resultList.getJSONObject(i).getString("group").trim(),
                                        resultList.getJSONObject(i).getString("ndbno").trim(),
                                        ""
                                );
                                foodItems.add(fi);
                            }

                            showListOfItems();

                        } catch (JSONException e) {
                            Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    } // public void onResponse(String response)
                }, // Response.Listener<String>()
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(), "Food source is not responding (USDA API), try again", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void showListOfItems() {
        System.out.println("FOODITEMS: ");
        System.out.println(foodItems.toString());

        //foodItemsListView.setAdapter((ListAdapter) foodItems);
        FoodItemListAdapter adapter = new FoodItemListAdapter(getActivity(), R.layout.food_item_row, foodItems);

        //ArrayAdapter adapter = new ArrayAdapter<FoodItem>(getActivity(), android.R.layout.simple_list_item_1, foodItems);
        foodItemsListView.setAdapter(adapter);

        foodItemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intent intent;


                // cancelling all requests about this search if in queue
                queue.cancelAll(TAG_SEARCH_MEASURE);
                // StringRequest: getting measure of the items
                // on the return of the request, go back to the caller Activity
                StringRequest stringRequest = searchItemMeasureStringRequest(foodItems.get(position).getItemNDB(), position);
                stringRequest.setTag(TAG_SEARCH_MEASURE);

                // executing the request (adding to queue)
                queue.add(stringRequest);
            }
        });
    }

    private StringRequest searchItemMeasureStringRequest(String ndbno, final int index) {
        //  StringRequest: getting measure of the items
        final String API = "&api_key=HRjlHt125eNuO6a6xE9KqrVKEpjHuAIBZrZhQFBZ";
        final String NUTRIENTS = "&nutrients=208";
        final String NDBNO = "&ndbno=" + ndbno;
        final String URL_PREFIX = "https://api.nal.usda.gov/ndb/nutrients/?format=json";

        String url = URL_PREFIX + API + NUTRIENTS + NDBNO;

        return new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject result = new JSONObject(response).
                                    getJSONObject("report").
                                    getJSONArray("foods").
                                    getJSONObject(0);
                            String measure = result.getString("measure").toLowerCase() +
                                    " / " + String.valueOf(result.getLong("weight")) +
                                    "g";
                            foodItems.get(index).setItemMeasure(measure);

                        } catch (JSONException e) {
                            Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                        // get item quantity = go to quantity screen
                        //callSingleItemActivity(index);

                    } // public void onResponse(String response)
                }, // Response.Listener<String>()
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(), "Food source is not responding (USDA API). Result is not complete.", Toast.LENGTH_LONG).show();
                        //finishMyActivityOk(index, 0);
                    }
                }); // Response.ErrorListener()

    }


//    @Override
//    public void onClick(View view) {
//
//        switch(view.getId()) {
//            case R.id.searchFoodButton:
//                System.out.println("Search Clicked");
//                EditText foodEditText = (EditText) view.findViewById(R.id.foodSearchEditBox);
//                System.out.println(foodEditText);
//                String foodString = foodEditText.getText().toString();
//                System.out.println(foodString);
//                ((MainActivity)getActivity()).pushFragment(new FoodFragment());
//                break;
//        }
//    }
}
