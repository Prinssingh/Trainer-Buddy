package gym.exercise.workout.trainerbuddy.Login;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
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
import java.net.URISyntaxException;
import java.util.Objects;

import gym.exercise.workout.trainerbuddy.DataBaseHandler.DataBaseHandler;
import gym.exercise.workout.trainerbuddy.Entities.ImportantFunctions;
import gym.exercise.workout.trainerbuddy.Entities.Trainer;
import gym.exercise.workout.trainerbuddy.R;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;
import static androidx.core.content.FileProvider.getUriForFile;
import static gym.exercise.workout.trainerbuddy.R.id.registerButton;

public class RegisterPage extends Fragment implements View.OnClickListener {
    TextView login;
    Button register;
    EditText Name,Email,Password,Mobile,confirmPass;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    ViewGroup progressView;
    protected boolean isProgressShowing = false;
    private FirebaseAuth mAuth;
    ImportantFunctions impFun;


    ImageButton img;
    private static final int FILE_SELECT_CODE = 101;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 200;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 202;

    private String DeviceId;

    private StorageReference mStorageRef;
    private  static Context mcontext;
    public static String fileName;
    public static final int REQUEST_IMAGE_CAPTURE = 0;
    public static final int REQUEST_GALLERY_IMAGE = 1;
    private boolean lockAspectRatio = true, setBitmapMaxWidthHeight = true;
    private int ASPECT_RATIO_X = 1, ASPECT_RATIO_Y = 1, bitmapMaxWidth = 10000, bitmapMaxHeight = 10000;
    private int IMAGE_COMPRESSION = 80;
    private static final String TAG = "Image Croping";


    public static RegisterPage newInstance() {
        return new RegisterPage();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View Root =inflater.inflate(R.layout.register_page, container, false);

        sp=requireContext().getSharedPreferences("TrainerBuddyPref", Context.MODE_PRIVATE);
        editor=sp.edit();
        mAuth =FirebaseAuth.getInstance();
        impFun= new ImportantFunctions(getContext());

        /*todo*/
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mcontext =getContext();

        login=Root.findViewById(R.id.Login);
        login.setOnClickListener(this);
        register=Root.findViewById(registerButton);
        register.setOnClickListener(this);
        Name=Root.findViewById(R.id.nameRegister);
        Email=Root.findViewById(R.id.emailRegister);
        Mobile=Root.findViewById(R.id.mobileRegister);
        Password=Root.findViewById(R.id.passwordRegister);
        confirmPass=Root.findViewById(R.id.confirmPasswordRegister);

        return Root;
    }


