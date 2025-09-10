#!/usr/bin/env bash

set -euo pipefail

usage() {
  cat <<'EOF'
Użycie: ./greet.sh --name IMIĘ
       ./greet.sh -n IMIĘ
Opcje:
  -n, --name IMIĘ
  -h, --help
Możesz też użyć formy: --name=IMIĘ
EOF
}

NAME=""

while [[ $# -gt 0 ]]; do
  case "$1" in
    -h|--help)
      usage; exit 0
      ;;
    -n|--name)
      [[ $# -ge 2 ]] || { echo "Błąd: brak wartości po $1"; exit 1; }
      NAME="$2"; shift 2
      ;;
    --name=*)
      NAME="${1#*=}"; shift
      ;;
    *)
      echo "Nieznana opcja: $1"
      usage; exit 1
      ;;
  esac
done

# Walidacja
if [[ -z "$NAME" ]]; then
  echo "Błąd: parametr --name/-n jest wymagany."
  usage
  exit 1
fi

when=$(date "+%Y-%m-%d %H:%M:%S")
echo "Cześć, $NAME! Jest $when."
