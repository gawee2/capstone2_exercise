package com.mju.exercise.OpenMatch;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mju.exercise.Domain.OpenMatchDTO;
import com.mju.exercise.HttpRequest.RetrofitUtil;
import com.mju.exercise.Preference.PreferenceUtil;
import com.mju.exercise.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OpenMatchCreatedFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OpenMatchCreatedFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<OpenMatchDTO> openMatches;
    ListView customListView;
    RetrofitUtil retrofitUtil;
    OpenMatchAdapter openMatchAdapter;
    PreferenceUtil preferenceUtil;

    public OpenMatchCreatedFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OpenMatchCreatedFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static OpenMatchCreatedFrag newInstance(String param1, String param2) {
        OpenMatchCreatedFrag fragment = new OpenMatchCreatedFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        preferenceUtil = PreferenceUtil.getInstance(getContext());
        retrofitUtil = RetrofitUtil.getInstance();
        openMatches = new ArrayList<>();
        loadOpenMatchesCreatedByMe();

        View view = inflater.inflate(R.layout.fragment_open_match_list, container, false);

        customListView = (ListView) view.findViewById(R.id.listViewOpenMatchList);

        // Inflate the layout for this fragment
        return view;
    }

    private void loadOpenMatchesCreatedByMe(){
        Long userIdx = Long.valueOf(preferenceUtil.getString("userIdx"));
        retrofitUtil.getRetrofitAPI().loadOpenMatchesCreatedByMe(userIdx).enqueue(new Callback<List<OpenMatchDTO>>() {
            @Override
            public void onResponse(Call<List<OpenMatchDTO>> call, Response<List<OpenMatchDTO>> response) {
                if(response.isSuccessful()){
                    openMatches = (ArrayList<OpenMatchDTO>) response.body();
                    openMatchAdapter = new OpenMatchAdapter(getContext(), openMatches);
                    customListView.setAdapter(openMatchAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<OpenMatchDTO>> call, Throwable t) {

            }
        });

    }
}