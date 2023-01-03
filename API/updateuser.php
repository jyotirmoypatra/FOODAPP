
<?php

header('Access-Control-Allow-Origin:*');
header('Content-type: application/json;');
header('Access-Control-Allow-Methods:POST');
header('Access-Control-Allow-Headers:Content-Type,Access-Control-Allow-Headers,Authorization,X-Request-With');

$data=json_decode(file_get_contents("php://input"),true);

include('db.php');

$uid = $data['id'];
$username=$data['uname'];
$userphone=$data['uphone'];
$useraddress=$data['uaddress'];
$userPass=$data['upass'];


if($username!=''  ){


    $updateQuery="UPDATE `users` SET `uname` ='$username',`upass`='$userPass', `uphone` = '$userphone', `uaddress` = '$useraddress' WHERE `users`.`id` = $uid";
    $runQuery=mysqli_query($con,$updateQuery);
    if($runQuery){
        echo json_encode(['status'=>'success','msg'=>'User Update Succesfully']);
    }else{
        echo json_encode(['status'=>'Failed','msg'=>'User Update Failed!!']);
    }
}



?>