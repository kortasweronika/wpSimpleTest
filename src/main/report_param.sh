#!/usr/bin/env bash
set -euo pipefail

# --- Parametry ---
UUID="${1-}"
: "${UUID:?Użycie: bash report.sh <UUID> [BYE]  # np. bash report.sh 123e4567 'do zobaczenia'}"

BYE="${2-bye}"

REPORT_DIR_PATH="${REPORT_DIR:-"./Test Results/$UUID/Report"}"
mkdir -p "$REPORT_DIR_PATH"

echo "Hello world"

# --- GLOBALNY RAPORT PASS/FAIL (jedyna prawda w tym trybie) ---
# Uwaga: w kolumnie 'id' używamy UUID (executor zmapuje to na STEP_UID).
printf "%s|Parametry przekazane poprawnie|2025-10-01 17:06:42|2025-10-01 17:06:42|FAIL\n" \
  "$UUID" > "$REPORT_DIR_PATH/${UUID}_report.csv"

# --- OUTPUT (key|value) – opcjonalny, ale przydatny ---
# Teraz faktycznie używamy parametru BYE.
cat > "$REPORT_DIR_PATH/${UUID}_output.csv" <<EOF
uuid|$UUID
bye|$BYE
EOF

# --- Strumienie (opcjonalne „legacy”, executor też je przeniesie) ---
{
  echo "Hello world"
  echo "Bye param: $BYE"
} > "$REPORT_DIR_PATH/${UUID}_stdout.txt"

echo "Utworzono: "
echo "  $REPORT_DIR_PATH/${UUID}_report.csv"
echo "  $REPORT_DIR_PATH/${UUID}_output.csv"
echo "  $REPORT_DIR_PATH/${UUID}_stdout.txt"
