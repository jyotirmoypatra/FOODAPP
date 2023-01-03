<?php

header('Access-Control-Allow-Origin:*');
header('Content-type: application/json;');
header('Access-Control-Allow-Methods:POST');
header('Access-Control-Allow-Headers:Content-Type,Access-Control-Allow-Headers,Authorization,X-Request-With');

$login=json_decode(file_get_contents("php://input"),true);


include('db.php');


$login_email=$login['uemail'];
$login_pass=$login['upass'];


if($login_email!='' && $login_pass!=''){

    $loginQuery="SELECT * FROM `users` WHERE `uemail`='$login_email' && `upass`='$login_pass'";
    $runLoginQuery=mysqli_query($con,$loginQuery);
    $loginCount=mysqli_num_rows($runLoginQuery);

    if($loginCount){
        $user= mysqli_fetch_assoc($runLoginQuery);

        $id=$user['id'];
        $uname= $user['uname'];
        $email= $user['uemail'];
        $pass = $user['upass'];
        $phone = $user['uphone'];
        $address = $user['uaddress'];
        

        echo json_encode(['id'=>$id,'uname'=>$uname,'uemail'=>$email,'upass'=>$pass,'uphone'=>$phone,'uaddress'=>$address]);
    }

}


?>