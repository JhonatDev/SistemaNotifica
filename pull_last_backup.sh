#!/bin/sh
# Script para puxar o último backup de um tipo especificado do servidor 192.168.3.101
# para a pasta de restauração local.
# Uso: ./pull_last_backup.sh <frontend|backend|db>

REMOTE_IP="192.168.3.101"
REMOTE_USER="backup_sys"

# Verifica se o parâmetro foi informado
if [ -z "$1" ]; then
    # Se nenhum parâmetro foi informado, exibe a mensagem de uso
    echo "Uso: $0 <frontend|backend|db>"
    exit 1
fi

TYPE="$1"

# Define o diretório de origem remoto com base no tipo informado
case "$TYPE" in
    frontend)
        REMOTE_DIR="/opt/backup/frontend"
        ;;
    backend)
        REMOTE_DIR="/opt/backup/backend"
        ;;
    db)
        REMOTE_DIR="/opt/backup/Banco de Dados"
        ;;
    *)
        echo "Tipo inválido: $TYPE"
        echo "Utilize: frontend, backend ou db"
        exit 1
        ;;
esac

# Define o diretório de destino local /opt/backup/restaurar/backup a restaurar
LOCAL_DIR="/opt/backup/restaurar/backup a restaurar"
mkdir -p "$LOCAL_DIR"

# Limpa o diretório local antes de copiar o novo backup
rm -rf "$LOCAL_DIR"/*
if [ $? -ne 0 ]; then
    echo "Falha ao limpar o diretório local: $LOCAL_DIR"
    exit 1
fi

# Verifica se o diretório remoto existe
ssh "$REMOTE_USER@$REMOTE_IP" "if [ ! -d '$REMOTE_DIR' ]; then echo 'Diretório remoto não encontrado: $REMOTE_DIR'; exit 1; fi"

# Puxa o último backup do diretório remoto, ignorando backup.log e backup_summary.log
LAST_BACKUP=$(ssh "$REMOTE_USER@$REMOTE_IP" "cd '$REMOTE_DIR' && ls -t | grep -Ev '^(backup\.log|backup_summary\.log)$' | head -n 1")

# Verifica se o nome do arquivo foi encontrado ou está vazio
if [ -z "$LAST_BACKUP" ]; then
    echo "Erro: Nenhum arquivo de backup encontrado no diretório remoto: $REMOTE_DIR"
    exit 1
fi

echo "Puxando o último backup: $LAST_BACKUP"
scp "$REMOTE_USER@$REMOTE_IP:$REMOTE_DIR/$LAST_BACKUP" "$LOCAL_DIR/"

# Verifica se o comando scp foi bem-sucedido
if [ $? -eq 0 ]; then
    echo "Backup puxado com sucesso para: $LOCAL_DIR/$LAST_BACKUP"
else
    echo "Falha ao puxar o backup."
    exit 1
fi
# Fim do script