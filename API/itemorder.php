
<?php

header('Access-Control-Allow-Origin:*');
header('Content-type: application/json;');
header('Access-Control-Allow-Methods:POST');
header('Access-Control-Allow-Headers:Content-Type,Access-Control-Allow-Headers,Authorization,X-Request-With');

$data=json_decode(file_get_contents("php://input"),true);

include('db.php');

$order_id=$data['order_id'];
$food_id=$data['food_id'];
$qty=$data['Quantity'];



if($order_id!='' && $food_id!='' && $qty!=''  ){

    $insertQuery="INSERT INTO `itemOrder`(`order_id`, `food_id`, `Quantity`) VALUES ('$order_id','$food_id','$qty')";
    $runQuery=mysqli_query($con,$insertQuery);
    if($runQuery){
        echo json_encode(['status'=>'success']);
    }
}



?>