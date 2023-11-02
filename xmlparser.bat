echo off
set command=%1
set file=%2
shift
shift
java -jar xmlparser-1.0-SNAPSHOT.jar %command% %file%