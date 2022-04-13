<?php
require './DBconnect.php';
    $query = "DELETE FROM `donations`";
    if(mysqli_query($connect, $query)){
        echo "200";
    } else{
        echo "404";
    }
    
?>