    public void  ChangeFragment(Fragment fragment){

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.LoginContainer,  fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.Login:
                ChangeFragment(LoginPage.newInstance());
                break;
            case R.id.registerButton:
                RegisterToApp();
                break;
        }


    }

    public boolean isValidInput(){
        boolean valid=true;
        if(Name.getText().toString().isEmpty()){
            Name.setError("Your can't left name field empty");
            Name.requestFocus();
            valid=false;
        }
        else if( Email.getText().toString().isEmpty()){
            Email.setError("You can't left email field empty!!");
            Email.requestFocus();
            valid=false;
        }
        else if(Mobile.getText().toString().isEmpty()){
            Mobile.setError("Your can't left Password field empty");
            Mobile.requestFocus();
            valid=false;
        }
        else if(Password.getText().toString().isEmpty()){
            Password.setError("Your can't left Password field empty");
            Password.requestFocus();
            valid=false;
        }
        else if (!Name.getText().toString().matches("^[A-Z a-z]+$")){
            Name.setError("Enter Character's and space only !!");
            Name.requestFocus();
            valid=false;
        }

        else if (!Patterns.EMAIL_ADDRESS.matcher(Email.getText().toString()).matches()){
            Email.setError("Please Fill Correct email address!!");
            Email.requestFocus();
            valid=false;
        }
        else if(!Mobile.getText().toString().matches("^(\\+?\\d{1,4}[\\s-])?(?!0+\\s+,?$)\\d{10}\\s*,?$")){
            Mobile.setError("Wrong mobile number formatting");
            Mobile.requestFocus();
            valid=false;
        }
        else if(Password.getText().length()<6){
            Password.setError("Weak PassWord!! Use atleast 6 character password");
            Password.requestFocus();
            valid=false;
        }
        else if(!Password.getText().toString().equals(confirmPass.getText().toString())){
            confirmPass.setError("Password & Confirm password are not matching!!");
            confirmPass.requestFocus();
            valid=false;

        }

        return valid;
    }
    public void setAllDisable(){
        Name.setEnabled(false);
        Email.setEnabled(false);
        Mobile.setEnabled(false);
        Password.setEnabled(false);
        confirmPass.setEnabled(false);
        register.setEnabled(false);
        login.setEnabled(false);
    }

    public void setAllEnable(){
        Name.setEnabled(true);
        Email.setEnabled(true);
        Mobile.setEnabled(true);
        Password.setEnabled(true);
        confirmPass.setEnabled(true);
        register.setEnabled(true);
        login.setEnabled(true);
    }

    public void RegisterToApp(){

        if(isValidInput()){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user!= null){

                editor.putBoolean("Register",true).commit();
                editor.putString("User_Name",user.getDisplayName()).commit();
                editor.putString("User_Email",user.getEmail()).commit();
                editor.putString("User_UID",user.getUid()).commit();

                ChangeFragment(LoginPage.newInstance());
            }
            else if(impFun.isConnectedToInternet()){
                showProgressingView();
                setAllDisable();
                final String email = Email.getText().toString();
                final String password = Password.getText().toString();
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success
                                    FirebaseUser user = mAuth.getCurrentUser();

//                                    //  set an Display name to  User.
//                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                                            .setDisplayName(Name1)
//                                            .build();
//
//                                    user.updateProfile(profileUpdates)
//                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<Void> task) {
//                                                    if (task.isSuccessful()) {
//                                                        Log.d("TAG", "User profile updated. Set Diaplay NAme");
//                                                    }
//                                                }
//                                            });
                                    //Setup Data
                                    assert user != null;

                                    editor.putBoolean("Register",true).commit();
                                    editor.putString("User_Email",email).commit();
                                    editor.putString("User_UID",user.getUid()).commit();
                                    editor.putString("User_Password",password).commit();
                                    Toast.makeText(getContext(),"Register Success!!",Toast.LENGTH_LONG).show();

                                    Trainer trainer =new Trainer();
                                    trainer.setName(Name.getText().toString());
                                    trainer.setEmail(email);
                                    trainer.setMobile(Mobile.getText().toString());
                                    trainer.setPassword(password);
                                    trainer.setUID(user.getUid());
                                    DataBaseHandler db =new DataBaseHandler(requireContext());
                                    db.RegisterTrainer(trainer);

                                    //send Verification Email
                                    sendVerificationEmail();

                                    // Hide Pregressbar and set Enable Everything
                                    hideProgressingView();
                                    setAllEnable();

                                }
                                else {
                                    // If sign in fails, display a message to the user.
                                    hideProgressingView();
                                    setAllEnable();
                                    Log.w("TAG", "createUserWithEmail:failed", task.getException());
                                    Toast.makeText(getContext(), "Registration failed.", Toast.LENGTH_LONG).show();
                                    try
                                    {
                                        throw Objects.requireNonNull(task.getException());
                                    }

                                    catch (FirebaseAuthWeakPasswordException weakPassword)
                                    {
                                        Log.d("TAG", "onComplete: weak_password");
                                        Password.setError("Weak PassWord!! Use atleast 6 character password");
                                        Password.requestFocus();
                                    }

                                    catch (FirebaseAuthInvalidCredentialsException malformedEmail)
                                    {
                                        Log.d("TAG", "onComplete: malformed_email");
                                        Email.setError("Please Fill Correct email address!!");
                                        Email.requestFocus();
                                    }
                                    catch (FirebaseAuthUserCollisionException existEmail)
                                    {
                                        Log.d("TAG", "onComplete: exist_email");
                                        Toast.makeText(requireContext(), "Email Already Exists!! Try login!", Toast.LENGTH_SHORT).show();
                                         ChangeFragment( LoginPage.newInstance());
                                    }
                                    catch (Exception e)
                                    {
                                        Log.d("TAG", "onComplete: " + e.getMessage());
                                    }
                                }
                            }
                        });
            }
            else{
                impFun.ShowToast(getLayoutInflater(),"No Internet Connection!!",
                        "Please, Connect to  Internet for Register with Math Reasoning!!");
            }
        }
    }



    public void showProgressingView() {

        if (!isProgressShowing) {
            isProgressShowing = true;
            progressView = (ViewGroup) getLayoutInflater().inflate(R.layout.mprogressbar, null);
            View v = requireActivity().findViewById(android.R.id.content).getRootView();
            ViewGroup viewGroup = (ViewGroup) v;
            viewGroup.addView(progressView);
        }
    }

    public void hideProgressingView() {
        View v = requireActivity().findViewById(android.R.id.content).getRootView();
        ViewGroup viewGroup = (ViewGroup) v;
        viewGroup.removeView(progressView);
        isProgressShowing = false;
    }

    private void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent
                            Log.d("TAG", "Email Send Success!!!" + task.isSuccessful());
                            // after email is sent just logout the user and finish this activity
                            FirebaseAuth.getInstance().signOut();
                            Toast.makeText(requireContext(), "Email Send LOGIN PLEASE", Toast.LENGTH_SHORT).show();
                            // Goto to login page
                            ChangeFragment(LoginPage.newInstance());
                        }
                        else
                        {
                            // email not sent, so display message and restart the activity or do whatever you wish to do
                            Log.d("TAG", "Email Send Failed!!!" + task.isSuccessful());

                        }

                    }
                });
    }


    /* Extera Items*/

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(requireContext(), "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Toast.makeText(requireContext(),"Error:"+e,Toast.LENGTH_LONG).show();
        }
    }

    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                Log.d("TAG", "getPath: "+e);
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
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
                        img.setImageBitmap(bitmap);
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

    private void checkIfEmailVerified() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified())
        {
            // user is verified, so you can finish this activity or send user to activity which you want.

            Toast.makeText(requireContext(), "Successfully logged in", Toast.LENGTH_SHORT).show();
        }
        else
        {
            // email is not verified, so just prompt the message to the user and restart this activity.
            // NOTE: don't forget to log out the user.
            FirebaseAuth.getInstance().signOut();

            //restart this activity

        }
    }





    /* Testing ------------------------------*/

    public void showImagePickerOptions(Context context) {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Set a Profile Picture");

        // add a list
        String[] opts = {"Take A Pic", "Choose a Pic"};
        builder.setItems(opts, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch (i) {
                    case 0:
                        requestPermissions( new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
                        break;
                    case 1:
                        requestPermissions( new String[]{WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_REQUEST_CODE);
                        break;
                }
            }
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
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
        options.setCompressionQuality(IMAGE_COMPRESSION);

        // applying UI theme
        options.setToolbarColor(ContextCompat.getColor(mcontext, R.color.colorPrimary));
        options.setStatusBarColor(ContextCompat.getColor(mcontext, R.color.colorPrimary));
       // options.setActiveWidgetColor(ContextCompat.getColor(mcontext, R.color.colorPrimary));

        lockAspectRatio =false;
        if (lockAspectRatio)
            options.withAspectRatio(ASPECT_RATIO_X, ASPECT_RATIO_Y);

        if (setBitmapMaxWidthHeight)
            options.withMaxResultSize(bitmapMaxWidth, bitmapMaxHeight);

        UCrop.of(sourceUri, destinationUri)
                .withOptions(options)
                .start(getActivity().getApplicationContext(), getFragmentManager().findFragmentById(R.id.LoginContainer));
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
            img.setImageBitmap(bitmap);

            SaveProfilePhoto(bitmap,"prinsSingh","Trainer");

        } catch (FileNotFoundException e) {
            Log.d("File NotFound ",""+e);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("IO exception",""+e);
        }

    }

    private void setResultCancelled() {
        Toast.makeText(mcontext, "Result Cancllles", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent();
//        getActivity().setResult(Activity.RESULT_CANCELED, intent);
//        getActivity(). finish();
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


    public void SaveProfilePhoto(Bitmap bitmap,String UID,String UserType){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imageRef = storage.getReference().child(UserType+"/"+UID+".png");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(data,new StorageMetadata());
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.d(TAG, "Upload is Failed"+exception );
            }
        })
        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getContext(), "Profile Photo saved", Toast.LENGTH_SHORT).show();

            }
        })
        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                Log.d(TAG, "Upload is " + progress + "% done");

            }
        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "Upload is paused");
            }
        });

    }

    public void ShowProfilePhoto(String UID,String UserType){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imageRef = storage.getReference().child(UserType+"/"+UID+".png");
        final long ONE_MEGABYTE = 1024 * 1024;
        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

}