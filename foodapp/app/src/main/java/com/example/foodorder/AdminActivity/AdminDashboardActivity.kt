package com.example.foodorder.AdminActivity

import android.Manifest
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.text.Editable
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.foodorder.ADMINMODEL.categoryModel
import com.example.foodorder.APISERVICE.APICLIENT
import com.example.foodorder.APISERVICE.ApiInterface
import com.example.foodorder.CategoryModel.CategoryResponseModel
import com.example.foodorder.R
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class adminDashboardActivity : AppCompatActivity() {
    lateinit var addCategory: TextView
    lateinit var addFood: TextView

    private val CAMERA_REQUEST = 1888
    private val MY_CAMERA_PERMISSION_CODE = 100

    private val GALLERY_PERMISSION_CODE = 400
    private val GALLERY_IMAGE_CHOOSE_CODE = 2999
    lateinit var foodImg: ImageView
    lateinit var selectedItem: String

    lateinit var fileForUpload: File
    lateinit var successAddFoodTxt: TextView
    lateinit var llFoodDetails: LinearLayout

    var galleryImage: Boolean = false
    var cameraImage: Boolean = false


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        supportActionBar?.hide()

        addCategory = findViewById(R.id.addCategory)
        addFood = findViewById(R.id.addFood)

        addCategory.setOnClickListener {
            showAddCategoryDialog()
        }

        addFood.setOnClickListener {
            openAddFoodDialog()
        }


    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun openAddFoodDialog() {
        val dialog = Dialog(this@adminDashboardActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)

        dialog.setContentView(R.layout.addfooddialog)
        val window: Window? = dialog.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val closeDialog = dialog.findViewById(R.id.closeDialog) as ImageView
        val saveFood = dialog.findViewById(R.id.saveFood) as TextView
        val addImage = dialog.findViewById(R.id.addImage) as ImageView
        val gallery = dialog.findViewById(R.id.gallery) as ImageView


        val foodName = dialog.findViewById(R.id.foodName) as EditText
        val foodPrice = dialog.findViewById(R.id.foodPrice) as EditText

        val spinner = dialog.findViewById(R.id.dropdown) as Spinner

        APiCategoryList(spinner)//fetch all category in dropdown

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedItem = parent?.getItemAtPosition(position).toString()
                //Toast.makeText(this@adminDashboardActivity,""+selectedItem,Toast.LENGTH_SHORT).show()
            }

        }

        foodImg = dialog.findViewById(R.id.foodImg) as ImageView
        successAddFoodTxt = dialog.findViewById(R.id.successAddFoodTxt) as TextView
        llFoodDetails = dialog.findViewById(R.id.llFoodDetails) as LinearLayout

        addImage.setOnClickListener {
            cameraImage = true
            pickImageFromGallery()
        }
        gallery.setOnClickListener {
            galleryImage = true
            pickImageFromGallery()
        }

        closeDialog.setOnClickListener {
            dialog.dismiss()
        }
        saveFood.setOnClickListener {


            ApiSaveFood(foodName, foodPrice, selectedItem)

        }



        dialog.show()
    }

    private fun ApiSaveFood(edtNameFood: EditText, edtPriceFood: EditText, category: String) {

        val fdName = edtNameFood.text.toString().trim()
        val fdPrice = edtPriceFood.text.toString().trim()

        if (fdName != "" && fdPrice != "" && category != "") {
            Log.e("food-", "------------" + fdName + fdPrice + selectedItem)

            val requestFile =
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), fileForUpload);
            val filebody =
                MultipartBody.Part.createFormData("image", fileForUpload.name, requestFile)

            val foodname = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), fdName);
            val foodcategory =
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), category);
            val foodprice = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), fdPrice);

            val retrofit = APICLIENT.getInstance().create(ApiInterface::class.java)
            retrofit.addFood(foodname, foodcategory, foodprice, filebody)
                ?.enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(
                            this@adminDashboardActivity,
                            "Error cat fetch " + t,
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e("err", "" + t)

                    }

                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {

                            edtNameFood.text = Editable.Factory.getInstance().newEditable("")
                            edtPriceFood.text = Editable.Factory.getInstance().newEditable("")
                            foodImg.setImageResource(R.drawable.defaultimg)

                            llFoodDetails.visibility = View.GONE
                            successAddFoodTxt.visibility = View.VISIBLE

                            // Toast.makeText(this@adminDashboardActivity,"Successfully Add new Food",Toast.LENGTH_SHORT).show()

                        } else {
                            Toast.makeText(
                                this@adminDashboardActivity,
                                "Something Went Wrong!!!",
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                    }
                })

        } else {
            Toast.makeText(
                this@adminDashboardActivity,
                "Please add all details!",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun APiCategoryList(spinner: Spinner) {
        Log.e("call cat api", "")
        val retrofit = APICLIENT.getInstance().create(ApiInterface::class.java)
        retrofit.getCategoryList()
            ?.enqueue(object : Callback<CategoryResponseModel> {
                override fun onFailure(call: Call<CategoryResponseModel>, t: Throwable) {
                    Toast.makeText(
                        this@adminDashboardActivity,
                        "Error cat fetch " + t,
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("err", "" + t)

                }

                override fun onResponse(
                    call: Call<CategoryResponseModel>,
                    response: Response<CategoryResponseModel>
                ) {
                    if (response.isSuccessful) {


                        var catlist = response.body()?.catlist

                        Log.e("list", "" + catlist?.size)
                        var list = ArrayList<String>()
                        for (i in 0 until catlist!!?.size) {

                            // Log.e("cat name",""+catlist.get(i).categoryName)
                            list.add(catlist.get(i).categoryName.toString())

                        }

                        Log.e("list-----", "" + list)

                        var adapter = ArrayAdapter<String>(
                            this@adminDashboardActivity,
                            android.R.layout.simple_spinner_item,
                            list
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinner.adapter = adapter

                    } else {
                        Toast.makeText(
                            this@adminDashboardActivity,
                            "Something Went Wrong!!!",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                }
            })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun pickImageFromGallery() {
        if (cameraImage) {
            cameraImage = false
            Log.e("camera", "inside camera code")
            if (
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                } else {
                    TODO("VERSION.SDK_INT < M")
                }
            ) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), MY_CAMERA_PERMISSION_CODE)
            } else {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_REQUEST)


            }
        } else if (galleryImage) {
            galleryImage = false
            Log.e("gallery", "inside gallery code")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, GALLERY_PERMISSION_CODE)
                } else {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    startActivityForResult(intent, GALLERY_IMAGE_CHOOSE_CODE)
                }
            } else {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, GALLERY_IMAGE_CHOOSE_CODE)
            }
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show()
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_REQUEST)
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show()
            }
        }
        if (requestCode == GALLERY_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, GALLERY_IMAGE_CHOOSE_CODE)
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override  fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("data","data--------------->"+data)
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Log.e("camera gallery", "camera image select")
            val photo = data?.extras!!["data"] as Bitmap?
            foodImg.setImageBitmap(photo)
            val tempUri: Uri = getImageUri(applicationContext, photo)

            // CALL THIS METHOD TO GET THE ACTUAL PATH

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            fileForUpload = File(getRealPathFromURI(tempUri))
            Log.e("file path ", "" + fileForUpload)
        }

        if (requestCode == GALLERY_IMAGE_CHOOSE_CODE && resultCode == RESULT_OK) {
            Log.e("accept gallery", "gallery image select")
       //    val photo = data?.extras!!["data"] as Bitmap?
            foodImg.setImageURI(data?.data)

//          val tempUri: Uri = getImageUri(applicationContext, photo)


         //  fileForUpload = File(getRealPathFromURI(tempUri))
       //   fileForUpload = File(getRealPathFromURI(data?.data))


            val bitmap = BitmapFactory.decodeFile(getRealPathFromURI(data?.data))
            val tempUri: Uri = getImageUri(applicationContext, bitmap)
            fileForUpload=File(getRealPathFromURI(tempUri))


        }
    }



    fun getRealPathFromURI(uri: Uri?): String? {
        var path = ""
        if (contentResolver != null) {
            val cursor: Cursor? = contentResolver.query(uri!!, null, null, null, null)
            if (cursor != null) {
                cursor.moveToFirst()
                val idx: Int = cursor.getColumnIndex(Images.ImageColumns.DATA)
                path = cursor.getString(idx)
                cursor.close()
            }
        }
        return path
    }

    fun getImageUri(inContext: Context, inImage: Bitmap?): Uri {
        val bytes = ByteArrayOutputStream()
        inImage?.compress(Bitmap.CompressFormat.JPEG, 50, bytes)
        val dateFormat = SimpleDateFormat("yyyyMMdd'T'HHmmss")
        val timeStamp: String = dateFormat.format(Date())
        val imageFileName = "picture_$timeStamp.jpg"
        val path =
            Images.Media.insertImage(inContext.getContentResolver(), inImage, imageFileName, null)
        return Uri.parse(path)
    }

    private fun showAddCategoryDialog() {
        val dialog = Dialog(this@adminDashboardActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)

        dialog.setContentView(R.layout.addcategory)
        val window: Window? = dialog.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val save = dialog.findViewById(R.id.saveCat) as TextView
        val edtCat = dialog.findViewById(R.id.edtCat) as EditText
        val close = dialog.findViewById(R.id.close) as ImageView
        val successText = dialog.findViewById(R.id.successText) as TextView

        close.setOnClickListener {
            dialog.dismiss()
        }

        save.setOnClickListener {
            categoryAdd(edtCat, successText, save)
        }

        dialog.show()


    }

    private fun categoryAdd(edt: EditText, successTxt: TextView, save: TextView) {

        val catName = edt.text.toString().trim()
        Log.e("Cat", "" + catName);

        if (catName != "") {
            val categoryReq = categoryModel()
            categoryReq.categoryName = catName
            val progressBar = ProgressDialog(this)
            progressBar.setCancelable(false)
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            progressBar.setMessage("Logging, Please Wait ...")
            progressBar.show()

            val retrofit = APICLIENT.getInstance().create(ApiInterface::class.java)

            retrofit.addCategory(categoryReq)
                ?.enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(
                            this@adminDashboardActivity,
                            "Error Admin Login!!!!!",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e("err", "" + t)




                        progressBar.dismiss();
                    }

                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {
                            progressBar.dismiss();
                            val addRes = response.body()
                            Log.e("res add cat", "" + addRes)

                            edt.text = Editable.Factory.getInstance().newEditable("")
                            edt.visibility = View.GONE
                            save.visibility = View.GONE
                            successTxt.visibility = View.VISIBLE


                        } else {
                            Toast.makeText(
                                this@adminDashboardActivity,
                                "Something Went Wrong!!!",
                                Toast.LENGTH_SHORT
                            ).show()
                            progressBar.dismiss();
                        }

                    }
                })
        }

    }
}






