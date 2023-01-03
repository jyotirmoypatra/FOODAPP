<?php

header('Access-Control-Allow-Origin:*');
header('Content-type: application/json;');
header('Access-Control-Allow-Methods:POST');
header('Access-Control-Allow-Headers:Content-Type,Access-Control-Allow-Headers,Authorization,X-Request-With');

$login=json_decode(file_get_contents("php://input"),true);


include('db.php');


$login_email=$login['ad_email'];
$login_pass=$login['ad_pass'];


if($login_email!='' && $login_pass!=''){

    $loginQuery="SELECT * FROM `admin` WHERE `ad_email`='$login_email' && `ad_pass`='$login_pass'";
    $runLoginQuery=mysqli_query($con,$loginQuery);
    $loginCount=mysqli_num_rows($runLoginQuery);

    if($loginCount){
        $admin= mysqli_fetch_assoc($runLoginQuery);

        $id=$admin['id'];
        $uname= $admin['ad_name'];
        $email= $admin['ad_email'];
        $pass = $admin['ad_pass'];
        

        echo json_encode(['id'=>$id,'adminname'=>$uname,'adminEmail'=>$email,'adminPassword'=>$pass]);
    }

}


?>