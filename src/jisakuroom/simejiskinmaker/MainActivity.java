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
	//�R���g���[���֘A
	//�{�^��
	public Button Button1;
	private static final int REQUEST_GALLERY = 0;
	private static final int REQUEST_CROP_PICK = 1;
	private static final int REQUEST_CROP_PICK2 = 2;
	//�ϐ�
	public String ThemeName;
	public Uri ThemeImage;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //�{�^��������
        Button1 = (Button) findViewById(R.id.Button1);
        Button1.setOnClickListener(new Button1Click());
    }
    
    //Button1�N���b�N�C�x���g
    public class Button1Click implements OnClickListener{
		@Override
		public void onClick(View v) {
        	final EditText edtInput = new EditText(MainActivity.this);
        	AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);  
        	alert.setIcon(android.R.drawable.ic_menu_edit);
        	alert.setView(edtInput);
        	alert.setTitle("�e�[�}�̍쐬");
        	alert.setMessage("�e�[�}�̖��O����͂��Ă�������");
        	alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            	public void onClick(DialogInterface dialog, int whichButton) {
            		/* OK�{�^�����N���b�N�������̏��� */
            		Toast.makeText(MainActivity.this, "�X�L���Ɏg���摜��I�����Ă�������", Toast.LENGTH_LONG).show();
                    try {
                    	ThemeName = edtInput.getText().toString();
            			Intent intent = new Intent();
            			intent.setType("image/*");
            			intent.setAction(Intent.ACTION_GET_CONTENT);
            			startActivityForResult(intent, REQUEST_GALLERY);
            		} catch (Exception e) {
            			Toast.makeText(MainActivity.this, "�M�������[�̋N���Ɏ��s���܂���", Toast.LENGTH_LONG).show();
            		}
            	}
            });
        	alert.setNegativeButton("�L�����Z��", new DialogInterface.OnClickListener() {
            	public void onClick(DialogInterface dialog, int whichButton) {
            		/* �L�����Z���{�^�����N���b�N�������̏��� */
            	}
            });
        	alert.show();
		}
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	//���摜�擾
    	if(requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
    		//�ϐ��ɑ��
    		ThemeImage = data.getData();
    		//���p�g���~���O�J�n
    		Toast.makeText(MainActivity.this, "�L�[�{�[�h�T�C�Y�ɍ��킹�ăg���~���O���s���܂�(�����)", Toast.LENGTH_LONG).show();
    		RunTrimmingLand(ThemeImage);
    	}
    	if(requestCode == REQUEST_CROP_PICK && resultCode == RESULT_OK) {
    		//�c��ʃg���~���O�J�n
    		Toast.makeText(MainActivity.this, "�L�[�{�[�h�T�C�Y�ɍ��킹�ăg���~���O���s���܂�(�c���)", Toast.LENGTH_LONG).show();
    		RunTrimmingPort(ThemeImage);
    	}
    	if(requestCode == REQUEST_CROP_PICK2 && resultCode == RESULT_OK) {
    		Toast.makeText(MainActivity.this, "�X�L���̍쐬���������܂���!!", Toast.LENGTH_LONG).show();
    	}
    }
    
    public void RunTrimmingPort(Uri Datauri){
    	//�g���~���O�A�v���N��
    	//�T�C�Y�Z�o
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
    	
    	int width = screen_height;//���T�C�Y
    	int height = (int) (screen_width * 0.246875);//�c�T�C�Y
    	
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
    	//�g���~���O�A�v���N��
    	//�T�C�Y�Z�o
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
    	
    	int width = screen_width;//���T�C�Y
    	int height = (int) (screen_height * 0.43);//�c�T�C�Y
    	//�g���~���O�A�v���N��
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
