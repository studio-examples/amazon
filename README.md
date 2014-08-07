# amazon s3 #

This example shows how to connect to Amazon S3 service and retrieve a CSV file that is mapped to XML format.

Step 1: sign up at [https://aws.amazon.com/marketplace/help/200940490](https://aws.amazon.com/marketplace/help/200940490) 
Step 2: sign in to [http://aws.amazon.com/console/](http://aws.amazon.com/console/)
Step 3: get your access key and secret key by following these instructions:
		from [https://console.aws.amazon.com/iam/home?#home](https://console.aws.amazon.com/iam/home?#home) click Create a new group of users. you name it whatever but at permissions step choose Amazon S3 Full Access. create a user. click continue until you have an option to Show User Security Credentials. you click it and you get access key and secret key. paste them to common.properties 
Step 4: choose S3 from the list of services ([https://console.aws.amazon.com/iam/home?#home](https://console.aws.amazon.com/iam/home?#home)) and create a bucket, you put this name to s3.bucket. upload scr/test/resources/customers.csv to this bucket. 
Step 5: install oracle DB, in 1 step you configure DB. remember the name of your db and admin password. download oracle sql developer and connect to the local instance using:

user: system
pass: password from the oracle installation
sid: db name from the oracle installation
 
after installation run scr/main/resources/customers.sql script that creates a customers table. 

Step 6: run mule app
Step 7: go to localhost:8081
Step 8: the output contains an exception saying a certain library is missing. i could not get this fixed. i suspect there is a bug in Amazon connector as from the log i could figure out following: there is a last modified date of S3 objects in the specific format. the connector wants to parse it but expects it to be a little bit different. maybe this was fixed in some version i dont know.

this is how it is set at amazon:
Mon 14 Jul 2014 09:40:38 GMT

this is the expected format:
"EEE, dd MMM yyyy HH:mm:ss z"

however the objects are inserted in oracle db. this can be verified by downloading oracle sql developer and connecting by the same connection parameters to the local instance and examining the customers table.

ORACLE JDBC DRIVER NEEDS TO BE DOWNLOADED AND ADDED MANUALLY TO THE PROJECT BECAUSE OF THE LICENSNE. 