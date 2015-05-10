package materialtest.sanjose.venkata.revealsearch.activity;

import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import materialtest.sanjose.venkata.revealsearch.R;
import materialtest.sanjose.venkata.revealsearch.adapter.MyAdapter;


public class MainActivity extends ActionBarActivity {

    //declare icons, titles for nav drawer
    int icons[] = {R.drawable.ic_home, R.drawable.ic_person, R.drawable.ic_calendar, R.drawable.ic_map, R.drawable.ic_search_grey, R.drawable.ic_movie};
    String titles[] = {"Home", "Profile", "Calendar", "Places", "Search", "Movies"};

    String userName = "Venkata Mani Naga Rakesh";
    String userEmail = "nagarakesh4@gmail.com";
    int userProfilePic = R.drawable.venkata;

    private Toolbar mToolBar;

    //declare recycler view and its properties
    RecyclerView mRecyclerView;
    RecyclerView mRecyclerViewRight;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.LayoutManager mLayoutManagerRight;

    //declare navigation drawer and its actionbar toggle property
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set the tool bar to this activity
        mToolBar = (Toolbar) findViewById(R.id.tool_bar);
        //set the tool bar as the custom action bar
        setSupportActionBar(mToolBar);

        /*1. The recycler view should be recognized from the layout xml
        * 2. An adapter should be set to the drawer's recycler list
        * (just as in fragments), this adapter will have all the text,
        * icons that the recycler view's has along with the image on the
        * header section of the drawer.
        * 3. The recycler view should also have a layout declared for it
        * from the layout manager, specify a linear layout manager in the
        * layout manager object*/

        //recognize the recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //declare the recycler list has fixed size
        mRecyclerView.setHasFixedSize(true);

        //recognize the recycler view
        mRecyclerViewRight = (RecyclerView) findViewById(R.id.recyclerViewRight);
        //declare the recycler list has fixed size
        mRecyclerViewRight.setHasFixedSize(true);

        //create an adapter and pass the icons and titles (header and recycler
        // list
        mAdapter = new MyAdapter(titles, icons, userName, userEmail, userProfilePic, this);

        //setting the adapter to the recycler view
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerViewRight.setAdapter(mAdapter);
        //create a linear layout manager and set this to the recycler view
        // pass this for the context
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManagerRight = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerViewRight.setLayoutManager(mLayoutManagerRight);
        /*Navigation drawer setup*/
        //recognize the drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.mainDrawer);

        /*create the drawer object and implement the
        drawer toggle onopen() and onclose()*/
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        /*
        * Perform on click of recycler list
        * */
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "onClick " + position, Toast.LENGTH_SHORT).show();
                //close the drawer on click of any items in the recycler view of the movie
                //navigation bar
                mDrawerLayout.closeDrawer(GravityCompat.START);

                //get the activity index which was clicked in the navigation menu
                //((MovieTabActivity) getActivity()).onDrawerItemClicked(position);
                //after introducing sectioned recycler view the gravatar is counted as a position
                // so decrementing to match the correct fragment.

            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "onlongClick " + position, Toast.LENGTH_SHORT).show();
            }
        }));

        // when an operation is performed on drawer layout is
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //open by default the drawer
        mDrawerLayout.openDrawer(mRecyclerViewRight);
        mDrawerToggle.syncState();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (item != null && item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
            } else {
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
        }
        return false;
    }
    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private GestureDetector gestureDetector;
        private ClickListener clickListener;
        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            Log.i("constructor", "invoked");
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    Log.i("single tapup", "touching " + e);
                    // to make gesture detector handle single tap up - step 4
                    return true;
                }

                // step -5 find child view which performed long press - its coordinates
                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    //child must not be null and click listener must not be null
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                    super.onLongPress(e);
                }
            });
        }
        // single touch is taken care by the onintercepttouch event
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());

            if(child!=null && clickListener!=null && gestureDetector.onTouchEvent(e)){
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            //further processing if any should take place inside the child view of recycler view
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            // CALL THE GESTURE DETECTORS ONTOUCHEVENT
        }
    }
    //step 2
    public static interface ClickListener{
        // the view that was clicked and the position where that particular child view was clicked in
        // the recycler view
        public void onClick(View view, int position);
        public void onLongClick(View view, int position);
    }
}
