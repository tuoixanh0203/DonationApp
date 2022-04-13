<?php
require './DBconnect.php';
    $_id  = "";
    if(isset($_GET['_id'])){
        $_id = $_GET['_id'];
    }else{
        echo "fail";
    }
    $query = "SELECT * FROM `donations` WHERE _id = '{$_id}'";
    $data = mysqli_query($connect, $query);
    $mang = [];
    while($row = mysqli_fetch_assoc($data))
        {
            array_push($mang, $row);
        }
    echo json_encode($mang); 
?>