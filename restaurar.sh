#!/bin/sh
#uso: /opt/backup/restaurar/restaurar.sh

RESTORE_DIR="/opt/backup/restaurar/backup a restaurar"
LOG_ERRO="/opt/backup/restaurar/restaurar.log"

# Encontra o arquivo .tgz no diretorio de backup a restaurar
BACKUP_FILE=$(find "$RESTORE_DIR" -name "*.tgz" | head -n 1)

if [ ! -f "$BACKUP_FILE" ]; then
    echo "$(date) - Nenhum arquivo .tgz encontrado em '$RESTORE_DIR'" >> "$LOG_ERRO"
    echo "Erro: Nenhum arquivo de backup encontrado." >&2
    exit 1
fi

# Cria diretario temporario para extracao
TEMP_DIR="/tmp/restore_$(date +%s)"
mkdir -p "$TEMP_DIR"

# Extrai o backup para o diretario temporario
if ! tar xzf "$BACKUP_FILE" -C "$TEMP_DIR"; then
    echo "$(date) - Falha ao extrair $BACKUP_FILE" >> "$LOG_ERRO"
    echo "Erro: Falha na extra��o do arquivo de backup." >&2
    rm -rf "$TEMP_DIR"
    exit 1
fi

echo "Backup extraido para $TEMP_DIR"

# Identifica o tipo de servidor pelo nome do arquivo de backup
BACKUP_NAME=$(basename "$BACKUP_FILE")

if echo "$BACKUP_NAME" | grep -qi "frontend"; then
    TYPE="frontend"
elif echo "$BACKUP_NAME" | grep -qi "backend"; then
    TYPE="backend"
elif echo "$BACKUP_NAME" | grep -qi "db"; then
    TYPE="db"
else
    echo "$(date) - Tipo de backup nao identificado em '$BACKUP_NAME'" >> "$LOG_ERRO"
    echo "Erro: Tipo de backup nao identificado." >&2
    rm -rf "$TEMP_DIR"
    exit 1
fi

echo "Tipo de backup identificado: $TYPE"

# Restauracao conforme o tipo de servidor
case "$TYPE" in
    frontend)
        # Verifica se o arquivo frontend_static_*.tgz existe
        mkdir -p /opt/frontend
        if ! tar xzf "$TEMP_DIR/frontend_static_"*.tgz -C /opt/frontend; then
            echo "$(date) - Falha ao restaurar arquivos do frontend" >> "$LOG_ERRO"
            rm -rf "$TEMP_DIR"
            exit 1
        fi
        # Verifica se o arquivo home_*.tgz existe
        if ! tar xzf "$TEMP_DIR/home_"*.tgz -C /home; then
            echo "$(date) - Falha ao restaurar arquivos de /home" >> "$LOG_ERRO"
            rm -rf "$TEMP_DIR"
            exit 1
        fi

        cp "$TEMP_DIR/authorized_keys" /root/.ssh/ 2>/dev/null
        cp "$TEMP_DIR/hosts" /etc/ 2>/dev/null
        cp "$TEMP_DIR/hostname" /etc/hostname 2>/dev/null
        ;;
    backend)
        mkdir -p /opt/backend
        # Verifica se o arquivo backend_files_*.tgz existe
        if ! tar xzf "$TEMP_DIR/backend_files_"*.tgz -C /opt/backend; then
            echo "$(date) - Falha ao restaurar arquivos do backend" >> "$LOG_ERRO"
            rm -rf "$TEMP_DIR"
            exit 1
        fi
        # Verifica se o arquivo home_*.tgz existe
        if ! tar xzf "$TEMP_DIR/home_"*.tgz -C /home; then
            echo "$(date) - Falha ao restaurar arquivos de /home" >> "$LOG_ERRO"
            rm -rf "$TEMP_DIR"
            exit 1
        fi
        # Verifica se o arquivo locald_*.tgz existe
        if ! tar xzf "$TEMP_DIR/locald_"*.tgz -C /etc/local.d; then
            echo "$(date) - Falha ao restaurar arquivos de /etc/local.d" >> "$LOG_ERRO"
            rm -rf "$TEMP_DIR"
            exit 1
        fi
        # Verifica se o arquivo images_*.tgz existe
        if ! tar xzf "$TEMP_DIR/images_"*.tgz -C /root/SpringNotifica/Notifica/src/main/resources/static/image/download; then
            echo "$(date) - Falha ao restaurar arquivos de imagens" >> "$LOG_ERRO"
            rm -rf "$TEMP_DIR"
            exit 1
        fi
        cp "$TEMP_DIR/authorized_keys" /root/.ssh/ 2>/dev/null
        cp "$TEMP_DIR/hosts" /etc/ 2>/dev/null
        cp "$TEMP_DIR/hostname" /etc/hostname 2>/dev/null
        ;;
    db)
        # Verifica se o arquivo db_dump_*.tgz existe
        if [ -f "$TEMP_DIR/db_dump_"*.tgz ]; then
            if ! gunzip -c "$TEMP_DIR/db_dump_"*.tgz | mysql; then
                echo "$(date) - Falha ao restaurar o dump do banco de dados" >> "$LOG_ERRO"
                rm -rf "$TEMP_DIR"
                exit 1
            fi
        else
            echo "$(date) - Arquivo de dump do banco de dados n�o encontrado" >> "$LOG_ERRO"
            rm -rf "$TEMP_DIR"
            exit 1
        fi
        # Verifica se o arquivo home_*.tgz existe
        if ! tar xzf "$TEMP_DIR/home_"*.tgz -C /home; then
            echo "$(date) - Falha ao restaurar arquivos de /home" >> "$LOG_ERRO"
            rm -rf "$TEMP_DIR"
            exit 1
        fi
        cp "$TEMP_DIR/authorized_keys" /root/.ssh/ 2>/dev/null
        cp "$TEMP_DIR/hosts" /etc/ 2>/dev/null
        cp "$TEMP_DIR/hostname" /etc/hostname 2>/dev/null
        ;;
esac

# Limpa o diretirio temporario
rm -rf "$TEMP_DIR"

# Se chegou aqui, a restauracao foi concluida com sucesso
echo "Restauracao do backup do $TYPE concluida com sucesso."

# Opcional: registra a mensagem em um log (pode ser adicionado ao final do arquivo de log de restauracao)
echo "$(date): Restauracao do $TYPE concluida com sucesso." >> /opt/backup/restaurar/restaurar.log

# Reboot no servidor restaurado
echo "Reiniciando o sistema..."
sleep 3
reboot
