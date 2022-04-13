<?php
class Donation {
    private $id ; 
    private $amount ; 
    private $paymenttype ; 
    private $upvotes ; 
    function Donation($id, $amount , $paymenttype, $upvotes){
        $this->_id = $id;
        $this->amount = $amount;
        $this->paymenttype = $paymenttype;
        $this->upvotes = $upvotes;
    }
  
}

?>