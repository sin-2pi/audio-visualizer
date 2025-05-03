# I'm leaving this script because Im assuming that Tyson used it for compiling
# some of the paths would have to be modified for its use again
# NOTE: the newest version of the project has dependencies managed by maven
# you will have to use maven to compile, I highly doubt this script will work

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
