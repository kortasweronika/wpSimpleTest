#!/usr/bin/env bash
set -euo pipefail

usage() {
  cat <<'EOF'
Użycie: ./greet.sh <EXECUTION_UID> --name IMIĘ
       ./greet.sh <EXECUTION_UID> -n IMIĘ
Albo z parametrami name=value (kompatybilnie z runnerem).
EOF
}

# 1) WYMAGANY pierwszy argument: UID wykonania
if [[ $# -lt 1 || "$1" == -* ]]; then
  echo "Błąd: pierwszy argument musi być EXECUTION_UID "; usage; exit 2
fi
EXECUTION_UID="$1"
shift

NAME=""

# 2) Obsłuż zarówno opcje (-n/--name), jak i pary name=value
while [[ $# -gt 0 ]]; do
  case "$1" in
    -h|--help) usage; exit 0 ;;
    -n|--name)
      [[ $# -ge 2 ]] || { echo "Błąd: brak wartości po $1"; exit 1; }
      NAME="$2"; shift 2 ;;
    --name=*)  NAME="${1#*=}"; shift ;;
    name=*)    NAME="${1#*=}"; shift ;;   # kompatybilnie z naszym runnerem
    *=*)       shift ;;                   # inne key=value ignorujemy
    *)         echo "Ignoruję argument: $1"; shift ;;
  esac
done

[[ -n "$NAME" ]] || { echo "Błąd: parametr --name/-n jest wymagany."; usage; exit 1; }

when=$(date "+%Y-%m-%d %H:%M:%S")
echo "UID=$EXECUTION_UID  Cześć, $NAME! Jest $when."