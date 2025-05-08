#!/bin/sh
# Script de Backup para Alpine Linux sem pasta de data/hora no meio da estrutura
# Uso:
#   SERVERTYPE=<frontend|backend|db> REMOTE_FRONT=192.168.3.101 /usr/local/bin/backup.sh
#
# Parametros:
#   SERVERTYPE   -> Tipo do servidor:
#                   frontend: arquivos estaticos em /opt/frontend
#                   backend: arquivos do backend (/opt/backend) e arquivo de inicializacao (/etc/local.d) e arquivo downloads onde ce encontra as imagens (/root/SpringNotifica/Notifica/src/main/resources/static/image/download)
#                   db: banco de dados MySQL
#   REMOTE_FRONT -> IP ou hostname do servidor front
if [ -z "$SERVERTYPE" ] || [ -z "$REMOTE_FRONT" ]; then
    echo "Erro: Informe as variaveis SERVERTYPE e REMOTE_FRONT."
    exit 1
fi

# Definindo data/hora para nomes de arquivos (apenas nos nomes dos arquivos, nao na estrutura de diretorios)
BACKUP_DATETIME=$(date +"%d-%m-%Y.%Hh%Mm")

# Diretorio local temporario para montagem do backup
LOCAL_BACKUP_DIR="/tmp/backup_${SERVERTYPE}_$(date +"%d-%m-%Y.%Hh%Mm")"

# Diretorio de destino no servidor front (sem pasta de timestamp intermediaria)
case "$SERVERTYPE" in
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
        echo "Tipo de servidor desconhecido: ${SERVERTYPE}"
        exit 1
        ;;
esac

mkdir -p "$LOCAL_BACKUP_DIR"

# Funcao para registrar os logs com a hora
log_msg() {
    echo "$(date +'%H:%M:%S') - $1" >> "$LOCAL_BACKUP_DIR/backup.log"
}

log_msg "Iniciando backup do servidor tipo ${SERVERTYPE}"

# 1. Copiar /root/.ssh/authorized_keys
if [ -f /root/.ssh/authorized_keys ]; then
    cp /root/.ssh/authorized_keys "$LOCAL_BACKUP_DIR/authorized_keys"
    log_msg "Copiado /root/.ssh/authorized_keys"
    echo "Copiado /root/.ssh/authorized_keys"
else
    log_msg "Arquivo /root/.ssh/authorized_keys nao encontrado."
    echo "Arquivo /root/.ssh/authorized_keys nao encontrado."
fi

# 2. Salvar /etc/hosts e hostname
if [ -f /etc/hosts ]; then
    cp /etc/hosts "$LOCAL_BACKUP_DIR/hosts"
    log_msg "Copiado /etc/hosts"
    echo "Copiado /etc/hosts"
fi
hostname > "$LOCAL_BACKUP_DIR/hostname"
log_msg "Salvo hostname"
echo "Salvo hostname"

# 3. Salvar /home
if [ -d /home ]; then
    tar czf "$LOCAL_BACKUP_DIR/home_$(date +"%d-%m-%Y.%Hh%Mm").tgz" -C /home .
    log_msg "Arquivos de /home compactados"
    echo "Arquivos de /home compactados"
else
    log_msg "Diretorio /home nao encontrado"
    echo "Diretorio /home nao encontrado"
fi

