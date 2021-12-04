package fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.marketplaceapp.ImageAdapter;
import com.example.marketplaceapp.Images;
import com.example.marketplaceapp.Post;
import com.example.marketplaceapp.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ComposeFragment extends Fragment {
    public static final String TAG = "ComposeFragment";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;


    private EditText etDescription;
    private EditText etCaption;
    private EditText etPrice;
    private Button btnCaptureImage;
    private Button btnSubmitImage;
    private ImageView ivPostImage;
    private File photoFile;
    private List<File> photeFiles = new ArrayList<>();
    public String photoFileName = "photo.jpg";

    private ViewPager vpPostImages;
    private ImageAdapter adapter;
    private List<Bitmap> mImages;


    public ComposeFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compose, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //ActionBar actionBar = view.getSupportActionBar();
        //actionBar.hide();

        etDescription = view.findViewById(R.id.etDescription);
        btnCaptureImage = view.findViewById(R.id.btnCaptureImage);
        //ivPostImage = view.findViewById(R.id.ivPostImage);
        etCaption = view.findViewById(R.id.etCaption);
        etPrice = view.findViewById(R.id.etPrice);
        btnSubmitImage = view.findViewById(R.id.btnSubmitImage);

        vpPostImages = view.findViewById(R.id.vpPostImages);
        adapter = new ImageAdapter(getContext());
        vpPostImages.setAdapter(adapter);

        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCamera();
            }
        });
        btnSubmitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = etDescription.getText().toString();
                String caption = etCaption.getText().toString();
                int price = Integer.parseInt(etPrice.getText().toString());
                if (description.isEmpty()){
                    Toast.makeText(getContext(),"Description cannot be empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                /*if(photoFile == null || ivPostImage.getDrawable()==null){
                    Toast.makeText(getContext(),"There is no image!",Toast.LENGTH_SHORT).show();
                }*/
                ParseUser currentUser = ParseUser.getCurrentUser();
                savePost(description,currentUser,price,caption);//savePost(description,currentUser,photoFile);
            }
        });
    }
    private void launchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider.marketplaceapp", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
        photeFiles.add(photoFile);
        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            //onActivityResult( CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE,RESULT_OK,intent);

        }
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + fileName);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                           //ivPostImage.setImageBitmap(takenImage);
                adapter.addImage(takenImage);
                //mImages.add(takenImage);
            } else { // Result was a failure
                Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void savePost(String description, ParseUser currentUser, int price, String caption) {
        Post post = new Post();
        post.setCaption(caption);
        post.setPrice(price);
        post.setUser(currentUser);
        post.setDescription(description);
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e !=null){
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(getContext(),"Error while saving!",Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG,"Post save was successful");
                etDescription.setText("");
                etPrice.setText("");
                etCaption.setText("");
                //ivPostImage.setImageResource(0);
                File[] arr = new File[photeFiles.size()];
                photeFiles.toArray(arr); // fill the array
                for(int i =0 ;i< arr.length;i++) {
                    Images image = new Images();
                    image.setImage(new ParseFile(arr[i]));
                    image.setPost(post);
                    image.saveInBackground();
                }
            }
        });
        /*File[] arr = new File[photeFiles.size()];
        photeFiles.toArray(arr); // fill the array
        for(int i =0 ;i< arr.length;i++){
            Image image = new Image();
            image.setImage(new ParseFile(arr[i]));
            image.setPost(post);*/

    }

}