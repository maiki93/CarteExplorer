
# extracted from eclipse IDE
#java -classpath /home/michael/projects/TestCarbon/Executable/target/classes:/home/michael/projects/TestCarbon/CarteAuxTresorMetier/target/classes:/home/michael/projects/TestCarbon/AppConsolePresentation/target/classes:/home/michael/projects/TestCarbon/FilePersistence/target/classes net.ddns.kimai.executable.CarteAuxTresors $1

# usage of the different librairies/ jar files
java -cp target/Executable-1.0-SNAPSHOT.jar:../CarteAuxTresorMetier/target/CarteAuxTresorMetier-1.0-SNAPSHOT.jar:../AppConsolePresentation/target/AppConsolePresentation-1.0-SNAPSHOT.jar:../InputConfigurationProvider/target/InputConfigurationProvider-1.0-SNAPSHOT.jar net.ddns.kimai.executable.CarteAuxTresors $1

# do to, executable jar

