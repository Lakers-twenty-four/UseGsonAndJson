package com.example.administrator.usegson;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {
    private JSONArray jsonArray;
    private TextView tv_content;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case  1:
                    JSONArray jsonArray= (JSONArray) msg.obj;
                    String content=jsonArray.toString();
                    tv_content.setText(content);
                    break;
                case 2:
                    JSONArray jsonArray1= (JSONArray) msg.obj;
                    JSONObject jsonObject1= (JSONObject) jsonArray1.opt(0);
                    JSONObject jsonObject2= (JSONObject) jsonArray1.opt(1);
                    JSONObject jsonObject3= (JSONObject) jsonArray1.opt(2);
                    try {
                        String name1 = jsonObject1.getString("name");
                        String number1 = jsonObject1.getString("number");
                        String point1 = jsonObject1.getString("point");
                        String name2 = jsonObject2.getString("name");
                        String number2 = jsonObject2.getString("number");
                        String point2 = jsonObject2.getString("point");
                        String name3 = jsonObject3.getString("name");
                        String number3 = jsonObject3.getString("number");
                        String point3 = jsonObject3.getString("point");

                        tv_content.setText(name1+"球衣号码"+number1+"单场最高得分为"+point1+"\n"+name2+"球衣号码"+number2+"单场最高得分为"+point2+"\n"+name3+"球衣号码"+number3+"单场最高得分为"+point3);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    break;
                case 3:
                    List<NBAStars>list= (List<NBAStars>) msg.obj;

                        String name1=list.get(0).getName();
                        String number1=list.get(0).getNumber();
                        String point1=list.get(0).getPoint();


                        String name2=list.get(1).getName();
                        String number2=list.get(1).getNumber();
                        String point2=list.get(1).getPoint();


                        String name3=list.get(2).getName();
                        String number3=list.get(2).getNumber();
                        String point3=list.get(2).getPoint();
                    tv_content.setText(name1+"球衣号码"+number1+"单场最高得分为"+point1+"\n"+name2+"球衣号码"+number2+"单场最高得分为"+point2+"\n"+name3+"球衣号码"+number3+"单场最高得分为"+point3);

                    break;
            }

            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
     }

    private void initView() {
        tv_content= (TextView) findViewById(R.id.tv_content);
    }

    //动态拼接Json
    public void click1(View v){
        NBAStars one=new NBAStars();
        one.setName("科比");one.setNumber("24");one.setPoint("81");
        NBAStars two=new NBAStars();
        two.setName("库里");two.setNumber("30");two.setPoint("51");
        NBAStars three=new NBAStars();
        three.setName("汤普森");three.setNumber("13");three.setPoint("60");
        List<NBAStars>list=new ArrayList<NBAStars>();
        list.add(one);list.add(two);list.add(three);

        jsonArray=new JSONArray();
        for(int i=0;i<list.size();i++){
            try {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("name",list.get(i).getName());
                jsonObject.put("number",list.get(i).getNumber());
                jsonObject.put("point",list.get(i).getPoint());
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Message msg=new Message();
        msg.obj=jsonArray;
        msg.what=1;
        handler.sendMessage(msg);

    }
    //Json解析
    public void click2(View v){
        try {
            String content=jsonArray.toString();
            JSONArray jsonArray1=new JSONArray(content);
            Message msg = Message.obtain();
            msg.obj=jsonArray1;
            msg.what=2;
            handler.sendMessage(msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    //Gson解析
    public void click3(View v){
        NBAStars nbaStars=new NBAStars();
        Gson gson=new Gson();
        String content=jsonArray.toString();
        System.out.println(content);
        Type lt=new TypeToken<List<NBAStars>>(){}.getType();
        List<NBAStars>list= (List<NBAStars>) gson.fromJson(content,lt);

        Message msg = Message.obtain();
        msg.obj=list;
        msg.what=3;
        handler.sendMessage(msg);


    }
}
