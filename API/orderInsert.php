
<?php

header('Access-Control-Allow-Origin:*');
header('Content-type: application/json;');
header('Access-Control-Allow-Methods:POST');
header('Access-Control-Allow-Headers:Content-Type,Access-Control-Allow-Headers,Authorization,X-Request-With');

$data=json_decode(file_get_contents("php://input"),true);

include('db.php');

$userId=$data['userid'];
$paymentType=$data['payment_type'];
$transactionId=$data['transaction_id'];
$TotalPrice=$data['total_price'];


if($userId!='' && $TotalPrice!='' && $paymentType!=''  ){

    $insertQuery="INSERT INTO `orderTable`(`userid`, `payment_type`, `transaction_id`,`total_price`) VALUES ('$userId','$paymentType','$transactionId','$TotalPrice')";
    $runQuery=mysqli_query($con,$insertQuery);
    if($runQuery){
         $orderid= mysqli_insert_id($con);
        echo json_encode(['orderid'=>$orderid]);
    }
}



?>