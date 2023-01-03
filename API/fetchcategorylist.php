<?php

header('Access-Control-Allow-Origin:*');
header('Content-type: application/json;');
header('Access-Control-Allow-Methods:POST');
header('Access-Control-Allow-Headers:Content-Type,Access-Control-Allow-Headers,Authorization,X-Request-With');

$login=json_decode(file_get_contents("php://input"),true);


include('db.php');





    $Query="SELECT * FROM `foodCategory`";
    $runQuery=mysqli_query($con,$Query);
    $Count=mysqli_num_rows($runQuery);

    $totalCategory=array();

    if($Count){
        while($row = mysqli_fetch_assoc($runQuery)) {

        $id=$row['id'];
        $catName= $row['categoryName'];
       
        $totalCategory[]=array('id' => "$id" ,'categoryName' => "$catName");
        }
        echo json_encode(["catlist"=>$totalCategory]);
    }




?>