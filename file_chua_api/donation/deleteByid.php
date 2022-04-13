<?php
require './DBconnect.php';
    $_id  = "";
    if(isset($_GET['_id'])){
        $_id = $_GET['_id'];
    }else{
        echo "fail";
    }
    $query = "DELETE FROM `donations` WHERE _id = '{$_id}'";
    
    if(mysqli_query($connect, $query)){
        echo "200";
    } else{
        echo "404";
    }

?>