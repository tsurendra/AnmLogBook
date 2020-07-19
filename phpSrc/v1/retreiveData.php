<?php

require_once '../includes/DbOperations.php';

$output="";

//https://www.webslesson.info/2016/02/export-mysql-data-to-excel-in-php-php-tutorial.html

$response = array();

$vaccineDate = $_GET['date'];
//$vaccineDate = "15/7/2020";

$db = new DbOperations();

$vaccine = $db->retreiveVaccineData($vaccineDate);

for($i=0;$i<count($vaccine);$i++){

	$response[$i]['childname']= $vaccine[$i]["childname"];
	$response[$i]["mothername"] = $vaccine[$i]["mothername"];
	$response[$i]["DOB"] = $vaccine[$i]["dateofbirth"];
	$response[$i]["Gender"]= $vaccine[$i]["sex"];
	$response[$i]["Dateof Vaccination"] =stripslashes($vaccine[$i]["dateofvaccine"]);

	$vaccine_data = explode("$",$vaccine[$i]["vaccinegiven"]);
	if(in_array("0", $vaccine_data)){
		$response[$i]["OPV-0 & HEP-B"] = $vaccine[$i]["dateofvaccine"];
	}else{
		$response[$i]["OPV-0 & HEP-B"] = "*";
	}

	if(in_array("1", $vaccine_data)){
		$response[$i]["BCG"] = $vaccine[$i]["dateofvaccine"];
	}else{
		$response[$i]["BCG"] = "*";
	}

	if(in_array("2", $vaccine_data)){
		$response[$i]["OPV-1 & PENTA-1"] = $vaccine[$i]["dateofvaccine"];
	}else{
		$response[$i]["OPV-1 & PENTA-1"] = "*";
	}

	if(in_array("3", $vaccine_data)){
		$response[$i]["RVV-1"] = $vaccine[$i]["dateofvaccine"];
	}else{
		$response[$i]["RVV-1"] = "*";
	}
	if(in_array("4", $vaccine_data)){
		$response[$i]["IPV-1"] = $vaccine[$i]["dateofvaccine"];
	}else{
		$response[$i]["IPV-1"] = "*";
	}
	if(in_array("5", $vaccine_data)){
		$response[$i]["OPV & PENTA-2"] = $vaccine[$i]["dateofvaccine"];
	}else{
		$response[$i]["OPV & PENTA-2"] = "*";
	}

	if(in_array("6", $vaccine_data)){
		$response[$i]["RVV-2"] = $vaccine[$i]["dateofvaccine"];
	}else{
		$response[$i]["RVV-2"] = "*";
	}
	if(in_array("7", $vaccine_data)){
		$response[$i]["OPV & PENTA-3"] = $vaccine[$i]["dateofvaccine"];
	}else{
		$response[$i]["OPV & PENTA-3"] = "*";
	}
	if(in_array("8", $vaccine_data)){
		$response[$i]["RVV-3"] = $vaccine[$i]["dateofvaccine"];
	}else{
		$response[$i]["RVV-3"] = "*";
	}
	if(in_array("9", $vaccine_data)){
		$response[$i]["IPV-2"] = $vaccine[$i]["dateofvaccine"];
	}else{
		$response[$i]["IPV-2"] = "*";
	}
	if(in_array("10", $vaccine_data)){
		$response[$i]["MR-1"] = $vaccine[$i]["dateofvaccine"];
	}else{
		$response[$i]["MR-1"] = "*";
	}
	if(in_array("11", $vaccine_data)){
		$response[$i]["DPT OPV & MR-2"] = $vaccine[$i]["dateofvaccine"];
	}else{
		$response[$i]["DPT OPV & MR-2"] = "*";
	}
	if(in_array("12", $vaccine_data)){
		$response[$i]["5Y-DPT"] = $vaccine[$i]["dateofvaccine"];
	}else{
		$response[$i]["5Y-DPT"] = "*";
	}
	if(in_array("13", $vaccine_data)){
		$response[$i]["10Y-TD"] = $vaccine[$i]["dateofvaccine"];
	}else{
		$response[$i]["10Y-TD"] = "*";
	}
	if(in_array("14", $vaccine_data)){
		$response[$i]["15Y-TD"] = $vaccine[$i]["dateofvaccine"];
	}else{
		$response[$i]["15Y-TD"] = "*";
	}
	if(in_array("15", $vaccine_data)){
		$response[$i]["PW-TD1"] = $vaccine[$i]["dateofvaccine"];
	}else{
		$response[$i]["PW-TD1"] = "*";
	}
	if(in_array("16", $vaccine_data)){
		$response[$i]["PW-TD2"] = $vaccine[$i]["dateofvaccine"];
	}else{
		$response[$i]["PW-TD2"] = "*";
	}





}


