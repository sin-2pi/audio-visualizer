#!/usr/bin/env bash
set -euo pipefail

# --- Configuration ----
PROJECT_ROOT="$(cd "$(dirname "$0")" && pwd)"
LIB_DIR="$PROJECT_ROOT/lib"
SRC_DIR="$PROJECT_ROOT/src/com/audioseperator"
BIN_DIR="$PROJECT_ROOT/bin"
JAVA_RELEASE=17

# --- Build steps ----
echo ">> Creating bin directory..."
mkdir -p "$BIN_DIR"

echo ">> Compiling Java sources..."
javac \
  --release $JAVA_RELEASE \
  -cp "$LIB_DIR/*" \
  -d "$BIN_DIR" \
  "$SRC_DIR"/*.java

echo ">> Compilation succeeded!"
echo
echo "To run your program, use:"
echo "  java -cp \"$BIN_DIR:$LIB_DIR/*\" com.audioseperator.Main"
