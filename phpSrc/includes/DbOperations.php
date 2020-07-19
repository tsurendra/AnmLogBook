<?php


    //echo dirname(__FILE__)."/DbConnect.php";
	class DbOperations{

		private $con;

		function __construct(){

			require_once dirname(__FILE__)."/DbConnect.php";

   
			$db = new DbConnect();

			$this->con = $db->connect();

		}

		/*CRUD OPERATION C -->CREATE USER */

		public function createUser($name,$designation,$mobile,$password,$subcenter,$phc){

			//$password = md5($pass);
			$stmt = $this->con->prepare("INSERT INTO `users` (`id`, `name`,`designation`,`mobile`, `password`, `subcenter`,`phc`) VALUES (NULL, ?, ?, ?,?,?,?);");

			$stmt->bind_param("ssisss",$name,$designation,$mobile,$password,$subcenter,$phc);
			
			if($stmt->execute()){
				return true;
			}else{
				return false;
			}

		}


		public function getUserDetails($mobile){
			$stmt= $this->con->prepare("SELECT * FROM `users` WHERE mobile =?");
			$stmt->bind_param("s",$mobile);
			$stmt->execute(); 
			return $stmt->get_result()->fetch_assoc();
		}
		public function getUserName($mobile){
			$stmt= $this->con->prepare("SELECT name FROM `users` WHERE mobile =?");
			$stmt->bind_param("s",$mobile);
			$stmt->execute(); 
			return $stmt->get_result()->fetch_assoc();
		}
		public function getUserDesignation($mobile){
			$stmt= $this->con->prepare("SELECT designation FROM `users` WHERE mobile =?");
			$stmt->bind_param("s",$mobile);
			$stmt->execute(); 
			return $stmt->get_result()->fetch_assoc();
		}
		public function getUserSubcenter($mobile){
			$stmt= $this->con->prepare("SELECT subcenter FROM `users` WHERE mobile =?");
			$stmt->bind_param("s",$mobile);
			$stmt->execute(); 
			return $stmt->get_result()->fetch_assoc();
		}
		public function getUserPhc($mobile){
			$stmt= $this->con->prepare("SELECT phc FROM `users` WHERE mobile =?");
			$stmt->bind_param("s",$mobile);
			$stmt->execute(); 
			return $stmt->get_result()->fetch_assoc();
		}

		public function isUserExist($mobile){
			//check for user already registered with given mobile number
			$stmt= $this->con->prepare("SELECT id FROM `users` WHERE mobile =?");
			$stmt->bind_param("s",$mobile);
			$stmt->execute();
			$stmt->store_result();
			return $stmt->num_rows>0;	
		}

		public function loginUser($mobile,$pass){
			//$password = md5(pass);
			$stmt = $this->con->prepare("SELECT id FROM `users` WHERE mobile =? AND password = ? ");
			$stmt->bind_param("is",$mobile,$pass);
			$stmt->execute();
			$stmt->store_result();
			return $stmt->num_rows>0;
		}

		public function vaccineEntry($childName,$motherName,$dob,$gender,$dateOfVaccine,$vaccine){
			$stmt = $this->con->prepare("INSERT INTO `immunisation` (`childname`, `mothername`, `dateofbirth`, `sex`, `dateofvaccine`, `vaccinegiven`) VALUES ( ?,?,?,?,?,?);");
			$stmt->bind_param("ssssss",$childName,$motherName,$dob,$gender,$dateOfVaccine,$vaccine);
			
			if($stmt->execute()){
				return true;
			}else{
				return false;
			}


		}
		public function retreiveVaccineData($vaccineDate){
			$stmt = $this->con->prepare("SELECT * FROM `immunisation` WHERE  dateofvaccine=?");
			$stmt->bind_param("s",$vaccineDate);
			$stmt->execute(); 
			//return $stmt->get_result()->fetch_all();
			if ($stmt->execute()) {
  				  $result = $stmt->get_result();
    				$usersArr = array();
    				while ($user = $result->fetch_assoc()){
        					$usersArr[] = $user;
  					  }
    					$stmt->close();
    						return $usersArr;
				} else {
    					return NULL;
				}

		}
		public function retreiveVaccineDataRows($vaccineDate){
			$stmt = $this->con->prepare("SELECT * FROM `immunisation` WHERE  dateofvaccine=?");
			$stmt->bind_param("s",$vaccineDate);
			$stmt->execute(); 
			$stmt->store_result();
			return $stmt->num_rows;

		}
	}