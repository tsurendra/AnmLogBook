<?php

require_once '../includes/DbOperations.php';

$response = array();

if($_SERVER['REQUEST_METHOD']=='POST'){

	if(isset($_POST['childName']) and 
		isset($_POST['motherName']) and 
		isset($_POST['dateOfBirth']) and
		isset($_POST['gender']) and
		isset($_POST['dateOfVaccine']) and
		isset($_POST['vaccine'])
		){
			$db = new DbOperations();
			if($db->vaccineEntry($_POST['childName'],
									$_POST['motherName'],
										$_POST['dateOfBirth'],
										$_POST['gender'],
										$_POST['dateOfVaccine'],
										$_POST['vaccine'])){
				$response['error']=false;
		    	$response['message']= "vaccine details inserted successfully";
			}else{
				$response['error']=true;
		    	$response['message']="some error occured try again";
			}

	}else{

		$response['error']="true";
		$response['message']="required fields are missing";
	}

}else{

	//request method is invalid

	$response['error']=true;

	$response['message']="Invalid Request";
}

echo json_encode($response);