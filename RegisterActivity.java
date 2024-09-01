package com.garima.sukhmayfoundation;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.garima.sukhmayfoundation.model.record;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    FloatingActionButton floating;
    CircleImageView imageView;
    TextInputEditText t1,t2,t3;
    Button btn;
    FirebaseDatabase database;
     DatabaseReference databaseReference;
     StorageReference storageReference;
     Uri filepath;
     Bitmap bitmap;
     SharedPreferences pref;
     SharedPreferences.Editor editor;
     TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        floating=findViewById(R.id.floatingbutton);
        imageView=findViewById(R.id.imageview);
        t1=findViewById(R.id.text);
        t2=findViewById(R.id.text1);
        t3=findViewById(R.id.text2);
        btn=findViewById(R.id.button);
        //tv=findViewById(R.id.textview6);
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("MyRecords");
        storageReference= FirebaseStorage.getInstance().getReference();
        pref=getSharedPreferences("mypref",MODE_PRIVATE);
        editor=pref.edit();

           /*tv.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent i=new Intent(RegisterActivity.this,LoginActivity.class);
                   startActivity(i);
                   finish();
               }
           });
*/
        if (pref.getString("name",null)!=null
                && pref.getString("phone",null)!=null
                && pref.getString("pass",null)!=null
                && pref.getString("img",null)!=null)
        {

            Intent i=new Intent(RegisterActivity.this,HomeActivity.class);
            startActivity(i);
            finish();
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgressDialog pd=new ProgressDialog(RegisterActivity.this);
                pd.setMessage("Processing");
                pd.show();
           try {

               StorageReference ref = storageReference.child("Images/"
                       + System.currentTimeMillis() + "." + getFileExtension(filepath));

               Glide.with(RegisterActivity.this).load(filepath).transform(new CircleCrop()).into(imageView);
               imageView.setImageURI(filepath);


               ref.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                   @Override
                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                       Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                       uriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                           @Override
                           public void onSuccess(Uri uri) {

                               String img = uri.toString();
                               String name = t1.getText().toString();
                               String phone = t2.getText().toString();
                               String pass = t3.getText().toString();

                               editor.putString("name", name);
                               editor.putString("phone", phone);
                               editor.putString("pass", pass);
                               editor.putString("img", img);
                               editor.commit();

                               record r = new record();
                               r.setName(name);
                               r.setPhone(phone);
                               r.setPass(pass);
                               r.setImageurl(img);

                               String childid = databaseReference.push().getKey();
                               databaseReference.child(childid).setValue(r);
                               Toast.makeText(RegisterActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                               Intent i = new Intent(RegisterActivity.this, HomeActivity.class);
                               startActivity(i);
                               finish();

                           }
                       });
                       pd.dismiss();

                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Toast.makeText(RegisterActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                       pd.dismiss();
                   }
               });
           }catch (Exception e)
           {
               Toast.makeText(RegisterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
               pd.dismiss();
           }
            }
        });


         floating.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 Dialog dialog=new Dialog(RegisterActivity.this);
                 dialog.setContentView(R.layout.design_ui);

                 ImageButton imageButton=dialog.findViewById(R.id.image1);
                 ImageButton imageButton2=dialog.findViewById(R.id.image2);

                 imageButton.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                         startActivityForResult(i,0);

                         dialog.dismiss();

                     }
                 });

                 imageButton2.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {

                         Intent i=new Intent(Intent.ACTION_PICK);
                         i.setType("image/*");
                         i.setAction(Intent.ACTION_GET_CONTENT);
                         startActivityForResult(i,1);

                         dialog.dismiss();

                     }
                 });
                 dialog.show();
             }
         });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode==0 && resultCode==RESULT_OK && data!=null)
        {
            Bundle b=data.getExtras();
             bitmap= (Bitmap)b.get("data");
            imageView.setImageBitmap(bitmap);

        } else if (requestCode==1 && resultCode==RESULT_OK && data!=null)
        {

            filepath = data.getData();
            imageView.setImageURI(filepath);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public String getFileExtension(Uri filepath)
    {
        ContentResolver cr=getContentResolver(); // getcontentresolver is used to get the file path from your phone
        MimeTypeMap map=MimeTypeMap.getSingleton(); //getSingleton is used to provide the file extension of the file
        return map.getExtensionFromMimeType(cr.getType(filepath));
    }


}