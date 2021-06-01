package es.rozkladkpi.remake.ui.lessons;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import es.rozkladkpi.remake.R;

public class LessonsFragment extends Fragment {

//    private LessonsViewModel lessonsViewModel;

    ArrayList<String> childItems;
    ArrayList<ArrayList<String>> childData;
    ArrayList<ArrayList<ArrayList<String>>> dayData;

    ExpandableListView list_of_buttons;

    private String listOfTopics [] = {"Понеділок","Вівторок","Середа","Четвер","П'ятниця","Субота"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_lessons, container, false);
        final TextView getGroupeView = root.findViewById(R.id.text_lessons_1);
        getGroupeView.setText("Введіть групу:");

        final TextView entryField = root.findViewById(R.id.entry_field_lessons);
        entryField.setText("");

        Button lessonsButton = root.findViewById(R.id.button_lessons);
        lessonsButton.setText("Знайти розклад занять");

        lessonsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupeName = entryField.getText().toString();
                getGroupeView.setVisibility(View.GONE);
                entryField.setVisibility(View.GONE);
                lessonsButton.setVisibility(View.GONE);

                list_of_buttons = root.findViewById(R.id.lessons_expandable_list_view);
                loadJSONFromURL("https://api.rozklad.org.ua/v2/groups/" + groupeName + "/timetable");
            }
        });

        return root;
    }


    private void  loadJSONFromURL(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener< String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONObject data = object.getJSONObject("data");
                            JSONObject weeks = data.getJSONObject("weeks");
                            dayData = new ArrayList<ArrayList<ArrayList<String>>>();
                            for (Iterator<String> it = weeks.keys(); it.hasNext(); ) {
                                String key = it.next();
                                JSONObject week = weeks.getJSONObject(key);
                                JSONObject days = week.getJSONObject("days");
                                ArrayList<String> emptylist = new ArrayList<String>();
                                emptylist.add("");
                                emptylist.add("");
                                emptylist.add("");
                                for (Iterator<String> i = days.keys(); i.hasNext(); ) {
                                    String keyDay = i.next();
                                    JSONObject day = days.getJSONObject(keyDay);
                                    JSONArray lessons = day.getJSONArray("lessons");
                                    childData = new ArrayList<ArrayList<String>>();
                                    int lessonCounter = 1;
                                    for(int j = 0; j < lessons.length();j++){
                                        JSONObject lesson = lessons.getJSONObject(j);
                                        childItems = new ArrayList<String>();
                                        childItems.add(lesson.getString("lesson_full_name"));
                                        childItems.add(lesson.getString("teacher_name"));
                                        childItems.add(lesson.getString("lesson_room") +" "+ lesson.getString("lesson_type"));
                                        if (lessonCounter == Integer.parseInt (lesson.getString("lesson_number"))){
                                            childData.add(childItems);
                                            lessonCounter++;
                                        }else{
                                            childData.add(emptylist);
                                            childData.add(childItems);
                                        }

                                    }
                                    dayData.add(childData);
                                }

                            }
                            list_of_buttons.setAdapter(new ExpListAdapter(LessonsFragment.this.getContext(), listOfTopics,dayData));
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LessonsFragment.this.getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        requestQueue.add(stringRequest);
    }
}

class ExpListAdapter extends BaseExpandableListAdapter {

    private ArrayList<ArrayList<ArrayList<String>>> mGroups;
    private String mTopics [];
    private Context mContext;

    public ExpListAdapter (Context context, String [] topics,ArrayList<ArrayList<ArrayList<String>>> groups){
        mContext = context;
        mGroups = groups;
        mTopics = topics;
    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroups.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroups.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.topic_lable, null);
        }

        if (isExpanded){
            //Изменяем что-нибудь, если текущая Group раскрыта
        }
        else{
            //Изменяем что-нибудь, если текущая Group скрыта
        }

        TextView textGroup = (TextView) convertView.findViewById(R.id.topicText);
        if (groupPosition < 6 ){
            textGroup.setText(mTopics[groupPosition]);
        }else{textGroup.setText(mTopics[groupPosition-6]); }


        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_lable, null);
        }

        TextView textChild1 = (TextView) convertView.findViewById(R.id.subgectText);
        textChild1.setText(mGroups.get(groupPosition).get(childPosition).get(0));

        TextView textChild2 = (TextView) convertView.findViewById(R.id.teachersText);
        textChild2.setText(mGroups.get(groupPosition).get(childPosition).get(1));

        TextView textChild3 = (TextView) convertView.findViewById(R.id.roomText);
        textChild3.setText(mGroups.get(groupPosition).get(childPosition).get(2));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}