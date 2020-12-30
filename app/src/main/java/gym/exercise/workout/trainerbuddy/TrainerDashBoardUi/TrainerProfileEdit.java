package gym.exercise.workout.trainerbuddy.TrainerDashBoardUi;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import gym.exercise.workout.trainerbuddy.DataBaseHandler.DataBaseHandler;
import gym.exercise.workout.trainerbuddy.DataBaseHandler.LocalDataBaseHandler;
import gym.exercise.workout.trainerbuddy.Entities.ImportantFunctions;
import gym.exercise.workout.trainerbuddy.Entities.Trainer;
import gym.exercise.workout.trainerbuddy.R;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;
import static androidx.core.content.FileProvider.getUriForFile;


public class TrainerProfileEdit extends Fragment implements View.OnClickListener{
    private StorageReference mStorageRef;
    private  static Context mcontext;
    ImportantFunctions impFun;
    private  Trainer self;
    Button update;
    CardView EditProfileImage;
    ImageView PHOTO;
    EditText nameEdit, mobileEdit, alternateEdit, addressEdit, gymNameEdit, ageEdit, weightEdit, heightEdit, aboutEdit, dobEdit, foodEdit;

    LocalDataBaseHandler LDB;
    public static TrainerProfileEdit newInstance(Trainer trainer) {
//        self =trainer;
        return  new TrainerProfileEdit();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View Root= inflater.inflate(R.layout.trainer_profile_edit, container, false);
        mcontext =requireContext();
        LDB =new LocalDataBaseHandler(mcontext);
        impFun =new ImportantFunctions(mcontext,getLayoutInflater());
        self=LDB.getTrainerLDB();

        //Photo Section
        EditProfileImage=Root.findViewById(R.id.TrainerProfileImage);
        EditProfileImage.setOnClickListener(this);
        PHOTO=Root.findViewById(R.id.TrainerProfilePIC);mStorageRef = FirebaseStorage.getInstance().getReference();





        // Entities Section
        nameEdit = Root.findViewById(R.id.nameEdit);
        mobileEdit = Root.findViewById(R.id.mobileEdit);
        alternateEdit = Root.findViewById(R.id.alternateEdit);
        addressEdit = Root.findViewById(R.id.addressEdit);
        gymNameEdit = Root.findViewById(R.id.gymNameEdit);
        ageEdit = Root.findViewById(R.id.ageEdit);
        weightEdit =Root.findViewById(R.id.weightEdit);
        heightEdit = Root.findViewById(R.id.heightEdit);
        aboutEdit = Root.findViewById(R.id.aboutEdit);
        dobEdit =Root.findViewById(R.id.dobEdit);
        foodEdit = Root.findViewById(R.id.foodEdit);

        update = Root.findViewById(R.id.update);
        update.setOnClickListener(this);


        nameEdit.setText(self.getName());
        mobileEdit.setText(self.getMobile());
        alternateEdit.setText(self.getAlternateMobile());
        addressEdit.setText(self.getGymAddress());
        gymNameEdit.setText(self.getGymName());
        ageEdit.setText(String.valueOf(self.getAge()));
        weightEdit.setText(String.valueOf(self.getWeight()));
        heightEdit.setText(String.valueOf(self.getHeight()));
        aboutEdit.setText(self.getAbout());
        //dobEdit.setText(self.getName());
        //foodEdit.setText(self.getName());

        return Root;
    }



