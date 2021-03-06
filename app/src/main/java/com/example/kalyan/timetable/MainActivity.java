package com.example.kalyan.timetable;
//showTut의 안내문구 변경, 요일 한글화, 메뉴탭 한글화
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.github.clans.fab.FloatingActionButton;
import com.majeur.cling.Cling;
import com.majeur.cling.ClingManager;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.io.File;
import java.util.Date;

public class MainActivity extends FragmentActivity {

    private static final String FOR_FIRST_TIME = "for first time" ;
    public int currentPage = 0;
    private static Context context;

    FloatingActionButton fab = null;
    ListView leftlist;
    static MainActivity mainActivity;

    static AlarmManager alarmManager;
    static PendingIntent pendingIntent;

    private DrawerLayout mDrawerLayout;

      @RequiresApi(api = Build.VERSION_CODES.N)
      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

          fab = (FloatingActionButton) findViewById(R.id.fab);
          mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

          leftlist = (ListView) findViewById(R.id.leftlist);

          mainActivity = this;
          context = this;
          String navigationSt[] = new String[]{"과제          ","튜토리얼          ","Attendence Manager       ","Poll      ","설정          ",
                  "의견보내기           "};
          int navigationImg[] = new int[]{R.mipmap.ic_proj,R.mipmap.ic_tut,R.mipmap.ic_att,R.mipmap.ic_poll,R.mipmap.ic_settings,
                  R.mipmap.ic_share,R.mipmap.ic_people};
          MyDrawerAdapter navigationAdapter = new MyDrawerAdapter(getApplicationContext(), navigationSt
                  , navigationImg);
          leftlist.setAdapter(navigationAdapter);

          leftlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  selectFromDrawer(position);
                  mDrawerLayout.closeDrawers();
              }
          });
          FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("월요일", Monday.class)
                .add("화요일", Tuesday.class)
                  .add("수요일",Wednesday.class)
                  .add("목요일",Thursday.class)
                  .add("금요일",Saturday.class)
                  .add("토요일",Saturday.class) //탭을 추가는 하였으나 실행되진 않음.
                  .add("일요일",Sunday.class)
                .create());

         // Calendar c = Calendar.getInstance();


        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);

          if(getIntent() != null){
              viewPager.setCurrentItem(getIntent().getIntExtra("page",0));
          }

          viewPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
              @Override
              public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

              }
              @Override
              public void onPageSelected(int position) {
                  currentPage = position;
                  //Toast.makeText(getApplicationContext(),""+currentPage,Toast.LENGTH_SHORT).show();
              }
              @Override
              public void onPageScrollStateChanged(int state) {

              }
          });
       fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,EditorActivity.class);
                startActivity(intent);
            }
        });
          /** Setup the shared preference listener **/
          SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

          if(prefs.getBoolean(FOR_FIRST_TIME,true)) {
              showTut();
              SharedPreferences.Editor editor = prefs.edit();
              editor.putBoolean(FOR_FIRST_TIME,false);
              editor.apply();
          }

          int hour = prefs.getInt(SettingsActivity.NOT_HOUR,7);
          addNotification(MainActivity.this,hour);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int hour = prefs.getInt(SettingsActivity.NOT_HOUR,7);
        addNotification(MainActivity.this,hour);
    }

    public void selectFromDrawer(int position){
        switch (position){
            case 4:
                Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(intent);
                break;
            case 6:
                Intent intent2 = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(intent2);
                break;
            case 5:
                PackageManager pm = getPackageManager();
                ApplicationInfo appInfo;
                try {
                    appInfo = pm.getApplicationInfo(getPackageName(),
                            PackageManager.GET_META_DATA);
                    Intent sendBt = new Intent(Intent.ACTION_SEND);
                    sendBt.setType("*/*");
                    sendBt.putExtra(Intent.EXTRA_STREAM,
                            Uri.parse("file://" + appInfo.publicSourceDir));
                    startActivity(Intent.createChooser(sendBt,
                            "Share it using"));
                } catch (PackageManager.NameNotFoundException e1) {
                    e1.printStackTrace();
                }
                break;
            case 0:
                Intent intent1 = new Intent(MainActivity.this,ProjectShowActivity.class);
                startActivity(intent1);
                break;
            case 1:
                showTut();
                break;
            case 2:
                Intent in = new Intent(this,AttendenceActivity.class);
                startActivity(in);
                break;
            case 3:
                Intent i=new Intent(this,Poll.class);
                startActivity(i);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addNotification(Context context,int hours){

        Calendar calendar = Calendar.getInstance();

        Date currentTime = Calendar.getInstance().getTime();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        int hour = prefs.getInt(SettingsActivity.NOT_HOUR,hours);
        int min = prefs.getInt(SettingsActivity.NOT_MIN,0);
       // calendar.add(Calendar.DATE,1);
//        Toast.makeText(getContext(),hour+":"+min,Toast.LENGTH_SHORT).show();

        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,min);
        calendar.set(Calendar.SECOND,0);

        Intent intent = new Intent(context,Notification_reciver.class);

        pendingIntent = PendingIntent.getBroadcast(context,
                100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
    }


    public static Context getContext(){
        return MainActivity.context;
    }

    public class MyDrawerAdapter extends BaseAdapter {

        private Context context;
        private String[] titles;
        private int[] images;
        private LayoutInflater inflater;

        public MyDrawerAdapter(Context context, String[] titles, int[] images) {
            this.context = context;
            this.titles = titles;
            this.images = images;
            this.inflater = LayoutInflater.from(this.context);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder mViewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.singlenavigation_view, null);
                mViewHolder = new ViewHolder();
                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (ViewHolder) convertView.getTag();
            }

            mViewHolder.tvTitle = (TextView) convertView
                    .findViewById(R.id.text_navigation);
            mViewHolder.ivIcon = (ImageView) convertView
                    .findViewById(R.id.image_navigation);

            mViewHolder.tvTitle.setText(titles[position]);
            mViewHolder.ivIcon.setImageResource(images[position]);

            return convertView;
        }

        private class ViewHolder {
            TextView tvTitle;
            ImageView ivIcon;
        }
    }

    private Intent createShareForecastIntent() {
        String path = "/sdcard/mytxt.txt";
        File file = new File(path);
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/*");
        try {
            sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file.getAbsolutePath()));
        }catch (Exception e){

        }
        startActivity(Intent.createChooser(sharingIntent, "share file with"));
        return sharingIntent;
    }

    private void showTut() {
        ClingManager mClingManager = new ClingManager(this);

        mClingManager.addCling(new Cling.Builder(this)
                .setTitle("자동설정 핸드폰!")
                .setContent("당신의 핸드폰이 자동으로\n설정하기 위한 튜토리얼입니다!")
                .build());

        mClingManager.addCling(new Cling.Builder(this)
                .setTitle("일정 확인")
                .setContent("화면을 좌/우로 드래그하여서\n넘어다닐 수 있습니다.")
                .setTarget(new com.majeur.cling.ViewTarget(this, R.id.viewpagertab))
                .build());

        mClingManager.addCling(new Cling.Builder(this)
                .setTitle("일정 추가")
                .setContent("버튼을 클릭하여 일정을 추가하세요")
                .setTarget(new com.majeur.cling.ViewTarget(this, R.id.fab))
                .build());

        mClingManager.addCling(new Cling.Builder(this)
                .setTitle("일정 변경")
                .setContent("각 일정을 길게 누르면 일정을\n수정,삭제 할 수 있습니다.")
                .setTarget(new com.majeur.cling.ViewTarget(this, R.id.list_single))
                .build());

        mClingManager.addCling(new Cling.Builder(this)
                .setTitle("메뉴")
                .setContent("화면 가장 왼쪽에서 오른쪽으로 드래그하면 여러가지 메뉴들이 등장합니다.")
                .setTarget(new com.majeur.cling.ViewTarget(this, R.id.navigation))
                .build());

        String content = "당신이 수업시간에 전화가 와서 곤란하거나\n\n또는 이를 막기위해 무음으로 \n설정하여 전화를 못받거나\n\n"+
                "밤에 시계를 보기위해 핸드폰을 킨\n당신의 눈을 보호해 줄 것입니다.";

        mClingManager.addCling(new Cling.Builder(this)
                .setTitle("이 어플은")
                .setContent(content)
                .build());

        mClingManager.start();
    }

}