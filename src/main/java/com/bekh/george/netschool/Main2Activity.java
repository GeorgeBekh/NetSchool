package com.bekh.george.netschool;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;

public class Main2Activity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    WebView netSchoolBrowser;
    WebView marks;
    final static String TAG = "MyActivity";
    protected static String UN;
    protected static String PW;
    boolean check;
    String diary;
    String day;
    String task;
    int j=0;
    int i=1;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    String currentWeek;
    TextView name;
    View marksLayout;
    View diaryLayout;
    View webviewLayout;
    View mainLayout;
    ArrayList<String> list= new ArrayList<String>();
    MyArrayAdapter adapter;
    ListView marksList;
    ListView listView ;


    View frag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializing(savedInstanceState);

        netSchoolBrowser.setWebChromeClient(new WebChromeClient() {
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                if (message.contains("week")) {
                    Log.v(TAG, message);
                    currentWeek = message.substring(5, message.length());
                    saveString(StartActivity.WEEK, currentWeek);
                    Log.v(TAG, currentWeek);

                    return true;
                } else {
                    if (message.contains("#$diary$#")) {
                        diary = message;
                        saveString(StartActivity.DIARY, diary);
                        //content.setText(message);
                        setDiaryContent(message);
                        Toast.makeText(Main2Activity.this, "Дневник загружен", Toast.LENGTH_SHORT).show();
                        //netSchoolBrowser.loadUrl("location.reload();");
                        Log.v(TAG, netSchoolBrowser.getUrl());

                        return true;
                    }
                }
                Log.v(TAG, message);
                return true;
            }
        });
        netSchoolBrowser.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {

                if (url.equals(StartActivity.URL)) {


                    switch (i) {
                        case 1:
                            view.loadUrl("javascript:document.getElementsByName('SID')[0].value='1';submitForm('SID',1);");
                            i++;
                            break;
                        case 2:
                            view.loadUrl("javascript:document.getElementsByName('CN')[0].value='1';submitForm('CN',3);");
                            i++;
                            break;
                        case 3:
                            view.loadUrl("javascript:document.getElementsByName('SCID')[0].value='1';submitForm('SCID',5);");
                            i++;
                            break;
                        case 4:
                            view.loadUrl("javascript:document.getElementsByName('UN')[0].value='" + UN + "';document.getElementsByName('PW')[0].value='" + PW + "';ok();");
                            Log.v(TAG, "Cтраница загружена");
                            i++;
                            break;
                    }

                } else {
                    if (url.equals("http://ns.gymn24.ru/asp/Announce/ViewAnnouncements.asp")) {
                        saveFormData();


                        view.loadUrl("javascript:SetSelectedMenu('14','/asp/Curriculum/Assignments.asp');");
                    } else {
                        if (url.equals("http://ns.gymn24.ru/asp/SecurityWarning.asp")) {
                            view.loadUrl("javascript:doContinue();");
                        } else {
                            if (url.equals("http://ns.gymn24.ru/asp/error.asp?DEST=http%3A%2F%2Fns%2Egymn24%2Eru%2F&ET=%D0%9D%D0%B5%D0%BF%D1%80%D0%B0%D0%B2%D0%B8%D0%BB%D1%8C%D0%BD%D1%8B%D0%B9+%D0%BF%D0%B0%D1%80%D0%BE%D0%BB%D1%8C+%D0%B8%D0%BB%D0%B8+%D0%B8%D0%BC%D1%8F+%D0%BF%D0%BE%D0%BB%D1%8C%D0%B7%D0%BE%D0%B2%D0%B0%D1%82%D0%B5%D0%BB%D1%8F&AT=")) {
                                Intent intent1 = new Intent();
                                intent1.setClass(Main2Activity.this, StartActivity.class);
                                startActivity(intent1);
                                Log.v(TAG, "Неверный пароль");

                            } else {
                                if (url.equals("http://ns.gymn24.ru/asp/Curriculum/Assignments.asp")) {
                                    if (j <= 0) {
                                        Log.v(TAG, "Началась загрузка дневника");
                                        netSchoolBrowser.loadUrl("javascript: var tbl=document.getElementsByClassName('ThinTable')[0];\n" +
                                                "var lines=tbl.getElementsByTagName('tr');\n" +
                                                "var lineslength = lines.length;\n" +
                                                "var Result = '#$diary$#';\n" +
                                                "var d;\n" +
                                                "var j;\n" +
                                                "var row = 1;\n" +
                                                "for(var i=1; i<lines.length;i++){\n" +
                                                "if(i==row){row=row+lines[i].getElementsByTagName('td')[0].rowSpan;d=1;\n" +
                                                "j = 1;Result=Result+lines[i].getElementsByTagName('td')[0].innerHTML+'!@#'; } else{d=0;\n" +
                                                "j=0;}\n" +
                                                "for(j;j<4+d;j++){\n" +
                                                "if(lines[i].getElementsByTagName('td')[j].getElementsByTagName('a')[0]!=undefined){\n" +
                                                "Result=Result+' '+lines[i].getElementsByTagName('td')[j].getElementsByTagName('a')[0].innerHTML;\n" +
                                                "}else{ \n" +
                                                "if(j==1+d){Result=Result+' \"'+lines[i].getElementsByTagName('td')[j].innerHTML+'\"'\n" +
                                                "}else{if(j==3+d){Result=Result+'\\nОценка:'+lines[i].getElementsByTagName('td')[j].innerHTML} else{\n" +
                                                "Result=Result+' '+lines[i].getElementsByTagName('td')[j].innerHTML;}}}}\n" +
                                                "Result=Result+'%&$';} \n" +
                                                "prompt(Result);");
                                        j++;
                                    } else {
                                        Log.v(TAG, "началась загрузка оценок");
                                        netSchoolBrowser.loadUrl("JavaScript:SetSelectedMenu('12','/asp/Reports/Reports.asp')");
                                    }

                                } else {
                                    if (url.equals("http://ns.gymn24.ru/asp/Reports/Reports.asp")) {
                                        view.loadUrl("GoToLink('ReportStudentTotal.asp','2','1');");
                                        Log.v(TAG, "отчеты");
                                    } else {
                                        if (url.equals("http://ns.gymn24.ru/asp/Reports/ReportStudentTotal.asp")) {
                                            view.loadUrl("javascript: ViewPrint(1401)");
                                            Log.v(TAG, "оценки");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
        marks.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, String url) {
                if (url.equals(StartActivity.URL)) {

                    switch (i) {
                        case 1:
                            view.loadUrl("javascript:document.getElementsByName('SID')[0].value='1';submitForm('SID',1);");
                            i++;
                            break;
                        case 2:
                            view.loadUrl("javascript:document.getElementsByName('CN')[0].value='1';submitForm('CN',3);");
                            i++;
                            break;
                        case 3:
                            view.loadUrl("javascript:document.getElementsByName('SCID')[0].value='1';submitForm('SCID',5);");
                            i++;
                            break;
                        case 4:
                            view.loadUrl("javascript:document.getElementsByName('UN')[0].value='" + UN + "';document.getElementsByName('PW')[0].value='" + PW + "';ok();");
                            Log.v(TAG, "Cтраница загружена");
                            i++;
                            break;
                    }
                }
                if(url.equals("http://ns.gymn24.ru/asp/Announce/ViewAnnouncements.asp")){
                    view.loadUrl("JavaScript:SetSelectedMenu('12','/asp/Reports/Reports.asp')");
                }else {
                    if (url.equals("http://ns.gymn24.ru/asp/Reports/Reports.asp")){
                        view.loadUrl("JavaScript:GoToLink('ReportStudentTotal.asp','2','1')");
                    }else{
                        if (url.equals("http://ns.gymn24.ru/asp/Reports/ReportStudentTotal.asp")){
                            view.loadUrl("javascript: ViewPrint(1401)");
                            Log.v(TAG,"оценки загруженыf");
                        }else{
                            if (url.equals("http://ns.gymn24.ru/asp/SecurityWarning.asp")){
                                view.loadUrl("javascript:doContinue()");
                            }else{
                                if(url.contains("http://ns.gymn24.ru/asp/Reports/StudentTotal.asp")){
                                    view.loadUrl("javascript:  var t = document.getElementsByClassName('xlTable')[0].getElementsByTagName('tr');\n" +
                                            "var result='';\n" +
                                            "var td;\n" +
                                            "for(var i=2; i<t.length;i++ ){\n" +
                                            " td= t[i].getElementsByTagName('td');\n" +
                                            "result=result+td[0].innerHTML+'!@#';\n" +
                                            "result=result+td[td.length-1].innerHTML+'$%^';\n" +
                                            "} prompt(result);");
                                }
                            }
                        }
                    }
                }
            }
        });
        marks.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
               marksList=(ListView) findViewById(R.id.marksList);
                saveString(StartActivity.MARKS, message);
                setMarksContent(message);

                return true;
            }
        });
        netSchoolBrowser.loadUrl(StartActivity.URL);
        marks.loadUrl(StartActivity.URL);
    }
/*    class MyJavaScriptInterface {

        private Context ctx;

        MyJavaScriptInterface(Context ctx) {
            this.ctx = ctx;
        }

        @JavascriptInterface
        @SuppressWarnings("unused")
        public void showHTML(String html) {
            Log.v(TAG, html);
        }

    }*/

    private void initializing(Bundle savedInstanceState){
        LayoutInflater inflater = getLayoutInflater();


        //diaryLayout = inflater.inflate(R.layout.fragment_main2, container);
        mainLayout = inflater.inflate(R.layout.activity_main2,null);
        marksLayout = mainLayout.findViewById(R.id.marksLinear);
        webviewLayout = mainLayout.findViewById(R.id.webLinear);
        diaryLayout = mainLayout.findViewById(R.id.diaryLinear);
        setContentView(mainLayout);
        final Intent intent = getIntent();
        UN = intent.getStringExtra(StartActivity.LOGIN);
        if(intent.getStringExtra(StartActivity.PASSWORD)==null){
            SharedPreferences sPref = getSharedPreferences(UN,MODE_PRIVATE);
            PW=sPref.getString(StartActivity.PASSWORD,"");
            Log.v(TAG, "Пароль был получен из папки " + UN);
            Toast.makeText(this,"Пароль был получен из папки " + UN,Toast.LENGTH_SHORT).show();
            check = true;
        }else {
            PW = intent.getStringExtra(StartActivity.PASSWORD);
            Log.v(TAG, "Пароль был получен из окна авторизации");
            Toast.makeText(this,"Пароль был получен из окна авторизации",Toast.LENGTH_SHORT).show();
            check=false;

        }

        netSchoolBrowser =new WebView(this);
        netSchoolBrowser.getSettings().setJavaScriptEnabled(true);
        netSchoolBrowser.getSettings().setSavePassword(false);
        netSchoolBrowser.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
       // netSchoolBrowser.addJavascriptInterface(new MyJavaScriptInterface(this),"MyJs");

        marks=new WebView(this);
        marks.getSettings().setJavaScriptEnabled(true);
        marks.getSettings().setSavePassword(false);
        marks.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
       // netSchoolBrowser.getSettings().setLoadsImagesAutomatically(false);
        if(!getString(StartActivity.DIARY).equals("")) {
            setDiaryContent(getString(StartActivity.DIARY));
        }
        name = (TextView) diaryLayout.findViewById(R.id.name);
//        content.setText(diary);
        listView=(ListView) diaryLayout.findViewById(R.id.listView);
        marksList = (ListView) marksLayout.findViewById(R.id.marksList);
        setMarksContent(getString(StartActivity.MARKS));
        name.setText("Пользователь:" + UN);
        currentWeek=getString(StartActivity.WEEK);
        Log.v(TAG, "Текущая неделя:" + currentWeek);
        if(!getString(StartActivity.DIARY).equals("")) {
            setDiaryContent(getString(StartActivity.DIARY));
        }
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

    }
    private void saveFormData(){
        SharedPreferences sPref = getSharedPreferences(UN, MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString(StartActivity.PASSWORD, PW);
        editor.apply();
        sPref = getSharedPreferences(StartActivity.FORM_DATA,MODE_PRIVATE);
        editor = sPref.edit();
        editor.putString(StartActivity.LOGIN, UN);
        editor.apply();
        Log.v(TAG, "Пароль пользователя " + UN + " помещен в его директорию");
    }

    private void checkWeek(){
        netSchoolBrowser.loadUrl("javascript:var t=document.getElementsByName('DATE')[0]; prompt('week '+t.options[t.selectedIndex].innerHTML);");
    }
    private void saveString(String name,String value){
        SharedPreferences sPref= getSharedPreferences(UN,MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString(name,value);
       // Log.v(TAG,"Строка "+value+" с именем "+name+" помещена в директорию "+UN);
        editor.apply();
    }
    private String getString(String name){
        SharedPreferences sPref = getSharedPreferences(UN, MODE_PRIVATE);
        return sPref.getString(name,"");
    }
    private void setMarksContent(String message){
        ArrayList<String> list = new ArrayList<String>();
        boolean f =true;
        String s;
        while(f){
            if(message.contains("$%^")){
                s=message.substring(0,message.indexOf("$%^"));
                list.add(s);
                message = message.substring(message.indexOf("$%^")+3,message.length()-1);
            }else{
                f=false;
            }
        }
        marksList.setAdapter(new MarksArrayAdapter(this,list));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {

            case 3:
                mTitle = getString(R.string.title_section3);
                if(diaryLayout!=null&&marksLayout!=null&&webviewLayout!=null) {
                    diaryLayout.setVisibility(View.VISIBLE);
                    marksLayout.setVisibility(View.GONE);
                    webviewLayout.setVisibility(View.GONE);
                }
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                if(diaryLayout!=null&&marksLayout!=null&&webviewLayout!=null) {
                    diaryLayout.setVisibility(View.GONE);
                    marksLayout.setVisibility(View.VISIBLE);
                    webviewLayout.setVisibility(View.GONE);
                }
                break;
            case 1:
                mTitle = getString(R.string.title_section1);
                if(diaryLayout!=null&&marksLayout!=null&&webviewLayout!=null) {
                    diaryLayout.setVisibility(View.GONE);
                    marksLayout.setVisibility(View.GONE);
                    webviewLayout.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
    public void setDiaryContent(String message){
        list = new ArrayList<String>();
        ArrayList<ArrayList<String>> parent = new ArrayList<ArrayList<String>>();
        ArrayList<String> child = new ArrayList<String>();
        boolean f=true;
        boolean d= false;
        message = message.replace("#$diary$#","");
        while (f){
            if(message.contains("%&$")){
                if(message.indexOf("!@#")<message.indexOf("%&$")&&message.contains("!@#")){
                    day=message.substring(0,message.indexOf("!@#")+3);
                    if(d){
                        parent.add(child);
                        child=new ArrayList<String>();
                    }
                    child.add(day);
                    list.add(day);
                    message=message.replace(day, "");
                    //Log.v(TAG,"День:"+day);
                }else{
                    if((message.indexOf("!@#")>message.indexOf("%&$"))||(!message.contains("!@#"))){
                        task=message.substring(0, message.indexOf("%&$"));
                        d=true;
                        list.add(task);
                        child.add(task);
                        //Log.v(TAG,message.replace(task + "%&$", ""));
                        message=message.replace(task + "%&$", "");
                        //Log.v(TAG,"Задание:"+task);
                    }
                }
            }else{
                parent.add(child);
                f=false;
            }
        }
        ExpandableListView listView1 =(ExpandableListView) findViewById(R.id.expandableListView);
        listView1.setAdapter(new ExpListAdapter(this,parent));
        if(listView!=null) {
            listView.setAdapter(new MyArrayAdapter(this, list));
        }

    }



    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main2, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((Main2Activity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }


}
