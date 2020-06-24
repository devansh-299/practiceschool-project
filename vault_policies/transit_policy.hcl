path "sys/mounts/transit" {
  capabilities = [ "create", "read", "update", "delete", "list" ]
}

path "sys/mounts" {
  capabilities = [ "read" ]
}

path "transit/*" {
  capabilities = [ "create", "read", "update", "delete", "list" ]
}
