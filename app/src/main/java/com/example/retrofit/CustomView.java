package com.example.retrofit;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CustomView extends ArrayAdapter<String> {
    List<String> date;
    List<String> description;
    List<Integer> image;
       Activity context;
    public CustomView( Activity context,  List<Integer> image,List<String> date, List<String> description) {
        super(context, R.layout.urlop,date);
        this.context=context;
        this.image=image;
        this.date=date;
        this.description=description;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
ViewHolder vv=null;
if(convertView==null)
{
    LayoutInflater layoutInflater=context.getLayoutInflater();
    convertView=layoutInflater.inflate(R.layout.urlop,null,true);
    vv=new ViewHolder(convertView);
    convertView.setTag(vv);


}else {

   vv= (ViewHolder) convertView.getTag();

}
vv.img.setImageResource(image.get(position));
vv.dd.setText(date.get(position));
vv.desc.setText(description.get(position));

        return convertView;
    }

    class ViewHolder{
TextView dd;
TextView desc;
ImageView img;
 ViewHolder(View v){
     dd=(TextView) v.findViewById(R.id.data);
     desc=(TextView) v.findViewById(R.id.description);
     img=(ImageView) v.findViewById(R.id.imageView);
        }

    }
}
