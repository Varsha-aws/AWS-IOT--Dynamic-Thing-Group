# AWS-IOT--Dynamic-Thing-Group
Dynamic Thing group -Step by Step


Scenario 1:

Architecture Simplied:

![image](https://user-images.githubusercontent.com/80298979/112763811-ab78ae00-9023-11eb-8bc4-24c08fbd0f2f.png)





Using AWS Lambda with Amazon API Gateway:
https://docs.aws.amazon.com/lambda/latest/dg/services-apigateway.html

Usage:
Step1:
In your AWS console or CLI, discover your newly created API ARN resource name for your API URL prefix

Input  for New Dyamic Thing group
{
    "Operation": "NEW",
    "GroupName": "HighVibration",
    "Attribute": "vibration",
    "Operator": ">",
    "Threshold": "100"
}

Input for getting an existing active group
{
    "Operation": "GET",
    "GroupName": "HighVibration"
}	


Input for deleting an existing active group
{
    "Operation": "DELETE",
    "GroupName": "HighVibration "
	
}

Step2:
Configure AWS Lambda with Amazon API Gateway:
Refer the documentation :
https://docs.aws.amazon.com/lambda/latest/dg/services-apigateway.html


Step 3:Upload the 'GroupCreated' class in to the lambda

Step 4:Create Things using the documentation

Step 5 :Update the reported state of the shadow using :
aws iot-data update-thing-shadow --thing-name AirConditioning-5 --cli-binary-format raw-in-base64-out --payload "{\"state\":{\"reported\":{\"vibration\":\"200\"}}}" "output.txt"

Step6:Testing :
Find the rest api endpoint
Using Postman or your favorite tool, submit a POST request with a body
{
    "Operation": "NEW",
    "GroupName": "HighVibration",
    "Attribute": "vibration",
    "Operator": ">",
    "Threshold": "100"
}	
You should receive the below response

{
    "response": "Group Created"
}

Step7:
Go to IOT Console->Manage->Thing Group->Check the devices added to the group 




