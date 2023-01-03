<?php

header('Access-Control-Allow-Origin:*');
header('Content-type: application/json;');
header('Access-Control-Allow-Methods:POST');
header('Access-Control-Allow-Headers:Content-Type,Access-Control-Allow-Headers,Authorization,X-Request-With');

$data=json_decode(file_get_contents("php://input"),true);

include('db.php');




//image upload to server folder
$image_file = $_FILES["image"];
if (isset($image_file)) {
    
    $error=array();
    $file_name = $_FILES["image"]['name'];
    $file_tmp = $_FILES["image"]['name'];
    $file_type =$_FILES["image"]['type'];

    $file_extn = strtolower(end(explode('.',$file_name)));

    $extension= array("jpeg","jpg","png");

    if(!in_array($file_extn,$extension)){
        $error[]="Please Select jpeg or jpg or png image";
    
    }
    if(empty($error)){
      
       $path=getcwd().'/foodimage/'.$_FILES['image']['name'];
        move_uploaded_file($_FILES['image']['tmp_name'], $path);
      
        //image path url
        $imgurl = (isset($_SERVER['HTTPS']) && $_SERVER['HTTPS'] === 'on' ? "https" : "http") . "://$_SERVER[HTTP_HOST]"."/foodimage/".$_FILES['image']['name'];
    //  echo json_encode(['status'=>'success','imgurl'=>$imgurl]);
    }  

} 




//data for save in database
// $foodName=$data['foodName'];
// $foodCategory=$data['foodCategory'];
// $foodPrice=$data['foodPrice'];
// $foodImageurl=  $imgurl;


$foodName= $_POST['foodName'];
$foodCategory=$_POST['foodCategory'];
$foodPrice=$_POST['foodPrice'];
$foodImageurl=  $imgurl;

if($foodName!='' && $foodCategory!='' && $foodPrice!=''  ){
  echo "inside";
    $insertQuery="INSERT INTO `food`(`foodName`, `foodCategory`, `foodPrice`,`imgurl`) VALUES ('$foodName','$foodCategory','$foodPrice','$foodImageurl')";
    $runQuery=mysqli_query($con,$insertQuery);
    if($runQuery){
        echo json_encode(['status'=>'success','msg'=>'Food Added Succesfully']);
    }
}



?>