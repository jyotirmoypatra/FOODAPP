
<?php

header('Access-Control-Allow-Origin:*');
header('Content-type: application/json;');
header('Access-Control-Allow-Methods:POST');
header('Access-Control-Allow-Headers:Content-Type,Access-Control-Allow-Headers,Authorization,X-Request-With');

$data=json_decode(file_get_contents("php://input"),true);

include('db.php');

$userId=$data['userid'];



if($userId!='' ){

    $insertQuery="SELECT `orderId`,`payment_type`,`transaction_id`,`total_price` FROM `orderTable` WHERE `userid`='$userId';";
    $runQuery=mysqli_query($con,$insertQuery);
    $oderCount=mysqli_num_rows($runQuery);


    $orderlist =array();

    if($oderCount){

       while( $order= mysqli_fetch_assoc($runQuery)){

        $orderid=$order['orderId'];
        $payment= $order['payment_type'];
        $transaction= $order['transaction_id'];
        $price= $order['total_price'];


        $foodQuery = "SELECT f.foodName,io.Quantity FROM food f,itemOrder io WHERE f.id=io.food_id AND io.order_id ='$orderid' ";
        $runFoodQuery = mysqli_query($con,$foodQuery);
        $foodCount= mysqli_num_rows($foodQuery);
        $foodArr = array();
        while($row = mysqli_fetch_assoc($runFoodQuery)) {

            $foodname = $row['foodName'];
            $qty = $row['Quantity'];
            $foodArr[]=array('foodname'=>$foodname,'quantity'=>$qty);

        }



        $orderlist[]=array('orderid' => "$orderid" ,'payment' => "$payment",'transaction'=>"$transaction",'price'=>"$price",'food'=>$foodArr);

       }
       echo json_encode(["orderlist"=>$orderlist]);
    }
}



?>