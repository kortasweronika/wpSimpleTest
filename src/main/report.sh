#!/usr/bin/env bash
set -euo pipefail

UUID="${1-}"

if [[ -z "$UUID" ]]; then
  echo "Użycie: bash report.sh <UUID>" >&2
  exit 1
fi

echo "Hello world"

FILENAME="id_${UUID}_report.csv"

# Zapisz dokładnie jedną linię w żądanym formacie i stałych datach
printf "id_%s|Parametry przekazane poprawnie|2025-10-01 17:06:42|2025-10-01 17:06:42|PASS\n" "$UUID" > "$FILENAME"

echo "Utworzono plik: $FILENAME"
