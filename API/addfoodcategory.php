<?php

header('Access-Control-Allow-Origin:*');
header('Content-type: application/json;');
header('Access-Control-Allow-Methods:POST');
header('Access-Control-Allow-Headers:Content-Type,Access-Control-Allow-Headers,Authorization,X-Request-With');

$data=json_decode(file_get_contents("php://input"),true);

include('db.php');

$categoryName=$data['categoryName'];



if($categoryName!=''){

    $insertQuery="INSERT INTO `foodCategory`(`categoryName`) VALUES ('$categoryName')";
    $runQuery=mysqli_query($con,$insertQuery);
    if($runQuery){
        echo json_encode(['status'=>'success','msg'=>'Food CAtegory Added Succesfully']);
    }else{
        echo "failed";
    }
}



?>