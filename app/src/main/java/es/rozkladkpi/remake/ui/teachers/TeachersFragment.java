package es.rozkladkpi.remake.ui.teachers;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import es.rozkladkpi.remake.R;

public class TeachersFragment extends Fragment {

    ArrayList<String> childItems;
    ArrayList<ArrayList<String>> childData;
    ArrayList<ArrayList<ArrayList<String>>> dayData;

    ExpandableListView list_of_buttons;

    private String listOfTopics [] = {"Понеділок","Вівторок","Середа","Четвер","П'ятниця","Субота"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_teachers, container, false);
        final TextView getTeachersView = root.findViewById(R.id.text_teachers_1);
        getTeachersView.setText("Введіть ПІБ викладача:");

        final TextView entryField = root.findViewById(R.id.entry_field_teachers);
        entryField.setText("");

        Button teachersButton = root.findViewById(R.id.button_teachers);
        teachersButton.setText("Знайти розклад викладача");

        teachersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String teacherName = entryField.getText().toString();
                getTeachersView.setVisibility(View.GONE);
                entryField.setVisibility(View.GONE);
                teachersButton.setVisibility(View.GONE);

                list_of_buttons = root.findViewById(R.id.teachers_expandable_list_view);
                loadJSONFromURL("https://api.rozklad.org.ua/v2/teachers/" + teacherName + "/lessons");
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
                            JSONArray data = object.getJSONArray("data");

                            ArrayList<String> emptylist = new ArrayList<String>();
                            emptylist.add("");
                            emptylist.add("");
                            emptylist.add("");
                            emptylist.add("");
                            dayData = new ArrayList<ArrayList<ArrayList<String>>>();
                            childData = new ArrayList<ArrayList<String>>();
                            childData.add(emptylist);
                            for (int i=0; i<12; i++){
                                dayData.add(childData);
                            }

                            int previousIterationDay = 0;
                            for(int i = 0; i < data.length();i++){
                                childData = new ArrayList<ArrayList<String>>();
                                JSONObject lesson = data.getJSONObject(i);
                                childItems = new ArrayList<String>();
                                childItems.add(lesson.getString("lesson_full_name"));
                                childItems.add(lesson.getString("teacher_name"));
                                childItems.add(lesson.getString("lesson_room") +" "+ lesson.getString("lesson_type"));

                                JSONArray groups = lesson.getJSONArray("groups");
                                String groupesNames = "";
                                for (int j=0;j<groups.length();j++){
                                    JSONObject groupe = groups.getJSONObject(j);
                                    groupesNames = groupesNames + groupe.getString("group_full_name") + " ";
                                }
                                childItems.add(groupesNames);
                                if (Integer.parseInt (lesson.getString("lesson_number")) == 1){
                                    childData.add(childItems);
                                }else{
                                    for (int k=1;k<Integer.parseInt(lesson.getString("lesson_number"));k++){
                                        childData.add(emptylist);
                                    }
                                    childData.add(childItems);
                                }

                                if (Integer.parseInt(lesson.getString("day_number")) != previousIterationDay) {
                                    if (Integer.parseInt(lesson.getString("lesson_week")) == 1) {
                                        dayData.set(Integer.parseInt(lesson.getString("day_number")) - 1, childData);
                                    } else {
                                        dayData.set(Integer.parseInt(lesson.getString("day_number")) + 5, childData);
                                    }
                                }else{
                                    if (Integer.parseInt(lesson.getString("lesson_week")) == 1) {
                                        ArrayList<ArrayList<String>> previousChildData = dayData.get(Integer.parseInt(lesson.getString("day_number")) - 1);
                                        int counter = 0;
                                        for (ArrayList<String> iterator: previousChildData){
                                            childData.set(counter,iterator);
                                            counter++;
                                        }
                                        dayData.set(Integer.parseInt(lesson.getString("day_number")) - 1, childData);
                                    } else {
                                        ArrayList<ArrayList<String>> previousChildData = dayData.get(Integer.parseInt(lesson.getString("day_number")) + 5);
                                        int counter = 0;
                                        for (ArrayList<String> iterator: previousChildData){
                                            childData.set(counter,iterator);
                                            counter++;
                                        }
                                        dayData.set(Integer.parseInt(lesson.getString("day_number")) + 5, childData);
                                    }
                                }
                                previousIterationDay = Integer.parseInt(lesson.getString("day_number"));

                            }
//                            for (Iterator<String> it = weeks.keys(); it.hasNext(); ) {
//                                String key = it.next();
//                                JSONObject week = weeks.getJSONObject(key);
//                                JSONObject days = week.getJSONObject("days");
//                                ArrayList<String> emptylist = new ArrayList<String>();
//                                emptylist.add("");
//                                emptylist.add("");
//                                emptylist.add("");
//                                for (Iterator<String> i = days.keys(); i.hasNext(); ) {
//                                    String keyDay = i.next();
//                                    JSONObject day = days.getJSONObject(keyDay);
//                                    JSONArray lessons = day.getJSONArray("lessons");
//                                    childData = new ArrayList<ArrayList<String>>();
//                                    int lessonCounter = 1;
//                                    for(int j = 0; j < lessons.length();j++){
//                                        JSONObject lesson = lessons.getJSONObject(j);
//                                        childItems = new ArrayList<String>();
//                                        childItems.add(lesson.getString("lesson_full_name"));
//                                        childItems.add(lesson.getString("teacher_name"));
//                                        childItems.add(lesson.getString("lesson_room") +" "+ lesson.getString("lesson_type"));
//                                        if (lessonCounter == Integer.parseInt (lesson.getString("lesson_number"))){
//                                            childData.add(childItems);
//                                            lessonCounter++;
//                                        }else{
//                                            childData.add(emptylist);
//                                            childData.add(childItems);
//                                        }
//
//                                    }
//                                    dayData.add(childData);
//                                }
//
//                            }
                            list_of_buttons.setAdapter(new TeachExpListAdapter(TeachersFragment.this.getContext(), listOfTopics,dayData));
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TeachersFragment.this.getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        requestQueue.add(stringRequest);
    }
}


class TeachExpListAdapter extends BaseExpandableListAdapter {

    private ArrayList<ArrayList<ArrayList<String>>> mGroups;
    private String mTopics [];
    private Context mContext;

    public TeachExpListAdapter (Context context, String [] topics,ArrayList<ArrayList<ArrayList<String>>> groups){
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