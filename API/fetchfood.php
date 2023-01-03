<?php

header('Access-Control-Allow-Origin:*');
header('Content-type: application/json;');
header('Access-Control-Allow-Methods:POST');
header('Access-Control-Allow-Headers:Content-Type,Access-Control-Allow-Headers,Authorization,X-Request-With');

$login=json_decode(file_get_contents("php://input"),true);


include('db.php');





    $Query="SELECT * FROM `food`";
    $runQuery=mysqli_query($con,$Query);
    $Count=mysqli_num_rows($runQuery);

    $totalCategory=array();

    if($Count){
        while($row = mysqli_fetch_assoc($runQuery)) {

        $id=$row['id'];
        $foodName= $row['foodName'];
        $foodCategory= $row['foodCategory'];
        $foodPrice= $row['foodPrice'];
        $imgurl= $row['imgurl'];
       
        $totalCategory[]=array('id' => "$id" ,'foodName' => "$foodName",'foodCategory'=>$foodCategory,'foodPrice'=>$foodPrice,'imgurl'=>$imgurl);
        }
        echo json_encode(["foodlist"=>$totalCategory]);
    }




?>