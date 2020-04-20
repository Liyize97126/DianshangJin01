package com.bawei.dianshangjin01;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

public class MainActivity extends AppCompatActivity {
    //定义
    private Button butt01,butt02,butt03,butt04,submityes,submitno;
    private ImageView image;
    private final int REQUEST_CODE = 100;
    private static final int REQUEST_IMAGE = 150;
    private AlertDialog.Builder builder;
    private EditText edittitle;
    private AlertDialog show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取id
        butt01 = findViewById(R.id.butt01);
        butt02 = findViewById(R.id.butt02);
        butt03 = findViewById(R.id.butt03);
        butt04 = findViewById(R.id.butt04);
        image = findViewById(R.id.image);
        //点击事件
        butt01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断系统版本，高于API 23的需要手动获取权限
                if(Build.VERSION.SDK_INT >= 23){
                    //checkSelfPermission方法就是检测是否有权限
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                        //拥有权限
                        Toast.makeText(MainActivity.this,"您已经获取权限，无需再次申请！",Toast.LENGTH_LONG).show();
                    }else {
                        //没有权限，申请权限
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},200);
                    }
                } else {
                    Toast.makeText(MainActivity.this,"仅6.0以上系统需要在此处获取权限，您无需在这里获取权限！",Toast.LENGTH_LONG).show();
                }
            }
        });
        butt02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断系统版本，高于API 23的需要手动获取权限
                if(Build.VERSION.SDK_INT >= 23){
                    //checkSelfPermission方法就是检测是否有权限
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED) {
                        //调用方法
                        openCaptureActivity();
                    }else {
                        //提示
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("提示");
                        builder.setMessage("未获取到相机使用权限，请先获取权限后再进行本操作！");
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        builder.setPositiveButton("去获取", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //申请权限
                                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},300);
                            }
                        });
                        builder.setCancelable(false);
                        builder.create();
                        builder.show();
                    }
                } else {
                    //调用方法
                    openCaptureActivity();
                }
            }
        });
        butt03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断系统版本，高于API 23的需要手动获取权限
                if(Build.VERSION.SDK_INT >= 23){
                    //checkSelfPermission方法就是检测是否有权限
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                        //调用方法
                        openImage();
                    }else {
                        //提示
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("提示");
                        builder.setMessage("未获取到文件读取权限，请先获取权限后再进行本操作！");
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        builder.setPositiveButton("去获取", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //申请权限
                                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},300);
                            }
                        });
                        builder.setCancelable(false);
                        builder.create();
                        builder.show();
                    }
                } else {
                    //调用方法
                    openImage();
                }
            }
        });
        butt04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建弹框
                builder = new AlertDialog.Builder(MainActivity.this);
                View inflate = View.inflate(MainActivity.this, R.layout.dialogcontent2, null);
                edittitle = inflate.findViewById(R.id.edittitle);
                submityes = inflate.findViewById(R.id.submityes);
                submitno = inflate.findViewById(R.id.submitno);
                //获取布局
                builder.setView(inflate);
                builder.setCancelable(false);
                //创建弹框
                builder.create();
                show = builder.show();
                //点击事件
                submityes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show.dismiss();
                        //获取弹框信息
                        final String name = edittitle.getText().toString();
                        //判断
                        if(TextUtils.isEmpty(name)){
                            Toast.makeText(MainActivity.this,"输入框不能为空！",Toast.LENGTH_LONG).show();
                        } else {
                            //创建弹框
                            builder = new AlertDialog.Builder(MainActivity.this);
                            View inflate = View.inflate(MainActivity.this, R.layout.dialogcontent1, null);
                            submityes = inflate.findViewById(R.id.submityes);
                            submitno = inflate.findViewById(R.id.submitno);
                            //获取布局
                            builder.setView(inflate);
                            builder.setCancelable(false);
                            //创建弹框
                            builder.create();
                            show = builder.show();
                            submityes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    show.dismiss();
                                    //生成带Logo的二维码图片
                                    Bitmap bitmap = CodeUtils.createImage(name, 400, 400, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                                    image.setImageBitmap(bitmap);
                                }
                            });
                            submitno.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    show.dismiss();
                                    //生成不带Logo的二维码图片
                                    Bitmap bitmap = CodeUtils.createImage(name, 400, 400, null);
                                    image.setImageBitmap(bitmap);
                                }
                            });
                            Toast.makeText(MainActivity.this,"二维码生成成功！",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                submitno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show.dismiss();
                    }
                });
            }
        });
    }
    //打开默认二维码扫描界面的方法
    private void openCaptureActivity(){
        //打开默认二维码扫描界面
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        startActivityForResult(intent,REQUEST_CODE);
    }
    //打开图库
    private void openImage(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE);
    }
    //处理权限返回结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //判断
        if(requestCode == 200 && grantResults.length == 3
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED
                && grantResults[2] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(MainActivity.this,"权限获取成功！",Toast.LENGTH_LONG).show();
        } else if(requestCode == 300 && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(MainActivity.this,"权限获取成功！",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this,"权限获取失败！",Toast.LENGTH_LONG).show();
        }
    }
    //返回结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //判断1
        if (requestCode == REQUEST_CODE && data != null) {
            //处理扫描结果（在界面上显示）
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(MainActivity.this, "解析结果：" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(MainActivity.this, "解析二维码失败！", Toast.LENGTH_LONG).show();
                }
            }
        }
        //判断2
        if (requestCode == REQUEST_IMAGE && data != null) {
            //处理扫描结果（在界面上显示）
            Uri uri = data.getData();
            String filePath = UriTofilePath.getFilePathByUri(MainActivity.this, uri);
            try{
                CodeUtils.analyzeBitmap(filePath, new CodeUtils.AnalyzeCallback() {
                    @Override
                    public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                        Toast.makeText(MainActivity.this, "解析结果：" + result, Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onAnalyzeFailed() {
                        Toast.makeText(MainActivity.this, "解析二维码失败！", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
