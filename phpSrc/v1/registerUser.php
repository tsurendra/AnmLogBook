<?php

require_once '../includes/DbOperations.php';

$response = array();

$designation ="ANM";
$password ="abc123";
$phc ="J PANGULURU";

if($_SERVER['REQUEST_METHOD']=='POST'){ //checking request method

	if(isset($_POST['name'])and 
			isset($_POST['mobile']) and
			isset($_POST['subcenter'])){

		//checked all variables are set and now continue Db operations 
        $db = new DbOperations();

    	if($db->isUserExist($_POST['mobile'])){ //check whether user already existin database
    		$response['error']=true;
		    $response['message']="User already regitered with given mobile number..";
    	}else{
		    if($db->createUser($_POST['name'],$designation,
		    						$_POST['mobile'],$password,
					    				$_POST['subcenter'],$phc)){
		    	$response['error']=false;
		    	$response['message']= "user details inserted successfully";
		    	//echo $_POST['mobile'];

		    }else{
		    	$response['error']=true;
		    	$response['message']="some error occured try again";	
		    }

		}
	}else{

		$response['error']=true;
		$response['message']="Required fields are missing";
	}

}else{
	//request method is invalid

	$response['error']=true;

	$response['message']="Invalid Request";

}

echo json_encode($response);