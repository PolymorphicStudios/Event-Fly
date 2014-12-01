package bth740.eventfly.View;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import bth740.eventfly.R;
import bth740.eventfly.R.id;
import bth740.eventfly.R.layout;

public class CustomListViewAdapter extends ArrayAdapter<RowItem> {

	Context context;

	public CustomListViewAdapter(Context context, int resourceId, List<RowItem> items) {
		super(context, resourceId, items);
		this.context = context;
	}
	
	/*private view holder class*/
	private class ViewHolder {
		ImageView imageView;
		TextView txtTitle;
		TextView txtTimeAndDate;
		TextView txtLocation;
	}
	
	@SuppressLint("NewApi") public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		RowItem rowItem = getItem(position);
		
		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item, parent, false);
			holder = new ViewHolder();
			holder.txtTitle = (TextView) convertView.findViewById(R.id.item_title);
			holder.imageView = (ImageView) convertView.findViewById(R.id.item_thumbnail);
			convertView.setTag(holder);
		} else 
			holder = (ViewHolder) convertView.getTag();

		holder.txtTitle.setText(rowItem.title);
		holder.imageView.setImageBitmap(rowItem.bitmap);
		
		
		return convertView;
	}
}