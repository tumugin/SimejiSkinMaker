package jisakuroom.simejiskinmaker;

import java.io.File;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	//コントロール関連
	//ボタン
	public Button Button1;
	private static final int REQUEST_GALLERY = 0;
	private static final int REQUEST_CROP_PICK = 1;
	private static final int REQUEST_CROP_PICK2 = 2;
	//変数
	public String ThemeName;
	public Uri ThemeImage;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ボタン初期化
        Button1 = (Button) findViewById(R.id.Button1);
        Button1.setOnClickListener(new Button1Click());
    }
    
    //Button1クリックイベント
    public class Button1Click implements OnClickListener{
		@Override
		public void onClick(View v) {
        	final EditText edtInput = new EditText(MainActivity.this);
        	AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);  
        	alert.setIcon(android.R.drawable.ic_menu_edit);
        	alert.setView(edtInput);
        	alert.setTitle("テーマの作成");
        	alert.setMessage("テーマの名前を入力してください");
        	alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            	public void onClick(DialogInterface dialog, int whichButton) {
            		/* OKボタンをクリックした時の処理 */
            		Toast.makeText(MainActivity.this, "スキンに使う画像を選択してください", Toast.LENGTH_LONG).show();
                    try {
                    	ThemeName = edtInput.getText().toString();
            			Intent intent = new Intent();
            			intent.setType("image/*");
            			intent.setAction(Intent.ACTION_GET_CONTENT);
            			startActivityForResult(intent, REQUEST_GALLERY);
            		} catch (Exception e) {
            			Toast.makeText(MainActivity.this, "ギャラリーの起動に失敗しました", Toast.LENGTH_LONG).show();
            		}
            	}
            });
        	alert.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
            	public void onClick(DialogInterface dialog, int whichButton) {
            		/* キャンセルボタンをクリックした時の処理 */
            	}
            });
        	alert.show();
		}
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	//元画像取得
    	if(requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
    		//変数に代入
    		ThemeImage = data.getData();
    		//横用トリミング開始
    		Toast.makeText(MainActivity.this, "キーボードサイズに合わせてトリミングを行います(横画面)", Toast.LENGTH_LONG).show();
    		RunTrimmingLand(ThemeImage);
    	}
    	if(requestCode == REQUEST_CROP_PICK && resultCode == RESULT_OK) {
    		//縦画面トリミング開始
    		Toast.makeText(MainActivity.this, "キーボードサイズに合わせてトリミングを行います(縦画面)", Toast.LENGTH_LONG).show();
    		RunTrimmingPort(ThemeImage);
    	}
    	if(requestCode == REQUEST_CROP_PICK2 && resultCode == RESULT_OK) {
    		Toast.makeText(MainActivity.this, "スキンの作成が完了しました!!", Toast.LENGTH_LONG).show();
    	}
    }
    
    public void RunTrimmingPort(Uri Datauri){
    	//トリミングアプリ起動
    	//サイズ算出
    	DisplayMetrics metrics = new DisplayMetrics(); 
    	getWindowManager().getDefaultDisplay().getMetrics(metrics);
    	int screen_width;
    	int screen_height;
    	if(metrics.heightPixels > metrics.widthPixels){
    		screen_width = metrics.heightPixels;
    		screen_height = metrics.widthPixels;
    	}else{
    		screen_width = metrics.widthPixels;
    		screen_height = metrics.heightPixels;
    	}
    	
    	int width = screen_height;//横サイズ
    	int height = (int) (screen_width * 0.246875);//縦サイズ
    	
    	Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setData(Datauri);
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("aspectX", width);
        intent.putExtra("aspectY", height);
        intent.putExtra("scale", true);
        intent.putExtra("output", Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/Simeji/skins/",ThemeName + "_port.png")));
        startActivityForResult(intent, REQUEST_CROP_PICK2);
    }
    
    public void RunTrimmingLand(Uri Datauri){
    	//トリミングアプリ起動
    	//サイズ算出
    	DisplayMetrics metrics = new DisplayMetrics(); 
    	getWindowManager().getDefaultDisplay().getMetrics(metrics);
    	int screen_width;
    	int screen_height;
    	if(metrics.heightPixels > metrics.widthPixels){
    		screen_width = metrics.heightPixels;
    		screen_height = metrics.widthPixels;
    	}else{
    		screen_width = metrics.widthPixels;
    		screen_height = metrics.heightPixels;
    	}
    	
    	int width = screen_width;//横サイズ
    	int height = (int) (screen_height * 0.43);//縦サイズ
    	//トリミングアプリ起動
    	Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setData(Datauri);
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("aspectX", width);
        intent.putExtra("aspectY", height);
        intent.putExtra("scale", true);
        intent.putExtra("output", Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/Simeji/skins/",ThemeName + "_land.png")));
        startActivityForResult(intent, REQUEST_CROP_PICK);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
}
