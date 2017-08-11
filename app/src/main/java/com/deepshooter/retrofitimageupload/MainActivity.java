package com.deepshooter.retrofitimageupload;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.deepshooter.retrofitimageupload.model.OutPutBean;
import com.deepshooter.retrofitimageupload.utils.CircularImageView;
import com.deepshooter.retrofitimageupload.utils.ConnectionDetector;
import com.deepshooter.retrofitimageupload.utils.OnResponseListener;
import com.deepshooter.retrofitimageupload.utils.WebServices;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;


public class MainActivity extends AppCompatActivity implements OnResponseListener {




    private String uriSting;
    String mFileNameGallery;
    String selectedImagePath;


    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static int SELECT_FILE = 1;
    String PicSourceType = null;
    Bitmap bitmap;
    Bitmap bitmapFromGallery;
    File myDir;

    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "AnyThing";
    Uri fileUri; // file url to store image/video
    String  myimagepath;


    Button mOpenCamera , mOpenGallery;
    CircularImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeValue();
    }


    private void initializeValue()
    {

        mOpenCamera =  (Button) findViewById(R.id.open_Camera);
        mOpenGallery = (Button) findViewById(R.id.open_Gallery);
        mImageView =   (CircularImageView) findViewById(R.id.image);

        setValues();

    }



    private void setValues()
    {


        mOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                // start the image capture Intent
                startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);



            }
        });


        mOpenGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);



            }
        });






    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        myDir = new File(Environment.getExternalStorageDirectory().toString(), IMAGE_DIRECTORY_NAME);

        if (!myDir.exists()) {
            Log.e("Checkdir", "okkkkkkkkkkkkk");
            myDir.mkdir();
        }

        if (resultCode == RESULT_OK) {


            if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {

                PicSourceType = "C";

                BitmapFactory.Options options = new BitmapFactory.Options();
                // downsizing image as it throws OutOfMemory Exception for larger images
                options.inSampleSize = 8;
                bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);


                myimagepath = compressImage(fileUri.getPath(), 1);
                Log.e("pathCcc", myimagepath + "");

                uploadImage(myimagepath);
                mImageView.setImageBitmap(bitmap);

            }else if (requestCode == SELECT_FILE) {

                PicSourceType = "G";
                onSelectFromGalleryResult(data);
                mImageView.setImageBitmap(bitmapFromGallery);
            }

        }

    }




    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        if (cursor == null) {
            Toast.makeText(getApplicationContext(), "Size is not Enough", Toast.LENGTH_SHORT).show();

        } else {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();

            String selectedImagePath = cursor.getString(column_index);
            Log.d("tokens", selectedImagePath);


            Bitmap bm;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(selectedImagePath, options);
            final int REQUIRED_SIZE = 200;
            int scale = 1;
            while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                    && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;
            options.inSampleSize = scale;
            options.inJustDecodeBounds = false;
            compressImage(selectedImagePath, 2);

            myimagepath = compressImage(selectedImagePath, 2);
            Log.e("pathGgg", myimagepath + "");

            uploadImage(myimagepath);

            bm = BitmapFactory.decodeFile(uriSting, options);
            Log.d("captureds", uriSting);
            Log.d("capt", "" + bm);
            fetchImageName(selectedImagePath);
        }

    }


    private void fetchImageName(String selectedImagePath) {

        mFileNameGallery = "";
        StringTokenizer st = new StringTokenizer(selectedImagePath, "/");

        while (st.hasMoreTokens()) {
            mFileNameGallery = st.nextToken().toString();

        }

        Log.e("tokens", mFileNameGallery);
        this.selectedImagePath = selectedImagePath;


    }

    public String compressImage(String imageUri, int flag) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
            Log.e("actual_height", bmp.getHeight() + " ");
            Log.e("actual_width", bmp.getWidth() + " ");
            bitmapFromGallery = bmp;
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
            Log.e("reduced_height", scaledBitmap.getHeight() + " ");
            Log.e("reduced_width", scaledBitmap.getWidth() + " ");
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename;
        if (flag == 1) {
            filename = imageUri;
        } else {
            filename = getFilename();
        }

        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        Log.e("uristring", "-->" + uriSting);

        return uriSting;


    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;

        }

        return inSampleSize;
    }


    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }


    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }


    @Override
    public void OnResponse(Object response, WebServices.ApiType URL, boolean isSucces) {

        if (URL == WebServices.ApiType.postUploadImage) {

            if (isSucces) {

                OutPutBean outPutBean = (OutPutBean) response;

                  if(outPutBean == null)
                  {
                      Toast.makeText(this,"Not Done",Toast.LENGTH_LONG).show();
                  }else {

                      Toast.makeText(this,"Done",Toast.LENGTH_LONG).show();
                      Toast.makeText(this,outPutBean.getStatuscode()+"",Toast.LENGTH_LONG).show();
                  }
                Toast.makeText(this,"Success",Toast.LENGTH_LONG).show();
            }else {


                Toast.makeText(this,"Failure",Toast.LENGTH_LONG).show();
            }

        }

    }


    private void uploadImage(String filePath) {


        ConnectionDetector mDetector = new ConnectionDetector(getApplicationContext());
        if (mDetector.isConnectingToInternet()) {

            WebServices<OutPutBean> fileUploadWebServices = new WebServices<>(MainActivity.this);
            fileUploadWebServices.uploadFile(WebServices.PublicDevService, WebServices.ApiType.postUploadImage, filePath, "12345");


        }else {
            Toast.makeText(getApplicationContext(), getString(R.string.check_internet), Toast.LENGTH_LONG).show();
        }


    }
}
