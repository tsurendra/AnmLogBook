<?php

require_once '../includes/DbOperations.php';

$response = array();

if($_SERVER['REQUEST_METHOD']=='POST'){
	//reuest method check passed

       if(isset($_POST['mobile']) and
   				isset($_POST['password'])){

       			//variables are set and start function
                $db = new DbOperations();
                if($db->loginUser($_POST['mobile'],$_POST['password'])){
                	$user = $db->getUserDetails($_POST['mobile']);
                	$response['error']=false;
		    		$response['message']= "login successful";
		    		$response['id'] = $user['id'];
		    		$response['name'] = $user['name'];
		    		$response['designation'] = $user['designation'];
		    		$response['mobile'] = $user['mobile'];
		    		$response['subcenter'] = $user['subcenter'];
		    		$response['phc']=$user['PHC'];

                }else{
                	$response['error']=true;
		    		$response['message']="Wrong Credentials try again ...";
                }

       }else{

      		$response['error']=true;
			$response['message']="Required fields are missing"; 
       }

}else{

//request method  error
	$response['error']=true;
	$response['message']="Invalid Request";
}
echo json_encode($response);

