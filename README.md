# Secure Database Using HashiCorp Vaut

A secure database storage service where data can be stored and be retrieved from in a completely secure manner. This is achieved by using Encryption as a Service (EaaS) -
Transit Secret Engine Service from HashiCorp Vault. The data within the database is at all times encrypted and it is only decrypted when an authenticated client requests
data from the web service.


This project has been developed while working as a student developer under the guidance of mentors from National Informatics Centre -New Delhi

## Setting up the project

To setup the project locally:

 - Clone the project
 - Start a MySQL server and edit the MySQL server properties as per your server inside [application.properties](https://github.com/devansh-299/practiceschool-project/blob/dev/patient-api/src/main/resources/application.properties)
 - Install Vault and start a `dev` Vault Server [see here](https://learn.hashicorp.com/vault/getting-started/dev-server)
 - Enable Vault's Transit Secret Engine [see here](https://learn.hashicorp.com/vault/encryption-as-a-service/eaas-transit)
 - Set up a key with name `patient` inside Transit Secret Engine. Hence your `BaseUrl` for the encrypting service should be like `DomainName/transit/encrypt/patient/`


## Testing APIs 

All the APIs from the web service and the Vault server can be tested using Post Man.

Example for testing Vault APIs

<img src="https://user-images.githubusercontent.com/46667021/85609071-557d7100-b673-11ea-93ae-c7c44b8a7538.png" width="800" height="400" />

Note: Do not forget to pass the `ROOT TOKEN` that you receive on starting the server along with the header for every request you make to the Vault server.