$output .= '
   <table class="table" bordered="1">  
                    <tr>  
                    	<th>S.No</th>
                         <th>child name</th>  
                         <th>mother name</th>  
                         <th>DOB</th>
                         <th>Gender</th>
                         <th>OPV &amp; HEP-0</th>
                         <th>BCG</th>
                         <th>OPV &amp; Penta-1</th>
                         <th>RVV-1</th>
                         <th>IPV-1</th>
                         <th>OPV &amp; Penta-2</th>
                         <th>RVV-2</th>
                         <th>OPV &amp; Penta-3</th>
                         <th>RVV-3</th>
                         <th>IPV-2</th>
                         <th>MR-1</th>
                         <th>DPT,OPV &amp; MR-2</th>
                         <th>5Y-DPT</th>
                         <th>10Y-TD</th>
                         <th>15Y-TD</th>
                         <th>PW-TD1</th>
                         <th>PW-TD2</th>
                         
                    </tr>
  ';

for($i=0;$i<count($vaccine);$i++){
      $sno = $i+1;
	 $output .= '
    <tr>  
                         <td>'.$sno.'</td>
                         <td>'.$vaccine[$i]['childname'].'</td>  
                         <td>'.$vaccine[$i]['mothername'].'</td>  
                         <td>'.$vaccine[$i]['dateofbirth'].'</td>   
       					<td>'.$vaccine[$i]['sex'].'</td>
       					<td>'.$response[$i]["OPV-0 & HEP-B"].'</td>
       					<td>'.$response[$i]["BCG"].'</td>
       					<td>'.$response[$i]["OPV-1 & PENTA-1"].'</td>
       					<td>'.$response[$i]["RVV-1"].'</td>
       					<td>'.$response[$i]["IPV-1"].'</td>
       					<td>'.$response[$i]["OPV & PENTA-2"].'</td>
       					<td>'.$response[$i]["RVV-2"].'</td>
       					<td>'.$response[$i]["OPV & PENTA-3"].'</td>
       					<td>'.$response[$i]["RVV-3"].'</td>
       					<td>'.$response[$i]["IPV-2"].'</td>
       					<td>'.$response[$i]["MR-1"].'</td>
       					<td>'.$response[$i]["DPT OPV & MR-2"].'</td>
       					<td>'.$response[$i]["5Y-DPT"].'</td>
       					<td>'.$response[$i]["10Y-TD"].'</td>
       					<td>'.$response[$i]["15Y-TD"].'</td>
       					<td>'.$response[$i]["PW-TD1"].'</td>
       					<td>'.$response[$i]["PW-TD2"].'</td>

                    </tr>
   ';
}
$output .= '</table>';
  header('Content-Type: application/xls');
  header('Content-Disposition: attachment; filename=vaccineLinelist.xls');
  echo $output;
//$rows = $db->retreiveVaccineDataRows($vaccineDate);

//echo $rows;
//echo json_encode($vaccine_data);

//if(in_array("0", $vaccine_data)){
//	echo"BCG given";
//}


//echo json_encode($vaccine);

//echo  stripslashes(json_encode($response));

