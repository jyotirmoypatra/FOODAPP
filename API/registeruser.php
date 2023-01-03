
<?php

header('Access-Control-Allow-Origin:*');
header('Content-type: application/json;');
header('Access-Control-Allow-Methods:POST');
header('Access-Control-Allow-Headers:Content-Type,Access-Control-Allow-Headers,Authorization,X-Request-With');

$data=json_decode(file_get_contents("php://input"),true);

include('db.php');

$username=$data['uname'];
$useremail=$data['uemail'];
$userpass=$data['upass'];
$userphone="";
$useraddress="";


if($username!='' && $useremail!='' && $userpass!=''  ){

    $insertQuery="INSERT INTO `users`(`uname`, `uemail`, `upass`,`uphone`,`uaddress`) VALUES ('$username','$useremail','$userpass','$userphone','$useraddress')";
    $runQuery=mysqli_query($con,$insertQuery);
    if($runQuery){
        echo json_encode(['status'=>'success','msg'=>'User Registration Succesfully']);
    }
}



?>