Rest API Examples

Park Api

vehicleType can only be 'CAR', 'TRUCK' or 'JEEP'

POST URL: http://localhost:8080/garage/park

BODY:  
{
"plate":"34-ugr-34",
"color":"blue",
"type":"CAR"
}

Leave Api

DELETE URL: http://localhost:8080/garage/leave/3

Status Api

GET URL: http://localhost:8080/garage/status