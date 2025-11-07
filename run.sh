#!/bin/bash
# Auto-compile and run the first Java file found in the project

# Go to your Java project root
cd /Users/happy/Desktop/LLD_Java || exit

# Find the first .java file in the project (or customize the path)
JAVA_FILE=$(find . -name "*.java" | head -n 1)

if [ -z "$JAVA_FILE" ]; then
  echo "❌ No Java file found!"
  exit 1
fi

echo "🧩 Found Java file: $JAVA_FILE"

# Compile the Java file
javac "$JAVA_FILE" || { echo "❌ Compilation failed"; exit 1; }

# Remove './' and '.java' to get the class name
CLASS_NAME=${JAVA_FILE#./}
CLASS_NAME=${CLASS_NAME%.java}
CLASS_NAME=${CLASS_NAME//\//.}

echo "🚀 Running: $CLASS_NAME"

# Run the compiled class
java "$CLASS_NAME"
