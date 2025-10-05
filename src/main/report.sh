#!/usr/bin/env bash
set -euo pipefail

UUID="${1-}"

if [[ -z "$UUID" ]]; then
  echo "Użycie: bash report.sh <UUID>" >&2
  exit 1
fi

# (opcjonalnie) prosta walidacja formatu UUID
if ! [[ "$UUID" =~ ^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$ ]]; then
  echo "Błędny format UUID: $UUID" >&2
  exit 2
fi

echo "Hello world"

FILENAME="${UUID}_report.csv"

# Zapisz dokładnie jedną linię w żądanym formacie i stałych datach
printf "%s|Parametry przekazane poprawnie|2025-10-01 17:06:42|2025-10-01 17:06:42|PASS\n" "$UUID" > "$FILENAME"

echo "Utworzono plik: $FILENAME"
