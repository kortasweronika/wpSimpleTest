#!/usr/bin/env bash
set -euo pipefail

UUID="${1-}"
: "${UUID:?Użycie: bash report.sh <UUID>}"

# Gdzie pisać (ENV przychodzi od executora). Fallback gdyby go nie było.
REPORT_DIR_PATH="${REPORT_DIR:-"./Test Results/$UUID/Report"}"
mkdir -p "$REPORT_DIR_PATH"

echo "Hello world"

# --- GLOBALNY RAPORT PASS/FAIL (jedyna prawda w tym trybie) ---
# Uwaga: w kolumnie 'id' używamy UUID (executor zmapuje to na STEP_UID).
printf "%s|Parametry przekazane poprawnie|2025-10-01 17:06:42|2025-10-01 17:06:42|FAIL\n" \
  "$UUID" > "$REPORT_DIR_PATH/${UUID}_report.csv"

# --- OUTPUT (key|value) – opcjonalny, ale przydatny ---
cat > "$REPORT_DIR_PATH/${UUID}_output.csv" <<EOF
EOF

# --- Strumienie (opcjonalne „legacy”, executor też je przeniesie) ---
echo "Hello world" > "$REPORT_DIR_PATH/${UUID}_stdout.txt"
# jeśli chcesz coś do stderr:
# echo "jakiś błąd" > "$REPORT_DIR_PATH/${UUID}_stderr.txt"

echo "Utworzono: "
echo "  $REPORT_DIR_PATH/${UUID}_report.csv"
echo "  $REPORT_DIR_PATH/${UUID}_output.csv"
echo "  $REPORT_DIR_PATH/${UUID}_stdout.txt"
