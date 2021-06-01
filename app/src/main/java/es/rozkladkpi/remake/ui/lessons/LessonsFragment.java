package es.rozkladkpi.remake.ui.lessons;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.rozkladkpi.remake.R;

public class LessonsFragment extends Fragment {

//    private LessonsViewModel lessonsViewModel;

    ArrayList<Map<String, String>> topics;
    ArrayList<Map<String, String>> childItems;
    ArrayList<ArrayList<Map<String, String>>> childData;
    Map<String, String> m;

    ExpandableListView list_of_buttons;

    private String listOfTopics [] = {"Понеділок","Вівторок","Середа","Четвер","П'ятниця","Субота"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        lessonsViewModel =
//                new ViewModelProvider(this).get(LessonsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_lessons, container, false);
        final TextView getGroupeView = root.findViewById(R.id.text_lessons_1);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        getGroupeView.setText("Введіть групу:");

        final TextView entryField = root.findViewById(R.id.entry_field_lessons);
        entryField.setText("ів-91");

        Button lessonsButton = root.findViewById(R.id.button_lessons);
        lessonsButton.setText("Знайти розклад занять");

        lessonsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGroupeView.setVisibility(View.GONE);
                entryField.setVisibility(View.GONE);
                lessonsButton.setVisibility(View.GONE);

                list_of_buttons = root.findViewById(R.id.lessons_expandable_list_view);
                list_of_buttons.setAdapter(adapterCreation());
            }
        });

        return root;
    }

    public SimpleExpandableListAdapter adapterCreation(){
        topics = new ArrayList<Map<String, String>>();
        for(int i=0; i<2; i++){
            for(String topic : listOfTopics){
                m = new HashMap<String, String>();
                m.put("topicName", topic);
                topics.add(m);
            }
        }

        String [] topicInformationHolder = new String[]{"topicName"};

        int [] topicConteiner = new int [] {R.id.topicText};

        childData = new ArrayList<ArrayList<Map<String, String>>>();

//        int listCounter = 0;
//        int priceCounter = 0;
//        int counter = 0;
//
//        for (String featID: listOfFeatures) {
//            childItems = new ArrayList<Map<String, String>>();
//            for (String offerPoint : listOfService) {
//                m = new HashMap<String, String>();
//
//                if(arrayOfKeysService.get(counter).equals(featID)) {
//                    m.put("offerPoint", offerPoint);
//                    childItems.add(m);
//                }
//                counter++;
//            }
//            childData.add(childItems);
//            counter = 0;
//        }


        String [] childInformationHolder = new String[]{"subjectName","teachersName","class"};

        int [] childConteiner = new int [] {R.id.subgectText, R.id.teachersText, R.id.classText};

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(this.getContext(),
                topics, R.layout.topic_lable,topicInformationHolder, topicConteiner,
                childData, R.layout.child_lable, childInformationHolder,childConteiner );

//        list_of_my_profile_buttons.setAdapter(adapter);
        return adapter;

    }
}