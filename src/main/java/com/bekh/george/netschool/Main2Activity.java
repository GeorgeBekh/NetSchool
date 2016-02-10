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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.view.ViewManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
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
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.regex.Matcher;

public class Main2Activity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    WebView netSchoolBrowser;
    final static String TAG = "MyActivity";
    protected static String UN;
    protected static String PW;
    boolean check;
    boolean g = false;
    String diary;
    String day;
    String task;
    int i=1;
    int fg=1;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    String currentWeek;
    TextView name;
    View marksLayout;
    View diaryLayout;
    View webviewLayout;
    View mainLayout;
    WebView marks;
    ArrayList<String> list= new ArrayList<String>();
    ListView marksList;
    Context context;
    protected void onStop(){

        netSchoolBrowser.clearHistory();
        netSchoolBrowser.clearSslPreferences();
        netSchoolBrowser.clearCache(true);
        netSchoolBrowser.clearMatches();
        marks.clearHistory();
        marks.clearSslPreferences();
        marks.clearCache(true);
        marks.clearMatches();
        Log.d(TAG, "Stop");

        super.onStop();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializing(savedInstanceState);

        netSchoolBrowser.setWebChromeClient(new WebChromeClient() {
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {

                if (message.contains("#$diary$#")) {
                    Log.v(TAG, url);
                    diary = message;
                    g = true;
                    saveString(StartActivity.DIARY, diary);
                    setDiaryContent(message);
                    Toast.makeText(Main2Activity.this, "Дневник загружен", Toast.LENGTH_SHORT).show();
                    result.confirm();
                    marks.loadUrl(StartActivity.URL);
                    return true;

                }
                Log.v(TAG, message);
                result.confirm();
                return true;
            }
        });
        netSchoolBrowser.setWebViewClient(new WebViewClient() {


            public void onPageFinished(WebView view, String url) {
                Log.v(TAG, url);
                netSchoolBrowser.clearHistory();
                if (url.equals(StartActivity.URL)) {
                    Log.v(TAG, "Загружена страница логина");


                    switch (fg) {
                        case 1:
                            view.loadUrl("javascript:document.getElementsByName('SID')[0].value='1';submitForm('SID',1);");
                            fg++;
                            break;
                        case 2:
                            view.loadUrl("javascript:document.getElementsByName('CN')[0].value='1';submitForm('CN',3);");
                            fg++;
                            break;
                        case 3:
                            view.loadUrl("javascript:document.getElementsByName('SCID')[0].value='1';submitForm('SCID',5);");
                            fg++;
                            break;
                        case 4:
                            view.loadUrl("javascript:document.getElementsByName('UN')[0].value='" + UN + "';document.getElementsByName('PW')[0].value='" + PW + "';ok();");
                            Log.v(TAG, "Cтраница загружена");
                            fg++;
                            break;
                    }

                } else {
                    if (url.equals("http://ns.gymn24.ru/asp/Announce/ViewAnnouncements.asp")) {
                        saveFormData();
                        Toast.makeText(context, "Пароль подтвержден", Toast.LENGTH_SHORT).show();


                        view.loadUrl("javascript:SetSelectedMenu('14','/asp/Curriculum/Assignments.asp');");
                    } else {
                        if (url.equals("http://ns.gymn24.ru/asp/SecurityWarning.asp")) {
                            view.loadUrl("javascript:doContinue();");
                        } else {
                            if (url.contains("http://ns.gymn24.ru/asp/error.asp")) {
                                Intent intent1 = new Intent();
                                intent1.setClass(Main2Activity.this, StartActivity.class);
                                Toast.makeText(context, "Неверный пароль", Toast.LENGTH_SHORT).show();
                                startActivity(intent1);

                                Log.v(TAG, "Неверный пароль");

                            } else {
                                if (url.equals("http://ns.gymn24.ru/asp/Curriculum/Assignments.asp")) {


                                        Log.v(TAG, "Началась загрузка дневника");
                                        view.loadUrl("javascript: var tbl=document.getElementsByClassName('ThinTable')[0];\n" +
                                                "                                    var lines=tbl.getElementsByTagName('tr');\n" +
                                                "                                                var lineslength = lines.length;\n" +
                                                "                                                var Result = '#$diary$#';\n" +
                                                "                                                var d;\n" +
                                                "                                                var j;\n" +
                                                "                                                var row = 1;\n" +
                                                "                                                for(var i=1; i<lines.length;i++){\n" +
                                                "                                                if(i==row){row=row+lines[i].getElementsByTagName('td')[0].rowSpan;d=1;\n" +
                                                "                                                j = 1;Result=Result+lines[i].getElementsByTagName('td')[0].innerHTML+lines[i].getAttribute('bgcolor')+'!@#'; } else{d=0;\n" +
                                                "                                                j=0;}\n" +
                                                "                                                for(j;j<4+d;j++){\n" +
                                                "                                                if(lines[i].getElementsByTagName('td')[j].getElementsByTagName('a')[0]!=undefined){\n" +
                                                "                                                Result=Result+' '+lines[i].getElementsByTagName('td')[j].getElementsByTagName('a')[0].innerHTML;\n" +
                                                "                                                }else{\n" +
                                                "                                                if(j==1+d){Result=Result+' \\\"'+lines[i].getElementsByTagName('td')[j].innerHTML+'\\\"';\n" +
                                                "                                                }else{if(j==3+d){Result=Result+'\\n Оценка:'+lines[i].getElementsByTagName('td')[j].innerHTML} else{\n" +
                                                "                                                Result=Result+' '+lines[i].getElementsByTagName('td')[j].innerHTML;}}}}\n" +
                                                "                                                Result=Result+lines[i].getAttribute('bgcolor')+'%&$';}\n" +
                                                "                                                prompt(Result);");


                                }

                            }
                        }
                    }
                }
            }
        });
        netSchoolBrowser.loadUrl(StartActivity.URL);
        Log.v(TAG, "запрос на страницу");
    }

    private void initializing(Bundle savedInstanceState){
        LayoutInflater inflater = getLayoutInflater();
        context=this;
        mainLayout = inflater.inflate(R.layout.activity_main2,null);
        marksLayout = mainLayout.findViewById(R.id.marksLinear);
        webviewLayout = mainLayout.findViewById(R.id.webLinear);
        diaryLayout = mainLayout.findViewById(R.id.diaryLinear);
        setContentView(mainLayout);
        final Intent intent = getIntent();
        UN = intent.getStringExtra(StartActivity.LOGIN);
        if(intent.getStringExtra(StartActivity.PASSWORD)==null){
            PW=getString(StartActivity.PASSWORD);
            Log.v(TAG, "Пароль был получен из папки " + UN);
            Toast.makeText(this,"Пароль был получен из папки " + UN,Toast.LENGTH_SHORT).show();
            check = true;
        }else {
            PW = intent.getStringExtra(StartActivity.PASSWORD);
            Log.v(TAG, "Пароль был получен из окна авторизации");
            Toast.makeText(this,"Пароль был получен из окна авторизации",Toast.LENGTH_SHORT).show();
            check=false;
        }
        netSchoolBrowser= new WebView(this);
        netSchoolBrowser.getSettings().setJavaScriptEnabled(true);
        netSchoolBrowser.getSettings().setSavePassword(false);
        netSchoolBrowser.getSettings().setLoadsImagesAutomatically(false);
        netSchoolBrowser.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        netSchoolBrowser.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        netSchoolBrowser.getSettings().setSaveFormData(false);
        marks = new WebView(this);
        marks.getSettings().setJavaScriptEnabled(true);
        marks.getSettings().setSavePassword(false);
        marks.getSettings().setLoadsImagesAutomatically(false);
        marks.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        marks.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        marks.getSettings().setSaveFormData(false);
        if(!getString(StartActivity.DIARY).equals("")) {
            setDiaryContent(getString(StartActivity.DIARY));
        }
        name = (TextView) webviewLayout.findViewById(R.id.name);
        marksList = (ListView) marksLayout.findViewById(R.id.marksList);
        setMarksContent(getString(StartActivity.MARKS));
        name.setText("Пользователь:" + UN);
        currentWeek=getString(StartActivity.WEEK);
        Log.v(TAG, "Текущая неделя:" + currentWeek);
        if(!getString(StartActivity.DIARY).equals("")) {
            setDiaryContent(getString(StartActivity.DIARY));
        }
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

                        }else{
                            if (url.equals("http://ns.gymn24.ru/asp/SecurityWarning.asp")){
                                view.loadUrl("javascript:doContinue()");
                            }else{
                                if(url.contains("http://ns.gymn24.ru/asp/Reports/StudentTotal.asp")){
                                    Log.v(TAG,"оценки загружены");
                                    Toast.makeText(context,"Оценки загружены",Toast.LENGTH_SHORT).show();
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
                marksList = (ListView) findViewById(R.id.marksList);
                saveString(StartActivity.MARKS, message);
                setMarksContent(message);
                result.confirm();

                return true;
            }
        });
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    private void saveFormData(){
        SharedPreferences sPref = getSharedPreferences(UN, MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.remove(StartActivity.PASSWORD);
        editor.commit();
        editor.putString(StartActivity.PASSWORD, PW);
        editor.commit();
        sPref = getSharedPreferences(StartActivity.FORM_DATA,MODE_PRIVATE);
        editor = sPref.edit();
        editor.remove(StartActivity.LOGIN);
        editor.putString(StartActivity.LOGIN, UN);
        editor.commit();
        Log.v(TAG, "Пароль пользователя " + UN + " помещен в его директорию");
    }


    private void saveString(String name,String value){
        SharedPreferences sPref= getSharedPreferences(UN,MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.remove(name);
        editor.commit();
        editor.putString(name, value);
        editor.commit();
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
        marksList.setAdapter(new MarksArrayAdapter(this, list));
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
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.expandableListView);
        listView.setAdapter(new ExpListAdapter(this, parent));



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
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId())
        {
            case R.id.refresh:
                netSchoolBrowser.loadUrl(StartActivity.URL);
                marks.loadUrl("about:blank");
                fg=1;
                i=1;
                Log.v(TAG, "обновление");
                break;
        }
        return true;
    }

}
