package bth740.eventfly.Create;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import bth740.eventfly.R;

public class SelectImageActivity extends Activity {
    protected Context THIS = null;
    ImageView image;
    GridView imageGrid;
    View oldImageView;
    int currentImage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);

        THIS = this;

        //image = (ImageView)findViewById(R.id.ec_image_selection_image_iv);

        imageGrid = (GridView) findViewById(R.id.ec_image_grid_gv);
        imageGrid.setAdapter(new ImageAdapter(this));

        imageGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //Toast.makeText(THIS, "image: " + position, Toast.LENGTH_SHORT).show();
                if (v != oldImageView) {
                    if (oldImageView != null){
                        oldImageView.setBackgroundColor(0xFFFFFFFF);
                        oldImageView.invalidate();
                    }
                    v.setBackgroundColor(0x6633B5E5);
                    v.invalidate();
                }
                oldImageView = v;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_image, menu);
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

        return super.onOptionsItemSelected(item);
    }

    public void onPreviousActivityClicked(View v){
        finish();
    }

    public void onNextActivityClicked(View v) {
        //Store image name?
        Intent intent = new Intent(THIS,MiscInfoActivity.class);
        startActivity(intent);
    }

    //----------------------------------------------------------------------------------------------
    //MOAR Nested classes. I really am a monster; that or a genius
    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {  // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(300,300));
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(mThumbIds[position]);
            image = imageView;
            return imageView;
        }

        // references to our images
        private Integer[] mThumbIds = {
                R.drawable.placeholder, R.drawable.beer,
                R.drawable.boardgames, R.drawable.food,
                R.drawable.movie, R.drawable.sports,
        };
    }
}
