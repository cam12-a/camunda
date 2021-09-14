#!/bin/bash
export DB_DRIVER="org.postgresql.Driver"
export DB_URL="jdbc:postgresql://host.docker.internal:5432/kafka"
export DB_USERNAME="postgres"
export DB_PASSWORD="demo"
export PLATFORM="org.hibernate.dialect.PostgreSQLDialect"