    public void  ChangeFragment(){

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.TrainerProfileContainer,  TrainerProfileShow.newInstance());
       // fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.TrainerProfileImage:
                showImagePickerOptions();
                break;
            case R.id.update:
                UpdateTrainerProfile();
                break;
        }
    }

    public Trainer getInputdata(){
        Trainer self= new Trainer();
        self.setUID(impFun.getSharedPrefUID());
        self.setName(nameEdit.getText().toString());
        self.setMobile(mobileEdit.getText().toString());
        self.setAlternateMobile(alternateEdit.getText().toString());
        self.setGymAddress(addressEdit.getText().toString());
        self.setGymName(gymNameEdit.getText().toString());
        self.setGymAddress(addressEdit.getText().toString());
        self.setAge(Integer.parseInt(ageEdit.getText().toString()));
        self.setHeight(Integer.parseInt(heightEdit.getText().toString()));
        self.setWeight(Integer.parseInt(weightEdit.getText().toString()));
        self.setAbout(aboutEdit.getText().toString());

        //TODO  Add [UI also] rest of items as Entity->Trainer Have

        return self;
    }

    //todo Validate Function
    private boolean isValidInput(){

        // todo Validation Code

        return true;
    }

    public void UpdateTrainerProfile(){
        if(isValidInput()){
            if(impFun.isConnectedToInternet()){
                impFun.showProgressingView();
                Trainer trainer=getInputdata();
                Log.d("Update Profile", "UpdateTrainerProfile: "+trainer.getAbout());
                try {
                    DataBaseHandler db = new DataBaseHandler(mcontext);
                    db.RegisterTrainer(trainer);
                    impFun.hideProgressingView();
                    Toast.makeText(mcontext, "Profile Updated!!", Toast.LENGTH_LONG).show();
                }catch(Exception e){
                    Log.d("TAG", "UpdateTrainerProfile: Error "+e);
                    Toast.makeText(mcontext, "Profile Update Failed!!", Toast.LENGTH_LONG).show();
                }
            }
            else{
                impFun.ShowToast(getLayoutInflater(), "No Internet Connection!!",
                        "Please, Connect to  Internet for Update your Profile!!");
            }
        }


    }



    /*-------------Image Picker -----------------*/

    private static final int FILE_SELECT_CODE = 101;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 200;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 202;
    public static String fileName;
    public static final int REQUEST_IMAGE_CAPTURE = 0;
    public static final int REQUEST_GALLERY_IMAGE = 1;
    private static final String TAG = "Image Croping";


    public void showImagePickerOptions() {
        if(impFun.isConnectedToInternet()) {
            // setup the alert builder
            AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
            builder.setTitle("Set a Profile Picture");

            // add a list
            String[] opts = {"Take A Pic", "Choose a Pic"};
            builder.setItems(opts, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    switch (i) {
                        case 0:
                            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
                            break;
                        case 1:
                            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_REQUEST_CODE);
                            break;
                    }
                }
            });
            // create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else {
            impFun.ShowToast(getLayoutInflater(), "No Internet Connection!!",
                    "Please, Connect to  Internet for Update your Profile!!");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean StorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (StorageAccepted && cameraAccepted) {
//                        Toast.makeText(mcontext, "Permission Granted, Now you can access  camera.", Toast.LENGTH_SHORT).show();
//                        //OPEN CAMERA HERE
                        fileName = System.currentTimeMillis() + ".jpg";
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, getCacheImagePath(fileName));
                        if (takePictureIntent.resolveActivity(mcontext.getPackageManager()) != null) {
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        }

                    }else {
                        Toast.makeText(mcontext, "Permission Denied, You cannot access  camera.", Toast.LENGTH_SHORT).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA) && shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) {

                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA},
                                                        CAMERA_PERMISSION_REQUEST_CODE);
                                            }
                                        });
                                return;
                            }
                            else{
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                        Uri.fromParts("package", getContext().getPackageName(), null));
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                            }
                                        });
                                return;

                            }
                        }

                    }
                }
                break;
            case STORAGE_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean WriteStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (WriteStorageAccepted && ReadStorageAccepted){
                        //OPEN Storage File  HERE
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto, REQUEST_GALLERY_IMAGE);
                    }else {
                        Toast.makeText(mcontext, "Permission Denied, You cannot Storage.", Toast.LENGTH_SHORT).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE},
                                                        STORAGE_PERMISSION_REQUEST_CODE);
                                            }
                                        });
                                return;
                            }else{
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                        Uri.fromParts("package", getContext().getPackageName(), null));
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                            }
                                        });
                                return;

                            }
                        }

                    }
                }
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case FILE_SELECT_CODE:
                if(resultCode == RESULT_OK
                        && data != null && data.getData() != null ) {
                    Uri filePath = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), filePath);
                        PHOTO.setImageBitmap(bitmap);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                break;

            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK) {
                    cropImage(getCacheImagePath(fileName));
                } else {
                    setResultCancelled();
                }
                break;

            case REQUEST_GALLERY_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri imageUri = data.getData();
                    cropImage(imageUri);
                } else {
                    setResultCancelled();
                }
                break;
            case UCrop.REQUEST_CROP:

                if (resultCode == RESULT_OK) {

                    handleUCropResult(data);
                } else {

                    setResultCancelled();
                }
                break;
            case UCrop.RESULT_ERROR:

                final Throwable cropError = UCrop.getError(data);
                Log.e(TAG, "Crop error: " + cropError);
                setResultCancelled();
                break;
            default:
                setResultCancelled();
        }//End Switch
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        Toast.makeText(mcontext, "showMessageOKCancel", Toast.LENGTH_SHORT).show();
        new AlertDialog.Builder(mcontext)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    private Uri getCacheImagePath(String fileName) {
        File path = new File(mcontext.getExternalCacheDir(), "camera");
        if (!path.exists()) path.mkdirs();
        File image = new File(path, fileName);
        return getUriForFile(getActivity(), mcontext.getPackageName() + ".provider", image);
    }

    private void cropImage(Uri sourceUri) {
        Uri destinationUri = Uri.fromFile(new File(mcontext.getCacheDir(), queryName(requireActivity().getContentResolver(), sourceUri)));
        UCrop.Options options = new UCrop.Options();
        int IMAGE_COMPRESSION = 80;
        options.setCompressionQuality(IMAGE_COMPRESSION);

        // applying UI theme
        options.setToolbarColor(ContextCompat.getColor(mcontext, R.color.colorPrimary));
        options.setStatusBarColor(ContextCompat.getColor(mcontext, R.color.colorPrimary));
        // options.setActiveWidgetColor(ContextCompat.getColor(mcontext, R.color.colorPrimary));

        boolean lockAspectRatio = false;
        int ASPECT_RATIO_X = 1;
        int ASPECT_RATIO_Y = 1;
        if (lockAspectRatio)
            options.withAspectRatio(ASPECT_RATIO_X, ASPECT_RATIO_Y);

        boolean setBitmapMaxWidthHeight = true;
        int bitmapMaxWidth = 10000;
        int bitmapMaxHeight = 10000;
        if (setBitmapMaxWidthHeight)
            options.withMaxResultSize(bitmapMaxWidth, bitmapMaxHeight);

        UCrop.of(sourceUri, destinationUri)
                .withOptions(options)
                .start(getActivity().getApplicationContext(), requireActivity().getSupportFragmentManager().findFragmentById(R.id.TrainerProfileContainer));
    }

    private void handleUCropResult(Intent data) {
        Log.d(TAG, "handleUCropResult: "+data);
        if (data == null) {
            setResultCancelled();
            return;
        }
        final Uri resultUri = UCrop.getOutput(data);
        setResultOk(resultUri);
    }

    private void setResultOk(Uri imagePath) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imagePath);
            PHOTO.setImageBitmap(bitmap);
            SaveProfilePhoto(bitmap,impFun.getSharedPrefUID());

        } catch (FileNotFoundException e) {
            Log.d("File NotFound ",""+e);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("IO exception",""+e);
        }

    }

    private void setResultCancelled() {
        Toast.makeText(mcontext, "Failed to set Profile picture!", Toast.LENGTH_SHORT).show();
    }

    private static String queryName(ContentResolver resolver, Uri uri) {
        Cursor returnCursor =
                resolver.query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }

    public void SaveProfilePhoto(Bitmap bitmap, String UID){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imageRef = storage.getReference().child("/Trainer/"+UID+".png");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(data,new StorageMetadata());
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(mcontext, "Failed!!", Toast.LENGTH_LONG).show();

                Log.d("TAG", "Upload is Failed"+exception );
            }
        })
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(mcontext, "Profile Photo saved!", Toast.LENGTH_LONG).show();
                        // todo update to local db
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                        Log.d("TAG", "Upload is " + progress + "% done");

                    }
                }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d("TAG", "Upload is paused");
            }
        });



    }


}