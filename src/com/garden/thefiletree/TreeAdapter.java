package com.garden.thefiletree;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.garden.thefiletree.api.TreeFile;

public class TreeAdapter extends ArrayAdapter<TreeFile> {
	
	private Context ctx;
	private LayoutInflater inflater;
	private int ressource;
	private int textViewRessourceId;
	private List<TreeFile> items;
	
	public TreeAdapter(Context context, int resource, int textViewResourceId,
			List<TreeFile> objects) {
		super(context, resource, textViewResourceId, objects);
		this.ctx = context;
		inflater = LayoutInflater.from(ctx);
		this.ressource = resource;
		this.textViewRessourceId = textViewResourceId;
		this.items = objects;
	}
	
	private class ViewHolder {
		TextView tvFile;
	}
	
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(ressource, null);
			holder.tvFile = (TextView)convertView.findViewById(textViewRessourceId);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		TreeFile file = items.get(position);
		int lastIndex = file.getPath().lastIndexOf("/");
		if(file.isDirectory()){
			holder.tvFile.setText(file.getPath().substring(1).substring(lastIndex)+"/");
		}else {
			holder.tvFile.setText(file.getPath().substring(1).substring(lastIndex));
		}
		return convertView;
	}
	
}
