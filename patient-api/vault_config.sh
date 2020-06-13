#!/bin/bash

export VAULT_TOKEN=s.JAU8ZprxaKWIsl76G2KOxUfG
export VAULT_ADDR='http://127.0.0.1:8200'

echo 'path "secret/spring-vault-demo" {
  capabilities = ["create", "read", "update", "delete", "list"]
}
path "secret/application" {
  capabilities = ["create", "read", "update", "delete", "list"]
}
path "transit/decrypt/patient" {
  capabilities = ["update"]
}
path "transit/encrypt/patient" {
  capabilities = ["update"]
}
path "database/creds/patient" {
  capabilities = ["read"]
}
path "sys/renew/*" {
  capabilities = ["update"]
}' | vault policy write patient -

#Mount transit backend
vault secrets enable transit

#Create transit key
vault write -f transit/keys/patient
