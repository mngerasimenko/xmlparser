# xmlparser

Для запуска приложения необходимо:
  собрать проtкт: mvn clean package
  запустить приложение: 
    можно использовать bat файл для запуска: xmlparser.bat sync test.xml
    или собрать docker контейнер с помощью dockerfile
      в терминале запущенного образа ввести команды, например:        
        bash xmlparser.sh sync test.xml
        bash xmlparser.sh export test2.xml
