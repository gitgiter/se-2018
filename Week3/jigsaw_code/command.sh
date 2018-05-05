mkdir -p build
javac -encoding UTF-8 -d build src/jigsaw/*.java src/solution/Solution.java src/Runners/*.java
java -cp build Runners.RunnerDemo
# javac -encoding UTF-8 -d build -cp lib/jigsaw.jar src/solution/Solution.java
# javac -encoding UTF-8 -d build -cp lib/jigsaw.jar:build src/Runners/*.java
# java -cp lib/jigsaw.jar:build Runners.RunnerDemo