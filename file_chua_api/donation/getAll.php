<?php
require './DBconnect.php';
require './Donation.php';

    $query = "SELECT * FROM donations";
    $data = mysqli_query($connect, $query);
    $mang = [];
    while($row = mysqli_fetch_assoc($data))
        {
            array_push($mang, $row);
        }
    echo json_encode($mang); 
    // echo "hi";
?>