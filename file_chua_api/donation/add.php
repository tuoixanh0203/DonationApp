<?php
    require './DBconnect.php';
    $json = file_get_contents('php://input');
    $obj = json_decode($json);
    $_id = $obj->{'_id'} . generateRandomString(5);
    $upvotes = $obj->{'upvotes'};
    $paymenttype = $obj->{'paymenttype'};
    $amount = $obj->{'amount'};
    $sql = "INSERT INTO `donations`(`_id`, `upvotes`, `paymenttype`, `amount`) VALUES ('${_id}','${upvotes}','${paymenttype}','${amount}')";
    if(mysqli_query($connect, $sql)){
        echo "200";
    } else{
        echo "404";
    }


    function generateRandomString($length = 10) {
        $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
        $charactersLength = strlen($characters);
        $randomString = '';
        for ($i = 0; $i < $length; $i++) {
            $randomString .= $characters[rand(0, $charactersLength - 1)];
        }
        return $randomString;
    }
?>