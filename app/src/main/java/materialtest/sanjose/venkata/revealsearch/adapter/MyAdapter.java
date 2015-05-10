package materialtest.sanjose.venkata.revealsearch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import materialtest.sanjose.venkata.revealsearch.R;


/**
 * Created by buddhira on 5/9/2015.
 */

    /*  Any fragment of a recycler view should setup an adapter.
    *   This adapter fills the section with icons, text supplied to it
    *   The drawer has two sections - header and list section
    *   these two sections should be initialized in the adapter
    *   inflate the header section as the first row in the recycler view
    *   and the other rows (icons and text) from the second row
    */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    //declare two views (header and item list) so as to inflate the actual view
    private static final int VIEW_HEADER = 0;
    private static final int VIEW_ITEM = 1;

    //declare all the icons, text and images
    private String mTitles[];
    private int mIcons[];
    private String mName;
    private String mEmail;
    private int mProfileImage;
    private Context context;

    /*
        create a constructor having all the required icons, text, images the
        recycler view takes in
    */
    public MyAdapter(String titles[], int icons[], String userName, String userEmail,
                     int userProfileImage, Context mContext){
        mTitles = titles;
        mIcons = icons;
        mName = userName;
        mEmail = userEmail;
        mProfileImage = userProfileImage;
        this.context = mContext;
    }

    /*
        1. Create the view holder class
        2. ViewHolder are for storing the inflated views
        and inflate corresponding views depending on the view type.
        3. View holders helps in recycling the stored inflated views
        4. The holder object holds all the text and icons
    */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        int holderId;

        TextView titleText;
        ImageView iconImage;
        ImageView profileImage;
        TextView textName;
        TextView textEmail;

        Context context;


        public ViewHolder(View itemView, int viewType) {
            super(itemView);

            /*
                when the holder object is created a viewtype is passed
                set the view type's text, icons, images
                accordingly based on the received view type.
            */
            if(viewType == VIEW_ITEM) {
                titleText = (TextView) itemView.findViewById(R.id.rowText);
                iconImage = (ImageView) itemView.findViewById(R.id.rowImage);

                holderId = 1;
            }else {
                textName = (TextView) itemView.findViewById(R.id.headerName);
                textEmail = (TextView) itemView.findViewById(R.id.headerEmail);
                profileImage = (ImageView) itemView.findViewById(R.id.headerProfileImage);

                holderId = 0;
            }

        }

        @Override
        public void onClick(View view) {

        }
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // this method is called when a view holder object is created
        // this method determines the view type and passes this to the
        // view holder class' constructor

        /*Officially :
            Called when RecyclerView needs a new RecyclerView.ViewHolder
             of the given type to represent an item.
        */

        if(viewType == VIEW_ITEM) {
            //inflate the items list layout
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);

            // Pass this inflated view to the view holder so that it generates objects
            // required for this view
            ViewHolder viewHolder = new ViewHolder(view, viewType);

            return viewHolder;

        }else if(viewType == VIEW_HEADER) {
            //inflate the header row layout
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_row, parent, false);

            ViewHolder viewHolder = new ViewHolder(view, viewType);

            return viewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder viewHolder, int position) {
        /*
        *   This method takes the position and gives the data at that position
        *
        *   Position : gives the item at that position (the header row
        *   or the texts, icons in the list of item rows.
        *
        *   Holder id of the holder object specifies the view type
        */

        /*  Officially:
        *   Called by RecyclerView to display the data at the specified position
        */

        if(viewHolder.holderId == VIEW_ITEM){

            //Decrementing the position because the position declares the total position
            // of the drawer list including the header.
            viewHolder.titleText.setText(mTitles[position-1]);
            viewHolder.iconImage.setImageResource(mIcons[position-1]);

        }else if(viewHolder.holderId == VIEW_HEADER) {
            viewHolder.textName.setText(mName);
            viewHolder.textEmail.setText(mEmail);
            viewHolder.profileImage.setImageResource(mProfileImage);
        }
     }

    @Override
    public int getItemCount() {
        // the length of the list of items and header row = list+1
        return mTitles.length+1;
    }

    /*
        to retrieve the view type depending on the position
    */
    @Override
    public int getItemViewType(int position) {
        if(isPositionHeader(position))
            return VIEW_HEADER;
        return VIEW_ITEM;
    }

    public boolean isPositionHeader(int position){
        return position == 0;
    }
}
