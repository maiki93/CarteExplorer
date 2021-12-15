
# extracted from eclipse IDE
#java -classpath /home/michael/projects/TestCarbon/Executable/target/classes:/home/michael/projects/TestCarbon/CarteAuxTresorMetier/target/classes:/home/michael/projects/TestCarbon/AppConsolePresentation/target/classes:/home/michael/projects/TestCarbon/FilePersistence/target/classes net.ddns.kimai.executable.CarteAuxTresors $1

# usage of the different librairies/ jar files
java -cp target/explorer-executable-1.0-SNAPSHOT.jar:../Metier/target/explorer-metier-1.0-SNAPSHOT.jar:../AppConsole/target/explorer-app-console-1.0-SNAPSHOT.jar:../InputProvider/target/explorer-input-provider-1.0-SNAPSHOT.jar net.ddns.kimai.explorer.executable.CarteAuxTresors $1

# do to, executable jar

