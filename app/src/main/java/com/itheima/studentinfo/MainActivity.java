package com.itheima.studentinfo;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.studentinfo.db.dao.StudentDao;
import com.itheima.studentinfo.domain.StudentInfo;

public class MainActivity extends Activity {
	private EditText et_name;
	private EditText et_phone;
	private ListView lv;
	/**
	 * 学生信息的dao
	 */
	private StudentDao dao;
	/**
	 * 所有的学生信息
	 */
	private List<StudentInfo> infos;
	/**
	 * 全局的数据适配器
	 */
	private StudentAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		et_name = (EditText) findViewById(R.id.et_name);
		et_phone = (EditText) findViewById(R.id.et_phone);
		lv = (ListView) findViewById(R.id.lv);
		dao = new StudentDao(this);
		infos = dao.findAll();
		adapter = new StudentAdapter();
		lv.setAdapter(adapter);
	}
	/**
	 * 添加一个信息到数据库
	 * @param view
	 */
	public void add(View view){
		String name = et_name.getText().toString().trim();
		String phone = et_phone.getText().toString().trim();
		if(TextUtils.isEmpty(name)||TextUtils.isEmpty(phone)){
			Toast.makeText(this, "数据不能为空", Toast.LENGTH_SHORT).show();
			return ;
		}
		long result = dao.add(name, phone);
		if(result>0){
			Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
			infos = dao.findAll();
			adapter.notifyDataSetChanged();//-->从新调用getcount 调用getview
		}else{
			Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show();
		}
	}


	private class StudentAdapter extends BaseAdapter{
		@Override
		public int getCount() {
			return infos.size();
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			System.out.println("position"+position);
			View view = View.inflate(MainActivity.this, R.layout.item, null);
			TextView tv_id = (TextView) view.findViewById(R.id.tv_id);
			TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
			TextView tv_phone = (TextView) view.findViewById(R.id.tv_phone);
			ImageView iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
			iv_delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog.Builder builder = new Builder(MainActivity.this);
					builder.setTitle("提醒");
					builder.setMessage("是否确定删除？");
					builder.setPositiveButton("确定删除", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							int result = dao.delete(infos.get(position).getId());
							Toast.makeText(MainActivity.this, "删除了"+result+"个记录", Toast.LENGTH_SHORT).show();
							infos = dao.findAll();
							adapter.notifyDataSetChanged();
						}
					});
					builder.setNegativeButton("取消", null);
					builder.create().show();
				}
			});
			tv_id.setText(infos.get(position).getId());
			tv_name.setText(infos.get(position).getName());
			tv_phone.setText(infos.get(position).getPhone());
			return view;
		}
		@Override
		public Object getItem(int position) {
			return null;
		}
		@Override
		public long getItemId(int position) {
			return 0;
		}
	}
}
