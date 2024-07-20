#!/bin/bash

# Variáveis
DB_USER="root"
DB_PASSWORD="root"
SQL_FILE="database/db.sql"
DB_NAME="bikeRentSystem"

# Cria o banco de dados se ele não existir
mysql -u $DB_USER -p$DB_PASSWORD -e "CREATE DATABASE IF NOT EXISTS $DB_NAME;"

# Verifica se o comando anterior foi bem sucedido
if [ $? -eq 0 ]; then
    echo "Database checked/created successfully."
else
    echo "Failed to create or check database."
    exit 1
fi

# Inicia o bd e executa o arquivo SQL
mysql -u $DB_USER -p$DB_PASSWORD $DB_NAME < $SQL_FILE

# Verifica se o comando anterior foi bem sucedido
if [ $? -eq 0 ]; then
    echo "Database initialized successfully."
else
    echo "Failed to initialize database."
    exit 1
fi

# Roda o projeto
sudo mvn tomcat7:run
