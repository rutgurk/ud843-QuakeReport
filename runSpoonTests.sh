echo "Running UI tests with Spoon!"
./gradlew spoon
echo "UI tests done, opening results."
open ./app/build/spoon-output/debug/index.html