# 4. Itens especaficos para cada tipo de servidor
case "$SERVERTYPE" in
    frontend)
        if [ -d /opt/frontend ]; then
            tar czf "$LOCAL_BACKUP_DIR/frontend_static_$(date +"%d-%m-%Y.%Hh%Mm").tgz" -C /opt/frontend .
            log_msg "Arquivos estaticos do frontend compactados"
            echo "Arquivos estaticos do frontend compactados"
        else
            log_msg "Diretorio /opt/frontend nao encontrado"
            echo "Diretorio /opt/frontend nao encontrado"
        fi
        ;;
    backend)
        if [ -d /opt/backend ]; then
            tar czf "$LOCAL_BACKUP_DIR/backend_files_$(date +"%d-%m-%Y.%Hh%Mm").tgz" -C /opt/backend .
            log_msg "Arquivos do backend compactados"
            echo "Arquivos do backend compactados"
        else
            log_msg "Diretorio /opt/backend nao encontrado"
            echo "Diretorio /opt/backend nao encontrado"
        fi
        if [ -d /etc/local.d ]; then
            tar czf "$LOCAL_BACKUP_DIR/locald_$(date +"%d-%m-%Y.%Hh%Mm").tgz" -C /etc/local.d .
            log_msg "Arquivos de /etc/local.d compactados"
            echo "Arquivos de /etc/local.d compactados"
        else
            log_msg "Diretorio /etc/local.d nao encontrado"
            echo "Diretorio /etc/local.d nao encontrado"
        fi
        if [ -d /root/SpringNotifica/Notifica/src/main/resources/static/image/download ]; then
            tar czf "$LOCAL_BACKUP_DIR/images_$(date +"%d-%m-%Y.%Hh%Mm").tgz" -C /root/SpringNotifica/Notifica/src/main/resources/static/image/download .
            log_msg "Arquivos de imagens compactados"
            echo "Arquivos de imagens compactados"
        else
            log_msg "Diretorio de imagens nao encontrado"
            echo "Diretorio de imagens nao encontrado"
        fi
        ;;
    db)
        DB_DUMP_FILE="$LOCAL_BACKUP_DIR/db_dump_$(date +"%d-%m-%Y.%Hh%Mm").tgz"
        if command -v mysqldump >/dev/null 2>&1; then
            mysqldump --all-databases | gzip > "$DB_DUMP_FILE"
            log_msg "Dump do banco de dados realizado com mysqldump"
            echo "Dump do banco de dados realizado com mysqldump"
        else
            log_msg "mysqldump nao disponivel. Verifique a instalacao"
            echo "mysqldump nao disponivel. Verifique a instalacao"
        fi
        ;;
esac

# 5. Gerar log resumo
{
    echo "========================"
    echo "Resumo do Backup - Data: $(date)"
    echo "Servidor: $SERVERTYPE"
    echo "========================"
    cat "$LOCAL_BACKUP_DIR/backup.log"
    echo
} >> "$LOCAL_BACKUP_DIR/backup_summary.log"

# 6. Compactar todo o backup em um unico arquivo
FINAL_ARCHIVE="/tmp/${SERVERTYPE}_backup_$(date +"%d-%m-%Y.%Hh%Mm").tgz"
tar czf "$FINAL_ARCHIVE" -C "$LOCAL_BACKUP_DIR" .

log_msg "Backup compactado em ${FINAL_ARCHIVE}"
echo "Backup compactado em ${FINAL_ARCHIVE}"

# 7. Enviar backup e logs para o servidor front via SSH (anexando os logs)
ssh backup_sys@"$REMOTE_FRONT" "mkdir -p '$REMOTE_DIR'"
scp "$FINAL_ARCHIVE" backup_sys@"$REMOTE_FRONT":"$REMOTE_DIR/"

# 8. Anexar logs no destino sem sobrescrever
ssh backup_sys@"$REMOTE_FRONT" "cat >> '$REMOTE_DIR/backup.log'" < "$LOCAL_BACKUP_DIR/backup.log"
ssh backup_sys@"$REMOTE_FRONT" "cat >> '$REMOTE_DIR/backup_summary.log'" < "$LOCAL_BACKUP_DIR/backup_summary.log"

log_msg "Backup e logs enviados para ${REMOTE_FRONT}:${REMOTE_DIR}"
echo "Backup e logs enviados para ${REMOTE_FRONT}:${REMOTE_DIR}"

# 9. Registrar o envio no log local
echo "$(date): Backup do ${SERVERTYPE} enviado para ${REMOTE_FRONT}:${REMOTE_DIR}" >> /var/log/backup_sys.log
echo "Backup do ${SERVERTYPE} enviado para ${REMOTE_FRONT}:${REMOTE_DIR}"

# 10. gerar resumo do backup para o console
echo "========================"
echo "Resumo do Backup - Data: $(date)"
echo "Servidor: $SERVERTYPE"
echo "========================"

# 11. Limpeza dos arquivos tempororios
rm -rf "$LOCAL_BACKUP_DIR" "$FINAL_ARCHIVE"

exit 0
