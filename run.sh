javac -d "classes" "main/TicTacToeGame.java"

javac -d "classes" -classpath "classes" "main/Window.java"
javac -d "classes" -classpath "classes" "main/GameObject.java"
javac -d "classes" -classpath "classes" "main/Handler.java"

javac -d "classes" -classpath "classes" "main/MainMenu.java"

javac -d "classes" -classpath "classes" "main/GameInterface.java"
javac -d "classes" -classpath "classes" "main/EasyComputer.java"
javac -d "classes" -classpath "classes" "main/MediumComputer.java"

java -classpath "classes" main.TicTacToeGame